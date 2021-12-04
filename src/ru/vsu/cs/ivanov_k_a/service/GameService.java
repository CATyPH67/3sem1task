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

    private Map<String, Cell> createBoard() {
        List<List<Cell>> rows = new ArrayList<>();

        List<CellColor> cellColors = Arrays.asList(CellColor.BLACK, CellColor.WHITE);

        Cell prevCell;
        Cell currCell;
        List<Cell> prevRow = null;
        for (int i = 0; i < 12; i++) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                currCell = new Cell(cellColors.get((i + j) % 2));
                if (prevCell != null) {
                    currCell.getNeighbors().put(Direction.WEST, prevCell);
                    prevCell.getNeighbors().put(Direction.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (prevRow != null) {
                    for (int k = 0; k < currRow.size(); k++) {
                        Cell currRowCell = currRow.get(k);
                        Cell prevRowCell = prevRow.get(k);
                        currRowCell.getNeighbors().put(Direction.NORTH, prevRowCell);
                        prevRowCell.getNeighbors().put(Direction.SOUTH, currRowCell);
                    }
                }
            }
            rows.add(currRow);
            prevRow = currRow;
        }
        return null;
    }

    private Map<String, Cell> createSmallBoard(Cell nCellA3, Cell nCellA4, Cell nCellA5, int direction) {
        Map<String, Cell> cellsMap = new LinkedHashMap<>();
        List<CellColor> cellColors = Arrays.asList(nCellA3.getColor(), nCellA4.getColor());
        Cell prevCell;
        Cell currCell;
        List<Cell> prevRow = null;
        for (int i = 4; i > 0; i--) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            char ch = 'a';
            for (int j = 1; j < 5; j++, ch++) {
                currCell = new Cell(cellColors.get((i + j + 1) % 2));
                cellsMap.put(Character.toString(ch) + direction + i, currCell);
                if (prevCell != null) {
                    currCell.getNeighbors().put(Direction.WEST, prevCell);
                    prevCell.getNeighbors().put(Direction.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (prevRow != null) {
                    for (int k = 0; k < currRow.size(); k++) {
                        Cell currRowCell = currRow.get(k);
                        Cell prevRowCellNorth = prevRow.get(k);
                        currRowCell.getNeighbors().put(Direction.NORTH, prevRowCellNorth);
                        prevRowCellNorth.getNeighbors().put(Direction.SOUTH, currRowCell);
                        if (k > 0) {
                            Cell prevRowCellNorthWest = prevRow.get(k - 1);
                            currRowCell.getNeighbors().put(Direction.NORTH_WEST, prevRowCellNorthWest);
                            prevRowCellNorthWest.getNeighbors().put(Direction.SOUTH_EAST, currRowCell);
                        }
                        if (k < currRow.size() - 1) {
                            Cell prevRowCellNorthEast = prevRow.get(k + 1);
                            currRowCell.getNeighbors().put(Direction.NORTH_EAST, prevRowCellNorthEast);
                            prevRowCellNorthEast.getNeighbors().put(Direction.SOUTH_WEST, currRowCell);
                        }
                    }
                }
            }
            prevRow = currRow;
        }
        return cellsMap;
    }

    public Game printGame(Game game) {
    }

    public Game processGame(Game game, Step step) {
    }
}
