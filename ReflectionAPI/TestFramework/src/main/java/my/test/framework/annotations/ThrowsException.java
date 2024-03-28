package my.test.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Тестовый метод обязан выбросить указанное исключение.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ThrowsException{
    /**
     * Ожидаемое исключение
     */
    Class<? extends Exception> exception();
}
