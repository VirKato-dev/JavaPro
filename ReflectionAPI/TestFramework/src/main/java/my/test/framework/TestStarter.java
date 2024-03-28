package my.test.framework;

import my.test.framework.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Запускалка тестов
 */
public class TestStarter {

    // Цикл пока имеется метод @Test
    // 1. выполнить все методы @Before
    // 2. выполнить метод @Test
    // 2.1. результат теста сохранить в статистике (название метода - результат)
    // 3. выполнить все методы @After
    // 4. исключения не прерывают выполнение цикла
    // Конец цикла
    // 5. Вывод результата.

    public static boolean execute(Class<?> testClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Object object = testClass.getConstructor().newInstance();

        final Map<Method, Throwable> results = new HashMap<>();
        final List<Method> testMethods = getTestMethods(testClass);
        final List<Method> beforeMethods = getWrapMethods(testMethods, Before.class);
        final List<Method> afterMethods = getWrapMethods(testMethods, After.class);
        final List<Method> suiteMethods = getSuiteMethods(testClass);

        getSuiteMethod(suiteMethods, BeforeSuite.class).ifPresent(ms -> {
            try {
                ms.invoke(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
            }
        });

        getTestOnlyMethods(testMethods).forEach(m -> {
            results.put(m, null);
            try {

                execTestMethods(object, m, beforeMethods, results);
                try {
                    m.invoke(object);
                } catch (InvocationTargetException e) {
                    Throwable t = results.get(m);
                    if (m.isAnnotationPresent(ThrowsException.class)) {
                        if (e.getTargetException().getClass() != m.getAnnotation(ThrowsException.class).exception()) {
                            t = e.getTargetException();
                        }
                    } else {
                        t = e.getTargetException();
                    }
                    results.put(m, t);
                }
                execTestMethods(object, m, afterMethods, results);

            } catch (Exception e) {
                results.put(m, e);
            }
        });

        getSuiteMethod(suiteMethods, AfterSuite.class).ifPresent(ms -> {
            try {
                ms.invoke(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
            }
        });

        int testOk = 0;
        int testFail = 0;
        for (Map.Entry<Method, Throwable> entry : results.entrySet()) {
            System.out.printf("%s\t: %s\n", entry.getKey().getName(), entry.getValue() != null ? entry.getValue().getMessage() : "OK");
            if (entry.getValue() == null) ++testOk;
            else ++testFail;
        }
        System.out.printf("Total: %d\tOk: %d\t Fail: %d\n", testOk + testFail, testOk, testFail);
        return testFail == 0;
    }

    /**
     * Получить список всех тестовых методов на основании аннотации Test
     *
     * @param testClass тестовый класс
     * @return список всех методов Test
     */
    private static List<Method> getTestMethods(Class<?> testClass) {
        boolean allMethodsAreTest = testClass.isAnnotationPresent(Test.class);
        if (testClass.isAnnotationPresent(Disabled.class)) {
            return Collections.emptyList();
        }
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> !m.isAnnotationPresent(Disabled.class))
                .filter(m -> !(m.isAnnotationPresent(BeforeSuite.class) || m.isAnnotationPresent(AfterSuite.class)))
                .filter(m -> (m.getModifiers() & (Modifier.PRIVATE | Modifier.STATIC)) == 0)
                .filter(m -> allMethodsAreTest || m.isAnnotationPresent(Test.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить список тестовых методов не имеющих аннотации Before/After
     *
     * @param testMethods все найденные тестовые методы
     * @return список public методов определённых как тесты
     */
    private static List<Method> getTestOnlyMethods(List<Method> testMethods) {
        return testMethods.stream()
                .filter(m -> !m.isAnnotationPresent(Before.class) && !m.isAnnotationPresent(After.class))
                .sorted((o1, o2) -> {
                    int order1 = o1.isAnnotationPresent(Test.class) ? o1.getAnnotation(Test.class).priority() : 10;
                    int order2 = o2.isAnnotationPresent(Test.class) ? o2.getAnnotation(Test.class).priority() : 10;
                    return order2 - order1;
                })
                .collect(Collectors.toList());
    }

    /**
     * Получить список "обёрточных" тестовых методов Before/After
     *
     * @param testMethods    все доступные тестовые методы тестового класса
     * @param wrapAnnotation конкретный тип "обёрточного" тестового метода
     * @return список методов Before либо After
     */
    private static List<Method> getWrapMethods(List<Method> testMethods, Class<? extends Annotation> wrapAnnotation) {
        return testMethods.stream()
                .filter(m -> m.isAnnotationPresent(wrapAnnotation))
                .collect(Collectors.toList());
    }

    /**
     * Получить метод типа BeforeSuite / AfterSuite
     *
     * @param testMethods
     * @param suiteAnnotation
     * @return
     */
    private static Optional<Method> getSuiteMethod(List<Method> testMethods, Class<? extends Annotation> suiteAnnotation) {
        return testMethods.stream()
                .filter(m -> m.isAnnotationPresent(suiteAnnotation))
                .findFirst();
    }

    /**
     * Только методы типа Suite
     *
     * @param testClass тестовый класс
     * @return методы BeforeSuite и AfterSuite
     */
    private static List<Method> getSuiteMethods(Class<?> testClass) {
        if (testClass.isAnnotationPresent(Disabled.class)) {
            return Collections.emptyList();
        }
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> !m.isAnnotationPresent(Disabled.class))
                .filter(m -> m.isAnnotationPresent(BeforeSuite.class) || m.isAnnotationPresent(AfterSuite.class))
                .collect(Collectors.toList());
    }

    /**
     * Только один метод группы Before или After выполнится
     *
     * @param testClass   тестовый класс
     * @param method      оборачиваемый тестовый метод
     * @param testMethods группа тестовый методов Before/After
     * @param results     хранилище результатов прохождения теста
     */
    private static void execTestMethods(Object testClass, Method method, List<Method> testMethods, Map<Method, Throwable> results) {
        testMethods.stream().findFirst().ifPresent(mb -> {
            try {
                mb.invoke(testClass);
            } catch (IllegalAccessException e) {
                results.put(method, e);
            } catch (InvocationTargetException e) {
                results.put(method, e.getTargetException());
            }
        });
    }
}
