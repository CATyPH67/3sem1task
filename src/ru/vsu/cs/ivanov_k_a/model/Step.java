package ru.vsu.cs.ivanov_k_a.model;

public class Step {
    private Player player;
    private Cell source;
    private Cell target;
    private Figure figure;
    private Figure killedFigure;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getSource() {
        return source;
    }

    public void setSource(Cell source) {
        this.source = source;
    }

    public Cell getTarget() {
        return target;
    }

    public void setTarget(Cell target) {
        this.target = target;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Figure getKilledFigure() {
        return killedFigure;
    }

    public void setKilledFigure(Figure killedFigure) {
        this.killedFigure = killedFigure;
    }
}
