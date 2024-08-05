package virkato.otus.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import virkato.otus.application.services.EquationPreparer;
import virkato.otus.application.services.GameProcessor;
import virkato.otus.application.services.impl.GameProcessorImpl;
import virkato.otus.application.services.IOService;
import virkato.otus.application.services.impl.IOServiceStreams;
import virkato.otus.application.services.PlayerService;

@Configuration
public class AppConfig {

    @Bean
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
