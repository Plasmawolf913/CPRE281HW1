package edu.iastate.cs2280.hw1;

/**
 * Nothing household class
 * 
 * @author tfolkers
 */

public class Nothing extends Household {

	public Nothing (NeighborhoodGrid grid, int row, int column) {
		super(grid, row, column);
	}
	
	public Sports getPreference() {
		return Sports.NOTHING;
	}
	
	public Household next(NeighborhoodGrid newGrid, int month) {
	    int[] households = new int[Sports.values().length];

	    survey(households);
	   
	    
	    if(households[Sports.SOCCER.ordinal()] > 5){
	    	return new Soccer(neighborhoodGrid, row, column, 5);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] > 4) {
	    	return new Football(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.BASKETBALL.ordinal()] > 3) {
	    	return new Basketball(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.BASEBALL.ordinal()] > 2) {
	    	return new Baseball(neighborhoodGrid, row, column, 0);
	    	
	    }else if (households[Sports.RUGBY.ordinal()] > 1) {
	    	return new Rugby(neighborhoodGrid, row, column, 2);
	    
	    }else if (households[Sports.EVERYTHING.ordinal()] >= 1) {
	    	return new Everything(neighborhoodGrid, row, column);
		    
	    }else {
	    	return this;
	    }
		
	}
	
	public String toString() {
		return "N  ";
	}
	
	
}
