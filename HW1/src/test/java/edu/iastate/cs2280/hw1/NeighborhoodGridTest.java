package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Tests for NeighborhoodGrid class.
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

//    @Test
//    void constructorWithFile_parsesSquareGrid() throws Exception {
//        // Create a 3x3 input file
//        Path temp = Files.createTempFile("grid", ".txt");
//        try {
//            Files.write(temp, Arrays.asList(
//                "B3 N F2",
//                "S1 R0 A2",
//                "E N B4"
//            ));
//
//            NeighborhoodGrid g = new NeighborhoodGrid(temp.toString());
//
//            // Square size checks
//            assertEquals(3, g.getSize(), "Grid should be 3x3.");
//            assertEquals(3, g.grid.length, "Grid must have 3 rows.");
//            for (int r = 0; r < g.getSize(); r++) {
//                assertEquals(3, g.grid[r].length, "Each row must have 3 columns.");
//            }
//
//            // Type & interest checks
//            assertTrue(g.grid[0][0] instanceof Basketball);
//            assertEquals(3, ((Basketball) g.grid[0][0]).getInterest());
//            assertTrue(g.grid[0][1] instanceof Nothing);
//            assertTrue(g.grid[0][2] instanceof Football);
//            assertEquals(2, ((Football) g.grid[0][2]).getInterest());
//
//            assertTrue(g.grid[1][0] instanceof Soccer);
//            assertEquals(1, ((Soccer) g.grid[1][0]).getInterest());
//            assertTrue(g.grid[1][1] instanceof Rugby);
//            assertEquals(0, ((Rugby) g.grid[1][1]).getInterest());
//            assertTrue(g.grid[1][2] instanceof Baseball);
//            assertEquals(2, ((Baseball) g.grid[1][2]).getInterest());
//
//            assertTrue(g.grid[2][0] instanceof Everything);
//            assertTrue(g.grid[2][1] instanceof Nothing);
//            assertTrue(g.grid[2][2] instanceof Basketball);
//            assertEquals(4, ((Basketball) g.grid[2][2]).getInterest());
//        } finally {
//            Files.deleteIfExists(temp);
//        }
//    }

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
