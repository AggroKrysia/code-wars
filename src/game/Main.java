package game;

import java.awt.*;
// You could add a summary (either as a comment here or on git description) of what this game actually is and its ruleset.
// It will make it easier to understand the project
//
/*Connect 4
Ruch gracza PLAYER_ONE
|v|_|_|_|_|_|_|

|0|0|0|0|0|0|0|
_______________
|0|0|0|0|0|0|0|
_______________
|0|0|0|0|0|0|0|
_______________
|0|0|0|0|0|0|0|
_______________
|0|0|0|0|0|0|0|
_______________
|0|0|0|0|0|0|0|
_______________

To win the game you need to connect 4 of your tokens in line - vertically, horizontally or diagonally. Of course, it won't
be easy because of your opponent. Game is hot chair style. Standard game is proceeded on 2D map of size 7x6.
*/
public class Main {
    //todo read height and widht from args
    public static void main(String[] args) {

        Frame controlWindow = new Frame("Demo");
        controlWindow.setLayout(new FlowLayout());
        controlWindow.setSize(200, 200);
        Label textInsideWindow = new Label();
        textInsideWindow.setText("Click here to control game");
        controlWindow.add(textInsideWindow);
        controlWindow.setVisible(true);

        GameMap gameMap = new GameMap();

        GameController gameController = new GameController(gameMap);

        controlWindow.addKeyListener(gameController);
    }
}

