package game;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Wursteisen David
 * Date: 29/09/12
 * Time: 15:20
 */
public class Grille {

    private final int[][] map;
    private static final Random random = new Random();
    int h;
    int w;

    private Grille(int h, int w, int[][] map) {
        this.h = h;
        this.w = w;
        this.map = map;
    }

    public static Grille initRandomMap(int h, int w) {
        final int[][] internalMap = new int[h][w];
        applyOnMap(h, w, new CallMe() {
            public void call(int x, int y) {
                internalMap[x][y] = random.nextInt(2);
            }
        });
        return new Grille(h, w, internalMap);
    }

    public static Grille initClearMap(int h, int w) {
        final int[][] internalMap = new int[h][w];
        applyOnMap(h, w, new CallMe() {
            public void call(int x, int y) {
                internalMap[x][y] = 0;
            }
        });
        return new Grille(h, w, internalMap);
    }

    public static Grille initOscillateurMap(int h, int w) {
        Grille grille = initClearMap(h, w);
        grille.setCell(3, 1, 1);
        grille.setCell(3, 2, 1);
        grille.setCell(3, 3, 1);
        return grille;
    }

    public static void applyOnMap(int h, int w, CallMe method) {
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                method.call(i, j);
            }
    }

    private static interface CallMe {
        void call(int x, int y);
    }


    public void setCell(int x, int y, int val) {
        map[x][y] = val;
    }

    public int neighboursCounter(final int cellX, final int cellY) {

        final AtomicInteger counter = new AtomicInteger(0);
        counter.addAndGet(-map[cellX][cellY]);

        applyOnMap(3, 3, new CallMe() {
            public void call(int x, int y) {
                int i = (x + cellX - 1 + h) % h;
                int j = (y + cellY - 1 + w) % w;
                counter.addAndGet(map[i][j]);
            }
        });
        return counter.get();
    }

    public Grille step() {
        final Grille newGrille = initClearMap(h, w);
        applyOnMap(h, w, new CallMe() {
            public void call(int x, int y) {
                int numberOfNeightbours = Grille.this.neighboursCounter(x, y);
                int currentStatus = Grille.this.map[x][y];
                int newState = statusMatrix[currentStatus][numberOfNeightbours];
                newGrille.setCell(x, y, newState);
            }
        });
        return newGrille;
    }

    // (etat courant, nombre voisin) -> nouvel etat
    private static final int[][] statusMatrix = new int[][]{
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0}
    };

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        applyOnMap(map.length, map[0].length, new CallMe() {
            public void call(int x, int y) {
                if (y == 0) {
                    builder.append('\n');
                }
                builder.append(map[x][y]);

            }
        });
        return builder.toString();
    }


}
