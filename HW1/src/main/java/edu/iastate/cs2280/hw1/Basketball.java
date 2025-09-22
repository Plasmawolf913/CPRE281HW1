package edu.iastate.cs2280.hw1;

public class Basketball extends SportsHouseholds {

	
	public Basketball (NeighborhoodGrid grid, int row, int column, int interestLevel) {
		super(grid, row, column, interestLevel);
	}

	@Override
	public Sports getPreference() {
		// TODO Auto-generated method stub
		return Sports.BASKETBALL;
	}

	@Override
	public Household next(NeighborhoodGrid newGrid, int month) {
		// TODO Auto-generated method stub
		return null;
	}
}
