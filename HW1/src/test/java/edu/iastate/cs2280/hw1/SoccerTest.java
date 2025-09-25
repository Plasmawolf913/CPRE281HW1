package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Soccer Tests
 * 
 * @author tfolkers
 */
public class SoccerTest {

    private NeighborhoodGrid grid3() { return new NeighborhoodGrid(3); }

    private void fillIfNullWithNothing(NeighborhoodGrid g) {
        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                if (g.grid[r][c] == null) g.grid[r][c] = new Nothing(g, r, c);
            }
        }
    }

    /* Rule 1: If its interest level reaches the maximum, it changes to a Nothing household. */
    @Test
    void maxInterest_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, Household.MAX_INTEREST);
        fillIfNullWithNothing(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing);
        assertEquals(Sports.NOTHING, next.getPreference());
    }

    /* Rule 2: If (Football + Basketball) >= Soccer, it changes to Everything. */
    @Test
    void footballPlusBasketballAtLeastSoccer_becomesEverything() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, 2);

        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);

        g.grid[0][2] = new Football(g, 0, 2, 0);
        g.grid[1][0] = new Basketball(g, 1, 0, 0);

        g.grid[1][2] = new Football(g, 1, 2,0);
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Everything);
        assertEquals(Sports.EVERYTHING, next.getPreference(),
                "When (F+Bask) >= S, Soccer should become Everything.");
    }

    /* Rule 3: If S > (Baseball + Basketball + Football + Rugby), it becomes Soccer(5). */
    @Test
    void soccerDominatesAllOthers_becomesSoccerLevel5() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, 3);

        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);
        g.grid[0][2] = new Soccer(g, 0, 2, 0);
        g.grid[1][0] = new Soccer(g, 1, 0, 0);

        g.grid[1][2] = new Baseball(g, 1, 2, 0);
        g.grid[2][0] = new Football(g, 2, 0, 0);
        g.grid[2][1] = new Rugby(g, 2, 1, 0);

        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(5, ((Soccer) next).getInterest(),
                "When S > (A+B+F+R), Soccer should become Soccer with interest 5.");
    }

    /* Rule 4: If there are more than 4 neighboring Rugby households, it becomes Rugby(3). */
    @Test
    void moreThanFourRugbyNeighbors_becomesRugbyLevel3() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, 1);

        g.grid[0][0] = new Rugby(g, 0, 0, 0);
        g.grid[0][1] = new Rugby(g, 0, 1, 0);
        g.grid[0][2] = new Rugby(g, 0, 2, 0);
        g.grid[1][0] = new Rugby(g, 1, 0, 0);
        g.grid[1][2] = new Rugby(g, 1, 2, 0);

        
        g.grid[2][0] = new Everything(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Rugby);
        assertEquals(3, ((Rugby) next).getInterest(),
                "With >4 Rugby neighbors, Soccer should become Rugby with interest 3.");
    }

    /* Rule 5: Otherwise, if there are no neighboring Everything households, it becomes Nothing. */
    @Test
    void noEverythingNeighbors_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, 2);

        g.grid[0][0] = new Football(g, 0, 0, 0);
        g.grid[0][1] = new Basketball(g, 0, 1, 0);
        g.grid[0][2] = new Baseball(g, 0, 2, 0);
        g.grid[1][0] = new Rugby(g, 1, 0, 0);
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Soccer(g, 2, 0, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Soccer(g, 2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing,
                "If no Everything neighbors and higher-priority rules don't trigger, Soccer becomes Nothing.");
    }

    /* Default case: otherwise, interest increases by one and stays Soccer. */
    @Test
    void otherwise_interestIncreases_andRemainsSoccer() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Soccer(g, 1, 1, 3);


        g.grid[0][0] = new Soccer(g, 0, 0, 0);     
        g.grid[0][1] = new Soccer(g, 0, 1, 0);    
        g.grid[0][2] = new Football(g, 0, 2, 0);   
        g.grid[1][0] = new Baseball(g, 1, 0, 0);   
        g.grid[1][2] = new Rugby(g, 1, 2, 0);     
        g.grid[2][0] = new Everything(g, 2, 0);    
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(4, ((Soccer) next).getInterest(),
                "Default path: interest should increase by 1 and remain Soccer.");
    }
}
