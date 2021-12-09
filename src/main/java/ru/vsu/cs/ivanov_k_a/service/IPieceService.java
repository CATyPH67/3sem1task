package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.List;

public interface IPieceService {
    PossibleMoves getPossibleMoves(Game game, Piece piece);
    Step doStep(Game game, Piece piece);
}
