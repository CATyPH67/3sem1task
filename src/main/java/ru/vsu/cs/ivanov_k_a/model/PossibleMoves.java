package ru.vsu.cs.ivanov_k_a.model;

import java.util.List;

public class PossibleMoves {
    private List<Cell> cellsMoves;
    private List<Cell> cellsAttackMoves;

    public PossibleMoves(List<Cell> possibleMoves, List<Cell> possibleAttackMoves) {
        this.cellsMoves = possibleMoves;
        this.cellsAttackMoves = possibleAttackMoves;
    }

    public List<Cell> getCellsMoves() {
        return cellsMoves;
    }

    public void setCellsMoves(List<Cell> possibleMoves) {
        this.cellsMoves = possibleMoves;
    }

    public List<Cell> getCellsAttackMoves() {
        return cellsAttackMoves;
    }

    public void setCellsAttackMoves(List<Cell> cellsAttackMoves) {
        this.cellsAttackMoves = cellsAttackMoves;
    }
}
