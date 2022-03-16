package game;

import java.util.Arrays;
import java.util.List;

import static game.GameToken.*;

public class GameMap {

    private GameToken[][] gameMap;
    private int sizeOfMap;
    private List<Vector2d> vectors = Vector2d.createList();
    private static final int DEFAULT_HEIGHT = 7;
    private static final int DEFAULT_WIDTH = 9;

    public GameMap() {
        gameMap = createEmptyMapWithPadding(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        sizeOfMap = (DEFAULT_HEIGHT - 1) * (DEFAULT_WIDTH - 2);
    }

    public GameMap(int height, int width) {
        gameMap = createEmptyMapWithPadding(height, width);
        sizeOfMap = (height - 1) * (width - 2);
    }

    private GameToken[][] createEmptyMapWithPadding(int height, int width) {
        GameToken[][] newMap = new GameToken[height][width];

        for (GameToken[] row : newMap) {
            Arrays.fill(row, EMPTY);
        }
        for (int bottomRow = 0; bottomRow < width; bottomRow++) {
            newMap[height - 1][bottomRow] = PADDING;
        }
        for (int firstColumn = 0; firstColumn < height; firstColumn++) {
            newMap[firstColumn][0] = PADDING;
        }
        for (int lastComulm = 0; lastComulm < height; lastComulm++) {
            newMap[lastComulm][width - 1] = PADDING;
        }
        return newMap;
    }

    public StringBuilder prepare() {
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < DEFAULT_HEIGHT - 1; i++) {
            map.append("|");
            for (int j = 1; j < DEFAULT_WIDTH - 1; j++) {
                switch (gameMap[i][j]) {
                    case EMPTY:
                        map.append("0");
                        break;
                    case PLAYER_ONE:
                        map.append("z");
                        break;
                    case PLAYER_TWO:
                        map.append("x");
                        break;
                    case PADDING:
                        map.append("error");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + gameMap[i][j]);
                }
                map.append("|");
            }
            map.append("\n");
            map.append("_______________");
            map.append("\n");
        }
        return map;
    }

    public int getFirstEmptyY(int positionX) {
        int positionY = 0;
        while (positionY < DEFAULT_HEIGHT) {
            if (gameMap[positionY][positionX] != EMPTY) {
                return positionY - 1;
            }
            positionY++;
        }
        return positionY;
    }

    public void placeToken(int positionX, int positionY, GameToken token) {
        gameMap[positionY][positionX] = token;
    }

    public boolean checkLastTokenForWin(int positionX, int positionY) {
        GameToken currentPlayer = gameMap[positionY][positionX];
        if (gameMap[positionY][positionX - 1] == currentPlayer) {
            if (1 + getNumberOfTokensInLine(positionY, positionX, vectors.get(0), currentPlayer)
                    + getNumberOfTokensInLine(positionY, positionX, vectors.get(4), currentPlayer) >= 4) {
                return true;
            }
        }
        if (gameMap[positionY + 1][positionX - 1] == currentPlayer) {
            if (1 + getNumberOfTokensInLine(positionY, positionX, vectors.get(1), currentPlayer)
                    + getNumberOfTokensInLine(positionY, positionX, vectors.get(5), currentPlayer) >= 4) {
                return true;
            }
        }
        if (gameMap[positionY + 1][positionX] == currentPlayer) {
            if (1 + getNumberOfTokensInLine(positionY, positionX, vectors.get(2), currentPlayer)
                    + getNumberOfTokensInLine(positionY, positionX, vectors.get(6), currentPlayer) >= 4) {
                return true;
            }
        }
        if (gameMap[positionY + 1][positionX + 1] == currentPlayer) {
            if (1 + getNumberOfTokensInLine(positionY, positionX, vectors.get(3), currentPlayer)
                    + getNumberOfTokensInLine(positionY, positionX, vectors.get(7), currentPlayer) >= 4) {
                return true;
            }

        }
        if (gameMap[positionY][positionX + 1] == currentPlayer) {
            if (1 + getNumberOfTokensInLine(positionY, positionX, vectors.get(4), currentPlayer)
                    + getNumberOfTokensInLine(positionY, positionX, vectors.get(0), currentPlayer) >= 4) {
                return true;
            }
        }
        return false;
    }

    private int getNumberOfTokensInLine(int positionY, int positionX, Vector2d vector, GameToken player) {
        int numberOfTokens = 0;
        int vectorY = vector.getY();
        int vectorX = vector.getX();
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                return numberOfTokens;
            }
            numberOfTokens++;
        }
        return numberOfTokens;
    }

    public boolean isAnySpaceFree() {
        sizeOfMap = sizeOfMap - 1;
        return sizeOfMap >= 0;
    }

    public StringBuilder preparePlayerMoves(int position) {
        StringBuilder playerMoves = new StringBuilder();
        playerMoves.append("|");
        for (int i = 1; i <= DEFAULT_HEIGHT; i++) {
            if (i == position) {
                playerMoves.append("v|");
            } else {
                playerMoves.append("_|");
            }
        }
        playerMoves.append("\n");
        return playerMoves;
    }
}
