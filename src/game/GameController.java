package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static game.GameToken.*;

public class GameController implements KeyListener {

    private static final int START_X_POSITION = 1;
    // I'd call it X_POS_DELTA or sth. Variables should be nouns, naming "things". "Change" is a verb, suggesting action (thus, a function)
    private static final int CHANGE_X_POS = 1;
    // Why final? What if we want to play again after finishing a game? Would be a waste to restart the application.
    // Consider adding such functionality
    private final GameMap gameMap;
    // currentPosX. Or maybe markerPosition (since it ony moves on xes anyway)?
    int posX;
    GameToken currentPlayer;

    GameController(GameMap gameMap) {
        this.gameMap = gameMap;
        currentPlayer = PLAYER_ONE;
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Wouldn't a switch statement look better here?
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
                executeTurn(currentPlayer);
                // Why don't you handle the overflow in "executeTurn"? You check for first free Y and you have access to map height, this should be trivial.
                // First, switching players should be handled in "executeTurn", as "keyPressed" is just an event handler. You shouldn't place any logic here, only delegate it to another function.
                // Second, You should only switch players when they should be switched. Switching back and forth could cause undesired behavior if some additional logic was added to
                // player change (like firing events, notifications, displaying stuff etc.)
            } catch (ArrayIndexOutOfBoundsException er) {
                System.out.print("Tego się nie spodziewałam.");
                changePlayer();
            }
            changePlayer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private int movePosX(int actualXpos, int change) {
        int newXpos = actualXpos + change;
        if (newXpos < 1) {
            newXpos = gameMap.getMapWidth();
        }
        if (newXpos > gameMap.getMapWidth()) {
            newXpos = 1;
        }
        return newXpos;
    }

    private void executeTurn(GameToken player) {
        int posY = gameMap.getFirstEmptyY(posX);
        if (posY == 0) {
            System.out.print("Nie da się włożyć w tej kolumnie więcej tokenów. Spróbuj innej.");
        }
        gameMap.placeToken(posX, posY, player);
        if (gameMap.checkLastTokenForWin(posX, posY)) {
            System.out.print("Gracz " + player + " wygral!");
            System.exit(0);
        }
        if (!gameMap.isAnySpaceFree()) {
            System.out.print("Brak wolnych miejsc. Koniec gry!");
            System.exit(0);
        }
        posX = START_X_POSITION;
        refreshScreen(posX);
    }

    private void refreshScreen(int position) {
        clearConsole();
        System.out.println("Ruch gracza " + currentPlayer);
        System.out.println(gameMap.preparePickedColumnDisplay(position));
        System.out.println(gameMap.prepareMap());
    }

    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void changePlayer() {
        if (currentPlayer == PLAYER_ONE) {
            currentPlayer = PLAYER_TWO;
        } else {
            currentPlayer = PLAYER_ONE;
        }
    }
}
