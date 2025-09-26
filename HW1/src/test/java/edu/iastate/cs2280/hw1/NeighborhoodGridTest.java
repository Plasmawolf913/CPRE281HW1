package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Tests for NeighborhoodGrid class.
 * 
 * @author tfolkers
 */
public class NeighborhoodGridTest {

    private NeighborhoodGrid grid3() { return new NeighborhoodGrid(3); }

    /* ---------------- Constructor Tests ---------------- */

    @Test
    void constructorWithSize_createsEmptySquareGrid() {
        NeighborhoodGrid g = new NeighborhoodGrid(4);
        assertEquals(4, g.getSize(), "Size should match constructor arg.");
        assertEquals(4, g.grid.length, "Grid must have 4 rows.");
        for (int r = 0; r < 4; r++) {
            assertEquals(4, g.grid[r].length, "Each row must have 4 columns.");
        }
    }



    /* ---------------- Method Tests ---------------- */

    @Test
    void getSize_returnsCorrectValue() {
        NeighborhoodGrid g = new NeighborhoodGrid(5);
        assertEquals(5, g.getSize());
    }

    @Test
    void randomInit_fillsSquareGrid() {
        NeighborhoodGrid g = new NeighborhoodGrid(5);
        g.randomInit();

        for (int r = 0; r < g.getSize(); r++) {
            for (int c = 0; c < g.getSize(); c++) {
                assertNotNull(g.grid[r][c], "randomInit must fill every cell.");
            }
            assertEquals(5, g.grid[r].length, "Each row must have exactly 5 columns.");
        }
    }

    @Test
    void toString_containsHouseholdCodes() {
        NeighborhoodGrid g = new NeighborhoodGrid(2);
        g.grid[0][0] = new Basketball(g, 0, 0, 3);
        g.grid[0][1] = new Nothing(g, 0, 1);
        g.grid[1][0] = new Football(g, 1, 0, 2);
        g.grid[1][1] = new Rugby(g, 1, 1, 1);

        String s = g.toString();
        assertTrue(s.contains("B3"));
        assertTrue(s.contains("N"));
        assertTrue(s.contains("F2"));
        assertTrue(s.contains("R1"));
    }

    @Test
    void write_outputsSquareGridToFile() throws Exception {
        NeighborhoodGrid g = new NeighborhoodGrid(2);
        g.grid[0][0] = new Basketball(g, 0, 0, 3);
        g.grid[0][1] = new Nothing(g, 0, 1);
        g.grid[1][0] = new Football(g, 1, 0, 2);
        g.grid[1][1] = new Rugby(g, 1, 1, 1);

        Path tempOut = Files.createTempFile("gridOut", ".txt");
        g.write(tempOut.toString());

        String content = Files.readString(tempOut);
        assertTrue(content.contains("B3"));
        assertTrue(content.contains("N"));
        assertTrue(content.contains("F2"));
        assertTrue(content.contains("R1"));

        Files.deleteIfExists(tempOut);
    }
}
