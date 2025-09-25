package edu.iastate.cs2280.hw1;

/**
 * Basketball household class
 * 
 * @author tfolkers
 */
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
		 int[] households = new int[Sports.values().length];

	    survey(households);
	   
	    
	    if(this.interestLevel >= MAX_INTEREST) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] > 5){
	    	return new Football(neighborhoodGrid, row, column, 2);
	    	
	    }else if(households[Sports.SOCCER.ordinal()] >= 2) {
	    	return new Soccer(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.BASKETBALL.ordinal()] < 2) {
	    	return new Everything(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] + households[Sports.BASEBALL.ordinal()] + households[Sports.BASKETBALL.ordinal()] > 6) {
	    	return new Everything(neighborhoodGrid, row, column);
	    	
	    }else {
	    	this.interestLevel++;
	    	return this;
	    }
	}
	
	public String toString() {
		return  "B" + this.getInterest() + " ";
	}
}
