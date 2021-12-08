package ru.vsu.cs.ivanov_k_a.model;

import java.util.*;

public class Game {
    private Map<Piece, Cell> piece2CellMap;
    private Map<Cell, Piece> cell2PieceMap;
    private Map<Player, Set<Piece>> player2PieceMap;
    private Map<Piece, Player> piece2PlayerMap;
    private Map<String, Cell> number2CellMap;
    private Map<Cell, String> cell2NumberMap;
    private List<Step> steps = new ArrayList<>();

    public Game(Map<Piece, Cell> piece2CellMap, Map<Cell, Piece> cell2PieceMap,
                Map<Player, Set<Piece>> player2PieceMap, Map<Piece, Player> piece2PlayerMap,
                Map<String, Cell> number2CellMap, Map<Cell, String> cell2NumberMap) {
        this.piece2CellMap = piece2CellMap;
        this.cell2PieceMap = cell2PieceMap;
        this.player2PieceMap = player2PieceMap;
        this.piece2PlayerMap = piece2PlayerMap;
        this.number2CellMap = number2CellMap;
        this.cell2NumberMap = cell2NumberMap;
    }

    public Map<Piece, Cell> getPiece2CellMap() {
        return piece2CellMap;
    }

    public void setPiece2CellMap(Map<Piece, Cell> piece2CellMap) {
        this.piece2CellMap = piece2CellMap;
    }

    public Map<Cell, Piece> getCell2PieceMap() {
        return cell2PieceMap;
    }

    public void setCell2PieceMap(Map<Cell, Piece> cell2PieceMap) {
        this.cell2PieceMap = cell2PieceMap;
    }

    public Map<Player, Set<Piece>> getPlayer2PieceMap() {
        return player2PieceMap;
    }

    public void setPlayer2PieceMap(Map<Player, Set<Piece>> player2PieceMap) {
        this.player2PieceMap = player2PieceMap;
    }

    public Map<Piece, Player> getPiece2PlayerMap() {
        return piece2PlayerMap;
    }

    public void setPiece2PlayerMap(Map<Piece, Player> piece2PlayerMap) {
        this.piece2PlayerMap = piece2PlayerMap;
    }

    public Map<String, Cell> getNumber2CellMap() {
        return number2CellMap;
    }

    public void setNumber2CellMap(Map<String, Cell> number2CellMap) {
        this.number2CellMap = number2CellMap;
    }

    public Map<Cell, String> getCell2NumberMap() {
        return cell2NumberMap;
    }

    public void setCell2NumberMap(Map<Cell, String> cell2NumberMap) {
        this.cell2NumberMap = cell2NumberMap;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
