package my.hw.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * При использовании над тестовым классом распространяет своё действие на все public методы
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Test {
    /**
     * Чем меньше число, тем выше приоритет выполнения
     * @return приоритет выполнения
     */
    int order() default 10;
}
