package virkato.otus.hw10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import virkato.otus.hw10.cli.App;

@ComponentScan({"virkato.otus.hw10"})
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        App app = context.getBean(App.class);
        app.run();
    }
}
