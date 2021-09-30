package ru.vsu.cs.ivanov_k_a.model;

import java.util.*;

public class Game {
    private Map<Figure, Cell> figureToCellMap = new LinkedHashMap<>();
    private Map<Cell, Figure> cellToFigureMap = new HashMap();
    private Map<Player, Set<Figure>> PlayerToFigureMap = new HashMap();
    private Map<Figure, Player> figureToPlayerMap = new LinkedHashMap<>();
    private List<Step> steps = new ArrayList<>();
}
