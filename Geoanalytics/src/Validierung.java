import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Validierung {

	private int score;
	private String rawAdressVal;
	private String extAdressVal;
	List<String> listNewAddress = new ArrayList<String>();
	List<ListOption> listOldAddress = new ArrayList<ListOption>();
	
	public Validierung(String rawAdress, String extAdress, List<AbfrageOSM> osm, HttpResponse<JsonNode> response) {
		this.rawAdressVal = rawAdress;
		this.extAdressVal = extAdress;
		pruefen(rawAdress, extAdress, osm, response);
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
		return listOldAddress;
	}

	public void setListOldAddress(List<ListOption> listOldAddress) {
		this.listOldAddress = listOldAddress;
	}
	
	// Nur in Methode pruefen anwendbar
	public void addListOldAddress(String rawAddress, int i){
		
			int startOld = rawAddress.indexOf(listNewAddress.get(i));
			int endOld = rawAddress.indexOf(listNewAddress.get(i)) + listNewAddress.get(i).length();
			String firstLetter = listNewAddress.get(i).substring(0, 1); //ersten Buchstaben nehmen
			int index = 0;
			List<String> list = new ArrayList<String>();
			ListOption f = new ListOption(list);
			listOldAddress.add(f);
			
			System.out.println("StartOld an der Stelle :" + startOld); //test
			System.out.println("EndOld an der Stelle :" + endOld); //test
			
			while (index >= 0 && index <= listNewAddress.get(i).length()){
				rawAddress.indexOf(firstLetter, index);
				startOld = index;
				endOld = index + listNewAddress.get(i).length();
				if (endOld <= listNewAddress.get(i).length()){
					list.add(listNewAddress.get(i).substring(startOld, endOld));
					index++;
				}
				else {
					index = endOld;
				}
			}
			f.setListOldOption(list);
			
			
			System.out.println("StartOld an der Stelle :" + startOld);
			System.out.println("EndOld an der Stelle :" + endOld);
			//listOldAddress.add(rawAddress.substring(startOld, endOld));
			System.out.println("listNewAddress String get(i) :" + listNewAddress.get(i));
			System.out.println("listOldaddress String get(i) :" + listOldAddress.get(i));
			// f.getListOldOption();
	}

	public int pruefen(String rawAddress, String extAdress, List<AbfrageOSM> osm, HttpResponse<JsonNode> response){ //response wird evt. nicht gebraucht -> löschen
		
		System.out.println("startprüfen: ");
		String newAddress = osm.get(0).getAddress().getRoad();
		String addressNumber = osm.get(0).getAddress().getHouse_number();
		String plz = osm.get(0).getAddress().getPostcode();
		String village = osm.get(0).getAddress().getVillage();
		System.out.println("newAddress: " + newAddress);
		System.out.println("oldAddress: " + rawAddress);
		
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
		listNewAddress.add(village);
		int countTrue = 0;
		int countFalse = 0;
		int provScore = 0;
		
		for (int i = 0; i <listNewAddress.size(); i++){
			
			addListOldAddress(rawAddress, i);
			
			if (rawAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) != true){
				
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
			}
			else {
				
				//String testNew = "Bachstrassen 13";	//String zum testen
				//String testOld = "Bachstrasse";		//String zum testen
				
				int nStartIndex = 0;
				int nEndIndex = 1;
				int nScoreTrue = 0;
				int nScoreFalse = 0;
				int countCheck = 0;
				String oldList = listOldAddress.get(i).getListOldOption().get(i).substring(nStartIndex, nEndIndex);
				String newList = listNewAddress.get(i).substring(nStartIndex, nEndIndex);
				
				//n-gram test
				for (int a = 0; a < listOldAddress.get(i).getListOldOption().size(); a++){
				
					for (int p = 0; p < listNewAddress.get(i).length(); p++){
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
				System.out.println("nScoreTrue finaly :" + nScoreTrue);
				System.out.println("nScoreFalse finaly :" + nScoreFalse);
				
					
				countCheck++;
				System.out.println("contain is false with :" + listNewAddress.get(i));
			}
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
