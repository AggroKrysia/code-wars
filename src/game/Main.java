package game;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Frame controllWindow = new Frame("Demo");
        controllWindow.setLayout(new FlowLayout());
        controllWindow.setSize(200, 200);
        Label textInsideWindow = new Label();
        textInsideWindow.setText("Click here to control game");
        controllWindow.add(textInsideWindow);
        controllWindow.setVisible(true);

        GameMap gameMap = new GameMap();

        GameController gameController = new GameController(gameMap);

        controllWindow.addKeyListener(gameController);
    }
}

