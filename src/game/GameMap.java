package game;

import java.util.Arrays;
import java.util.List;

import static game.GameToken.*;

public class GameMap {

    private final int mapWidth;
    private GameToken[][] gameMap;
    private int emptyFieldsRemaining;
    // This is very unnecessary and extremely unreadable without delving into Vector2d class.
    // Delete this variable, the createList function (btw, poor name) and create the required vectors in place.
    // Reasoning: Vector2d(0,1) is FAR more readable than vectors[4]
    private List<Vector2d> vectors = Vector2d.createList();
    private static final int DEFAULT_HEIGHT = 6;
    private static final int DEFAULT_WIDTH = 7;

    public GameMap() {
        gameMap = createEmptyMapWithPadding(DEFAULT_HEIGHT + 2, DEFAULT_WIDTH + 2);
        emptyFieldsRemaining = DEFAULT_HEIGHT * DEFAULT_WIDTH;
        mapWidth = DEFAULT_WIDTH;
    }

    public GameMap(int height, int width) {
        this.gameMap = createEmptyMapWithPadding(height + 2, width + 2);
        this.emptyFieldsRemaining = height * width;
        this.mapWidth = width;
    }

    private GameToken[][] createEmptyMapWithPadding(int height, int width) {
        GameToken[][] newMap = new GameToken[height][width];

        for (GameToken[] row : newMap) {
            Arrays.fill(row, EMPTY);
        }

        for (int topRow = 0; topRow < width; topRow++) {
            newMap[0][topRow] = PADDING;
        }
        for (int bottomRow = 0; bottomRow < width; bottomRow++) {
            newMap[height - 1][bottomRow] = PADDING;
        }
        for (int firstColumn = 0; firstColumn < height; firstColumn++) {
            newMap[firstColumn][0] = PADDING;
        }
        for (int lastColumn = 0; lastColumn < height; lastColumn++) {
            newMap[lastColumn][width - 1] = PADDING;
        }
        return newMap;
    }

    public StringBuilder prepareMap() {
        StringBuilder map = new StringBuilder();
        for (int i = 1; i < DEFAULT_HEIGHT + 1; i++) {
            map.append("|");
            for (int j = 1; j < DEFAULT_WIDTH + 1; j++) {
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
        int positionY = 1;
        while (positionY < DEFAULT_HEIGHT + 2) {
            if (gameMap[positionY][positionX] != EMPTY) {
                return positionY - 1;
            }
            positionY++;
        }
        return positionY;
    }

    public void placeToken(int positionX, int positionY, GameToken token) {
        gameMap[positionY][positionX] = token;
        deleteOneFreeSpace();
    }

    public boolean checkLastTokenForWin(int positionX, int positionY) {
        GameToken currentPlayer = gameMap[positionY][positionX];
        if (getNumberOfTokensInLine(positionY, positionX, vectors.get(0), currentPlayer) >= 4) {
            return true;
        }
        if (getNumberOfTokensInLine(positionY, positionX, vectors.get(1), currentPlayer) >= 4) {
            return true;
        }
        if (getNumberOfTokensInLine(positionY, positionX, vectors.get(2), currentPlayer) >= 4) {
            return true;
        }
        if (getNumberOfTokensInLine(positionY, positionX, vectors.get(3), currentPlayer) >= 4) {
            return true;
        }
        return false;
    }

    private int getNumberOfTokensInLine(int positionY, int positionX, Vector2d vector, GameToken player) {
        int numberOfTokens = 1;
        int vectorY = vector.getY();
        int vectorX = vector.getX();
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY + vectorY * i][positionX + vectorX * i] != player) {
                break;
            }
            numberOfTokens++;
        }
        for (int i = 1; i <= 3; i++) {
            if (gameMap[positionY - vectorY * i][positionX - vectorX * i] != player) {
                break;
            }
            numberOfTokens++;
        }
        return numberOfTokens;
    }

    public boolean isAnySpaceFree() {
        return emptyFieldsRemaining >= 0;
    }

    private void deleteOneFreeSpace() {
        emptyFieldsRemaining = emptyFieldsRemaining - 1;
    }

    public StringBuilder preparePickedColumnDisplay(int currentPosition) {
        StringBuilder playerMoves = new StringBuilder();
        playerMoves.append("|");
        for (int i = 1; i <= DEFAULT_HEIGHT + 1; i++) {
            playerMoves.append((i == currentPosition) ? "v|" : "_|");
        }
        playerMoves.append("\n");
        return playerMoves;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
