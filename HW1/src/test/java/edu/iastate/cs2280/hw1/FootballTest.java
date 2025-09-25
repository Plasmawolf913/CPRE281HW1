package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for Football.next() according to HW1 spec §5.4.
 */
public class FootballTest {

    private NeighborhoodGrid grid3() {
        return new NeighborhoodGrid(3);
    }

    private void fillWithNothing(NeighborhoodGrid g) {
        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                if (g.grid[r][c] == null) {
                    g.grid[r][c] = new Nothing(g, r, c);
                }
            }
        }
    }

    /**
     * April–June: if ≥1 Everything or Nothing neighbor, interest increases by 1 (capped),
     * and type does not change.
     */
    @Test
    void aprToJun_increasesInterest_withENeighbor() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, 2);

        g.grid[0][0] = new Everything(g, 0, 0); // at least one E neighbor
        fillWithNothing(g);

        Household next = g.grid[1][1].next(g, 4); // April
        assertTrue(next instanceof Football);
        assertEquals(3, ((Football) next).getInterest(),
                "Should increase interest by 1 and remain Football in Apr–Jun when an E neighbor exists.");
    }

    

    /** Rule 1: At max interest, becomes Nothing. */
    @Test
    void maxInterest_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, Household.MAX_INTEREST);
        fillWithNothing(g);

        Household next = g.grid[1][1].next(g, 1);
        assertTrue(next instanceof Nothing);
    }

    /** Rule 2: (B + Bask + F) > 7 → Everything. */
    @Test
    void sumBBFgreaterThan7_becomesEverything() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, 0);

        // Place 7 more Baseball/Basketball/Football neighbors
        g.grid[0][0] = new Baseball(g, 0, 0, 1);
        g.grid[0][1] = new Basketball(g, 0, 1, 2);
        g.grid[0][2] = new Football(g, 0, 2, 1);
        g.grid[1][0] = new Baseball(g, 1, 0, 2);
        g.grid[1][2] = new Basketball(g, 1, 2, 3);
        g.grid[2][0] = new Football(g, 2, 0, 0);
        g.grid[2][1] = new Baseball(g, 2, 1, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 7); // July (normal rules)
        assertTrue(next instanceof Everything);
    }

    /** Rule 3: >3 Basketball neighbors → Basketball(0). */
    @Test
    void moreThan3BasketballNeighbors_becomesBasketball0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, 2);

        g.grid[0][0] = new Basketball(g, 0, 0, 1);
        g.grid[0][1] = new Basketball(g, 0, 1, 2);
        g.grid[0][2] = new Basketball(g, 0, 2, 3);
        g.grid[1][0] = new Basketball(g, 1, 0, 0); // 4 total
        fillWithNothing(g);

        Household next = g.grid[1][1].next(g, 7);
        assertTrue(next instanceof Basketball);
        assertEquals(0, ((Basketball) next).getInterest());
    }

    /** Rule 4: <2 Football neighbors → Baseball(0). */
    @Test
    void fewerThan2FootballNeighbors_becomesBaseball0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, 3);
        // No other Footballs, so count = 1 (just self)
        fillWithNothing(g);

        Household next = g.grid[1][1].next(g, 2);
        assertTrue(next instanceof Baseball);
        assertEquals(0, ((Baseball) next).getInterest());
    }

    /** Otherwise: interest increments by 1 and stays Football. */
    @Test
    void defaultCase_incrementsInterest() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Football(g, 1, 1, 2);

        // One extra Football neighbor so FOOTBALL count ≥ 2
        g.grid[0][0] = new Football(g, 0, 0, 0);
        fillWithNothing(g);

        Household next = g.grid[1][1].next(g, 8); // August
        assertTrue(next instanceof Football);
        assertEquals(3, ((Football) next).getInterest());
    }
}
