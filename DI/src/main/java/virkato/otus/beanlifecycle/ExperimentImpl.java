package virkato.otus.beanlifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
public class ExperimentImpl implements InitializingBean, DisposableBean,
        BeanNameAware, BeanFactoryAware, ApplicationContextAware, Experiment {

    @PostConstruct
    public void postConstructInit() {
        System.out.println("Вызов postConstructInit");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Вызов afterPropertiesSet");
    }

    public void beanInitMethod() {
        System.out.println("Вызов beanInitMethod");

    }

    @Override
    public void destroy() {
        System.out.println("Вызов afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroyInit() {
        System.out.println("Вызов preDestroyInit");
    }

    public void beanDestroyMethod() {
        System.out.println("Вызов beanDestroyMethod");
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void printMessage() {
        System.out.println("Message");
    }
}
