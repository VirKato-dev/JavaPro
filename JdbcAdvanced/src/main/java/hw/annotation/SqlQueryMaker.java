package hw.annotation;

import java.util.Optional;
import java.util.StringJoiner;

public class SqlQueryMaker {

    public static <T> String queryCreateTable(T entityClass) {
        ReflectionUtils.isEntityCorrect(entityClass);

        String tableName = ReflectionUtils.getTableName(entityClass.getClass());

        String prefix = "CREATE TABLE `" + tableName + "` (";
        String suffix = ", PRIMARY KEY (`id`) AUTO_INCREMENT);";

        StringJoiner sjFields = ReflectionUtils.beginOfQueryWithFields(entityClass, true, prefix, suffix);

        StringJoiner sjValues = ReflectionUtils.endOfQueryWithFieldValues(entityClass, true, "", "");

        //todo доделать запрос
        // не хватает определения типов

        return sjFields.toString();
    }

    // CRUD

    public static <T> String queryInsert(T entityClass) {
        ReflectionUtils.isEntityCorrect(entityClass);

        String tableName = ReflectionUtils.getTableName(entityClass.getClass());

        String prefix = "INSERT INTO `" + tableName + "` (";
        String suffix = ") ";
        StringJoiner sjFields = ReflectionUtils.beginOfQueryWithFields(entityClass, false, prefix, suffix);

        prefix = "VALUES (";
        suffix = ");";
        StringJoiner sjValues = ReflectionUtils.endOfQueryWithFieldValues(entityClass, false, prefix, suffix);

        //fixme для PreparedStatement вставлять вместо значений символы '?'
        return sjFields.toString() + sjValues.toString();
    }

    public static <T> Optional<T> queryFindById(T entityClass, long id) {
        ReflectionUtils.isEntityCorrect(entityClass.getClass());

        String tableName = ReflectionUtils.getTableName(entityClass.getClass());

        String prefix = "SELECT * FROM `" + tableName + "` ";
        String suffix = "WHERE `id` = ?";
        StringJoiner sjFields = ReflectionUtils.beginOfQueryWithFields(entityClass, true, prefix, suffix);
        return Optional.empty();
    }


}
