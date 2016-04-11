package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;

import ch.zhaw.core.query.queryOSM.QueryOSM;

public class Validation {

	private int score;
	private String rawAdressVal;
	//private String extAdressVal;
	private List<String> listNewAddress = new ArrayList<String>();
	private List<String> listOldAddress = new ArrayList<String>();
	private List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	
	public Validation(String rawAdress, String extAdress, List<QueryOSM> osm) {
		this.rawAdressVal = rawAdress;
		//this.extAdressVal = extAdress;
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
	public String getExtAdressVal() {
		return extAdressVal;
	}

	public void setExtAdressVal(String extAdressVal) {
		this.extAdressVal = extAdressVal;
	}
	*/
	
	public List<String> getListNewAddress() {
		return listNewAddress;
	}

	public void setListNewAddress(List<String> listNewAddress) {
		this.listNewAddress = listNewAddress;
	}

	public List<ListOption> getListOldAddress() {
		return provListOldAddress;
	}

	public void setListOldAddress(List<ListOption> listOldAddress) {
		this.provListOldAddress = listOldAddress;
	}
	
	public List<ListOption> getProvListOldAddress() {
		return provListOldAddress;
	}

	public void setProvListOldAddress(List<ListOption> provListOldAddress) {
		this.provListOldAddress = provListOldAddress;
	}

	// Nur in Methode pruefen anwendbar
	public void addListOldAddress(String rawAddress, int i){
		
		System.out.println("Start der Methode addListOldAddress"); //test
		
		int lengthRawAddress = listNewAddress.get(i).length();
		int startOld = rawAddress.indexOf(listNewAddress.get(i));
		int endOld = rawAddress.indexOf(listNewAddress.get(i)) + lengthRawAddress;
		String firstLetter = listNewAddress.get(i).substring(0, 1); //ersten Buchstaben nehmen
		int startIndex = rawAddress.indexOf(firstLetter, 0);
		int endIndex = startIndex + lengthRawAddress;
		List<String> list = new ArrayList<String>();
		ListOption f = new ListOption();
			
			
			
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
		f.setListOldOption(list);
		provListOldAddress.add(f);
			
		System.out.println("StartOld an der Stelle :" + startOld);
		System.out.println("EndOld an der Stelle :" + endOld);
		System.out.println("StartIndex an der Stelle :" + startIndex); 
		System.out.println("EndIndex an der Stelle :" + endIndex); 
		System.out.println("listNewAddress String get(i) :" + listNewAddress.get(i));
		
		//aktuelle liste printen
		for (int x = 0; x < provListOldAddress.get(i).listOldOption.size(); x++){
			System.out.println("listOldaddress String get(i) :" + provListOldAddress.get(i).getListOldOption().get(x));
			System.out.println("int x :" + x);
		}
		System.out.println("Ende der Methode addListOldAddress"); //test
	}

	public int validate(String rawAddress, Object obj, List<String> listNewAddress){ //response wird evt. nicht gebraucht -> löschen
		
		System.out.println("startprüfen: ");
		System.out.println("oldAddress: " + rawAddress);
		
		setListNewAddress(listNewAddress); //Input listNewAddress in Attribut listNewAddress speichern damit Methode addListOldAddress geht
		//listNewAddress = osm.get(0).getListNewAddressOSM(); //muss evt. geändert werden -> osm.get(0) falls mehrere Addressen vorkommen
		
		//für Score berechnen für "if contains == true"
		List<Boolean> listCheck = new ArrayList<Boolean>();
		Boolean roadCheck = null;
		Boolean addNrCheck = null;
		Boolean plzCheck = null;
		Boolean villageCheck = null;
		
		/*
		int result = oldAddress.compareToIgnoreCase(newAddress);
		System.out.println(result);
		
		result = newAddress.compareToIgnoreCase(oldAddress);
		System.out.println(result);
		*/
		
		int countTrue = 0;
		int countFalse = 0;
		int provScore = 0;
		
		for (int i = 0; i <listNewAddress.size(); i++){
			
			System.out.println("listNewAddress :" + listNewAddress.get(i) + " bei i :" + i);
			
			// überprüfen, ob ein Wert null ist
			// falls sinnvoll evt. Bedingung festlegen welche Werte zwingend != null sein müssen
			if(listNewAddress.get(i) == null){
				System.out.println("listNewAddress.get(i) == null -> keine Punkte vergeben (Score = 0 für diesen Wert) oder anderer Service wird abgefragt");
				//provScore = 0; // falls null wird im Moment 0 punkte gegeben d.h. kein Code nötig
				//System.out.println("provScore :" + provScore);
			}
			else{
			
			
				//ListOption f = new ListOption();
				addListOldAddress(rawAddress, i);

				System.out.println("rawAddresslowerCase :" + rawAddress.toLowerCase());
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i).toLowerCase());

				if (rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == true){ //zu testzwecken: == mit != ersetzt
					System.out.println("rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == true");

					if (i == 0){
						roadCheck = true;
						listCheck.add(roadCheck); //falls nur liste benötigt wird: listCheck.add(true);
						provScore += 100;
					}
					else if (i == 1){
						addNrCheck = true;
						listCheck.add(addNrCheck);
						provScore += 100;
					}
					else if (i == 2){
						plzCheck = true;
						listCheck.add(plzCheck);
						provScore += 100;
					}
					else if (i == 3){
						villageCheck = true;
						listCheck.add(villageCheck);
						provScore += 100;
					}
					else {
						listCheck.add(false);
						System.out.println("True-Wert kann nicht zugeordnet werden bei List-Stelle :" + i);
					}

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
			
				}
				else {

					System.out.println("rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == false");
					//String testNew = "Bachstrassen 13";	//String zum testen
					//String testOld = "Bachstrasse";		//String zum testen

					int nStartIndex = 0;
					int nEndIndex = 2;
					int nScoreTrue = 0;
					int nScoreFalse = 0;
					int valScore = 0; // kann evt. auch mit Variabel provScore gemacht werden
					int totalCheck = 0;
					List<Integer> checkList = new ArrayList<Integer>();
					List<Integer> scoreList = new ArrayList<Integer>();
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
						listOldAddress.add(provListOldAddress.get(i).listOldOption.get(0));
					}
					else {
						int max = 0;
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
						listOldAddress.add(provListOldAddress.get(i).listOldOption.get(ind));
						System.out.println("final one Score of list :" + scoreList.get(0));
						//Score berechnen für "if contains == false" und false oder true in listcheck zurückgeben
						int n = checkList.get(0);
						int score = scoreList.get(0);
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
			}
		}

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
		countTrue = 0; //countTrue zurücksetzen
		provScore = 0; //provScore zurücksetzen
		return provScore;
	}
}
