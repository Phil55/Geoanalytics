package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;

//import ch.zhaw.core.query.queryOSM.QueryOSM;

public class Validation {

	private int score;
	private String rawAdressVal;
	//private List<List<String>> listNewAddress = new ArrayList<List<String>>();
	private List<String> newAddress = new ArrayList<String>();
	private List<String> oldAddress = new ArrayList<String>(); //wenn das beste Element aus listOption gefunden wurde kann es hier gespeichert werden
	private ListOption listOption;
	private List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	private List<Boolean> listCheck = new ArrayList<Boolean>(); //für Score berechnen benötigt
	
	public Validation(String rawAdress) {
		this.rawAdressVal = rawAdress;
		this.listOption = new ListOption();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
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

	public List<Boolean> getListCheck() {
		return listCheck;
	}

	public void setListCheck(List<Boolean> listCheck) {
		this.listCheck = listCheck;
	}

	public void setPosibleRawAddress(String rawAddress, List<String> newAddress){
		System.out.println("Start der Methode setPosibleRawAddress"); //test
		
		
		for(int i = 0; i < newAddress.size(); i++){
			int lengthComponent = newAddress.get(i).length();
			int startOld = rawAddress.indexOf(newAddress.get(i));
			int endOld = rawAddress.indexOf(newAddress.get(i)) + lengthComponent;
			String firstLetter = newAddress.get(i).substring(0, 1); //ersten Buchstaben nehmen
			int startIndex = rawAddress.indexOf(firstLetter, 0);
			int endIndex = startIndex + lengthComponent;
			//List<String> list = new ArrayList<String>();
			provListOldAddress.add(i, listOption);
			
			System.out.println("StartOld an der Stelle :" + startOld); //test
			System.out.println("EndOld an der Stelle :" + endOld); //test
			System.out.println("StartIndex an der Stelle :" + startIndex); //test
			System.out.println("EndIndex an der Stelle :" + endIndex); //test
			
			while (startIndex >= 0 && endIndex <= rawAddress.length() ){
				int x = 0;
				if (endIndex <= rawAddress.length()){
					Option option = new Option ();
					provListOldAddress.get(i).getAddress_component().add(x, option);
					provListOldAddress.get(i).getAddress_component().get(x).setComponent(rawAddress.substring(startIndex, endIndex));
					//list.add(rawAddress.substring(startIndex, endIndex));
					System.out.println("add.list on i :" + i); //test
					System.out.println("list.add :" + rawAddress.substring(startIndex, endIndex)); //test
					startIndex = rawAddress.indexOf(firstLetter, startIndex + 1);
					endIndex = startIndex + lengthComponent;
					x++;
				}
				else {
					System.out.println("no more add.list on i :" + i); //test
				}
			}
			//provListOldAddress.get(i).setListOldOption(list);
			
			//Liste printen an der Stelle i
			for(int x = 0; x < provListOldAddress.size(); x++){
				System.out.println("provListOldAddress.get(i).getAddress_component().get(x).getComponent():" + provListOldAddress.get(i).getAddress_component().get(x).getComponent());
				System.out.println("i :" + i); 
				System.out.println("x :" + x);
			}
			System.out.println("Ende der Methode setPosibleRawAddress"); //test
			System.out.println(); //test
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
		
		System.out.println("startprüfen: ");
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
			//dies wird eigentlich schon früher geprüft, aber wird zur sicherheit dringelassen
			if(listNewAddress.get(i) == null){
				System.out.println("listNewAddress.get(i) == null -> keine Punkte vergeben (Score = 0 für diesen Wert) oder anderer Service wird abgefragt");
				//provScore = 0; // falls null wird im Moment 0 punkte gegeben d.h. kein Code nötig
				//System.out.println("provScore :" + provScore);
			}
			else{
			
				System.out.println("rawAddresslowerCase :" + rawAddress);
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i));
				

				for(int x = 0; x < provListOldAddress.get(i).getAddress_component().size(); x++){
					String oldOption = provListOldAddress.get(i).getAddress_component().get(x).getComponent();
					
					if (oldOption.contains(listNewAddress.get(i)) == true){ //zu testzwecken: == mit != ersetzt
						System.out.println("rawAddress.contains(listNewAddress.get(i)) == true");
						//starte Methode valContains() und fügt eine Punktzahl bei provScore hinzu
						valContains(rawAddress, listNewAddress, i, x);

					}
					else {
						
						valNgram(rawAddress, listNewAddress, i, x);
					}
				}
				
				//Component mit dem besten Score wird bei oldAddress hinzugefügt
				addOldAddress(i);
				
				//Score überprüfen
				
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
			}
		}

		
		//zu einer Methode machen -> z.B countListCheck
		//Score berechnen für "if contains == false"
		//evt. kann Berechnung für Score bei beiden fällen (contains == false UND true) benutzt werden
		for (int q = 0; q < listCheck.size(); q++){ //hier entsteht fehler, dass calculateScore nicht funktionieren lässt
			if (listCheck.get(q) == true){
				countTrue++;
			}
			else {
				countFalse++;
			}
		}
		
		score = calculateScore(provScore, countTrue, countFalse);

		System.out.println("Ende pruefen: score " + score); //test-code
		return score;			
	}
	
	//berechnet den Score anhand der gezählten True's und False's und gibt das ergebnis als int zurück
	public int calculateScore(int provScore, int countTrue, int countFalse){
		System.out.println("int provScore vor Berechnung :" + provScore);
		provScore = provScore/countTrue - 25*countFalse;
		System.out.println("provScore provScore/countTrue - 25*countFalse :" + provScore);
		System.out.println("int provScore neu :" + provScore);
		System.out.println("int countTrue :" + countTrue);
		System.out.println("int countFalse :" + countFalse);
		setScore(provScore);
		return provScore;
	}
	
	// überprüft ob addresse valide ist anhand des scores
	public Boolean getVal(){
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
	
	public void valContains(String rawAddress, List<String> listNewAddress, int i, int x){
		
		//für Score berechnen für "if contains == true"
		//Boolean roadCheck = null;
		//Boolean addNrCheck = null;
		Boolean addressLineCheck = null;
		Boolean plzCheck = null;
		Boolean villageCheck = null;
		int provScore = 0;
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
			addressLineCheck = true;
			listCheck.add(addressLineCheck);
			provScore = 100;
			address_component.setProvScore(provScore);
		}
		else if (i == 1){
			plzCheck = true;
			listCheck.add(plzCheck);
			provScore = 100;
			address_component.setProvScore(provScore);
		}
		else if (i == 2){
			villageCheck = true;
			listCheck.add(villageCheck);
			provScore = 100;
			address_component.setProvScore(provScore);
		}
		else {
			listCheck.add(false);
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
		
		
		
	}
	
	public void addOldAddress(int i){
		List<Option> addressComponent = provListOldAddress.get(i).getAddress_component();
		List<Integer> scoreList = new ArrayList<Integer>();
		int ind = 0;
		
		if (addressComponent.size() == 1){
			oldAddress.add(addressComponent.get(0).getComponent());
		}
		else {
			//scoreList erstellen
			for(int x = 0; x < addressComponent.size(); x++){
				scoreList.add(addressComponent.get(x).getProvScore());
			}
			//scoreList überprüfen
			int max = 0;
			for (int x = 0; x < addressComponent.size(); x++){
				if (max < scoreList.get(x)){
					max = scoreList.get(x);
					ind = x;
					System.out.println("max < scoreList.get(x) :" + scoreList.get(x));
				}
				else{
					System.out.println("int max >= scoreList.get(x)" + scoreList.get(x));
				}
			}
			oldAddress.add(addressComponent.get(ind).getComponent());
			System.out.println("final one Score of list :");
			System.out.println("Component :" + addressComponent.get(ind).getComponent());
			System.out.println("Score :" + addressComponent.get(ind).getProvScore());
			System.out.println();
		}
		
		
	}
}
