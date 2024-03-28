package my.test.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * При использовании над тестовым классом распространяет своё действие на все public методы.
 * Не добавлять на private и static методы.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Test {
    /**
     * Чем больше число, тем выше приоритет выполнения
     * @return приоритет выполнения
     */
    int priority() default  10;
}
