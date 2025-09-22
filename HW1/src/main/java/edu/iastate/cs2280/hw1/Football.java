package edu.iastate.cs2280.hw1;

public class Football extends SportsHouseholds {

	public Football(NeighborhoodGrid grid, int row, int column, int interestLevel) {
		super(grid, row, column, interestLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sports getPreference() {
		// TODO Auto-generated method stub
		return Sports.FOOTBALL;
	}

	@Override
	public Household next(NeighborhoodGrid newGrid, int month) {
		// TODO Auto-generated method stub
		return null;
	}

}
