package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Neighborhood driver and updateGrid().
 */
public class NeighborhoodTest {

    private PrintStream originalOut;
    private java.io.InputStream originalIn;

    @BeforeEach
    void saveStd() {
        originalOut = System.out;
        originalIn = System.in;
    }

    @AfterEach
    void restoreStd() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    // -------------------- Unit test for updateGrid(...) --------------------

    @Test
    void updateGrid_appliesNext_andDoesNotMutateOld() {
        // oldGrid is 1x1 with Football at MAX_INTEREST so it must become Nothing next month
        NeighborhoodGrid oldGrid = new NeighborhoodGrid(1);
        oldGrid.grid[0][0] = new Football(oldGrid, 0, 0, Household.MAX_INTEREST);

        NeighborhoodGrid newGrid = new NeighborhoodGrid(1);

        // Exercise
        Neighborhood.updateGrid(oldGrid, newGrid, /*currentMonth*/ 0);

        // Verify newGrid gets the "next" household
        assertTrue(newGrid.grid[0][0] instanceof Nothing,
                "Expected newGrid[0][0] to be Nothing when Football is at max interest.");

        // Verify oldGrid not mutated
        assertTrue(oldGrid.grid[0][0] instanceof Football,
                "oldGrid should not be mutated by updateGrid (still Football).");
        assertEquals(Household.MAX_INTEREST,
                ((Football) oldGrid.grid[0][0]).getInterest(),
                "oldGrid cell interest should be unchanged.");
    }

    // -------------------- Integration tests for main(...) --------------------

    @Test
    void main_loadFromFile_runsSimulation_andPrintsMonths() throws Exception {
        // Prepare an 3x3 file with a few types (easy to see in output)
        Path temp = Files.createTempFile("grid_in", ".txt");
        try {
            Files.write(temp, Arrays.asList(
                "A0 N  E",
                "F2 B3 R1",
                "E  N  B4"
            ));

            // Simulate console input:
            // 2  -> load from file
            // <path>
            // 2  -> simulate 2 months (prints Month 0, Month 1, Month 2)
            // 3  -> exit
            String input = String.join(System.lineSeparator(),
                "2",                       // choose "load from file"
                temp.toString(),           // input file path
                "2",                       // months to simulate
                "3"                        // exit
            ) + System.lineSeparator();

            ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outBuf));
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            Neighborhood.main(new String[0]);

            String out = outBuf.toString();

            // Very tolerant checks so you don't have to match exact formatting
            assertTrue(out.toLowerCase().contains("month 0") || out.contains("Month: 0"),
                    "Output should include Month 0.");
            assertTrue(out.toLowerCase().contains("month 1") || out.contains("Month: 1"),
                    "Output should include Month 1.");
            assertTrue(out.toLowerCase().contains("month 2") || out.contains("Month: 2"),
                    "Output should include Month 2.");

            // Spot-check that some tokens from the file appeared at least once
            assertTrue(out.contains("A0") || out.contains("A0 "),
                    "Output should show Baseball A0 at least once.");
            assertTrue(out.contains("E")  || out.contains("E "),
                    "Output should show Everything at least once.");
            assertTrue(out.contains("B3") || out.contains("B3 "),
                    "Output should show Basketball B3 at least once.");

        } finally {
            Files.deleteIfExists(temp);
        }
    }



}
