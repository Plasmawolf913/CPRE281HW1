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
	   
	    
	    if(this.interestLevel >= MAX_INTEREST) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.SOCCER.ordinal()] > 3){
	    	return new Soccer(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.BASEBALL.ordinal()] < 2) {
	    	return new Rugby(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.BASEBALL.ordinal()] + households[Sports.SOCCER.ordinal()] > 5) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] > (2 * households[Sports.BASEBALL.ordinal()])) {
	    	return new Football(neighborhoodGrid, row, column, 0);
	    	
	    }else {
	    	this.interestLevel++;
	    	return this;
	    }
	    
	}

	public String toString() {
		return  "A" + this.getInterest() + " ";
	}
	
}
