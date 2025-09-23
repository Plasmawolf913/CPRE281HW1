package edu.iastate.cs2280.hw1;

public class Soccer extends SportsHouseholds {

	public Soccer(NeighborhoodGrid grid, int row, int column, int interestLevel) {
		super(grid, row, column, interestLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sports getPreference() {
		// TODO Auto-generated method stub
		return Sports.SOCCER;
	}

	@Override
	public Household next(NeighborhoodGrid newGrid, int month) {
		// TODO Auto-generated method stub
	    int[] households = new int[Sports.values().length];

	    survey(households);
	   
	    
	    if(this.interestLevel > MAX_INTEREST) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] + households[Sports.BASKETBALL.ordinal()] >= households[Sports.SOCCER.ordinal()]){
	    	return new Everything(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.SOCCER.ordinal()] > (households[Sports.BASEBALL.ordinal()] + households[Sports.BASKETBALL.ordinal()] + households[Sports.FOOTBALL.ordinal()] + households[Sports.RUGBY.ordinal()])) {
	    	return new Soccer(neighborhoodGrid, row, column, 5);
	    	
	    }else if(households[Sports.RUGBY.ordinal()] > 4) {
	    	return new Rugby(neighborhoodGrid, row, column, 3);
	    	
	    }else if(households[Sports.EVERYTHING.ordinal()] < 1) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else {
	    	this.interestLevel++;
	    	return this;
	    }
	    
	}
	
	public String toString() {
		return "S" + this.getInterest() + " ";
	}

}
