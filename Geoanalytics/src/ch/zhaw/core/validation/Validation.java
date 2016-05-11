package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;


public class Validation {

	private double score; //double wegen ngram
	private String rawAddressVal;
	private List<String> newAddress = new ArrayList<String>();
	private List<String> oldAddress = new ArrayList<String>(); //wenn das beste Element aus listOption gefunden wurde kann es hier gespeichert werden
	private List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	//wird nicht ben�tigt da nur die anzahl der Elemente, die �berpr�ft werden gez�hlt werden muss Boolean ist nicht notwendig, da der Score erst sp�ter bewertet wird
	//die l�nge von listScore ist als z�hler gen�gend
	private List<Double> listScore = new ArrayList<Double>();//um den finalscore �berpr�fen zu k�nnen muss eine ScoreList erstellt werden die den Score von allen newAddress-Elementen auflistet 
	
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
		System.out.println(); //test-code
		System.out.println("Start setPosibleRawAddress()"); //test
		
		for(int i = 0; i < newAddress.size(); i++){
			int lengthComponent = newAddress.get(i).length();
			int startOld = rawAddress.indexOf(newAddress.get(i));
			int endOld = rawAddress.indexOf(newAddress.get(i)) + lengthComponent;
			String firstLetter = newAddress.get(i).substring(0, 1); //ersten Buchstaben nehmen
			int startIndex = rawAddress.indexOf(firstLetter, 0);
			int endIndex = startIndex + lengthComponent;
			//List<String> list = new ArrayList<String>();
			ListOption l = new ListOption();
			provListOldAddress.add(i, l);
			
			System.out.println("StartOld an der Stelle :" + startOld); //test
			System.out.println("EndOld an der Stelle :" + endOld); //test
			System.out.println("StartIndex an der Stelle :" + startIndex); //test
			System.out.println("EndIndex an der Stelle :" + endIndex); //test
			
			Boolean status = false;
			int x = 0;
			while (status == false){
			//while (startIndex >= 0 && endIndex <= rawAddress.length() || status == false){
				//falls firstLetter im rawAddress nicht drin ist wird der score auf 0 gesetzt
				if(startIndex == -1){
					Option option = new Option ();
					provListOldAddress.get(i).getAddress_component().add(x, option);
					provListOldAddress.get(i).getAddress_component().get(x).setComponent(null);
					provListOldAddress.get(i).getAddress_component().get(x).setProvScore(0.0);
					System.out.println("Bei rawAddress gibt es kein Buchstaben " + firstLetter + ". Es wird f�r diesen component einen Score 0.0 gegeben"); //test
					status = true;
				}
				else{
					if (endIndex <= rawAddress.length()){ 
						Option option = new Option ();
						provListOldAddress.get(i).getAddress_component().add(x, option);
						provListOldAddress.get(i).getAddress_component().get(x).setComponent(rawAddress.substring(startIndex, endIndex));
						//list.add(rawAddress.substring(startIndex, endIndex));
						System.out.println("add.list on i :" + i); //test
						System.out.println("list.add :" + rawAddress.substring(startIndex, endIndex)); //test
						startIndex = rawAddress.indexOf(firstLetter, startIndex + 1);
						endIndex = startIndex + lengthComponent;
						System.out.println("StartIndex neu :" + startIndex); //test
						System.out.println("EndIndex neu :" + endIndex); //test
						x++;
					}
					else {
						Option option = new Option ();
						provListOldAddress.get(i).getAddress_component().add(x, option);
						provListOldAddress.get(i).getAddress_component().get(x).setComponent(null); //score wird sp�ter bei Methode validate() auf 0 gesetzt
						System.out.println("Wert ist gr�sser als das Element von der DB und endIndex ist gr�sser als die l�nge von rawAddress -> Wert wird auf null gesetzt"); //test
						System.out.println("provListOldAddress.get(i).getAddress_component().get(x).setComponent(null) :"); //test
						System.out.println("i :" + i); //test
						status = true;
					}
					if(startIndex == -1){
						status = true;
					}
				}
			}
			
			//Liste printen an der Stelle i
			for(int y = 0; y < provListOldAddress.get(i).getAddress_component().size(); y++){
				System.out.println("provListOldAddress.get(i).getAddress_component().get(y).getComponent():" + provListOldAddress.get(i).getAddress_component().get(y).getComponent());
				System.out.println("i :" + i); 
				System.out.println("y :" + y);
			}
		}
	}
	
	public int validate(String rawAddress, List<String> listNewAddress){
		System.out.println(); //test-code
		System.out.println("Start validate()"); //test
		System.out.println("oldAddress: " + rawAddress);
		
		//erstellt liste rawAddress mit allen m�glichkeiten pro liste
		/*
		z.B. rawAddress = T�rkenstrasse 89 8789 lasten
		listNewAddress = T�rkenstrasse 89 / 8789 / lasten
		listOldAddress = T�rkenstrasse 89, trasse 89 8789 l / 8987, 8789, 89 l / lasten 
		 */
		setPosibleRawAddress(rawAddress, listNewAddress);
		
		setNewAddress(listNewAddress); //Input listNewAddress in Attribut listNewAddress speichern damit Methode addListOldAddress geht
				
		for (int i = 0; i <listNewAddress.size(); i++){
			
			System.out.println("listNewAddress :" + listNewAddress.get(i) + " bei i :" + i);
			
			// �berpr�fen, ob ein Wert null ist
			// falls sinnvoll evt. Bedingung festlegen welche Werte zwingend != null sein m�ssen
			// dies wird eigentlich schon fr�her gepr�ft, aber wird zur sicherheit dringelassen
			if(listNewAddress.get(i) == null){
				System.out.println("listNewAddress.get(i) == null -> keine Punkte vergeben (Score = 0 f�r diesen Wert) oder anderer Service wird abgefragt");
				//provScore = 0; // falls null wird im Moment 0 punkte gegeben d.h. kein Code n�tig
				//System.out.println("provScore :" + provScore);
			}
			else{
			
				System.out.println();
				System.out.println("rawAddresslowerCase :" + rawAddress);
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i));
				
				// alle M�glichkeiten von rawAddress �berpr�fen und die Komponente ausw�hlen, die den besten Score hat
				for(int x = 0; x < provListOldAddress.get(i).getAddress_component().size(); x++){
					String oldOption = provListOldAddress.get(i).getAddress_component().get(x).getComponent();
					System.out.println("provListOldAddress(oldOption) on i = " + i + " on x = " + x + " " + oldOption);
					
					//hier if-Schleife einf�gen dass wenn address_component == null wird score auf 0 gesetzt
					if(oldOption == null){
						provListOldAddress.get(i).getAddress_component().get(x).setProvScore(0.0);
						System.out.println("oldOption == null -> keine Punkte vergeben (Score = 0 f�r diesen Wert)");
					}
					else{
						if (oldOption.contains(listNewAddress.get(i)) == true){ //zu testzwecken: == mit != ersetzt
							System.out.println("rawAddress.contains(listNewAddress.get(i)) == true");
							//starte Methode valContains() und f�gt eine Punktzahl bei provScore hinzu
							valContains(i, x);
						}
						else {
							System.out.println("rawAddress.contains(listNewAddress.get(i)) == false");
							//starte Methode valNgram() und f�gt eine Punktzahl bei provScore hinzu
							valNgram(listNewAddress, i, x);
						}
					}
				}
				//Component mit dem besten Score wird bei oldAddress hinzugef�gt
				//System.out.println("i vor addOldAddress: " + i);
				//System.out.println("wert bei x == 0: " + provListOldAddress.get(i).getAddress_component().get(0).getComponent()); //ausgabe verwirrt
				//System.out.println("score bei x == 0: " + provListOldAddress.get(i).getAddress_component().get(0).getProvScore()); //ausgabe verwirrt
				addOldAddress(i);
			}
		}
		
		//Score berechnen
		double provScore = countScore();
		score = calculateFinalScore(provScore, listScore.size());
		System.out.println("Ende pruefen: score " + score); //test-code
		Double scoreRound = Math.ceil(score); //double in Double umwandeln, evt. �berall in Double �bernehmen
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
	
	//berechnet den Score anhand der gez�hlten True's und False's und gibt das ergebnis als int zur�ck
	public double calculateFinalScore(double provScore, int sizeListScore){
		System.out.println(); //test-code
		System.out.println("Start calculateScore()"); //test
		System.out.println("int provScore vor Berechnung :" + provScore);
		System.out.println("sizeListScore: " + sizeListScore);
		provScore = provScore/sizeListScore;
		System.out.println("provScore provScore/sizeListScore :" + provScore);
		System.out.println("int provScore neu :" + provScore);
		setScore(provScore);
		return provScore;
	}
	
	// �berpr�ft ob addresse valide ist anhand des scores
	public Boolean getVal(){
		System.out.println(); //test-code
		System.out.println("Start getVal()"); //test
		Boolean val = null;
		
		if (getScore() >= 70){
			System.out.println("Score der Adresse ist gleich/gr�sser 70 : " + getScore()); //test-code
			val = true;
		}
		else {
			System.out.println("Score der Adresse ist kleiner 70 : " + getScore()); //test-code
			val = false;
		}
		
		return val;
	}
	
	public void valContains(int i, int x){
		System.out.println(); //test-code
		System.out.println("Start valContains()"); //test
		
		Option address_component = provListOldAddress.get(i).getAddress_component().get(x);
		
		address_component.setProvScore(100.0);
		System.out.println("Score :" + address_component.getProvScore());
		System.out.println("i :" + i);
		System.out.println("x :" + x);
	}
	
	public void valNgram(List<String> listNewAddress, int i, int x){
		System.out.println(); //test-code
		System.out.println("Start valNgram()"); //test
		System.out.println("rawAddress.contains(listNewAddress.get(i)) == false");

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
			
		//n-gram test
		for (int a = 0; a < listNewAddress.get(i).length(); a++){
			while(nEndIndex <= listNewAddress.get(i).length()){
						
				oldComponentN = oldComponent.substring(nStartIndex, nEndIndex);
				newComponentN = newComponent.substring(nStartIndex, nEndIndex);
				
				System.out.println("oldComponentN :" + oldComponentN);
				System.out.println("newComponentN :" + newComponentN);
				System.out.println("nStartIndex :" + nStartIndex);
				System.out.println("nStartIndex :" + nEndIndex);
				if (oldComponentN.equals(newComponentN) == true) {
					nStartIndex++;
					nEndIndex++;
					nScoreTrue++;
					System.out.println("nScoreTrue :" + nScoreTrue);
				}
				else {
					nStartIndex++;
					nEndIndex++;
					nScoreFalse++;
					System.out.println("nScoreFalse :" + nScoreFalse);
				}
			}			
		}

		//Listen erstellen f�r bestimmung des besten Strings
		totalCheck = nScoreFalse + nScoreTrue;
		checkList.add(totalCheck);
		provScore = nScoreTrue/totalCheck*100;
		address_component.setProvScore(provScore);

		/*
		//println f�r testzwecken
		System.out.println("nScoreTrue finaly :" + nScoreTrue);
		System.out.println("nScoreFalse finaly :" + nScoreFalse);
		System.out.println("nScore Total (True+False) :" + totalCheck);
		System.out.println("provScore :" + provScore);
		*/
	}
	
	public void addOldAddress(int i){
		System.out.println(); //test-code
		System.out.println("Start addOldAddress()"); //test
		System.out.println("addressComponent.size(): " + provListOldAddress.get(i).getAddress_component().size()); //test
		
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
			//scoreList �berpr�fen
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
