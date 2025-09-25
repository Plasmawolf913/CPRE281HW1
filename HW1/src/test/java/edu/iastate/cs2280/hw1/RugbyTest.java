package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class RugbyTest {

    private NeighborhoodGrid grid3() {
        return new NeighborhoodGrid(3);
    }

    private void fillWithNothingIfNull(NeighborhoodGrid g) {
        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                if (g.grid[r][c] == null) {
                    g.grid[r][c] = new Nothing(g, r, c);
                }
            }
        }
    }

    /* ---------- Rule 1: Max interest -> Nothing ---------- */
    @Test
    void maxInterest_becomesNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Rugby(g, 1, 1, Household.MAX_INTEREST);
        fillWithNothingIfNull(g);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing);
        assertEquals(Sports.NOTHING, next.getPreference());
    }

    /* ---------- Rule 2: (F + S) >= 8 -> Soccer(2) ---------- */
    @Test
    void eightOrMoreFootballPlusSoccerNeighbors_becomesSoccerLevel2() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Rugby(g, 1, 1, 1);

        // Fill all 8 neighbors with Football/Soccer so F+S == 8
        g.grid[0][0] = new Football(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g,   0, 1, 0);
        g.grid[0][2] = new Football(g, 0, 2, 0);
        g.grid[1][0] = new Soccer(g,   1, 0, 1);
        g.grid[1][2] = new Football(g, 1, 2, 2);
        g.grid[2][0] = new Soccer(g,   2, 0, 0);
        g.grid[2][1] = new Football(g, 2, 1, 1);
        g.grid[2][2] = new Soccer(g,   2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(Sports.SOCCER, next.getPreference());
        assertEquals(2, ((Soccer) next).getInterest(),
                "Spec: if (F+S) >= 8, Rugby becomes Soccer with interest 2.");
    }

    /* ---------- Rule 3: #Baseball > 2 * #Basketball -> Baseball(4) ---------- */
    @Test
    void baseballMoreThanTwiceBasketball_becomesBaseballLevel4() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Rugby(g, 1, 1, 2);

        // Use 4 Baseball, 1 Basketball, 3 Rugby neighbors to ensure:
        // - B = 4, Bask = 1  -> 4 > 2*1  (true)
        // - Rugby neighbors = 3 (>=2) so Rule 4 won't steal priority
        // - F+S kept at 0 so Rule 2 won't trigger
        g.grid[0][0] = new Baseball(g, 0, 0, 1);
        g.grid[0][1] = new Baseball(g, 0, 1, 2);
        g.grid[0][2] = new Baseball(g, 0, 2, 3);
        g.grid[1][0] = new Baseball(g, 1, 0, 0);

        g.grid[1][2] = new Basketball(g, 1, 2, 2);

        g.grid[2][0] = new Rugby(g, 2, 0, 0);
        g.grid[2][1] = new Rugby(g, 2, 1, 0);
        g.grid[2][2] = new Rugby(g, 2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Baseball);
        assertEquals(Sports.BASEBALL, next.getPreference());
        assertEquals(4, ((Baseball) next).getInterest(),
                "Spec: if B > 2*Basketball, Rugby becomes Baseball with interest 4.");
    }

    /* ---------- Rule 4: #Rugby < 2 -> Football(0) ---------- */
    @Test
    void fewerThanTwoRugbyNeighbors_becomesFootballLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Rugby(g, 1, 1, 3);

        // Make all neighbors non-Rugby so rugby-neighbor count = 0 (<2).
        // Also avoid Rule 2 (keep F+S small) and Rule 3 (balance B vs Bask).
        g.grid[0][0] = new Baseball(g, 0, 0, 1);
        g.grid[0][1] = new Basketball(g, 0, 1, 1);
        g.grid[0][2] = new Nothing(g,   0, 2);
        g.grid[1][0] = new Everything(g,1, 0);
        g.grid[1][2] = new Nothing(g,   1, 2);
        g.grid[2][0] = new Baseball(g,  2, 0, 0);
        g.grid[2][1] = new Basketball(g,2, 1, 0);
        g.grid[2][2] = new Nothing(g,   2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Football);
        assertEquals(Sports.FOOTBALL, next.getPreference());
        assertEquals(0, ((Football) next).getInterest(),
                "Spec: if #Rugby neighbors < 2, Rugby becomes Football with interest 0.");
    }

    /* ---------- Otherwise: interest++ and stay Rugby ---------- */
    @Test
    void otherwise_interestIncreasesAndRemainsRugby() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Rugby(g, 1, 1, 1);

        // Ensure none of the four rules trigger:
        // - Not max interest
        // - (F+S) < 8 (put only 2 total)
        // - Baseball NOT > 2*Basketball (set B=2, Bask=2)
        // - #Rugby neighbors >= 2 (set exactly 2)
        g.grid[0][0] = new Baseball(g,   0, 0, 0);
        g.grid[0][1] = new Basketball(g, 0, 1, 0);
        g.grid[0][2] = new Rugby(g,      0, 2, 0);
        g.grid[1][0] = new Baseball(g,   1, 0, 1);
        g.grid[1][2] = new Basketball(g, 1, 2, 1);
        g.grid[2][0] = new Rugby(g,      2, 0, 0);
        g.grid[2][1] = new Football(g,   2, 1, 0); // F+S total stays low
        g.grid[2][2] = new Nothing(g,    2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Rugby);
        assertEquals(Sports.RUGBY, next.getPreference());
        assertEquals(2, ((Rugby) next).getInterest(),
                "Default: Rugby interest increases by one.");
    }
}
