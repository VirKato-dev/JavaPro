package hw.annotation;

import hw.exception.MalformedEntityClassException;
import hw.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionUtils {

    private final static Map<Class<?>, Field[]> cashedFields = new HashMap<>();


    /**
     * Сконструировать начало (или весь) запроса
     *
     * @param entity сущность
     * @param prefix слева от перечисления колонок таблицы
     * @param suffix справа от перечисления колонок таблицы
     * @return перечисление названий полей (колонок)
     */
    public static StringJoiner beginOfQueryWithFields(Object entity, Boolean withId, String prefix, String suffix) {
        StringJoiner sj = new StringJoiner(",", prefix, suffix);
        getAnnotatedFields(entity.getClass(), RepositoryField.class).forEach(f -> {
                    if (!f.isAnnotationPresent(RepositoryIdField.class) || withId) {
                        sj.add("`" + getFieldName(f) + "`");
                    }
                }
        );
        return sj;
    }


    /**
     * Сконструировать окончание запроса
     *
     * @param entity сущность
     * @param prefix слева от перечисления значений
     * @param suffix справа от перечисления значений
     * @return перечисление значений
     */
    public static StringJoiner endOfQueryWithFieldValues(Object entity, Boolean withId, String prefix, String suffix) {
        StringJoiner sj = new StringJoiner(",", prefix, suffix);
        getAnnotatedFields(entity.getClass(), RepositoryField.class).forEach(f -> {
                    if (!f.isAnnotationPresent(RepositoryIdField.class) || withId) {
                        try {
                            String wrapper = "";
                            if (f.getType() == String.class) {
                                wrapper = "'";
                            }
                            Method method = entity.getClass().getDeclaredMethod("get" + StringUtils.capitalizeFirstLetter(getFieldName(f)));
                            sj.add(wrapper + method.invoke(entity) + wrapper);
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new MalformedEntityClassException("The problem with getter method.", e);
                        }
                    }
                }
        );
        return sj;
    }


    /**
     * Сущность определена корректно?
     *
     * @param entity сущность таблицы
     * @return true - корректно
     */
    public static boolean isEntityCorrect(Object entity) {
        if (!entity.getClass().isAnnotationPresent(RepositoryTable.class)) {
            throw new MalformedEntityClassException("RepositoryTable annotation not present.");
        }

        long numberId = Arrays.stream(getFields(entity.getClass()))
                .filter(f -> f.isAnnotationPresent(RepositoryIdField.class))
                .count();
        if (numberId != 1) {
            throw new MalformedEntityClassException("RepositoryId annotation not present.");
        }
        return true;
    }


    /**
     * Получить поля сущности
     *
     * @param entity сущность
     * @return все поля
     */
    public static Field[] getFields(Class<?> entity) {
        if (cashedFields.containsKey(entity)) {
            return cashedFields.get(entity);
        }
        Field[] fields = entity.getDeclaredFields();
        cashedFields.put(entity, fields);
        return fields;
    }


    /**
     * Получить список аннотированных полей сущности (колонок)
     *
     * @param entityClass     класс сущности
     * @param annotationClass требуемая аннотация над полем
     * @return только аннотированные поля
     */
    public static List<Field> getAnnotatedFields(Class<?> entityClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(getFields(entityClass))
                .filter(f -> f.isAnnotationPresent(annotationClass))
                .toList();
    }


    /**
     * Получить имя таблицы из сущности
     *
     * @param entityClass класс сущности
     * @return имя таблицы
     */
    public static String getTableName(Class<?> entityClass) {
        return entityClass.getAnnotation(RepositoryTable.class).name().isEmpty()
                ? entityClass.getName().toLowerCase()
                : entityClass.getAnnotation(RepositoryTable.class).name();
    }


    /**
     * Получить название поля сущности (колонки)
     *
     * @param field аннотированное поле
     * @return название в соответствии с аннотацией
     */
    public static String getFieldName(Field field) {
        return field.getAnnotation(RepositoryField.class).name().isEmpty()
                ? field.getName().toLowerCase()
                : field.getAnnotation(RepositoryField.class).name();
    }
}
