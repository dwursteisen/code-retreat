package game;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Wursteisen David
 * Date: 29/09/12
 * Time: 15:20
 */
public class Grille {

    private static final Random random = new Random();

    // (etat courant, nombre voisin) -> nouvel etat
    private static final int[][] statusMatrix = new int[][]{
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0}
    };

    private final int[][] map;
    private final int height;
    private final int width;

    private Grille(int height, int width, int[][] map) {
        this.height = height;
        this.width = width;
        this.map = map;
    }

    public static Grille initRandomMap(int height, int width) {
        final int[][] internalMap = new int[height][width];
        applyOnMap(height, width, (x, y) -> {
            internalMap[x][y] = random.nextInt(2);
        });
        return new Grille(height, width, internalMap);
    }

    public static Grille initClearMap(int height, int width) {
        final int[][] internalMap = new int[height][width];
        applyOnMap(height, width, (x, y) -> {
            internalMap[x][y] = 0;
        });
        return new Grille(height, width, internalMap);
    }

    public static Grille initOscillateurMap(int height, int width) {
        Grille grille = initClearMap(height, width);
        grille.setCell(3, 1, 1);
        grille.setCell(3, 2, 1);
        grille.setCell(3, 3, 1);
        return grille;
    }

    public static void applyOnMap(int height, int width, CallMe method) {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                method.call(i, j);
            }
    }

    public void setCell(int x, int y, int val) {
        map[x][y] = val;
    }

    public int neighboursCounter(final int cellX, final int cellY) {

        final AtomicInteger counter = new AtomicInteger(0);
        counter.addAndGet(-map[cellX][cellY]);

        applyOnMap(3, 3, (x, y) -> {
            int i = (x + cellX - 1 + height) % height;
            int j = (y + cellY - 1 + width) % width;
            counter.addAndGet(map[i][j]);
        });
        return counter.get();
    }

    public Grille step() {
        final Grille newGrille = initClearMap(height, width);
        applyOnMap(height, width, (int x, int y) -> {
            int numberOfNeightbours = Grille.this.neighboursCounter(x, y);
            int currentStatus = Grille.this.map[x][y];
            int newState = statusMatrix[currentStatus][numberOfNeightbours];
            newGrille.setCell(x, y, newState);
        });
        return newGrille;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        applyOnMap(height, width, (x, y) -> {
            if (y == 0) {
                builder.append('\n');
            }
            builder.append(map[x][y]);
        });
        return builder.toString();
    }


}
