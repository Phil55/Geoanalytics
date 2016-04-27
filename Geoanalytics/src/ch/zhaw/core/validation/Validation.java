package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;

//import ch.zhaw.core.query.queryOSM.QueryOSM;

public class Validation {

	private double score; //double wegen ngram
	private String rawAdressVal;
	//private List<List<String>> listNewAddress = new ArrayList<List<String>>();
	private List<String> newAddress = new ArrayList<String>();
	private List<String> oldAddress = new ArrayList<String>(); //wenn das beste Element aus listOption gefunden wurde kann es hier gespeichert werden
	private ListOption listOption;
	private List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	//wird nicht benötigt da nur die anzahl der Elemente, die überprüft werden gezählt werden muss Boolean ist nicht notwendig, da der Score erst später bewertet wird
	//die länge von listScore ist als zähler genügend
	//private List<Boolean> listCheck = new ArrayList<Boolean>(); 
	private List<Double> listScore = new ArrayList<Double>();//um den finalscore überprüfen zu können muss eine ScoreList erstellt werden die den Score von allen newAddress-Elementen auflistet 
	
	public Validation(String rawAdress) {
		this.rawAdressVal = rawAdress;
		//this.listOption = new ListOption();
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getRawAdressVal() {
		return rawAdressVal;
	}

	public void setRawAdressVal(String rawAdressVal) {
		this.rawAdressVal = rawAdressVal;
	}
	/*
	public List<List<String>> getListNewAddress() {
		return listNewAddress;
	}

	public void setListNewAddress(List<List<String>> listNewAddress) {
		this.listNewAddress = listNewAddress;
	}
	*/
	
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

	public ListOption getListOption() {
		return listOption;
	}

	public void setListOption(ListOption listOption) {
		this.listOption = listOption;
	}

	/*
	public List<Boolean> getListCheck() {
		return listCheck;
	}

	public void setListCheck(List<Boolean> listCheck) {
		this.listCheck = listCheck;
	}
	*/

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
					System.out.println("Bei rawAddress gibt es kein Buchstaben " + firstLetter + ". Es wird für diesen component einen Score 0.0 gegeben"); //test
					status = true;
				}
				else{
					if (endIndex <= rawAddress.length()){ //if-Schlaufe unnötig!!
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
						provListOldAddress.get(i).getAddress_component().get(x).setComponent(null); //score wird später bei Methode validate() auf 0 gesetzt
						System.out.println("Wert ist grösser als das Element von der DB und endIndex ist grüsser als die länge von rawAddress -> Wert wird auf null gesetzt"); //test
						System.out.println("provListOldAddress.get(i).getAddress_component().get(x).setComponent(null) :"); //test
						System.out.println("i :" + i); //test
						status = true;
					}
					if(startIndex == -1){
						status = true;
					}
					/*
					else{
						
					}
					*/
				}
			}
			//provListOldAddress.get(i).setListOldOption(list);
			
			//Liste printen an der Stelle i
			for(int y = 0; y < provListOldAddress.get(i).getAddress_component().size(); y++){
				System.out.println("provListOldAddress.get(i).getAddress_component().get(y).getComponent():" + provListOldAddress.get(i).getAddress_component().get(y).getComponent());
				System.out.println("i :" + i); 
				System.out.println("y :" + y);
			}
		}
	}
	
	/*
	// Nur in Methode pruefen anwendbar
	public void addListOldAddress(String rawAddress, int i){
		
		System.out.println("Start der Methode addListOldAddress"); //test
		
		int lengthRawAddress = newAddress.get(i).length();
		int startOld = rawAddress.indexOf(newAddress.get(i));
		int endOld = rawAddress.indexOf(newAddress.get(i)) + lengthRawAddress;
		String firstLetter = newAddress.get(i).substring(0, 1); //ersten Buchstaben nehmen
		int startIndex = rawAddress.indexOf(firstLetter, 0);
		int endIndex = startIndex + lengthRawAddress;
		List<String> list = new ArrayList<String>();
		//ListOption f = new ListOption();
			
		System.out.println("StartOld an der Stelle :" + startOld); //test
		System.out.println("EndOld an der Stelle :" + endOld); //test
		System.out.println("StartIndex an der Stelle :" + startIndex); //test
		System.out.println("EndIndex an der Stelle :" + endIndex); //test
			
		while (startIndex >= 0 && endIndex <= rawAddress.length()){
				
			if (endIndex <= rawAddress.length()){
				list.add(rawAddress.substring(startIndex, endIndex));
				System.out.println("add.list on i :" + i); //test
				System.out.println("list.add :" + rawAddress.substring(startIndex, endIndex)); //test
				startIndex = rawAddress.indexOf(firstLetter, startIndex + 1);
				endIndex = startIndex + lengthRawAddress;
			}
			else {
				System.out.println("no more add.list on i :" + i); //test
			}
		}
		setListOldAddress(list);
		//f.setListOldOption(list);
		//provListOldAddress.add(f);
			
		System.out.println("StartOld an der Stelle :" + startOld);
		System.out.println("EndOld an der Stelle :" + endOld);
		System.out.println("StartIndex an der Stelle :" + startIndex); 
		System.out.println("EndIndex an der Stelle :" + endIndex); 
		System.out.println("newAddress String get(i) :" + newAddress.get(i));
		
		//aktuelle liste printen
		for (int x = 0; x < listOldAddress.size(); x++){
			System.out.println("listOldaddress String get(i) :" + listOldAddress.get(x));
			System.out.println("int x :" + x);
		}
		System.out.println("Ende der Methode addListOldAddress"); //test
	}
	*/

	public int validate(String rawAddress, List<String> listNewAddress){
		System.out.println(); //test-code
		System.out.println("Start validate()"); //test
		System.out.println("oldAddress: " + rawAddress);
		
		//erstellt liste rawAddress mit allen möglichkeiten pro liste
		/*
		z.B. rawAddress = Türkenstrasse 89 8789 lasten
		listNewAddress = Türkenstrasse 89 / 8789 / lasten
		listOldAddress = Türkenstrasse 89, trasse 89 8789 l / 8987, 8789, 89 l / lasten 
		 */
		setPosibleRawAddress(rawAddress, listNewAddress);
		
		setNewAddress(listNewAddress); //Input listNewAddress in Attribut listNewAddress speichern damit Methode addListOldAddress geht
		//listNewAddress = osm.get(0).getListNewAddressOSM(); //muss evt. geändert werden -> osm.get(0) falls mehrere Addressen vorkommen
				
		for (int i = 0; i <listNewAddress.size(); i++){
			
			System.out.println("listNewAddress :" + listNewAddress.get(i) + " bei i :" + i);
			
			// überprüfen, ob ein Wert null ist
			// falls sinnvoll evt. Bedingung festlegen welche Werte zwingend != null sein müssen
			// dies wird eigentlich schon früher geprüft, aber wird zur sicherheit dringelassen
			if(listNewAddress.get(i) == null){
				System.out.println("listNewAddress.get(i) == null -> keine Punkte vergeben (Score = 0 für diesen Wert) oder anderer Service wird abgefragt");
				//provScore = 0; // falls null wird im Moment 0 punkte gegeben d.h. kein Code nötig
				//System.out.println("provScore :" + provScore);
			}
			else{
			
				System.out.println();
				System.out.println("rawAddresslowerCase :" + rawAddress);
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i));
				
				// alle Möglichkeiten von rawAddress überprüfen und die Komponente auswählen, die den besten Score hat
				for(int x = 0; x < provListOldAddress.get(i).getAddress_component().size(); x++){
					String oldOption = provListOldAddress.get(i).getAddress_component().get(x).getComponent();
					System.out.println("provListOldAddress(oldOption) on i = " + i + " on x = " + x + " " + oldOption);
					
					//hier if-Schleife einfügen dass wenn address_component == null wird score auf 0 gesetzt
					if(oldOption == null){
						provListOldAddress.get(i).getAddress_component().get(x).setProvScore(0.0);
						System.out.println("oldOption == null -> keine Punkte vergeben (Score = 0 für diesen Wert)");
					}
					else{
						if (oldOption.contains(listNewAddress.get(i)) == true){ //zu testzwecken: == mit != ersetzt
							System.out.println("rawAddress.contains(listNewAddress.get(i)) == true");
							//starte Methode valContains() und fügt eine Punktzahl bei provScore hinzu
							valContains( i, x);
						}
						else {
							System.out.println("rawAddress.contains(listNewAddress.get(i)) == false");
							//starte Methode valNgram() und fügt eine Punktzahl bei provScore hinzu
							valNgram(rawAddress, listNewAddress, i, x);
						}
					}
				}
				//Component mit dem besten Score wird bei oldAddress hinzugefügt
				//System.out.println("i vor addOldAddress: " + i);
				//System.out.println("wert bei x == 0: " + provListOldAddress.get(i).getAddress_component().get(0).getComponent()); //ausgabe verwirrt
				//System.out.println("score bei x == 0: " + provListOldAddress.get(i).getAddress_component().get(0).getProvScore()); //ausgabe verwirrt
				addOldAddress(i);
			}
		}
		
		//Score überprüfen
		double provScore = countScore();
		score = calculateFinalScore(provScore, listScore.size());

		System.out.println("Ende pruefen: score " + score); //test-code
		Double scoreRound = Math.ceil(score); //double in Double umwandeln, evt. überall in Double übernehmen
		int scoreRoundInt = scoreRound.intValue();
		return scoreRoundInt;					
				
				/*
				if (rawAddress.contains(listNewAddress.get(i)) == true){ //zu testzwecken: == mit != ersetzt
					System.out.println("rawAddress.contains(listNewAddress.get(i)) == true");
					//starte Methode valContains() und fügt eine Punktzahl bei provScore hinzu
					valContains(rawAddress, listNewAddress, i, x);

				}
				else {
					
					System.out.println("rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == false");
					//String testNew = "Bachstrassen 13";	//String zum testen
					//String testOld = "Bachstrasse";		//String zum testen

					int nStartIndex = 0;
					int nEndIndex = 2;
					double nScoreTrue = 0;
					double nScoreFalse = 0;
					double valScore = 0; // kann evt. auch mit Variabel provScore gemacht werden
					double totalCheck = 0;
					List<Double> checkList = new ArrayList<Double>();
					List<Double> scoreList = new ArrayList<Double>();
					//List<Integer> totalList = new ArrayList<Integer>();

					for (int x = 0; x < provListOldAddress.get(i).listOldOption.size(); x++){
						String oldList = provListOldAddress.get(i).getListOldOption().get(x).substring(nStartIndex, nEndIndex); // evt. auf 244 verschieben bzw. löschen
						String newList = listNewAddress.get(i).substring(nStartIndex, nEndIndex); // evt. auf 245 verschieben bzw. löschen
						
						//n-gram test
						for (int a = 0; a < provListOldAddress.get(i).getListOldOption().size(); a++){

							for (int p = 0; p < listNewAddress.get(i).length(); p++){
								
								while(nEndIndex <= listNewAddress.get(i).length()){
									
									oldList = provListOldAddress.get(i).getListOldOption().get(x).substring(nStartIndex, nEndIndex);
									newList = listNewAddress.get(i).substring(nStartIndex, nEndIndex);
									System.out.println("oldList :" + oldList);
									System.out.println("newList :" + newList);

									if (oldList.equalsIgnoreCase(newList) == true) {
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
						}

						//Listen erstellen für bestimmung des besten Strings
						totalCheck = nScoreFalse + nScoreTrue;
						checkList.add(totalCheck);
						valScore = nScoreTrue/totalCheck*100;
						scoreList.add(valScore);

						//println für testzwecken
						System.out.println("nScoreTrue finaly :" + nScoreTrue);
						System.out.println("nScoreFalse finaly :" + nScoreFalse);
						System.out.println("nScore Total (True+False) :" + totalCheck);
						System.out.println("valScore :" + valScore);
						System.out.println("contain is false with :" + listNewAddress.get(i));
					}

					//grösster Score finden
					int ind = 0;
					System.out.println("provListOldAddress.get(i).getListOldOption().size() :" + provListOldAddress.get(i).getListOldOption().size());
					System.out.println("size() == 0 :" + provListOldAddress.get(i).getListOldOption().get(0));
					
					if (provListOldAddress.get(i).getListOldOption().size() == 1){
						oldAddress.add(provListOldAddress.get(i).listOldOption.get(0));
					}
					else {
						double max = 0;
						for (int x = 0; x < provListOldAddress.get(i).listOldOption.size(); x++){
							if (max < scoreList.get(x)){
								max = scoreList.get(x);
								ind = x;
								System.out.println("min = scoreList.get(x) :" + scoreList.get(x));
							}
							else{
								System.out.println("scoreList.remove :" + scoreList.get(x));
								scoreList.remove(x);
								checkList.remove(x);
							}
						}
						oldAddress.add(provListOldAddress.get(i).listOldOption.get(ind));
						System.out.println("final one Score of list :" + scoreList.get(0));
						//Score berechnen für "if contains == false" und false oder true in listcheck zurückgeben
						double n = checkList.get(0);
						double score = scoreList.get(0);
						if (n <= 5){
							if(score >= 75){
								listCheck.add(true);
								provScore += 100;
								System.out.println("listCheck.add(true)");
							}
							else{
								listCheck.add(false);
								System.out.println("listCheck.add(false)");
							}
						}
						else {
							if (n <= 10){
								if(score >= 70){
									listCheck.add(true);
									provScore += 100;
									System.out.println("listCheck.add(true)");
								}
								else{
									listCheck.add(false);
									System.out.println("listCheck.add(false)");
								}
							}
							//wenn n > 10
							else{
								if(score >= 60){
									listCheck.add(true);
									provScore += 100;
									System.out.println("listCheck.add(true)");
								}
								else{
									listCheck.add(false);
									System.out.println("listCheck.add(false)");
								}
							}
						}
					}
				}
				*/


		/*
		//Score berechnen für "if contains == false"
		//evt. kann Berechnung für Score bei beiden fällen (contains == false UND true) benutzt werden
		for (int q = 0; q < listCheck.size(); q++){ 
			if (listCheck.get(q) == true){
				countTrue++;
			}
			else {
				countFalse++;
			}
		}
		*/
		
	}
	
	public double countScore(){
		double provScore = 0;
		for(int i = 0; i < listScore.size(); i++){
			provScore += listScore.get(i);
		}
		return provScore;
	}
	
	//berechnet den Score anhand der gezählten True's und False's und gibt das ergebnis als int zurück
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
	
	/*
	//berechnet den Score anhand der gezählten True's und False's und gibt das ergebnis als int zurück
	public int calculateFinalScore(int provScore, int countTrue, int countFalse){
		System.out.println(); //test-code
		System.out.println("Start calculateScore()"); //test
		System.out.println("int provScore vor Berechnung :" + provScore);
		provScore = provScore/countTrue - 25*countFalse;
		System.out.println("provScore provScore/countTrue - 25*countFalse :" + provScore);
		System.out.println("int provScore neu :" + provScore);
		System.out.println("int countTrue :" + countTrue);
		System.out.println("int countFalse :" + countFalse);
		setScore(provScore);
		return provScore;
	}
	*/
	
	// überprüft ob addresse valide ist anhand des scores
	public Boolean getVal(){
		System.out.println(); //test-code
		System.out.println("Start getVal()"); //test
		Boolean val = null;
		
		if (getScore() >= 120){
			System.out.println("Score der Adresse ist gleich/grösser 120 : " + getScore()); //test-code
			val = true;
		}
		else {
			System.out.println("Score der Adresse ist kleiner 120 : " + getScore()); //test-code
			val = false;
		}
		
		return val;
	}
	
	public void valContains(int i, int x){
		System.out.println(); //test-code
		System.out.println("Start valContains()"); //test
		
		//für Score berechnen für "if contains == true"
		//Boolean roadCheck = null;
		//Boolean addNrCheck = null;
		//Boolean addressLineCheck = null;
		//Boolean plzCheck = null;
		//Boolean villageCheck = null;
		Option address_component = provListOldAddress.get(i).getAddress_component().get(x);
		
		/*
		if (i == 0){
			roadCheck = true;
			listCheck.add(roadCheck); //falls nur liste benötigt wird: listCheck.add(true);
			provScore = 100;
			address_component.setProvScore(provScore);
		}
		else if (i == 1){
			addNrCheck = true;
			listCheck.add(addNrCheck);
			provScore = 100;
			address_component.setProvScore(provScore);
		}
		*/
		if (i == 0){
			//addressLineCheck = true;
			//listCheck.add(addressLineCheck);
			address_component.setProvScore(100.0);
			System.out.println("Score :" + address_component.getProvScore());
			System.out.println("i :" + i);
			System.out.println("x :" + x);

		}
		else if (i == 1){
			//plzCheck = true;
			//listCheck.add(plzCheck);
			address_component.setProvScore(100.0);
			System.out.println("Score :" + address_component.getProvScore());
			System.out.println("i :" + i);
			System.out.println("x :" + x);
		}
		else if (i == 2){
			//villageCheck = true;
			//listCheck.add(villageCheck);
			address_component.setProvScore(100.0);
			System.out.println("Score :" + address_component.getProvScore());
			System.out.println("i :" + i);
			System.out.println("x :" + x);
		}
		else {
			//listCheck.add(false);
			System.out.println("True-Wert kann nicht zugeordnet werden bei List-Stelle :" + i);
		}

		/*
		//Score berechnen für "if contains == true"
		for (int q = 0; q < listCheck.size(); q++){
			if (listCheck.get(q) == true){
				countTrue++;
			}
			else {
				countFalse++;
			}
		}
		score = calculateScore(provScore, countTrue, countFalse);
		countTrue = 0; //countTrue zurücksetzen
		provScore = 0; //provScore zurücksetzen
		*/

	}
	
	public void valNgram(String rawAddress, List<String> listNewAddress, int i, int x){
		System.out.println(); //test-code
		System.out.println("Start valNgram()"); //test
		
		System.out.println("rawAddress.contains(listNewAddress.get(i)) == false");
		//String testNew = "Bachstrassen 13";	//String zum testen
		//String testOld = "Bachstrasse";		//String zum testen

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
		//double valScore = 0; // kann evt. auch mit Variabel provScore gemacht werden
		double totalCheck = 0;
		List<Double> checkList = new ArrayList<Double>();
		//List<Double> scoreList = new ArrayList<Double>();
		//List<Integer> totalList = new ArrayList<Integer>();

			
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

		//Listen erstellen für bestimmung des besten Strings
		totalCheck = nScoreFalse + nScoreTrue;
		checkList.add(totalCheck);
		provScore = nScoreTrue/totalCheck*100;
		address_component.setProvScore(provScore);
		//scoreList.add(valScore);

		/*
		//wird evt. nicht benötigt
		if(provScore >= 120){
			listCheck.add(i, true);
		}
		else{
			listCheck.add(i, false);
		}
		System.out.println("listCheck bei i :" + listCheck.get(i));
		*/
		
		//println für testzwecken
		System.out.println("nScoreTrue finaly :" + nScoreTrue);
		System.out.println("nScoreFalse finaly :" + nScoreFalse);
		System.out.println("nScore Total (True+False) :" + totalCheck);
		System.out.println("provScore :" + provScore);
		
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
