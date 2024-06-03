package virkato.otus.beanlifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExperimentBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (aClass.equals(ExperimentImpl.class)) {
            System.out.println("Вызов postProcessBeforeInitialization: " + aClass.getName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (aClass.equals(ExperimentImpl.class)) {
            System.out.println("Вызов postProcessAfterInitialization: " + aClass.getName());
        }
        return bean;
    }
}
