package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 * Everything Tests
 * 
 * @author tfolkers
 */
public class EverythingTest {

    private NeighborhoodGrid grid3() { return new NeighborhoodGrid(3); }

    /* ---------------- Rule 1 ------------- */
    @Test
    void soccerAtLeastTripleEverything_becomesSoccerLevel3() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Everything(g, 1, 1); // center

        // neighbors:
        g.grid[0][0] = new Nothing(g, 0, 0); 
        g.grid[0][1] = new Soccer(g, 0, 1, 0);  // S = 1
        g.grid[0][2] = new Soccer(g, 0, 2, 0);  // S = 2
        g.grid[1][0] = new Soccer(g, 1, 0, 0);  // S = 3
        g.grid[1][2] = new Nothing(g, 1, 2);    // N >= F+S guard
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);    // R = 0, F = 0

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(3, ((Soccer) next).getInterest(),
                "Rule 1: #S >= 3*#E → Soccer with interest 3.");
    }

    /* ---------------- Rule 2 ---------------- */
    @Test
    void moreThanThreeFootballNeighbors_becomesFootballLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Everything(g, 1, 1);

        g.grid[0][0] = new Football(g, 0, 0, 0);
        g.grid[0][1] = new Football(g, 0, 1, 0);
        g.grid[0][2] = new Football(g, 0, 2, 0);
        g.grid[1][0] = new Football(g, 1, 0, 0);   // F = 4 (>3)

        g.grid[1][2] = new Everything(g, 1, 2);    // E = 1
        g.grid[2][0] = new Soccer(g, 2, 0, 0);     // S = 1
        g.grid[2][1] = new Nothing(g, 2, 1);       // N large enough
        g.grid[2][2] = new Soccer(g, 2, 2, 0);     // S = 2 (< 3*E = 3) OK

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Football);
        assertEquals(0, ((Football) next).getInterest(),
                "Rule 2: >3 Football neighbors → Football(0).");
    }

    /* ---------------- Rule 3 -------------- */
    @Test
    void nothingLessThanFootballPlusSoccer_becomesBasketballLevel2() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Everything(g, 1, 1);

        g.grid[0][0] = new Everything(g, 0, 0);    // E = 1
        g.grid[0][1] = new Soccer(g, 0, 1, 0);     // S = 1
        g.grid[0][2] = new Soccer(g, 0, 2, 0);     // S = 2
        g.grid[1][0] = new Football(g, 1, 0, 0);   // F = 1
        g.grid[1][2] = new Nothing(g, 1, 2);       // N = 1
        g.grid[2][0] = new Nothing(g, 2, 0);       // N = 2
        g.grid[2][1] = new Football(g, 2, 1, 0);   // F = 2  → F+S = 4
        g.grid[2][2] = new Nothing(g, 2, 2);       // N = 3 … need N < F+S → make one not-N:
        // change one N to Baseball to reduce N to 2 and keep other rules off:
        g.grid[2][2] = new Baseball(g, 2, 2, 0);   // N = 2, F+S = 4 → N < F+S holds

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Basketball);
        assertEquals(2, ((Basketball) next).getInterest(),
                "Rule 3: N < (F+S) → Basketball(2).");
    }

    /* ---------------- Rule 4 ----------------
       If #Rugby > #Everything → Rugby(0)
       Avoid Rule 1: keep S < 3*E; Rule 2: F ≤ 3; Rule 3: N ≥ F+S. */
    @Test
    void moreRugbyThanEverything_becomesRugbyLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Everything(g, 1, 1);

        g.grid[0][0] = new Everything(g, 0, 0);    // E = 1
        g.grid[0][1] = new Rugby(g, 0, 1, 0);
        g.grid[0][2] = new Rugby(g, 0, 2, 0);      // R = 2 (> E)
        g.grid[1][0] = new Rugby(g, 1, 0, 0);       // N >= F+S guard
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Soccer(g, 2, 0, 0);     // S = 1 (< 3*E = 3)
        g.grid[2][1] = new Football(g, 2, 1, 0);   // F = 1 (≤ 3)
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Rugby);
        assertEquals(0, ((Rugby) next).getInterest(),
                "Rule 4: #Rugby > #Everything → Rugby(0).");
    }

    /* ---------------- Default ----------------
       Otherwise, remain Everything (no interest level). */
    @Test
    void otherwise_remainsEverything() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Everything(g, 1, 1);

        // Make all conditions false:
        // Rule1: S < 3*E  (E=2, S=5 would trigger; so set S=2, E=2 → 2 !>= 6)
        g.grid[0][0] = new Everything(g, 0, 0);
        g.grid[0][1] = new Everything(g, 0, 1);    // E = 2
        g.grid[0][2] = new Soccer(g, 0, 2, 0);
        g.grid[1][0] = new Soccer(g, 1, 0, 0);     // S = 2  (< 3*E = 6)
        // Rule2: F ≤ 3
        g.grid[1][2] = new Football(g, 1, 2, 0);   // F = 1
        // Rule3: N ≥ F+S
        g.grid[2][0] = new Nothing(g, 2, 0);       // N = 1
        g.grid[2][1] = new Nothing(g, 2, 1);       // N = 2  → N(2) ≥ F+S(3)? Not yet.
        // Reduce F+S to 2 total to satisfy N ≥ F+S:
        g.grid[1][2] = new Nothing(g, 1, 2);       // F = 0; S still 2; N now 3 → N(3) ≥ F+S(2)
        // Rule4: R ≤ E
        g.grid[2][2] = new Rugby(g, 2, 2, 0);      // R = 1 ≤ E = 2

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Everything,
                "Default: when none of the rules 1–4 apply, remain Everything.");
        assertEquals(Sports.EVERYTHING, next.getPreference());
    }
}
