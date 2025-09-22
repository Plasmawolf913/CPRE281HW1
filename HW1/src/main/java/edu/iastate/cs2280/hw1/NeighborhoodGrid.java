package edu.iastate.cs2280.hw1;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * NeighborhoodGrid refers to the households' layout for each simulation.
 * It is a square grid [size X size].
 *
 * @author
 */
public class NeighborhoodGrid {
    /**
     * Represents the size of the neighborhood grid.
     */
    private int size; //changed, was final
    /**
     * A 2D grid representing a neighborhood where each cell is occupied by a household.
     * Each the rows and columns define the position of a household within the neighborhood.
     */
    public Household[][] grid;

    /**
     * Constructs a NeighborhoodGrid by reading household data from an input file.
     * Each square in the grid corresponds to a household defined by a specific letter
     * and (if applicable) a passion level.
     *
     * @param inputFileName the name of the file that contains the household data
     *                      used to populate the grid
     * @throws FileNotFoundException if the specified file does not exist or cannot be opened
     * @throws ParseException        if the data in the file is not in the expected format or
     *                               contains invalid household specifications
     */
    public NeighborhoodGrid(String inputFileName) throws FileNotFoundException, ParseException {
    	ArrayList<String[]> rows = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(inputFileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                // tokens are space-separated like: A1  E   B3  N
                String[] tokens = line.split("\\s+");
                rows.add(tokens);
            }
        }

        if (rows.isEmpty()) {
            throw new ParseException("Input file is empty.", 0);
        }

        // Determine size and validate square grid
          int n = rows.get(0).length;
//        if (n == 0) throw new ParseException("No tokens found in first line.", 0);
//        if (rows.size() != n) {
//            throw new ParseException("Grid must be square: got " + rows.size() + " rows and " + n + " columns.", 0);
//        }
//        for (int i = 1; i < rows.size(); i++) {
//            if (rows.get(i).length != n) {
//                throw new ParseException("Row " + i + " has " + rows.get(i).length + " tokens; expected " + n + ".", i);
//            }
//        }

        this.size = n;
        

        // Fill grid
        for (int r = 0; r < n; r++) {
            String[] tokens = rows.get(r);
            for (int c = 0; c < n; c++) {
                String t = tokens[c];
                if (t.length() < 1) {
                    throw new ParseException("Empty token at (" + r + "," + c + ")", r);
                }

                char kind = t.charAt(0); // A,B,E,F,N,R,S
                // Everything (E) and Nothing (N) have no interest digit
                switch (kind) {
                    case 'E': {
                        grid[r][c] = new Everything(this, r, c);
                        break;
                    }
                    case 'N': {
                        grid[r][c] = new Nothing(this, r, c);
                        break;
                    }
                    case 'A': case 'B': case 'F': case 'R': case 'S': {
                        if (t.length() < 2 || !Character.isDigit(t.charAt(1))) {
                            throw new ParseException("Missing/invalid interest for '" + kind + "' at (" + r + "," + c + "): '" + t + "'", r);
                        }
                        int lvl = t.charAt(1) - '0'; // expect 0–5 per spec
                        switch (kind) {
                            case 'A':
                                grid[r][c] = new Baseball(this, r, c, lvl);
                                break;
                            case 'B':
                                grid[r][c] = new Basketball(this, r, c, lvl);
                                break;
                            case 'F':
                                grid[r][c] = new Football(this, r, c, lvl);
                                break;
                            case 'R':
                                grid[r][c] = new Rugby(this, r, c, lvl);
                                break;
                            case 'S':
                                grid[r][c] = new Soccer(this, r, c, lvl);
                                break;
                        }
                        break;
                    }
                    default:
                        throw new ParseException("Unknown household type '" + kind + "' at (" + r + "," + c + "): '" + t + "'", r);
                }
            }
        }
    
    
    }

    /**
     * Constructs a NeighborhoodGrid of a specified size.
     * If the provided size is greater than zero, it initializes an empty grid of the
     * specified dimensions. Otherwise, it throws an IllegalArgumentException.
     *
     * @param size the size of the grid (number of rows and columns). Must be greater than 0.
     * @throws IllegalArgumentException if the size is not greater than 0.
     */
    public NeighborhoodGrid(int size) {
        this.size = size;
        if (size > 0) {
            grid = new Household[size][size];
        } else {
            throw new IllegalArgumentException("Width must be > 0");
        }
    }

    /**
     * Retrieves the size of the neighborhood grid.
     *
     * @return the size of the grid, representing the number of rows and columns.
     */
    public int getSize() {
        return size;
    }


    /**
     * Randomly initializes the grid of the NeighborhoodGrid instance with various household types.
     * Each cell in the grid is assigned one of the household types (Basketball, Football, Baseball,
     * Soccer, Everything, Nothing, or Rugby) based on a random selection.
     */
    public void randomInit() {
    }

    /**
     * Output the Neighborhood grid.
     * For each square, output the letter associated with the household occupying the square.
     * If the household is interested in a sport, output the interest level in that sport followed
     * by a blank space. Otherwise, output two blank spaces after the letter. One of the blank space
     * is part of toString() implementation of the households
     *
     * @return String
     */
    public String toString() {
    	return "yo";
    }

    /**
     * Write the Neighborhood grid to a file.
     *
     * @param outputFileName
     * @throws FileNotFoundException
     */
    public void write(String outputFileName) throws FileNotFoundException {
    }
}
