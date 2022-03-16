package game;

import java.util.ArrayList;
import java.util.List;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int y, int x) {
        this.x = x;
        this.y = y;
    }

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
        vectors.add(new Vector2d(0, 1));
        vectors.add(new Vector2d(-1, 1));
        vectors.add(new Vector2d(-1, 0));
        vectors.add(new Vector2d(-1, -1));
        return vectors;
    }
}
