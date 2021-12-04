package ru.vsu.cs.ivanov_k_a.model;

public class Step {
    private Player player;
    private Cell startCell;
    private Cell endCell;
    private Figure piece;
    private Figure killedPiece;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
    }

    public Figure getPiece() {
        return piece;
    }

    public void setPiece(Figure piece) {
        this.piece = piece;
    }

    public Figure getKilledPiece() {
        return killedPiece;
    }

    public void setKilledPiece(Figure killedPiece) {
        this.killedPiece = killedPiece;
    }
}
