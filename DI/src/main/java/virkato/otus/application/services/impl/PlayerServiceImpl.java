package virkato.otus.application.services.impl;

import org.springframework.stereotype.Component;
import virkato.otus.application.model.Player;
import virkato.otus.application.services.IOService;
import virkato.otus.application.services.PlayerService;

@Component
public class PlayerServiceImpl implements PlayerService {

    private IOService ioService;

    public PlayerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Player getPlayer() {
        ioService.out("Представьтесь пожалуйста");
        String playerName = ioService.readLn("Введите имя: ");
        return new Player(playerName);
    }
}
