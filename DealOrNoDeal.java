package dealOrNoDeal;

import java.util.*;
import java.text.NumberFormat;
import java.util.Locale;

public class DealOrNoDeal {

	//private member variables
	//to be used by this class
	private int[] briefcasePoss = {1,5,10,25,50,100,200,300,400,500,750,1000,5000,10000,25000,50000,75000,100000,200000,300000,400000,500000,750000,1000000};
	private int[] casesperround = {7,5,4,3,2,1,1,1};
	private final int numcases = 24;
	private Dealer dealer;
	private Briefcase selectedbriefcase;
	private NumberFormat currFormat = NumberFormat.getCurrencyInstance(new Locale("en","IN"));
	
	//to be initialized in gameSetup()
	ArrayList<Briefcase> allbriefcases = new ArrayList();
	private int totalcases;
	private int currentround;
	private int remainingcases;
	private int curroundcases;
	
	//to be read by other classes
	private boolean gameactive;
	private boolean dealpending;
	private boolean acceptedoffer;
	//getters
	public boolean isGameActive(){
		return gameactive;
	}
	public boolean isDealPending(){
		return dealpending;
	}
	public boolean isOfferAccepted(){
		return acceptedoffer;
	}
	
	// default constructor
	public DealOrNoDeal() {
		dealer = new Dealer();
	}

	//gameSetup() method initializes parameters and returns shuffled briefcase list
	public List<Briefcase> gameSetup() {
		for(int j=0; j<briefcasePoss.length; j++){
			allbriefcases.add(new Briefcase(briefcasePoss[j]));
		}
		Collections.shuffle(allbriefcases);
		for(int k=0; k<allbriefcases.size(); k++){
			allbriefcases.get(k).setText(Integer.toString(numcases - k));
		}
		
		//initialize variables
		this.totalcases = briefcasePoss.length;
		this.currentround = 1;
		this.remainingcases = briefcasePoss.length;
		this.curroundcases = casesperround[0];
		this.gameactive = true;
		this.dealpending = false;
		this.acceptedoffer = false;
		return allbriefcases;
	}

	//getInstruction() method returns string of next instruction to GUI
	public String getInstruction() {
		currFormat.setMinimumFractionDigits(0);
		currFormat.setMaximumFractionDigits(0);
		String returnmessage;
		if(currentround == 1 && remainingcases == totalcases){
			returnmessage = "Ready to start?  Select your case.";
		} else if((remainingcases == 17 || remainingcases == 12 || remainingcases == 8 || remainingcases == 5 || remainingcases == 3 || remainingcases == 2 || remainingcases == 1) && (curroundcases == 0)){
			initiateDeal();
			returnmessage = "The Dealer's offer is " + currFormat.format(dealer.returnOffer()) + ".";
		} else if(remainingcases == 0){
			returnmessage = this.getResult();
		}
		  else {
			returnmessage = "Select " + Integer.toString(curroundcases) + " cases.";
		}
		return returnmessage;
	}

	//initiateDeal() method initiates deal with dealer
	private void initiateDeal() {
		if(curroundcases == 0){
			dealer.calculateOffer(currentround, allbriefcases);
			this.currentround += 1;
			this.curroundcases = casesperround[currentround-1];
			this.dealpending = true;
		}
	}

	//selectCase method; if first case, set playerCase to true
	public void selectCase(Briefcase briefcase) {
		if(remainingcases == totalcases){
			briefcase.setPlayerCase(true);
			briefcase.setRemoved(true);
			briefcase.setEnabled(false);
			this.remainingcases -= 1;
			this.curroundcases -= 1;
		}  else if(briefcase.returnIsRemoved() == false){
			briefcase.setRemoved(true);
			briefcase.setEnabled(false);
			this.remainingcases -= 1;
			this.curroundcases -= 1;
		}
	}

	//deal() method changes accepted offer to true and ends game
	public void deal() {
		this.dealpending = false;
		this.acceptedoffer = true;
		this.gameactive = false;
	}

	//noDeal method increments game
	public void noDeal() {
		this.dealpending = false;
		this.acceptedoffer = false;
		this.gameactive = true;
	}

	public String getResult() {
		currFormat.setMinimumFractionDigits(0);
		currFormat.setMaximumFractionDigits(0);
		int briefvalue = 0;
		String finalmessage;
		if(acceptedoffer == true || (remainingcases == 0 && acceptedoffer == false)){
			for(int k = 0; k<allbriefcases.size(); k++){
				if(allbriefcases.get(k).returnIsPlayerCase() == true){
					briefvalue = allbriefcases.get(k).returnValue(); 
				}
			}
		}
		if(briefvalue <= dealer.returnOffer()){
			if(acceptedoffer == true){
				finalmessage = "Your case was worth " + currFormat.format(briefvalue) + ".  You accepted the Dealer's offer of " + currFormat.format(dealer.returnOffer()) + ".  You WIN!";
			} else {
				finalmessage = "Your case was worth " + currFormat.format(briefvalue) + ".  You did not accept the Dealer's offer of " + currFormat.format(dealer.returnOffer()) + ".  You LOSE!";
			}
		} else {
			if(acceptedoffer == true){
				finalmessage = "Your case was worth " + currFormat.format(briefvalue) + ".  You accepted the Dealer's offer of " + currFormat.format(dealer.returnOffer()) + ".  You LOSE!";
			} else {
				finalmessage = "Your case was worth " + currFormat.format(briefvalue) + ".  You did not accept the Dealer's offer of " + currFormat.format(dealer.returnOffer()) + ".  You WIN!";
			}
		}
		return finalmessage;
	}

}