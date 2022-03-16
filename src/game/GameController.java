package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static game.GameToken.*;

public class GameController implements KeyListener {

    private static final int START_X_POSITION = 1;
    private static final int CHANGE_X_POS = 1;
    private final GameMap gameMap;
    int posX;
    GameToken player;

    GameController(GameMap gameMap) {
        this.gameMap = gameMap;
        player = PLAYER_ONE;
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            posX = movePosX(posX, -CHANGE_X_POS);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            posX = movePosX(posX, +CHANGE_X_POS);
            refreshScreen(posX);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                executeTurn(player);
            } catch (IndexOutOfBoundsException er) {
                System.out.print("Nie da się włożyć w tej kolumnie więcej tokenów. Spróbuj innej.");
                player = changePlayer(player);
            }
            player = changePlayer(player);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public int movePosX(int actualXpos, int change) {
        int newXpos;
        newXpos = actualXpos + change;
        if (newXpos < 1) {
            newXpos = 7;
        }
        if (newXpos > 7) {
            newXpos = 1;
        }
        return newXpos;
    }

    private void executeTurn(GameToken player) {
        int posY = gameMap.getFirstEmptyY(posX);
        gameMap.placeToken(posX, posY, player);
        if (!gameMap.isAnySpaceFree()) {
            System.out.print("Brak wolnych miejsc. Koniec gry!");
            System.exit(0);
        }
        if (gameMap.checkLastTokenForWin(posX, posY)) {
            System.out.print("Gracz " + player + " wygral!");
            System.exit(0);
        }
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    public void refreshScreen(int position) {
        clearConsole();
        System.out.println("Ruch gracza " + player);
        System.out.println(gameMap.preparePlayerMoves(position));
        System.out.println(gameMap.prepare());
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public GameToken changePlayer(GameToken player) {
        if (player == PLAYER_ONE) {
            return PLAYER_TWO;
        } else {
            return PLAYER_ONE;
        }
    }
}
