package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class PawnService implements IPieceService{
    @Override
    public PossibleMoves getPossibleMoves(Game game, Piece piece) {
        List<Cell> cellsMoves = new ArrayList<>();
        List<Cell> cellsAttackMoves = new ArrayList<>();
        PieceColor pieceColor = piece.getColor();
        Direction moveDirection;
        List<Direction> attackDirections = new ArrayList<>();
        if (pieceColor == PieceColor.WHITE) {
            moveDirection = Direction.SOUTH;
            attackDirections.add(Direction.SOUTH_EAST);
            attackDirections.add(Direction.SOUTH_WEST);
        } else if (pieceColor == PieceColor.BLUE) {
            moveDirection = Direction.WEST;
            attackDirections.add(Direction.NORTH_WEST);
            attackDirections.add(Direction.SOUTH_WEST);
        } else if (pieceColor == PieceColor.GREEN) {
            moveDirection = Direction.NORTH;
            attackDirections.add(Direction.NORTH_WEST);
            attackDirections.add(Direction.NORTH_EAST);
        } else {
            moveDirection = Direction.EAST;
            attackDirections.add(Direction.NORTH_EAST);
            attackDirections.add(Direction.SOUTH_EAST);
        }
        Map<Piece, Cell> piece2CellMap = game.getPiece2CellMap();
        Cell currCell = piece2CellMap.get(piece);
        Cell moveCell;
        for (int i = 0; i < 2; i++) {
            moveCell = currCell.getNeighbors().get(attackDirections.get(i));
            if (moveCell != null) {
                Piece checkPiece = game.getCell2PieceMap().get(moveCell);
                if ((checkPiece != null) && (checkPiece.getColor() != piece.getColor())) {
                    cellsAttackMoves.add(moveCell);
                }
            }
        }
        int countStep = 1;
        if (isFirstStep(game, piece)) {
            countStep++;
        }
        moveCell = currCell;
        for (int i = 0; i < countStep; i++) {
            moveCell = moveCell.getNeighbors().get(moveDirection);
            if (moveCell != null) {
                Piece checkPiece = game.getCell2PieceMap().get(moveCell);
                if (checkPiece == null) {
                    cellsMoves.add(moveCell);
                }
            }
        }
        return new PossibleMoves(cellsMoves, cellsAttackMoves);
    }

    private boolean isFirstStep(Game game, Piece piece) {
        Cell curCell = game.getPiece2CellMap().get(piece);
        PieceColor pieceColor = piece.getColor();
        String pieceNumber = game.getCell2NumberMap().get(curCell);
        char pieceCh = pieceNumber.charAt(0);
        int pieceInd = Integer.parseInt(pieceNumber.substring(1));
        if (pieceColor == PieceColor.WHITE) {
            if (pieceInd == 13) {
                return true;
            }
        } else if (pieceColor == PieceColor.BLUE) {
            if (pieceCh == 'm') {
                return true;
            }
        } else if (pieceColor == PieceColor.GREEN) {
            if (pieceInd == 4) {
                return true;
            }
        } else if (pieceColor == PieceColor.BLACK) {
            if (pieceCh == 'd') {
                return true;
            }
        }
        return false;
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
