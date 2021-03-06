package dealOrNoDeal;

import java.util.List;

public class Dealer {

	//private member variables
	private double bankeroffer;
	
	//getters
	public double returnOffer(){
		return bankeroffer;
	}
	
	//setters - calculates offer by formula average value of remaining cases * round / 10
	public void calculateOffer(int roundofplay, List<Briefcase> allcases){
		double runningsum = 0;
		int remainingcases = 0;
		for(int i = 0; i < allcases.size(); i++){
			if(allcases.get(i).returnIsRemoved() == false){
				runningsum += allcases.get(i).returnValue();
				remainingcases += 1;
			}
		}
		this.bankeroffer = (runningsum) / remainingcases * roundofplay / 10;
	}
	
}