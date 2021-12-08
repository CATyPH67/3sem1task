package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.Game;
import ru.vsu.cs.ivanov_k_a.model.PieceType;

import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Map<PieceType, IPieceService> pieceServiceMap = new HashMap<>();
        pieceServiceMap.put(PieceType.PAWN, new PawnService());
        pieceServiceMap.put(PieceType.ROOK, new RookService());
        pieceServiceMap.put(PieceType.KNIGHT, new KnightService());
        pieceServiceMap.put(PieceType.BISHOP, new BishopService());
        pieceServiceMap.put(PieceType.QUEEN, new QueenService());
        pieceServiceMap.put(PieceType.KING, new KingService());
        GameService gameService = new GameService(pieceServiceMap);
        Game game = gameService.createGame();
        for (int i = 0; i < 100; i++) {
            gameService.processGame(game);
            gameService.printGame(game);
        }
    }
}
