package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class KnightService implements IPieceService{
    @Override
    public PossibleMoves getPossibleMoves(Game game, Piece piece) {
        List<Cell> cellsMoves = new ArrayList<>();
        List<Cell> cellsAttackMoves = new ArrayList<>();
        List<Direction> moveDirections = Arrays.asList(
                Direction.WEST, Direction.NORTH,
                Direction.EAST, Direction.SOUTH,
                Direction.WEST, Direction.NORTH
        );
        Map<Piece, Cell> piece2CellMap = game.getPiece2CellMap();
        for (int i = 1; i <= 4; i++) {
            Direction moveDirection = moveDirections.get(i);
            Cell moveCell = piece2CellMap.get(piece);
            for (int j = 0; j < 2; j++) {
                if (moveCell != null) {
                    moveCell = moveCell.getNeighbors().get(moveDirection);
                }
            }
            for (int j = -1; j < 2; j += 2) {
                if (moveCell != null) {
                    Cell sideMoveCell = moveCell.getNeighbors().get(moveDirections.get(i + j));
                    if (sideMoveCell != null) {
                        Piece checkPiece = game.getCell2PieceMap().get(sideMoveCell);
                        if (checkPiece == null) {
                            cellsMoves.add(sideMoveCell);
                        } else {
                            if (checkPiece.getColor() != piece.getColor()) {
                                cellsAttackMoves.add(sideMoveCell);
                            }
                        }
                    }
                }
            }
        }
        return new PossibleMoves(cellsMoves, cellsAttackMoves);
    }

    @Override
    public Step doStep(Game game, Piece piece) {
        Player player = game.getPiece2PlayerMap().get(piece);
        Cell startCell = game.getPiece2CellMap().get(piece);
        Cell endCell;
        Piece killedPiece = null;

        PossibleMoves possibleMoves = getPossibleMoves(game, piece);
        List<Cell> cellsMoves = possibleMoves.getCellsMoves();
        int cellsMovesSize = cellsMoves.size();
        List<Cell> cellsAttackMoves = possibleMoves.getCellsAttackMoves();
        int cellsAttackMovesSize = cellsAttackMoves.size();

        Random random = new Random();

        if(cellsAttackMovesSize > 0) {
            endCell = cellsAttackMoves.get(random.nextInt(cellsAttackMovesSize));
            killedPiece = game.getCell2PieceMap().get(endCell);
        } else if (cellsMovesSize > 0){
            endCell = cellsMoves.get(random.nextInt(cellsMovesSize));
        } else {
            return null;
        }

        return new Step(player, startCell, endCell, piece, killedPiece);
    }
}
