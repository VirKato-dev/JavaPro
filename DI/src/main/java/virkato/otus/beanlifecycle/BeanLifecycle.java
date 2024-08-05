package virkato.otus.beanlifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class BeanLifecycle {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanLifecycle.class);
        Experiment experiment = context.getBean(Experiment.class);
        experiment.printMessage();
    }
}
