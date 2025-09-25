package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Basketball.next() rules.
 * 
 * @author tfolkers
 */
public class BasketballTest {

    /**
     * Rule 1: If interest reaches maximum, change to Nothing.
     */
    @Test
    void basketballAtMaxInterestBecomesNothing() {
        NeighborhoodGrid g = new NeighborhoodGrid(1);
        g.grid[0][0] = new Basketball(g, 0, 0, Household.MAX_INTEREST);

        Household next = g.grid[0][0].next(g, 0);

        assertEquals(Sports.NOTHING, next.getPreference(),
                "Basketball with max interest should become Nothing.");
        assertTrue(next instanceof Nothing);
    }

    /**
     * Rule 2 (example): If > 2 neighboring Baseball households, it becomes Baseball(interest 0).
     */
    @Test
    void basketballBecomesBaseballWithEnoughBaseballNeighbors() {
        NeighborhoodGrid g = new NeighborhoodGrid(3);

        // Place Basketball center
        g.grid[1][1] = new Basketball(g, 1, 1, 2);

        // Surround with Baseball neighbors
        g.grid[0][0] = new Soccer(g, 0, 0, 1);
        g.grid[0][1] = new Soccer(g, 0, 1, 3);
        g.grid[0][2] = new Soccer(g, 0, 2, 2);

        // Fill rest with Nothing to simplify
        g.grid[1][0] = new Nothing(g, 1, 0);
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);

        assertEquals(Sports.SOCCER, next.getPreference(),
                "Basketball with >2 Baseball neighbors should become Baseball.");
        assertTrue(next instanceof Soccer);
        assertEquals(0, ((Soccer) next).getInterest(),
                "New Baseball household should start at interest 0.");
    }

    /**
     * Otherwise: Interest increases by 1.
     */
    @Test
    void basketballInterestIncrementsNormally() {
    	NeighborhoodGrid g = new NeighborhoodGrid(3);

        // Place Basketball center
        g.grid[1][1] = new Basketball(g, 1, 1, 2);

        // Surround with Baseball neighbors
        g.grid[0][0] = new Basketball(g, 0, 0, 1);
        g.grid[0][1] = new Basketball(g, 0, 1, 3);
        g.grid[0][2] = new Basketball(g, 0, 2, 2);

        // Fill rest with Nothing to simplify
        g.grid[1][0] = new Nothing(g, 1, 0);
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);

        assertEquals(Sports.BASKETBALL, next.getPreference());
        assertTrue(next instanceof Basketball);
        assertEquals(3, ((Basketball) next).getInterest(),
                "Interest should increment by 1 when no other rule applies.");
    }
}
