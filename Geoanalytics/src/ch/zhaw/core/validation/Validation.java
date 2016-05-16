package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

public class Validation {

	private double score; 
	private String rawAddressVal;
	private List<String> newAddress = new ArrayList<String>();
	private List<String> oldAddress = new ArrayList<String>(); 
	private List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	private List<Double> listScore = new ArrayList<Double>();
	
	public Validation(String rawAddress) {
		this.rawAddressVal = rawAddress;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getRawAddressVal() {
		return rawAddressVal;
	}

	public void setRawAddressVal(String rawAddressVal) {
		this.rawAddressVal = rawAddressVal;
	}
	
	public List<String> getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(List<String> newAddress) {
		this.newAddress = newAddress;
	}

	
	public List<String> getOldAddress() {
		return oldAddress;
	}
	
	public void setOldAddress(List<String> oldAddress) {
		this.oldAddress = oldAddress;
	}
	

	public List<ListOption> getProvListOldAddress() {
		return provListOldAddress;
	}

	public void setProvListOldAddress(List<ListOption> provListOldAddress) {
		this.provListOldAddress = provListOldAddress;
	}

	public List<Double> getListScore() {
		return listScore;
	}

	public void setListScore(List<Double> listScore) {
		this.listScore = listScore;
	}

	public void setPosibleRawAddress(String rawAddress, List<String> newAddress){
		System.out.println(); 
		System.out.println("Start setPosibleRawAddress()"); 
		
		for(int i = 0; i < newAddress.size(); i++){
			int lengthComponent = newAddress.get(i).length();
			String firstLetter = newAddress.get(i).substring(0, 1); 
			int startIndex = rawAddress.indexOf(firstLetter, 0);
			int endIndex = startIndex + lengthComponent;
			ListOption l = new ListOption();
			provListOldAddress.add(i, l);
			
			/*
			// print zu Testzwecken
			int startOld = rawAddress.indexOf(newAddress.get(i));
			int endOld = rawAddress.indexOf(newAddress.get(i)) + lengthComponent;
			System.out.println("StartOld an der Stelle :" + startOld); //test
			System.out.println("EndOld an der Stelle :" + endOld); //test
			System.out.println("StartIndex an der Stelle :" + startIndex); //test
			System.out.println("EndIndex an der Stelle :" + endIndex); //test
			*/
			
			Boolean status = false;
			int x = 0;
			while (status == false){
				if(startIndex == -1){
					Option option = new Option ();
					provListOldAddress.get(i).getAddress_component().add(x, option);
					provListOldAddress.get(i).getAddress_component().get(x).setComponent(null);
					provListOldAddress.get(i).getAddress_component().get(x).setProvScore(0.0);
					System.out.println("Bei rawAddress gibt es kein Buchstaben " + firstLetter + ". Es wird für diesen component einen Score 0.0 gegeben");
					status = true;
				}
				else{
					if (endIndex <= rawAddress.length()){ 
						Option option = new Option ();
						provListOldAddress.get(i).getAddress_component().add(x, option);
						provListOldAddress.get(i).getAddress_component().get(x).setComponent(rawAddress.substring(startIndex, endIndex));
						startIndex = rawAddress.indexOf(firstLetter, startIndex + 1);
						endIndex = startIndex + lengthComponent;
						x++;
					}
					else {
						Option option = new Option ();
						provListOldAddress.get(i).getAddress_component().add(x, option);
						provListOldAddress.get(i).getAddress_component().get(x).setComponent(null); 
						System.out.println("Wert ist grösser als das Element von der DB und endIndex ist grösser als die länge von rawAddress -> Wert wird auf null gesetzt");
						status = true;
					}
					if(startIndex == -1){
						status = true;
					}
				}
			}
			/*
			//Liste printen an der Stelle i zu Testzwecken
			for(int y = 0; y < provListOldAddress.get(i).getAddress_component().size(); y++){
				System.out.println("provListOldAddress.get(i).getAddress_component().get(y).getComponent():" + provListOldAddress.get(i).getAddress_component().get(y).getComponent());
				System.out.println("i :" + i); 
				System.out.println("y :" + y);
			}
			*/
		}
	}
	
	public int validate(String rawAddress, List<String> listNewAddress){
		System.out.println(); 
		System.out.println("Start validate()");
		System.out.println("oldAddress: " + rawAddress);
		
		setPosibleRawAddress(rawAddress, listNewAddress);
		setNewAddress(listNewAddress); 
				
		for (int i = 0; i <listNewAddress.size(); i++){
			// überprüfen, ob ein Wert null ist
			if(listNewAddress.get(i) == null){
				System.out.println("Liste listNewAddress ist leer");
			}
			else{
				System.out.println();
				System.out.println("rawAddresslowerCase :" + rawAddress);
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i));
				
				// alle Möglichkeiten von rawAddress überprüfen und die Komponente auswählen, die den besten Score hat
				for(int x = 0; x < provListOldAddress.get(i).getAddress_component().size(); x++){
					String oldOption = provListOldAddress.get(i).getAddress_component().get(x).getComponent();
					System.out.println("provListOldAddress(oldOption) on i = " + i + ", on x = " + x + ", " + oldOption);
					if(oldOption == null){
						provListOldAddress.get(i).getAddress_component().get(x).setProvScore(0.0);
						System.out.println("oldOption == null -> keine Punkte vergeben (Score = 0 für diesen Wert)");
					}
					else{
						if (oldOption.contains(listNewAddress.get(i)) == true){ 
							valContains(i, x);
						}
						else {
							valNgram(listNewAddress, i, x);
						}
					}
				}
				addOldAddress(i);
			}
		}
		
		//Score berechnen
		double provScore = countScore();
		score = calculateFinalScore(provScore, listScore.size());
		Double scoreRound = Math.ceil(score);
		int scoreRoundInt = scoreRound.intValue();
		return scoreRoundInt;
	}
	
	public double countScore(){
		double provScore = 0;
		for(int i = 0; i < listScore.size(); i++){
			provScore += listScore.get(i);
		}
		return provScore;
	}
	
	//berechnet den Score anhand der gezählten True's und False's und gibt das Ergebnis zurück
	public double calculateFinalScore(double provScore, int sizeListScore){
		System.out.println(); 
		System.out.println("Start calculateScore()"); 
		provScore = provScore/sizeListScore;
		System.out.println("provScore :" + provScore);
		setScore(provScore);
		return provScore;
	}
	
	// überprüft ob Adresse valide ist anhand des scores
	public Boolean getVal(){
		System.out.println();
		System.out.println("Start getVal()");
		Boolean val = null;
		
		if (getScore() >= 70){
			System.out.println("Score der Adresse ist gleich/grösser 70 : " + getScore()); 
			val = true;
		}
		else {
			System.out.println("Score der Adresse ist kleiner 70 : " + getScore());
			val = false;
		}
		
		return val;
	}
	
	public void valContains(int i, int x){
		System.out.println(); 
		System.out.println("Start valContains()"); 
		
		Option address_component = provListOldAddress.get(i).getAddress_component().get(x);
		address_component.setProvScore(100.0);
		System.out.println("Score :" + address_component.getProvScore());
		System.out.println("i :" + i);
		System.out.println("x :" + x);
	}
	
	public void valNgram(List<String> listNewAddress, int i, int x){
		System.out.println(); 
		System.out.println("Start valNgram()");

		int nStartIndex = 0;
		int nEndIndex = 2;
		double nScoreTrue = 0;
		double nScoreFalse = 0;
		double provScore = 0;
		String oldComponent = provListOldAddress.get(i).getAddress_component().get(x).getComponent();
		String newComponent = listNewAddress.get(i);
		System.out.println("oldComponent :" + oldComponent);
		System.out.println("newComponent :" + newComponent);
		String oldComponentN = oldComponent.substring(nStartIndex, nEndIndex);
		String newComponentN = newComponent.substring(nStartIndex, nEndIndex);
		Option address_component = provListOldAddress.get(i).getAddress_component().get(x);
		double totalCheck = 0;
		List<Double> checkList = new ArrayList<Double>();
			
		for (int a = 0; a < listNewAddress.get(i).length(); a++){
			while(nEndIndex <= listNewAddress.get(i).length()){
				oldComponentN = oldComponent.substring(nStartIndex, nEndIndex);
				newComponentN = newComponent.substring(nStartIndex, nEndIndex);
				/*
				//Print zu Testzwecken
				System.out.println("oldComponentN :" + oldComponentN);
				System.out.println("newComponentN :" + newComponentN);
				System.out.println("nStartIndex :" + nStartIndex);
				System.out.println("nStartIndex :" + nEndIndex);
				*/
				if (oldComponentN.equals(newComponentN) == true) {
					nStartIndex++;
					nEndIndex++;
					nScoreTrue++;
					//Print zu Testzwecken
					//System.out.println("nScoreTrue :" + nScoreTrue);
				}
				else {
					nStartIndex++;
					nEndIndex++;
					nScoreFalse++;
					//Print zu Testzwecken
					//System.out.println("nScoreFalse :" + nScoreFalse);
				}
			}			
		}

		//Liste erstellen für Bestimmung des besten Strings
		totalCheck = nScoreFalse + nScoreTrue;
		checkList.add(totalCheck);
		provScore = nScoreTrue/totalCheck*100;
		address_component.setProvScore(provScore);
		/*
		//Print für testzwecken
		System.out.println("nScoreTrue finaly :" + nScoreTrue);
		System.out.println("nScoreFalse finaly :" + nScoreFalse);
		System.out.println("nScore Total (True+False) :" + totalCheck);
		System.out.println("provScore :" + provScore);
		*/
	}
	
	public void addOldAddress(int i){
		System.out.println();
		System.out.println("Start addOldAddress()"); 
		
		List<Option> addressComponent = provListOldAddress.get(i).getAddress_component();
		List<Double> scoreList = new ArrayList<Double>();
		int index = 0;
		
		if (addressComponent.size() == 1){
			oldAddress.add(addressComponent.get(0).getComponent());
			listScore.add(addressComponent.get(0).getProvScore());
			System.out.println("scoreList on 0: " + addressComponent.get(0).getProvScore()); //test
		}
		else {
			//scoreList erstellen
			for(int x = 0; x < addressComponent.size(); x++){
				scoreList.add(addressComponent.get(x).getProvScore());
				System.out.println("scoreList on x: " + x + " , Score: " + addressComponent.get(x).getProvScore()); //test
			}
			//scoreList überprüfen
			double max = 0;
			for (int x = 0; x < addressComponent.size(); x++){
				if (max < scoreList.get(x)){
					max = scoreList.get(x);
					index = x;
					System.out.println("max < scoreList.get(x) :" + scoreList.get(x));
					System.out.println("index (bzw. x) :" + x);
				}
				else{
					System.out.println("int max >= scoreList.get(x)" + scoreList.get(x));
				}
			}
			listScore.add(addressComponent.get(index).getProvScore());
			oldAddress.add(addressComponent.get(index).getComponent());
			System.out.println("final one Score of list :");
			System.out.println("Component :" + addressComponent.get(index).getComponent());
			System.out.println("Score :" + addressComponent.get(index).getProvScore());
			System.out.println();
		}
	}
}
