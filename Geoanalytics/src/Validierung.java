import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Validierung {

	private int score;
	private String rawAdressVal;
	private String extAdressVal;
	List<String> listNewAddress = new ArrayList<String>();
	List<String> listOldAddress = new ArrayList<String>();
	List<ListOption> provListOldAddress = new ArrayList<ListOption>();
	
	public Validierung(String rawAdress, String extAdress, List<AbfrageOSM> osm) {
		this.rawAdressVal = rawAdress;
		this.extAdressVal = extAdress;
		pruefen(rawAdress, extAdress, osm);
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

	public String getExtAdressVal() {
		return extAdressVal;
	}

	public void setExtAdressVal(String extAdressVal) {
		this.extAdressVal = extAdressVal;
	}
	
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
	
	// Nur in Methode pruefen anwendbar
	public void addListOldAddress(String rawAddress, int i){
		
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
				startIndex = rawAddress.indexOf(firstLetter, endIndex + 1);
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
	}

	public int pruefen(String rawAddress, String extAdress, List<AbfrageOSM> osm){ //response wird evt. nicht gebraucht -> löschen
		
		System.out.println("startprüfen: ");
		String newAddress = osm.get(0).getAddress().getRoad();
		String addressNumber = osm.get(0).getAddress().getHouse_number();
		String plz = osm.get(0).getAddress().getPostcode();
		String village = osm.get(0).getAddress().getVillage();
		String town = osm.get(0).getAddress().getTown();
		String city = osm.get(0).getAddress().getCity();
		System.out.println("newAddress: " + newAddress);
		System.out.println("oldAddress: " + rawAddress);
		System.out.println("village: " + village);
		System.out.println("town: " + town);
		System.out.println("city: " + city);
		
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
		
		// statische überprüfung: wäre pro Service unterschiedlich
		listNewAddress.add(newAddress);
		listNewAddress.add(addressNumber);
		listNewAddress.add(plz);
		if (village != null){
			listNewAddress.add(village);
		}
		if (town != null){
			listNewAddress.add(town);
		}
		if (city != null){
			listNewAddress.add(city);
		}
		int countTrue = 0;
		int countFalse = 0;
		int provScore = 0;
		
		for (int i = 0; i <listNewAddress.size(); i++){
			
			/*
			if(listNewAddress.get(i) == null){
				System.out.println("listNewAddress.get(i) == null");
			}
			else{
			*/
			
				//ListOption f = new ListOption();
				addListOldAddress(rawAddress, i);

				System.out.println("rawAddresslowerCase :" + rawAddress.toLowerCase());
				System.out.println("newAddresslowerCase :" + listNewAddress.get(i).toLowerCase());

				if (rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == true){ //zu testzwecken: == mit != ersetzt

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

					provScore = provScore/countTrue - 25*countFalse;
					System.out.println("provScore provScore/countTrue - 25*countFalse :" + provScore);

					System.out.println("int countTrue :" + countTrue);
					countTrue = 0; //countTrue zurücksetzen
					setScore(provScore);
				}
				else {

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
					List<Integer> totalList = new ArrayList<Integer>();

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
		//}

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

		provScore = provScore/countTrue - 25*countFalse;
		System.out.println("provScore provScore/countTrue - 25*countFalse :" + provScore);

		System.out.println("int countTrue :" + countTrue);
		setScore(provScore);

		/*
		if (rawAdress.equals(extAdress) == true){
			setScore(100);
		}
		if (rawAdress.equalsIgnoreCase(extAdress) == true){
			setScore(75);
		}
		else {
			setScore(30);
		}
		 */

		System.out.println("Ende pruefen: score " + score); //test-code
		return score;			
	}	
}
