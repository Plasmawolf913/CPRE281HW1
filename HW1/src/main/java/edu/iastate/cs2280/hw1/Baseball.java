package edu.iastate.cs2280.hw1;

public class Baseball extends SportsHouseholds {

	public Baseball(NeighborhoodGrid grid, int row, int column, int interestLevel) {
		super(grid, row, column, interestLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sports getPreference() {
		// TODO Auto-generated method stub
		return Sports.BASEBALL;
	}

	@Override
	public Household next(NeighborhoodGrid newGrid, int month) {
		// TODO Auto-generated method stub
	    int[] households = new int[Sports.values().length];

	    survey(households);
	    
	    int footballHouseholds = households[Sports.FOOTBALL.ordinal()];
	    
	    
	    //then do fucking if else stuff
	    
	    return null;
	}

	public String toString() {
		return this.getPreference() + "" + this.getInterest() + " ";
	}
	
}
