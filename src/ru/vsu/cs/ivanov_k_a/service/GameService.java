package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class GameService {
    private Map<FigureType, IFigureService> figureServiceMap = new HashMap<>();

    public GameService(Map<FigureType, IFigureService> figureServiceMap) {
        this.figureServiceMap = figureServiceMap;
    }

    public Game createGame() {
    }

    public Game printGame(Game game) {
    }

    public Game processGame(Game game, Step step) {
    }
}
