package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Wursteisen David
 * Date: 29/09/12
 * Time: 15:20
 */
public class Grille {

    private int[][] map;
    private final Random random = new Random();
    int h;
    int w;

    public Grille initRandomMap(int h, int w) {
        this.h = h;
        this.w = w;
        map = new int[h][w];
        applyOnMap(h, w, new CallMe() {
            public void call(int x, int y) {
                map[x][y] = random.nextInt(2);
            }
        });
        return this;

    }

    public Grille initClearMap(int h, int w) {
        map = new int[h][w];
        this.h = h;
        this.w = w;
        applyOnMap(h, w, new CallMe() {
            public void call(int x, int y) {
                map[x][y] = 0;
            }
        });
        return this;
    }

    public void applyOnMap(int h, int w, CallMe method) {
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                method.call(i, j);
            }
    }

    public Grille initOcialteurMap(int i, int i1) {
        initClearMap(i, i1);
        map[0][1] = 1;
        map[0][2] = 1;
        map[0][3] = 1;
        return this;
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
                int val = Grille.this.neighboursCounter(x, y);
                int newState = newStatus.get(val).intValue();
                newGrille.setCell(x, y, newState);
            }
        });
        return newGrille;
    }

    // voisin, futur etat
    private static final Map<Integer, Integer> newStatus = new HashMap<Integer, Integer>();

    static {
        newStatus.put(0, 0);
        newStatus.put(1, 0);
        newStatus.put(2, 0);
        newStatus.put(3, 1);
        newStatus.put(4, 0);
        newStatus.put(5, 0);
        newStatus.put(6, 0);
        newStatus.put(7, 0);
        newStatus.put(8, 0);

    }


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
