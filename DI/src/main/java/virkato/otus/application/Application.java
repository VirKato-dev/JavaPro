package virkato.otus.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import virkato.otus.application.services.GameProcessor;

@ComponentScan
public class Application {
    public static void main(String[] args) {
//        EquationPreparer equationPreparer = new EquationPreparerImpl();
//        IOService ioService = new IOServiceStreams(System.out, System.in);
//        PlayerService playerService = new PlayerServiceImpl(ioService);
//        GameProcessor gameProcessor = new GameProcessorImpl(ioService, equationPreparer, playerService);

        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        GameProcessor gameProcessor = context.getBean(GameProcessor.class);

        gameProcessor.startGame();
    }
}
