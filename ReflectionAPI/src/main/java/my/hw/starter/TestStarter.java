package my.hw.starter;

import my.hw.annotations.After;
import my.hw.annotations.Before;
import my.hw.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        getTestOnlyMethods(testMethods).forEach(m -> {
            results.put(m, null);
            try {

                execTestMethods(object, m, getWrapMethods(testMethods, Before.class), results);
                try {
                    m.invoke(object);
                } catch (InvocationTargetException e) {
                    results.put(m, e.getTargetException());
                }
                execTestMethods(object, m, getWrapMethods(testMethods, After.class), results);

            } catch (Exception e) {
                results.put(m, e);
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
     * @param testClass тестовый класс
     * @return список всех методов Test
     */
    private static List<Method> getTestMethods(Class<?> testClass) {
        boolean allMethodsAreTest = testClass.isAnnotationPresent(Test.class);
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> (m.getModifiers() & Modifier.PUBLIC) > 0 &&
                        allMethodsAreTest ||
                        m.isAnnotationPresent(Test.class)
                )
                .collect(Collectors.toList());
    }

    /**
     * Получить список тестовых методов не имеющих аннотации Before/After
     * @param testMethods все найденные тестовые методы
     * @return список методов имеющих только аннотацию Test
     */
    private static List<Method> getTestOnlyMethods(List<Method> testMethods) {
        return testMethods.stream()
                .filter(m -> !m.isAnnotationPresent(Before.class) && !m.isAnnotationPresent(After.class))
                .sorted((o1, o2) -> {
                    int order1 = o1.isAnnotationPresent(Test.class) ? o1.getAnnotation(Test.class).order() : 10;
                    int order2 = o2.isAnnotationPresent(Test.class) ? o2.getAnnotation(Test.class).order() : 10;
                    return order1 - order2 < 0 ? -1 : order1 == order2 ? 0 : 1;
                })
                .collect(Collectors.toList());
    }

    /**
     * Получить список "обёрточных" тестовых методов Before/After
     * @param testMethods все доступные тестовые методы тестового класса
     * @param repeatableAnnotation конкретный тип "обёрточного" тестового метода
     * @return список методов Before либо After
     */
    private static List<Method> getWrapMethods(List<Method> testMethods, Class<? extends Annotation> repeatableAnnotation) {
        return testMethods.stream()
                .filter(m -> m.isAnnotationPresent(repeatableAnnotation))
                .collect(Collectors.toList());
    }

    /**
     * Только один метод группы Before или After выполнится
     * @param testClass тестовый класс
     * @param method оборачиваемый тестовый метод
     * @param testMethods группа тестовый методов Before/After
     * @param results хранилище результатов прохождения теста
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
