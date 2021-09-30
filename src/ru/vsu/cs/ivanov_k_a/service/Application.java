package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.Game;

public class Application {

    public static void main(String[] args) {
        GameService gameService = new GameService();
        Game game = gameService.createGame();
        gameService.printGame(game);
        gameService.processGame(game, );
        gameService.printGame(game);
    }
}
