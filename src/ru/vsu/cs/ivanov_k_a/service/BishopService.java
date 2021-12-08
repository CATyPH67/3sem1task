package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class BishopService implements IPieceService{
    @Override
    public PossibleMoves getPossibleMoves(Game game, Piece piece) {
        List<Cell> cellsMoves = new ArrayList<>();
        List<Cell> cellsAttackMoves = new ArrayList<>();
        List<Direction> moveDirections = Arrays.asList(
                Direction.NORTH_WEST, Direction.NORTH_EAST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST
        );
        Map<Piece, Cell> piece2CellMap = game.getPiece2CellMap();
        for (int i = 0; i < moveDirections.size(); i++) {
            Direction moveDirection = moveDirections.get(i);
            Cell moveCell = piece2CellMap.get(piece);
            Boolean isMoveAvailable = true;
            while (isMoveAvailable) {
                moveCell = moveCell.getNeighbors().get(moveDirection);
                if (moveCell != null) {
                    Piece checkPiece = game.getCell2PieceMap().get(moveCell);
                    if (checkPiece == null) {
                        cellsMoves.add(moveCell);
                    } else {
                        isMoveAvailable = false;
                        if (checkPiece.getColor() != piece.getColor()) {
                            cellsAttackMoves.add(moveCell);
                        }
                    }
                } else {
                    isMoveAvailable = false;
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
