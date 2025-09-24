package edu.iastate.cs2280.hw1;

public class Rugby extends SportsHouseholds {

	public Rugby(NeighborhoodGrid grid, int row, int column, int interestLevel) {
		super(grid, row, column, interestLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sports getPreference() {
		// TODO Auto-generated method stub
		return Sports.RUGBY;
	}

	@Override
	public Household next(NeighborhoodGrid newGrid, int month) {
		// TODO Auto-generated method stub
		int[] households = new int[Sports.values().length];

	    survey(households);
	   
	    
	    if(this.interestLevel >= MAX_INTEREST) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] + households[Sports.SOCCER.ordinal()] >= 8){
	    	return new Soccer(neighborhoodGrid, row, column, 2);
	    	
	    }else if(households[Sports.BASEBALL.ordinal()] > (2 * households[Sports.BASKETBALL.ordinal()])) {
	    	return new Baseball(neighborhoodGrid, row, column, 4);
	    	
	    }else if(households[Sports.RUGBY.ordinal()] < 2) {
	    	return new Football(neighborhoodGrid, row, column, 0);
	    	
	    }else {
	    	this.interestLevel++;
	    	return this;
	    }
	}

	public String toString() {
		return "R" + this.getInterest() + " ";
	}
}
