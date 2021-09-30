package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.List;

public interface IFigureService {
    List<Cell> getPossibleMoves(Game game, Figure f);
    Step doStep(Game game, Figure f);
}
