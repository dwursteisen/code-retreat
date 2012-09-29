package game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Wursteisen David
 * Date: 29/09/12
 * Time: 15:26
 */
public class GrilleTest {
    @Test
    public void testInitMap() throws Exception {
        Grille grille = Grille.initOscillateurMap(5, 5);
        System.out.println(grille);
        System.out.println("-------------");
        System.out.println("Nombre de voisin de 3, 3 => " + grille.neighboursCounter(3, 3));
        for (int i = 0; i < 30; i++) {
            System.out.println("-------------");
            grille = grille.step();
            System.out.println(grille);
        }
    }


    @Test
    public void neighboursCounter() {
        Grille grille = Grille.initOscillateurMap(5, 5);
        int result = grille.neighboursCounter(1, 1);
        assertEquals(0, result);
    }


    @Test
    public void neighboursCounter2() {
        Grille grille = Grille.initOscillateurMap(5, 5);
        int result = grille.neighboursCounter(3, 1);
        assertEquals(1, result);
    }

    @Test
    public void neighboursCounter3() {
        Grille grille = Grille.initOscillateurMap(5, 5);
        int result = grille.neighboursCounter(3, 3);
        assertEquals(1, result);
    }

    @Test
    public void neighboursCounter4() {
        Grille grille = Grille.initOscillateurMap(5, 5);
        int result = grille.neighboursCounter(3, 2);
        assertEquals(2, result);
    }
}
