package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NothingTest {

    private NeighborhoodGrid grid3() { return new NeighborhoodGrid(3); }

    private void fillIfNullWithNothing(NeighborhoodGrid g) {
        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                if (g.grid[r][c] == null) g.grid[r][c] = new Nothing(g, r, c);
            }
        }
    }

    /* Rule 1: >5 neighboring Soccer -> Soccer(5) */
    @Test
    void moreThanFiveSoccerNeighbors_becomesSoccerLevel5() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // 6 Soccer neighbors (strictly > 5)
        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Soccer(g, 0, 1, 0);
        g.grid[0][2] = new Soccer(g, 0, 2, 0);
        g.grid[1][0] = new Soccer(g, 1, 0, 0);
        g.grid[1][2] = new Soccer(g, 1, 2, 0);
        g.grid[2][0] = new Soccer(g, 2, 0, 0);

        // Remaining two neighbors neutral (avoid other rules)
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Soccer);
        assertEquals(5, ((Soccer) next).getInterest(),
                "With >5 Soccer neighbors, Nothing should become Soccer(5).");
    }

    /* Rule 2: >4 neighboring Football -> Football(0) */
    @Test
    void moreThanFourFootballNeighbors_becomesFootballLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // 5 Football neighbors (>4), ensure Soccer ≤ 5 to not trigger Rule 1
        g.grid[0][0] = new Football(g, 0, 0, 0);
        g.grid[0][1] = new Football(g, 0, 1, 0);
        g.grid[0][2] = new Football(g, 0, 2, 0);
        g.grid[1][0] = new Football(g, 1, 0, 0);
        g.grid[1][2] = new Football(g, 1, 2, 0);

        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Soccer(g, 2, 1, 0); // keep soccer count low
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Football);
        assertEquals(0, ((Football) next).getInterest(),
                "With >4 Football neighbors, Nothing should become Football(0).");
    }

    /* Rule 3: >3 neighboring Basketball -> Basketball(0) */
    @Test
    void moreThanThreeBasketballNeighbors_becomesBasketballLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // 4 Basketball neighbors (>3)
        g.grid[0][0] = new Basketball(g, 0, 0, 0);
        g.grid[0][1] = new Basketball(g, 0, 1, 0);
        g.grid[0][2] = new Basketball(g, 0, 2, 0);
        g.grid[1][0] = new Basketball(g, 1, 0, 0);

        // Keep Soccer ≤5 and Football ≤4 to avoid Rules 1 and 2
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Football(g, 2, 1, 0);
        g.grid[2][2] = new Soccer(g, 2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Basketball);
        assertEquals(0, ((Basketball) next).getInterest(),
                "With >3 Basketball neighbors, Nothing should become Basketball(0).");
    }

    /* Rule 4: >2 neighboring Baseball -> Baseball(0) */
    @Test
    void moreThanTwoBaseballNeighbors_becomesBaseballLevel0() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // 3 Baseball neighbors (>2)
        g.grid[0][0] = new Baseball(g, 0, 0, 0);
        g.grid[0][1] = new Baseball(g, 0, 1, 0);
        g.grid[0][2] = new Baseball(g, 0, 2, 0);

        // Keep higher-priority thresholds below their triggers
        g.grid[1][0] = new Nothing(g, 1, 0);
        g.grid[1][2] = new Nothing(g, 1, 2);
        g.grid[2][0] = new Soccer(g, 2, 0, 0);
        g.grid[2][1] = new Football(g, 2, 1, 0);
        g.grid[2][2] = new Basketball(g, 2, 2, 0);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Baseball);
        assertEquals(0, ((Baseball) next).getInterest(),
                "With >2 Baseball neighbors, Nothing should become Baseball(0).");
    }

    /* Rule 5: >1 neighboring Rugby -> Rugby(2) */
    @Test
    void moreThanOneRugbyNeighbor_becomesRugbyLevel2() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // 2 Rugby neighbors (>1)
        g.grid[0][0] = new Rugby(g, 0, 0, 0);
        g.grid[0][1] = new Rugby(g, 0, 1, 0);

        // Keep earlier rules from triggering
        g.grid[0][2] = new Nothing(g, 0, 2);       // Soccer count ≤5
        g.grid[1][0] = new Football(g, 1, 0, 0);   // Football count ≤4
        g.grid[1][2] = new Basketball(g, 1, 2, 0); // Basketball count ≤3
        g.grid[2][0] = new Baseball(g, 2, 0, 0);   // Baseball count ≤2
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Rugby);
        assertEquals(2, ((Rugby) next).getInterest(),
                "With >1 Rugby neighbor, Nothing should become Rugby(2).");
    }

    /* Rule 6: ≥1 neighboring Everything -> Everything */
    @Test
    void atLeastOneEverythingNeighbor_becomesEverything() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // One Everything neighbor
        g.grid[0][0] = new Everything(g, 0, 0);

        // Keep all earlier-rule thresholds below triggers
        g.grid[0][1] = new Football(g, 0, 1, 0);   // Football ≤4
        g.grid[0][2] = new Basketball(g, 0, 2, 0); // Basketball ≤3
        g.grid[1][0] = new Baseball(g, 1, 0, 0);   // Baseball ≤2 (others set to avoid >2)
        g.grid[1][2] = new Soccer(g, 1, 2, 0);     // Soccer ≤5
        g.grid[2][0] = new Nothing(g, 2, 0);
        g.grid[2][1] = new Nothing(g, 2, 1);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Everything,
                "With ≥1 Everything neighbor (and no higher rule), Nothing should become Everything.");
    }

    /* Rule 7: Otherwise remain Nothing */
    @Test
    void otherwise_remainsNothing() {
        NeighborhoodGrid g = grid3();
        g.grid[1][1] = new Nothing(g, 1, 1);

        // Arrange neighbors so none of the thresholds are exceeded:
        // S ≤ 5, F ≤ 4, Bask ≤ 3, Base ≤ 2, Rugby ≤ 1, and include 0 Everything to avoid Rule 6
        g.grid[0][0] = new Soccer(g, 0, 0, 0);
        g.grid[0][1] = new Football(g, 0, 1, 0);
        g.grid[0][2] = new Basketball(g, 0, 2, 0);
        g.grid[1][0] = new Baseball(g, 1, 0, 0);
        g.grid[1][2] = new Soccer(g, 1, 2, 0);
        g.grid[2][0] = new Football(g, 2, 0, 0);
        g.grid[2][1] = new Basketball(g, 2, 1, 0);
        g.grid[2][2] = new Nothing(g, 2, 2);

        Household next = g.grid[1][1].next(g, 0);
        assertTrue(next instanceof Nothing,
                "When no rule 1–6 triggers, Nothing should remain Nothing.");
        assertEquals(Sports.NOTHING, next.getPreference());
    }
}
