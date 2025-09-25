package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for Baseball.next() 
 * 
 * @author tfolkers
 */
public class BaseballTest {

    private NeighborhoodGrid grid3() { return new NeighborhoodGrid(3); }

    private void fillWithNothingIfNull(NeighborhoodGrid g) {
        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                if (g.grid[r][c] == null) {
                    g.grid[r][c] = new Nothing(g, r, c);
                }
            }
        }
    }

    /* Rule 1: Max interest -> Nothing */
    @Test
    void maxInterest_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, Household.MAX_INTEREST);
        fillWithNothingIfNull(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing);
        assertEquals(Sports.NOTHING, next.getPreference());
    }

    /* Rule 2: >3 Soccer neighbors -> Soccer(0) */
    @Test
    void moreThanThreeSoccerNeighbors_becomesSoccer0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, 2);

        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);
        g.grid[0][2] = new Soccer(g, 0, 2, 0);
        g.grid[1][0] = new Soccer(g, 1, 0, 0); // 4 total

        fillWithNothingIfNull(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(0, ((Soccer) next).getInterest(),
                "Spec: >3 Soccer neighbors → Soccer with interest 0.");
    }

    /* Rule 3: <2 Baseball neighbors -> Rugby(0) */
    @Test
    void fewerThanTwoBaseballNeighbors_becomesRugby0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, 1);

        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Football(g, 0, 1, 0);
        g.grid[0][2] = new Basketball(g, 0, 2, 0);
        g.grid[1][0] = new Nothing(g, 1, 0);
        g.grid[1][2] = new Everything(g, 1, 2);
        g.grid[2][0] = new Rugby(g, 2, 0, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Soccer(g, 2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Rugby);
        assertEquals(0, ((Rugby) next).getInterest(),
                "Spec: <2 Baseball neighbors → Rugby with interest 0.");
    }

    /* Rule 4: (Baseball + Soccer) > 5 -> Nothing */
    @Test
    void baseballPlusSoccerGreaterThan5_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, 2);

        // 3 Soccer + 3 Baseball = 6 (>5)
        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);
        g.grid[0][2] = new Soccer(g, 0, 2, 0);
        g.grid[1][0] = new Baseball(g, 1, 0, 1);
        g.grid[1][2] = new Baseball(g, 1, 2, 2);
        g.grid[2][0] = new Baseball(g, 2, 0, 0);

        fillWithNothingIfNull(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing,
                "Spec: (Baseball + Soccer) > 5 → Nothing household.");
    }

    /* Rule 5: #Football > 2 * #Baseball -> Football(0) */
    @Test
    void footballMoreThanTwiceBaseball_becomesFootball0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, 3);

        g.grid[0][0] = new Baseball(g, 0, 0, 0);

        g.grid[0][1] = new Football(g, 0, 1, 0);
        g.grid[0][2] = new Football(g, 0, 2, 0);
        g.grid[1][0] = new Football(g, 1, 0, 0);
        g.grid[1][2] = new Football(g, 1, 2, 0);
        g.grid[2][0] = new Football(g, 2, 0, 0);

        fillWithNothingIfNull(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Football);
        assertEquals(0, ((Football) next).getInterest(),
                "Spec: if #Football > 2*#Baseball → Football with interest 0.");
    }

    /* Default: interest +1, remain Baseball */
    @Test
    void otherwise_interestIncreasesAndRemainsBaseball() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Baseball(g, 1, 1, 2);


        g.grid[0][0] = new Baseball(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);
        g.grid[0][2] = new Basketball(g, 0, 2, 0);
        g.grid[1][0] = new Nothing(g, 1, 0);
        g.grid[1][2] = new Everything(g, 1, 2);
        g.grid[2][0] = new Rugby(g, 2, 0, 0);
        g.grid[2][1] = new Basketball(g, 2, 1, 0);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Baseball);
        assertEquals(3, ((Baseball) next).getInterest(),
                "Default: Baseball should increment interest by 1.");
    }
}
