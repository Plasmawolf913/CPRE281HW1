package edu.iastate.cs2280.hw1;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Performs simulations over the neighborhood grid.
 *
 * @author
 */
public class Neighborhood {
    /**
     * Updates the grid by calculating the next state of each household in the grid.
     * Each household in the old grid determines its new state and updates the
     * corresponding position in the new grid.
     *
     * @param oldGrid      the current state of the neighborhood grid (old grid)
     * @param newGrid      the updated state of the neighborhood grid (new grid)
     * @param currentMonth the current month of the simulation, used for household updates
     */
    public static void updateGrid(NeighborhoodGrid oldGrid, NeighborhoodGrid newGrid, int currentMonth) {

    	for(int r = 0; r < oldGrid.grid.length; r++) {
    		for(int c = 0; c < oldGrid.grid[0].length; c++) {
    			
    			newGrid.grid[r][c] = oldGrid.grid[r][c].next(newGrid, currentMonth); //why pass new grid in??? maybe that happens in next()
    			
    		}
    	}
    	
    }

    /**
     * The main method serves as the entry point for the simulation of neighborhood sports preferences.
     * It allows users to initialize grids (randomly or from a file), set the duration of the simulation,
     * and view the evolution of the grid over time.
     *
     * @param args command-line arguments passed to the application
     * @throws FileNotFoundException  if the specified file for grid input is not found
     * @throws ParseException         if there is an error in parsing the input file for the grid
     * @throws InputMismatchException if user input does not match the expected input type
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, InputMismatchException {
        System.out.println("Neighborhood Sports Preferences");
        System.out.println("===============================");
        Scanner scan = new Scanner(System.in);
        
        int quit = 0;
        int count = 0;
        while(quit == 0) {
        System.out.println("Keys: 1 (random grid); 2 (file input); 3 (exit)");
        String initialInput = scan.next();
        
        if(initialInput == "1") {
        	System.out.println("Enter grid width: "); //need to do inputmismatch + other exceptions
        	int width = scan.nextInt();
            
        	System.out.println("Simulation Number: " + count + " Random Grid");
            System.out.println("Enter the number of months: ");
            int numMonths = scan.nextInt();
            
        	
        	NeighborhoodGrid neighborhoodGrid = new NeighborhoodGrid(width);
        	neighborhoodGrid.randomInit();
        	
        	neighborhoodGrid.toString();
        	for(int i = 1; i <= numMonths; i++) {
        		NeighborhoodGrid newGrid = new NeighborhoodGrid(width);
        		updateGrid(neighborhoodGrid, newGrid, i);
        		System.out.println("Updated Grid for month " + i);
        		neighborhoodGrid.toString();
        	}
        	
        	
        }else if(initialInput == "2") {
        	System.out.println("Please enter the name of your file: ");
        	String inputFileName = scan.next();
        	
        	System.out.println("Enter grid width: "); //need to do inputmismatch + other exceptions
        	int width = scan.nextInt();
        	
        	System.out.println("Simulation Number: " + count + " File Input");
        	System.out.println("Enter the number of months: ");
            int numMonths = scan.nextInt();
        	
        	NeighborhoodGrid neighborhoodGrid = new NeighborhoodGrid(inputFileName);
        	
        	System.out.println("Initial Grid:");
        	neighborhoodGrid.toString();
        	for(int i = 1; i <= numMonths; i++) {
        		NeighborhoodGrid newGrid = new NeighborhoodGrid(width);
        		updateGrid(neighborhoodGrid, newGrid, i);
        		System.out.println("Updated Grid for month " + i);
        		neighborhoodGrid.toString();
        	}
        	
        }else if(initialInput == "3") {
        	quit = 1;
        }
        


        
        
        
        count++;
        }
        

    }
}
