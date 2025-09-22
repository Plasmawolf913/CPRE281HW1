package edu.iastate.cs2280.hw1;


public class Nothing extends Household {

	public Nothing (NeighborhoodGrid grid, int row, int column) {
		super(grid, row, column);
	}
	
	public Sports getPreference() {
		return null;
	}
	
	public Household next(NeighborhoodGrid newGrid, int month) {
		return null;
		
	}
	
	
}
