package ru.vsu.cs.ivanov_k_a.model;

import java.util.*;

public class Game {
    private Map<Figure, Cell> piece2CellMap = new LinkedHashMap<>();
    private Map<Cell, Figure> cell2PieceMap = new HashMap();
    private Map<Player, Set<Figure>> player2PieceMap = new HashMap();
    private Map<Figure, Player> piece2PlayerMap = new LinkedHashMap<>();
    private Map<String, Cell> cellsMap;
    private List<Step> steps = new ArrayList<>();

    public Map<Figure, Cell> getPiece2CellMap() {
        return piece2CellMap;
    }

    public void setPiece2CellMap(Map<Figure, Cell> piece2CellMap) {
        this.piece2CellMap = piece2CellMap;
    }

    public Map<Cell, Figure> getCell2PieceMap() {
        return cell2PieceMap;
    }

    public void setCell2PieceMap(Map<Cell, Figure> cell2PieceMap) {
        this.cell2PieceMap = cell2PieceMap;
    }

    public Map<Player, Set<Figure>> getPlayer2PieceMap() {
        return player2PieceMap;
    }

    public void setPlayer2PieceMap(Map<Player, Set<Figure>> player2PieceMap) {
        this.player2PieceMap = player2PieceMap;
    }

    public Map<Figure, Player> getPiece2PlayerMap() {
        return piece2PlayerMap;
    }

    public void setPiece2PlayerMap(Map<Figure, Player> piece2PlayerMap) {
        this.piece2PlayerMap = piece2PlayerMap;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
