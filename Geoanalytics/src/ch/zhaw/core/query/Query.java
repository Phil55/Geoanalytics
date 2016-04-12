package ch.zhaw.core.query;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import ch.zhaw.core.query.queryOSM.*;
import ch.zhaw.core.query.queryBing.*;
import ch.zhaw.core.query.queryGoogle.*;

public class Query {

	private String rawAddress;
	private String extAddress; //evt. Array
	private List<QueryOSM> osm;
	private QueryBing bing;
	private QueryGoogle google;
	private HttpResponse<JsonNode> response;
	private Boolean statusOSM;
	private Boolean statusBing;
	private Boolean statusGoogle;
	private List<Boolean> statusList; // f�r �berpr�fung bei Struktur n�tig
	
	public Query(String rawAddress) {
		//super();
		this.rawAddress = rawAddress;
		this.statusOSM = null; //auf null gesetzt damit Bedingung simpler abzufragen ist
		this.statusBing = null; //auf null gesetzt damit Bedingung simpler abzufragen ist
		this.statusGoogle= null; //auf null gesetzt damit Bedingung simpler abzufragen ist
		this.statusList = new ArrayList<Boolean>(); 
		//folgende Reihenfolge wird bei statusList festgelegt:
		//1 -> osm, 2 -> bing
		for(int i = 0; i < 3; i++){ //zahl 3 repr�sentiert die anzahl Services, die es gibt
			statusList.add(null);
		}
	}

	public String getRawAddress() {
		return rawAddress;
	}

	public void setRawAddress(String rawAddress) {
		this.rawAddress = rawAddress;
	}

	public String getExtAddress() {
		return extAddress;
	}

	public void setExtAddress(String extAddress) {
		this.extAddress = extAddress;
	}
	
	public List<QueryOSM> getOsm() {
		return osm;
	}

	public void setOsm(List<QueryOSM> osm) {
		this.osm = osm;
	}

	public HttpResponse<JsonNode> getResponse() {
		return response;
	}

	public void setResponse(HttpResponse<JsonNode> response) {
		this.response = response;
	}
	
	public QueryBing getBing() {
		return bing;
	}

	public void setBing(QueryBing bing) {
		this.bing = bing;
	}

	public Boolean getStatusOSM() {
		return statusOSM;
	}

	public void setStatusOSM(Boolean statusOSM) {
		this.statusOSM = statusOSM;
	}

	public Boolean getStatusBing() {
		return statusBing;
	}

	public void setStatusBing(Boolean statusBing) {
		this.statusBing = statusBing;
	}

	public List<Boolean> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Boolean> statusList) {
		this.statusList = statusList;
	}
	
	public QueryGoogle getGoogle() {
		return google;
	}

	public void setGoogle(QueryGoogle google) {
		this.google = google;
	}

	public Boolean getStatusGoogle() {
		return statusGoogle;
	}

	public void setStatusGoogle(Boolean statusGoogle) {
		this.statusGoogle = statusGoogle;
	}

	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen k�nnen sich noch �ndern
	//Methode �berpr�ft welche Services bereits �berpr�ft wurden und ruft dann die Methode f�r den entsprechenden Service der als n�chster kommt auf
	//Methode gibt vordefinierten String zur�ck, des aussagt welcher Service aufgerufen wurde (z.B. osm)
	public String query() {
		String rawAddress = getRawAddress();
		System.out.println("Beginn abfrage: rawAddress " + rawAddress); //test-code
		//OSM Service abfragen und auf Bollean statusOSM setzen, um zu �berpr�fen ob abfrage vollst�ndig war
		//�berpr�fen welche Services bereits genutzt wurden
		
		 
		//Code noch nicht vollst�ndig: es wird �berpr�ft ob vorherige abfrage positiv war. 
		//wenn positiv beginnt Validierung, wenn negativ wird anderer Service aufgerufen
		if(statusOSM == null){
			System.out.println("statusOSM null, Query beginnt"); //test-code
			statusOSM = queryOSM(rawAddress); //Boolean f�r OSM ob anfrage i.O ist
			System.out.println("statusOSM neu : " + statusOSM); //test-code
			//.set weil der Wert an dieser Position ersetzt werden soll
			statusList.set(0, statusOSM); //statusOSM-wert in statusList hinzuf�gen. OSM hat den platz 1 bei statusList
			return "osm";
		}
		else{
			if(statusBing == null){
				System.out.println("statusBing null, Query beginnt"); //test-code
				statusBing = queryBing(rawAddress); //Boolean f�r Bing ob anfrage i.O ist
				System.out.println("statusBing neu : " + statusBing); //test-code
				//.set weil der Wert an dieser Position ersetzt werden soll
				statusList.set(1, statusBing); //statusBing-wert in statusList hinzuf�gen. Bing hat den platz 2 bei statusList
				return "bing";
			}
			else{
				if(statusGoogle == null){
					System.out.println("statusGoogle null, Query beginnt"); //test-code
					statusGoogle = queryGoogle(rawAddress); //Boolean f�r Bing ob anfrage i.O ist
					System.out.println("statusGoogle neu : " + statusGoogle); //test-code
					//.set weil der Wert an dieser Position ersetzt werden soll
					statusList.set(2, statusGoogle); //statusBing-wert in statusList hinzuf�gen. Bing hat den platz 2 bei statusList
					return "google";
				}
				else{
					//anderer Service aufrufen
					System.out.println("Query: anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
				}
			}
		}
	System.out.println("return error -> kein vordefinierter service konnte ausgew�hlt werden "); //test-code
	return "error";
	}

	//Methode erh�lt eine externe Adresse (extAddress) und gibt Boolean zur�ck Wenn True war Abfrage Erfolgreich und vollst�ndig, ansonsten false
	public Boolean queryOSM(String rawAddress){
		System.out.println("Beginn abfrage bei OSM: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		System.out.println("Beginn abfrage bei OSM: urlVar " + urlVar); //test-code
		Boolean status = null; //Boolean f�r OSM ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		//DL/Service wird abgefragt und man erh�lt eine "externe Adresse"
		
		try {
				
			response = Unirest.get("http://nominatim.openstreetmap.org/search?q=" + urlVar + "&format=json&polygon=1&addressdetails=1").asJson();
			
			/*
			response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
					routeParam("address", urlVar). //routeParam will so nicht funktionieren. Fehler noch nicht gefunden
					asJson();
			*/
			
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);
			//String class in classe umbenennen
			String newBody = body.replaceAll("class", "classe");
			System.out.println("bodyNew: " + newBody);
				
			ObjectMapper mapper = new ObjectMapper();
			osm = mapper.readValue(newBody, new TypeReference<List<QueryOSM>>(){});
			
			//achtung nicht vollst�ndig -> get(0) muss noch mit int ersetzt werden sonst wird immer das gleiche aufgerufen
			String osmName = osm.get(0).getDisplay_name();
			System.out.println("name: " + osmName);
			setExtAddress(osmName);
			
			int sizeResults = osm.size();
			
			for(int k = 0; k < sizeResults; k++){
				
				osmName = osm.get(k).getDisplay_name();
				System.out.println("name: " + osmName);
				
				// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressBing
				// OSM-Adresse erstellen f�r Alignment -> evt. in Konstruktor QueryOSM integrieren?
				System.out.println("start Methode createListNewAddressOSM an der Stelle (k): " + k); //test-code
				osm.get(k).createListNewAddressOSM();
				
				//get alle newlistaddress durch mit der methode check NewList
				int sizeNewAddress = osm.get(k).getListNewAddressOSM().size();
				List<String> newAddress = osm.get(k).getListNewAddressOSM();
				System.out.println("start Methode checkNewList bei OSM"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (osm.get(k) -> k) bei newAddressTrue hinzugef�gt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					osm.get(k).getNewAddressTrue().add(kInteger);
					System.out.println("f�gt index bei List NewAddressTrue. index k: " + k + " , address: " + osmName);
				}
				else{
					System.out.println("nichts wird hinzugef�gt -> checkList: " + checkList);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//wenn newNewAddressTrue leer ist gibt es false zur�ck, ansonsten true
		if(osm.get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			status = false;
		}
		else{
			status = true;
		}
		
		return status;
	}
	
	public Boolean queryBing(String rawAddress){
		
		System.out.println("Beginn abfrage bei Bing: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		System.out.println("Beginn abfrage bei Bing: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean f�r Bing ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get("http://dev.virtualearth.net/REST/v1/Locations/" + urlVar + "?&key=" + bingKey).asJson();
				
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			bing = mapper.readValue(body, QueryBing.class);
			
			//achtung nicht vollst�ndig -> get(0) muss noch mit int ersetzt werden sonst wird immer das gleiche aufgerufen
			String bingName = bing.getResourceSets().get(0).getResources().get(0).getName();
			System.out.println("name: " + bingName);
			
			setExtAddress(bingName);
			System.out.println("ExtAddress: " + getExtAddress());
			
			int sizeResults = bing.getResourceSets().get(0).getResources().size();
			
			for(int k = 0; k < sizeResults; k++){
				
				bingName = bing.getResourceSets().get(0).getResources().get(k).getName();
				System.out.println("name: " + bingName);
				
				// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressBing
				// Bing-Adresse erstellen f�r Alignment -> evt. in Konstruktor QueryBing integrieren?
				System.out.println("start Methode createListNewAddressBing an der Stelle (k): " + k); //test-code
				bing.getResourceSets().get(0).getResources().get(k).createListNewAddressBing();
				
				//get alle newlistaddress durch mit der methode check NewList
				//noch nicht getestet aber vermute, dass l�nge von resourceSets immer 0 ist
				int sizeNewAddress = bing.getResourceSets().get(0).getResources().get(k).getListNewAddressBing().size();
				List<String> newAddress = bing.getResourceSets().get(0).getResources().get(k).getListNewAddressBing();
				System.out.println("start Methode checkNewList bei Bing"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (bing.getResourceSets().get(0).getResources().get(k) -> k) bei newAddressTrue hinzugef�gt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					bing.getResourceSets().get(0).getNewAddressTrue().add(kInteger);
					System.out.println("f�gt index bei List NewAddressTrue. index k: " + k + " , address: " + bingName);
				}
				else{
					System.out.println("nichts wird hinzugef�gt -> checkList: " + checkList);
				}
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//wenn newNewAddressTrue leer ist gibt es false zur�ck, ansonsten true
		if(bing.getResourceSets().get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			status = false;
		}
		else{
			status = true;
		}
		
		return status;
	}
	
	public Boolean queryGoogle(String rawAddress){
		
		System.out.println("Beginn abfrage bei Google: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		String googleKey = "AIzaSyAzpsj4yxTE4bQ8R3WysueyAHUNmJ9Chzw";
		System.out.println("Beginn abfrage bei Google: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean f�r Google ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get("https://maps.googleapis.com/maps/api/geocode/json?address=" + urlVar + "&key=" + googleKey).asJson();
				
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			google = mapper.readValue(body, QueryGoogle.class);
			
			int sizeResults = google.getResults().size();
			
			String googleName = google.getResults().get(0).getFormatted_address(); //muss noch ge�ndert werden siehe untere for-schlaufe
			setExtAddress(googleName);
			System.out.println("ExtAddress: " + getExtAddress());
			
			for(int k = 0; k < sizeResults; k++){
				
				googleName = google.getResults().get(k).getFormatted_address();
				System.out.println("formated_address: " + googleName);
				
				// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressGoogle
				// Google-Adresse erstellen f�r Alignment -> evt. in Konstruktor QueryGoogle integrieren?
				System.out.println("start Methode createListNewAddressGoogle an der Stelle (k): " + k); //test-code
				google.getResults().get(k).createListNewAddressGoogle(k); 
				
				//get alle newlistaddress durch mit der methode check NewList
				int sizeNewAddress = google.getResults().get(k).getListNewAddressGoogle().size();
				List<String> newAddress = google.getResults().get(k).getListNewAddressGoogle();
				System.out.println("start Methode checkNewList bei Google"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (google.getResults().get(k) -> k) bei newAddressTrue hinzugef�gt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					google.getNewAddressTrue().add(kInteger);
					System.out.println("f�gt index bei List NewAddressTrue. index k: " + k + " , address: " + googleName);
				}
				else{
					System.out.println("nichts wird hinzugef�gt -> checkList: " + checkList);
				}
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//wenn newNewAddressTrue leer ist gibt es false zur�ck, ansonsten true
		if(google.getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			status = false;
		}
		else{
			status = true;
		}
				
		return status;
	}
	
	//�berpr�fen ob newlistaddress vollst�ndig ist
	public Boolean checkNewList(int sizeNewAddress, List<String> newAddress){ //List<String> weil get(i) innerhalb der while-Methode sein muss
		System.out.println("Start checkNewList : "); //test-code
		Boolean status = true;
		int i = 0;
		
		//wenn position != null wird ein true zur�ckgegeben, ansonsten ein false und die schlaufe wird verlassen
		while(i < sizeNewAddress && status == true){
			String address = newAddress.get(i);
			System.out.println("sizeNewAddress : " + sizeNewAddress); //test-code
			System.out.println("int i : " + i); //test-code
			System.out.println("address : " + address); //test-code
			if( address != null){
				status = true;
				i++;
			}
			else{
				status = false;
			}
		}
		return status;
	}
}


