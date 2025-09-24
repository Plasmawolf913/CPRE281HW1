package edu.iastate.cs2280.hw1;


public class Everything extends Household {
	
	private NeighborhoodGrid grid;
	
	public Everything (NeighborhoodGrid grid, int row, int column) {
		super(grid, row, column);
		this.grid = grid;
	}
	
	public Sports getPreference() {
		return Sports.EVERYTHING;
	}
	
	public Household next(NeighborhoodGrid newGrid, int month) {
		
		int[] households = new int[Sports.values().length];
		
		
		
	    survey(households);
	   
	    
	    if(households[Sports.SOCCER.ordinal()] >= (3 * households[Sports.EVERYTHING.ordinal()])) {
	    	return new Soccer(neighborhoodGrid, row, column, 3);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] > 3){
	    	return new Football(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.NOTHING.ordinal()] < (households[Sports.FOOTBALL.ordinal()] + households[Sports.SOCCER.ordinal()])) {
	    	return new Basketball(neighborhoodGrid, row, column, 2);
	    	
	    }else if(households[Sports.RUGBY.ordinal()] > households[Sports.EVERYTHING.ordinal()]) {
	    	return new Rugby(neighborhoodGrid, row, column, 0);

	    }else {
	    	return this;
	    }
	    
	}
	
	public String toString() {
		return "E  ";
	}
}
