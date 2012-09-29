package game;

import org.junit.Test;

/**
 * User: Wursteisen David
 * Date: 29/09/12
 * Time: 15:26
 */
public class GrilleTest {
    @Test
    public void testInitMap() throws Exception {
        Grille grille = new Grille().initOcialteurMap(5, 5);
        System.out.print(grille.toString());
        System.out.println("\n-------------");
        System.out.print(grille.neighboursCounter(3, 3));
        System.out.println("-------------");
        System.out.print(grille.step().toString());

    }
}
