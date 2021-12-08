package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class BishopService implements IPieceService{
    @Override
    public PossibleMoves getPossibleMoves(Game game, Piece piece) {
        List<Cell> cellsMoves = new ArrayList<>();
        List<Cell> cellsAttackMoves = new ArrayList<>();
        List<Direction> directions = Arrays.asList(
                Direction.NORTH_WEST, Direction.NORTH_EAST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST
        );
        Map<Piece, Cell> piece2CellMap = game.getPiece2CellMap();
        for (int i = 0; i < directions.size(); i++) {
            Direction direction = directions.get(i);
            Cell moveCell = piece2CellMap.get(piece);
            Boolean isMoveAvailable = true;
            while (isMoveAvailable) {
                moveCell = moveCell.getNeighbors().get(direction);
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
        int cellsAttackMovesSize = cellsMoves.size();

        Random random = new Random();

        if(cellsAttackMovesSize > 0) {
            endCell = cellsAttackMoves.get(random.nextInt(cellsAttackMovesSize - 1));
            killedPiece = game.getCell2PieceMap().get(endCell);
            Player enemyPlayer = game.getPiece2PlayerMap().get(killedPiece);
            game.getPlayer2PieceMap().get(enemyPlayer).remove(killedPiece);
            game.getPiece2PlayerMap().remove(killedPiece);
        } else if (cellsMovesSize > 0){
            endCell = cellsMoves.get(random.nextInt(cellsMovesSize - 1));
        } else {
            return null;
        }

        Step step = new Step(player, startCell, endCell, piece, killedPiece);

        game.getPiece2CellMap().put(piece, endCell);
        game.getCell2PieceMap().put(endCell, piece);
        game.getCell2PieceMap().remove(startCell);
        game.getSteps().add(step);

        return step;
    }
}
