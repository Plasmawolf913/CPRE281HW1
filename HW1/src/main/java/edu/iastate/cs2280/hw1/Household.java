package edu.iastate.cs2280.hw1;

/**
 * The Household class serves as an abstract representation of a household entity
 * within a neighborhood grid. It encapsulates details about the household's
 * location, as well as its preferences regarding sports interests.
 * The class is intended to be extended by specific household types that define
 * concrete preferences and behaviors.
 *
 * @author
 */
public abstract class Household {
    /**
     * An array that keeps track of the number of households in the neighborhood grid
     * with a specific sports preference. Each index in the array corresponds to the ordinal
     * value of a sport in the Sports enum.
     */
    public static final int[] members = new int[Sports.values().length];
    /**
     * The maximum allowable passion or interest level for a household in a specific sports category.
     * This constant is used to determine when a household has reached its peak interest level
     * and can no longer increase its passion for a particular sport.
     */
    public static final int MAX_INTEREST = 5;
    /**
     * The grid in which the household resides.
     */
    protected NeighborhoodGrid neighborhoodGrid;
    /**
     * The row index of the household's position in the neighborhood grid.
     */
    protected int row;
    /**
     * The column index of the household's position within the neighborhood grid.
     */
    protected int column;

    /**
     * Constructs a new Household object at the specified position in the given neighborhood grid.
     *
     * @param grid   the NeighborhoodGrid instance where the household resides
     * @param row    the row index of the household's position in the grid
     * @param column the column index of the household's position in the grid
     */
    public Household(NeighborhoodGrid grid, int row, int column) {
        neighborhoodGrid = grid;
        this.row = row;
        this.column = column;
    }

    /*
     * Surveys the preferences of neighboring households within the neighborhood grid.
     * Each neighboring household's preference is counted and stored in the 'households' array,
     * where each index corresponds to a preference from the Sports enumeration.
     *
     * @param households An array to store the aggregated counts of sports preferences for neighboring households.
     */
    protected void survey(int households[]) {

    	//double for loop, check its not out the grid and continue if so, store with ordinal
    	for(int r = 0; r < neighborhoodGrid.grid.length; r++) {
    		for(int c = 0; c < neighborhoodGrid.grid[0].length; c++) {
    			//if outside grid then continue
    			  // skip out-of-bounds
                if (r < 0 || r >= neighborhoodGrid.grid.length || 
                    c < 0 || c >= neighborhoodGrid.grid[0].length) {
                    continue;
                }
    			
    			
    			Sports x = neighborhoodGrid.grid[r][c].getPreference();
    			
    			households[x.ordinal()]++;
    			

    		}
    	}
    }

    /**
     * Retrieves the sports preference of the household.
     *
     * @return The specific sport preference of the household, represented as a value from the Sports enumeration.
     */
    public abstract Sports getPreference();

    /**
     * Determines the state of the household in the next simulation step based on its current
     * context and external influences. This method computes the next household to occupy
     * the corresponding grid cell in the updated grid.
     *
     * @param newGrid the updated neighborhood grid for the upcoming simulation step
     * @param month   the current month of the simulation, potentially influencing the household's state
     * @return the household that will occupy this position in the new grid
     */
    public abstract Household next(NeighborhoodGrid newGrid, int month);

}
