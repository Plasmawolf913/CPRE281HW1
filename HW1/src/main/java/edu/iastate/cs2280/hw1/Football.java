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
		int[] households = new int[Sports.values().length];

	    survey(households);
	   
		if(month == 4 || month == 5 || month == 6) {
			if(households[Sports.NOTHING.ordinal()] > 1 || households[Sports.EVERYTHING.ordinal()] > 1) {
				this.interestLevel++;
				return this;
			}else {
				return this;
			}
		}
		
		else if(this.interestLevel >= MAX_INTEREST) {
	    	return new Nothing(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.BASEBALL.ordinal()] + households[Sports.BASKETBALL.ordinal()] + households[Sports.FOOTBALL.ordinal()] > 7){
	    	return new Everything(neighborhoodGrid, row, column);
	    	
	    }else if(households[Sports.BASKETBALL.ordinal()] > 3) {
	    	return new Basketball(neighborhoodGrid, row, column, 0);
	    	
	    }else if(households[Sports.FOOTBALL.ordinal()] < 2) {
	    	return new Baseball(neighborhoodGrid, row, column, 0);
	    
	    }else {
	    	this.interestLevel++;
	    	return this;
	    }
		
	}

	public String toString() {
		return "F" + this.getInterest() + " ";
	}
}
