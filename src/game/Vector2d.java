package game;

import java.util.ArrayList;
import java.util.List;

public class Vector2d {
    private final int x;
    private final int y;

    // To make the class that much more useful I'd suggest adding '+' and '*' operators. This will make the logic clearer and simpler when
    // you use these vectors to calculate the length of a line of tokens
    public Vector2d(int y, int x) {
        this.x = x;
        this.y = y;
    }

    // This is not your fault, more of a stupidly widespread delusion that placing getters and setters everywhere makes the code cleaner and better.
    // I'd argue it makes it more bloated with useless functions. You can achieve exactly same results if you declare x/y as public final.
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static List<Vector2d> createList() {
        ArrayList<Vector2d> vectors = new ArrayList<>();
        vectors.add(new Vector2d(0, -1));
        vectors.add(new Vector2d(1, -1));
        vectors.add(new Vector2d(1, 0));
        vectors.add(new Vector2d(1, 1));
        return vectors;
    }
}
