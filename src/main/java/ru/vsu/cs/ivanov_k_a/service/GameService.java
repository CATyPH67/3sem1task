package ru.vsu.cs.ivanov_k_a.service;

import ru.vsu.cs.ivanov_k_a.model.*;

import java.util.*;

public class GameService {
    private Map<PieceType, IPieceService> pieceServiceMap;

    public GameService(Map<PieceType, IPieceService> pieceServiceMap) {
        this.pieceServiceMap = pieceServiceMap;
    }

    public Game createGame() {
        Map<String, Cell> number2CellMap = new LinkedHashMap<>();
        Map<Cell, String> cell2NumberMap = new LinkedHashMap<>();
        createBoard(number2CellMap, cell2NumberMap);

        Map<Player, Set<Piece>> player2PieceMap = new LinkedHashMap<>();
        Map<Piece, Player> piece2PlayerMap = new LinkedHashMap<>();
        List<Player> listPlayers = Arrays.asList(new Player("whitePlayer"), new Player("bluePlayer"),
                            new Player("GreenPlayer"), new Player("BlackPlayer"));
        PieceColor[] pieceColors = PieceColor.values();
        for (int i = 0; i < 4; i++) {
            setPlayerPieces(listPlayers.get(i), pieceColors[i], player2PieceMap, piece2PlayerMap);
        }
        Map<Piece, Cell> piece2CellMap = new LinkedHashMap<>();
        Map<Cell, Piece> cell2PieceMap = new LinkedHashMap<>();
        setPieceCell(player2PieceMap, piece2CellMap, cell2PieceMap, number2CellMap, listPlayers);
        Queue<Player> players = new LinkedList<>(listPlayers);
        return new Game(piece2CellMap, cell2PieceMap,
                player2PieceMap, piece2PlayerMap,
                number2CellMap, cell2NumberMap, players);
    }

    private void createBoard(Map<String, Cell> number2CellMap, Map<Cell, String> cell2NumberMap) {
        createSmallBoard(number2CellMap, cell2NumberMap,'c', 14, 12);
        createSmallBoard(number2CellMap, cell2NumberMap,'a', 16, 4);
        createSmallBoard(number2CellMap, cell2NumberMap,'m', 16, 4);
        createSmallBoard(number2CellMap, cell2NumberMap,'m', 4, 4);
        createSmallBoard(number2CellMap, cell2NumberMap,'a', 4, 4);

        createGates(Arrays.asList(
                number2CellMap.get("e12"), number2CellMap.get("d13"), number2CellMap.get("l12"), number2CellMap.get("m13"),
                number2CellMap.get("l5"), number2CellMap.get("m4"), number2CellMap.get("e5"), number2CellMap.get("d4")
        ));
        createBorders(Arrays.asList(
                number2CellMap.get("c12"), number2CellMap.get("d12"), number2CellMap.get("l14"), number2CellMap.get("l13"),
                number2CellMap.get("n5"), number2CellMap.get("m5"), number2CellMap.get("e3"), number2CellMap.get("e4")
        ));
    }

    private void createSmallBoard(Map<String, Cell> number2CellMap,
                                  Map<Cell, String> cell2NumberMap,
                                  char firstCh, int firstInd, int size) {
        List<CellColor> cellColors = Arrays.asList(CellColor.BLACK, CellColor.WHITE);
        Cell prevCell;
        Cell currCell;
        List<Cell> prevRow = null;
        int ind = firstInd;
        for (int i = 0; i < size; i++, ind--) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            char ch = firstCh;
            for (int j = 0; j < size; j++, ch++) {
                currCell = new Cell(cellColors.get((i + j) % 2));
                String number = Character.toString(ch) + ind;
                Cell repeatedCell = number2CellMap.get(number);
                if (repeatedCell != null) {
                    cell2NumberMap.remove(repeatedCell);
                }
                number2CellMap.put(number, currCell);
                cell2NumberMap.put(currCell, number);
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
    }

    private void createGates(List<Cell> cells) {
        List<Direction> directions = Arrays.asList(
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
        );
        for (int i = 0; i < 4; i++) {
            connectCells(cells.get(i * 2), cells.get(i * 2 + 1), directions.get(i));
        }
    }

    private void connectCells(Cell boardCell, Cell castleCell, Direction direction) {
        List<Direction> diagonallyUp;
        List<Direction> left;
        List<Direction> diagonallyDown;
        int castleIndex;
        int boardIndex;
        if ((direction == Direction.NORTH) || (direction == Direction.SOUTH)) {
            diagonallyUp = Arrays.asList(Direction.NORTH_WEST, Direction.SOUTH_EAST);
            left = Arrays.asList(Direction.WEST, Direction.EAST);
            diagonallyDown = Arrays.asList(Direction.SOUTH_WEST, Direction.NORTH_EAST);
            if (direction == Direction.NORTH) {
                castleIndex = 1;
                boardIndex = 0;
            } else {
                castleIndex = 0;
                boardIndex = 1;
            }
        } else {
            diagonallyUp = Arrays.asList(Direction.NORTH_EAST, Direction.SOUTH_WEST);
            left = Arrays.asList(Direction.NORTH, Direction.SOUTH);
            diagonallyDown = Arrays.asList(Direction.NORTH_WEST, Direction.SOUTH_EAST);
            if (direction == Direction.EAST) {
                castleIndex = 1;
                boardIndex = 0;
            } else {
                castleIndex = 0;
                boardIndex = 1;
            }
        }

        Cell prevCell = null;
        Cell startCastleCell = castleCell;
        for (int i = 0; i < 3; i++) {
            castleCell.getNeighbors().put(diagonallyUp.get(castleIndex), boardCell);
            boardCell.getNeighbors().put(diagonallyUp.get(boardIndex), castleCell);
            if (i > 0) {
                prevCell.getNeighbors().put(left.get(castleIndex), boardCell);
                boardCell.getNeighbors().put(left.get(boardIndex), prevCell);
            }
            if (i == 2) {
                startCastleCell.getNeighbors().put(diagonallyDown.get(castleIndex), boardCell);
                boardCell.getNeighbors().put(diagonallyDown.get(boardIndex), startCastleCell);
            }
            prevCell = castleCell;
            castleCell = castleCell.getNeighbors().get(direction);
            boardCell = boardCell.getNeighbors().get(direction);
        }
    }

    private void createBorders(List<Cell> cells) {
        List<Direction> directions = Arrays.asList(
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST,
                Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST,
                Direction.SOUTH_WEST, Direction.NORTH_WEST
        );
        for (int i = 0; i < 4; i++) {
            Cell firstCell = cells.get(i * 2);
            Cell secondCell = cells.get(i * 2 + 1);
            firstCell.getNeighbors().remove(directions.get(i));
            firstCell.getNeighbors().remove(directions.get(i + 5));
            secondCell.getNeighbors().remove(directions.get(i));
            secondCell.getNeighbors().remove(directions.get(i + 4));
        }
    }

    private void setPlayerPieces(Player player, PieceColor pieceColor,
                                 Map<Player, Set<Piece>> player2PieceMap,
                                 Map<Piece, Player> piece2PlayerMap) {
        Set<Piece> pieces = new LinkedHashSet<>();
        Piece piece;
        PieceType[] pieceTypes = PieceType.values();
        for (int i = 0; i < 8; i++) {
            piece = new Piece(pieceTypes[0], pieceColor);
            pieces.add(piece);
            piece2PlayerMap.put(piece, player);
        }
        for (int i = 1; i < 6; i++) {
            piece = new Piece(pieceTypes[i], pieceColor);
            pieces.add(piece);
            piece2PlayerMap.put(piece, player);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 3; j > 0; j--) {
                piece = new Piece(pieceTypes[j], pieceColor);
                pieces.add(piece);
                piece2PlayerMap.put(piece, player);
            }
        }
        player2PieceMap.put(player, pieces);
    }

    private void setPieceCell(Map<Player, Set<Piece>> player2PieceMap,
                              Map<Piece, Cell> piece2CellMap,
                              Map<Cell, Piece> cell2PieceMap,
                              Map<String, Cell> number2CellMap,
                              List<Player> players) {
        arrangeHorizontalPiece(player2PieceMap.get(players.get(0)), 'l', 13,
                                piece2CellMap, cell2PieceMap, number2CellMap, -1);
        arrangeVerticalPiece(player2PieceMap.get(players.get(1)), 'm', 5,
                                piece2CellMap, cell2PieceMap, number2CellMap, 1);
        arrangeHorizontalPiece(player2PieceMap.get(players.get(2)), 'e', 4,
                                piece2CellMap, cell2PieceMap, number2CellMap, 1);
        arrangeVerticalPiece(player2PieceMap.get(players.get(3)), 'd', 12,
                                piece2CellMap, cell2PieceMap, number2CellMap, -1);
    }

    private void arrangeHorizontalPiece(Set<Piece> pieces, char firstCh, int firstInd,
                                        Map<Piece, Cell> piece2CellMap,
                                        Map<Cell, Piece> cell2PieceMap,
                                        Map<String, Cell> number2CellMap, int directionInd) {
        char ch = ' ';
        int ind = 0;
        Cell cell;
        Piece piece;
        List<Piece> listPieces = new ArrayList<>(pieces);
        int pieceInd = 0;
        for (int i = 0; i < 2; i++) {
            ch = firstCh;
            ind = firstInd - directionInd * i;
            for (int j = 0; j < 8; j++, ch += directionInd, pieceInd++) {
                cell = number2CellMap.get(Character.toString(ch) + ind);
                piece = listPieces.get(pieceInd);
                piece2CellMap.put(piece, cell);
                cell2PieceMap.put(cell, piece);
            }
        }
        cell = number2CellMap.get(Character.toString((char) (ch + 2 * directionInd)) + (ind - 2 * directionInd));
        piece = listPieces.get(pieceInd++);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
        cell = number2CellMap.get(Character.toString((char) (ch + directionInd)) + ind);
        piece = listPieces.get(pieceInd++);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
        cell = number2CellMap.get(Character.toString((char) (ch + 3 * directionInd)) + (ind + directionInd));
        piece = listPieces.get(pieceInd);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
    }

    private void arrangeVerticalPiece(Set<Piece> pieces, char firstCh, int firstInd,
                                        Map<Piece, Cell> piece2CellMap,
                                        Map<Cell, Piece> cell2PieceMap,
                                        Map<String, Cell> number2CellMap, int directionInd) {
        char ch = ' ';
        int ind = 0;
        Cell cell;
        Piece piece;
        List<Piece> listPieces = new ArrayList<>(pieces);
        int pieceInd = 0;
        for (int i = 0; i < 2; i++) {
            ch = (char) (firstCh + directionInd * i);
            ind = firstInd;
            for (int j = 0; j < 8; j++, ind += directionInd, pieceInd++) {
                cell = number2CellMap.get(Character.toString(ch) + ind);
                piece = listPieces.get(pieceInd);
                piece2CellMap.put(piece, cell);
                cell2PieceMap.put(cell, piece);
            }
        }
        cell = number2CellMap.get(Character.toString((char) (ch + 2 * directionInd)) + (ind + 2 * directionInd));
        piece = listPieces.get(pieceInd++);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
        cell = number2CellMap.get(Character.toString(ch) + (ind + directionInd));
        piece = listPieces.get(pieceInd++);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
        cell = number2CellMap.get(Character.toString((char) (ch - directionInd)) + (ind + 3 * directionInd));
        piece = listPieces.get(pieceInd);
        piece2CellMap.put(piece, cell);
        cell2PieceMap.put(cell, piece);
    }

    public boolean isGameOver(Game game) {
        List<Player> players = new LinkedList<>(game.getPlayers());
        int countKing = 0;
        for (Player player: players) {
            boolean isKingDead = true;
            Set<Piece> pieces = game.getPlayer2PieceMap().get(player);
            for (Piece piece: pieces) {
                if (piece.getType() == PieceType.KING) {
                    isKingDead = false;
                }
            }
            if (isKingDead) {
                return false;
            }
        }
        return true;
    }

    public void processGame(Game game) {
        Queue<Player> players = game.getPlayers();
        Player player = players.poll();
        players.add(player);
        Set<Piece> pieces = game.getPlayer2PieceMap().get(player);
        List<Piece> listPieces = new ArrayList<>(pieces);
        Random random = new Random();
        Piece piece;
        Step step;
        do {
            if (listPieces.size() > 1) {
                piece = listPieces.get(random.nextInt(listPieces.size()));
            } else {
                piece = listPieces.get(0);
            }
            IPieceService pieceService = pieceServiceMap.get(piece.getType());
            step = pieceService.doStep(game, piece);
        } while (step == null);
        Cell endCell = step.getEndCell();
        Cell startCell = step.getStartCell();

        game.getPiece2CellMap().put(piece, endCell);
        game.getCell2PieceMap().put(endCell, piece);
        game.getCell2PieceMap().remove(startCell);
        game.getSteps().add(step);

        Piece killedPiece = step.getKilledPiece();
        if (killedPiece != null) {
            Player enemyPlayer = game.getPiece2PlayerMap().get(killedPiece);
            game.getPlayer2PieceMap().get(enemyPlayer).remove(killedPiece);
            game.getPiece2PlayerMap().remove(killedPiece);
        }
    }

    public void printGame(Game game) {
        List<Step> steps = game.getSteps();
        Step step = steps.get(steps.size() - 1);
        Cell startCell = step.getStartCell();
        String numStartCell = game.getCell2NumberMap().get(startCell);
        Cell endCell = step.getEndCell();
        String numEndCell = game.getCell2NumberMap().get(endCell);
        Piece piece = step.getPiece();
        Piece killedPiece = step.getKilledPiece();
        String killedPieceName;
        String killedPieceColor;
        if (killedPiece != null) {
            killedPieceName = String.valueOf(killedPiece.getType());
            killedPieceColor = String.valueOf(killedPiece.getColor());
        } else {
            killedPieceName = "";
            killedPieceColor = "";
        }
        System.out.printf("%s %s %s -> %s %s %s \n", piece.getColor(), piece.getType(),
                numStartCell, numEndCell, killedPieceName, killedPieceColor);
    }

    public void printResult(Game game) {
        List<Step> steps = game.getSteps();
        Step step = steps.get(steps.size() - 1);
        String winnerPlayer = step.getPlayer().getName();
        System.out.println("The winner is: " + winnerPlayer);
    }
}
