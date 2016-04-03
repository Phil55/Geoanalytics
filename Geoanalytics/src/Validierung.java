import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Validierung {

	private int score;
	private String rawAdressVal;
	private String extAdressVal;
	
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
	
	public int pruefen(String rawAdress, String extAdress, List<AbfrageOSM> osm, HttpResponse<JsonNode> response){ //response wird evt. nicht gebraucht -> löschen
		
		System.out.println("startprüfen: ");
		String oldAddress = rawAdress;
		String newAddress = osm.get(0).getAddress().getRoad();
		String addressNumber = osm.get(0).getAddress().getHouse_number();
		String plz = osm.get(0).getAddress().getPostcode();
		String village = osm.get(0).getAddress().getVillage();
		System.out.println("newAddress: " + newAddress);
		System.out.println("oldAddress: " + oldAddress);
		
		/*
		int result = oldAddress.compareToIgnoreCase(newAddress);
		System.out.println(result);
		
		result = newAddress.compareToIgnoreCase(oldAddress);
		System.out.println(result);
		*/
		
		// statische überprüfung: wäre pro Service unterschiedlich
		List<String> listNewAddress = new ArrayList<String>();
		List<String> listOldAddress = new ArrayList<String>();
		listNewAddress.add(newAddress);
		listNewAddress.add(addressNumber);
		listNewAddress.add(plz);
		listNewAddress.add(village);
		int countTrue = 0;
		int countCheck = 0;
		for (int i = 0; i <listNewAddress.size(); i++){
			if (oldAddress.toLowerCase().contains(listNewAddress.get(i).toLowerCase()) == true){
				int startOld = oldAddress.indexOf(listNewAddress.get(i));
				int endOld = oldAddress.indexOf(listNewAddress.get(i)) + listNewAddress.get(i).length();
				System.out.println("listNewAddress String get(i) :" + listNewAddress.get(i));
				System.out.println("StartOld an der Stelle :" + startOld);
				System.out.println("EndOld an der Stelle :" + endOld);
				listOldAddress.add(oldAddress.substring(startOld, endOld));
				System.out.println("listOldaddress String get(i) :" + listOldAddress.get(i));
				countTrue++;
				countCheck++;
				
				//n-gram test
				int nStartIndex = 0;
				int nEndIndex = 1;
				int nScoreTrue = 0;
				int nScoreFalse = 0;
				String oldList = listOldAddress.get(i).substring(nStartIndex, nEndIndex);
				String newList = listNewAddress.get(i).substring(nStartIndex, nEndIndex);
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
				System.out.println("nScoreTrue finaly :" + nScoreTrue);
				System.out.println("nScoreFalse finaly :" + nScoreFalse);
				
				
			}
			else {
				countCheck++;
				System.out.println("contain is false with :" + listNewAddress.get(i));
			}
		}
		
		System.out.println("int countTrue :" + countTrue);
		setScore(countCheck/countTrue);
		
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
