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
	
	public int pruefen(String rawAdress, String extAdress, List<AbfrageOSM> osm, HttpResponse<JsonNode> response){
		
		System.out.println("startprüfen: ");
		String oldAddress = rawAdress;
		String newAddress = osm.get(0).getAddress().getRoad();;
		System.out.println("newAddress: " + newAddress);
		System.out.println("oldAddress: " + oldAddress);
		
		int result = oldAddress.compareToIgnoreCase(newAddress);
		System.out.println(result);
		
		result = newAddress.compareToIgnoreCase(oldAddress);
		System.out.println(result);
		
		if (rawAdress.equals(extAdress) == true){
			setScore(100);
		}
		if (rawAdress.equalsIgnoreCase(extAdress) == true){
			setScore(75);
		}
		else {
			setScore(30);
		}
		
		System.out.println("Ende pruefen: score " + score); //test-code
		return score;
	}
	
}
