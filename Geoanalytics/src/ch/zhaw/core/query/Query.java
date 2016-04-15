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
	private List<Boolean> statusList; // für überprüfung bei Struktur nötig
	private Boolean statusOSM; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	private Boolean statusBing; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	private Boolean statusGoogle; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	
	public Query(String rawAddress) {
		//super();
		this.rawAddress = rawAddress;
		this.statusList = new ArrayList<Boolean>();
		//this.osm = new ArrayList<QueryOSM>();
		//this.bing = new QueryBing();
		//this.google = new QueryGoogle();
		//folgende Reihenfolge wird bei statusList festgelegt:
		//1 -> osm, 2 -> bing
		for(int i = 0; i < 3; i++){ //zahl 3 repräsentiert die anzahl Services, die es gibt
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

	public Boolean getStatusGoogle() {
		return statusGoogle;
	}

	public void setStatusGoogle(Boolean statusGoogle) {
		this.statusGoogle = statusGoogle;
	}

	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	//Methode überprüft welche Services bereits überprüft wurden und ruft dann die Methode für den entsprechenden Service der als nächster kommt auf
	//Methode gibt vordefinierten String zurück, des aussagt welcher Service aufgerufen wurde (z.B. osm)
	public String query() {
		
		String rawAddress = getRawAddress();
		System.out.println(); //test-code
		System.out.println("start query(), rawAddress :" + rawAddress); //test-code
		//OSM Service abfragen und auf Bollean statusOSM setzen, um zu überprüfen ob abfrage vollständig war
		//überprüfen welche Services bereits genutzt wurden
		
		//drei Boolean muss hier instanziert werden
		Boolean statusOSM = null;
		Boolean statusBing = null;
		Boolean statusGoogle = null;
		
		//überprüfen ob OSM bereits überprüft wurde wenn nicht, wird null gesetzt, ansonsten false oder true
		//Überprüfung kann evt. verbessert werden
		if(osm == null){
			statusOSM = null;
		}
		else{
			statusOSM = osm.get(0).getStatusQuery();
		}
		//überprüfen ob OSM bereits überprüft wurde wenn nicht, wird null gesetzt, ansonsten false oder true
		if(bing == null){
			statusBing = null;
		}
		else{
			statusBing = bing.getResourceSets().get(0).getStatusQuery();
		}
		//überprüfen ob OSM bereits überprüft wurde wenn nicht, wird null gesetzt, ansonsten false oder true
		if(google == null){
			statusGoogle = null;
		}
		else{
			statusGoogle = google.getStatusQuery();
		}
		 
		//Code noch nicht vollständig: es wird überprüft ob vorherige abfrage positiv war. 
		//wenn positiv beginnt Validierung, wenn negativ wird anderer Service aufgerufen
		if(statusOSM == null){
			System.out.println("statusOSM null, Query beginnt"); //test-code
			statusOSM = queryOSM(rawAddress);
			this.statusOSM = statusOSM;
			System.out.println("statusOSM neu : " + statusOSM); //test-code
			//.set weil der Wert an dieser Position ersetzt werden soll
			statusList.set(0, statusOSM); //statusOSM-wert in statusList hinzufügen. OSM hat den platz 1 bei statusList
			return "osm";
		}
		else{
			if(statusBing == null){
				System.out.println("statusBing null, Query beginnt"); //test-code
				statusBing = queryBing(rawAddress);
				this.statusBing = statusBing;
				System.out.println("statusBing neu : " + statusBing); //test-code
				//.set weil der Wert an dieser Position ersetzt werden soll
				statusList.set(1, statusBing); //statusBing-wert in statusList hinzufügen. Bing hat den platz 2 bei statusList
				return "bing";
			}
			else{
				if(statusGoogle == null){
					System.out.println("statusGoogle null, Query beginnt"); //test-code
					statusGoogle = queryGoogle(rawAddress);
					this.statusGoogle = statusGoogle;
					System.out.println("statusGoogle neu : " + statusGoogle); //test-code
					//.set weil der Wert an dieser Position ersetzt werden soll
					statusList.set(2, statusGoogle); //statusBing-wert in statusList hinzufügen. Bing hat den platz 2 bei statusList
					return "google";
				}
				else{
					//anderer Service aufrufen
					System.out.println("Query: anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
				}
			}
		}
	System.out.println("return error -> kein vordefinierter service konnte ausgewählt werden "); //test-code
	return "error";
	}

	//Methode erhält eine externe Adresse (extAddress) und gibt Boolean zurück Wenn True war Abfrage Erfolgreich und vollständig, ansonsten false
	public Boolean queryOSM(String rawAddress){
		System.out.println(); //test-code
		System.out.println("start queryOSM()"); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		String finalURL = "http://nominatim.openstreetmap.org/search?q=" + urlVar + "&format=json&polygon=1&addressdetails=1";
		System.out.println("URL : " + finalURL); //test-code
		System.out.println("Beginn abfrage bei OSM: urlVar " + urlVar); //test-code
		Boolean status = null; //Boolean für OSM ob anfrage i.O ist ; wird default-mässig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		//DL/Service wird abgefragt und man erhält eine "externe Adresse"
		
		try {
				
			response = Unirest.get(finalURL).asJson();
			
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
			
			//achtung nicht vollständig -> get(0) muss noch mit int ersetzt werden sonst wird immer das gleiche aufgerufen
			String osmName = osm.get(0).getDisplay_name();
			System.out.println("name: " + osmName);
			setExtAddress(osmName);
			
			int sizeResults = osm.size();
			
			for(int k = 0; k < sizeResults; k++){
				
				osmName = osm.get(k).getDisplay_name();
				System.out.println("name: " + osmName);
				
				// for-Schlaufe falls mehrere Adressen zurückkommen erstellt ListNewAddressBing
				// OSM-Adresse erstellen für Alignment -> evt. in Konstruktor QueryOSM integrieren?
				System.out.println("start Methode createListNewAddressOSM an der Stelle (k): " + k); //test-code
				osm.get(k).getAddress().createListNewAddress();
				
				//get alle newlistaddress durch mit der methode check NewList
				int sizeNewAddress = osm.get(k).getAddress().getListNewAddress().size();
				List<String> newAddress = osm.get(k).getAddress().getListNewAddress();
				System.out.println("start Methode checkNewList bei OSM"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (osm.get(k) -> k) bei newAddressTrue hinzugefügt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					osm.get(k).getNewAddressTrue().add(kInteger);
					System.out.println("fügt index bei List NewAddressTrue. index k: " + k + " , address: " + osmName);
				}
				else{
					System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//wenn newNewAddressTrue leer ist gibt es false zurück, ansonsten true
		if(osm.get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			osm.get(0).setStatusQuery(false); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für OSM : " + osm.get(0).getStatusQuery()); //test-code
			status = false;
		}
		else{
			osm.get(0).setStatusQuery(true); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für OSM : " + osm.get(0).getStatusQuery()); //test-code
			status = true;
		}
		
		return status;
	}
	
	public Boolean queryBing(String rawAddress){
		System.out.println(); //test-code
		System.out.println("start queryBing()"); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		String finalURL = "http://dev.virtualearth.net/REST/v1/Locations/" + urlVar + "?&key=" + bingKey;
		System.out.println("URL : " + finalURL); //test-code
		System.out.println("Beginn abfrage bei Bing: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean für Bing ob anfrage i.O ist ; wird default-mässig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get(finalURL).asJson();
				
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			bing = mapper.readValue(body, QueryBing.class);
			
			//achtung nicht vollständig -> get(0) muss noch mit int ersetzt werden sonst wird immer das gleiche aufgerufen
			String bingName = bing.getResourceSets().get(0).getResources().get(0).getName();
			System.out.println("name: " + bingName);
			
			setExtAddress(bingName);
			System.out.println("ExtAddress: " + getExtAddress());
			
			int sizeResults = bing.getResourceSets().get(0).getResources().size();
			
			for(int k = 0; k < sizeResults; k++){
				
				bingName = bing.getResourceSets().get(0).getResources().get(k).getName();
				System.out.println("name: " + bingName);
				
				// for-Schlaufe falls mehrere Adressen zurückkommen erstellt ListNewAddressBing
				// Bing-Adresse erstellen für Alignment -> evt. in Konstruktor QueryBing integrieren?
				System.out.println("start Methode createListNewAddressBing an der Stelle (k): " + k); //test-code
				bing.getResourceSets().get(0).getResources().get(k).createListNewAddress();
				
				//get alle newlistaddress durch mit der methode check NewList
				//noch nicht getestet aber vermute, dass länge von resourceSets immer 0 ist
				int sizeNewAddress = bing.getResourceSets().get(0).getResources().get(k).getNewAddress().size();
				List<String> newAddress = bing.getResourceSets().get(0).getResources().get(k).getNewAddress();
				System.out.println("start Methode checkNewList bei Bing"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (bing.getResourceSets().get(0).getResources().get(k) -> k) bei newAddressTrue hinzugefügt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					bing.getResourceSets().get(0).getNewAddressTrue().add(kInteger);
					System.out.println("fügt index bei List NewAddressTrue. index k: " + k + " , address: " + bingName);
				}
				else{
					System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
				}
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//wenn newNewAddressTrue leer ist gibt es false zurück, ansonsten true
		if(bing.getResourceSets().get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			bing.getResourceSets().get(0).setStatusQuery(false); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für Bing : " + bing.getResourceSets().get(0).getStatusQuery()); //test-code
			status = false;
		}
		else{
			bing.getResourceSets().get(0).setStatusQuery(true); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für Bing : " + bing.getResourceSets().get(0).getStatusQuery()); //test-code
			status = true;
		}
		
		return status;
	}
	
	public Boolean queryGoogle(String rawAddress){
		System.out.println(); //test-code
		System.out.println("start queryGoogle()"); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		String googleKey = "AIzaSyAzpsj4yxTE4bQ8R3WysueyAHUNmJ9Chzw";
		String finalURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlVar + "&key=" + googleKey;
		System.out.println("URL : " + finalURL); //test-code
		System.out.println("Beginn abfrage bei Google: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean für Google ob anfrage i.O ist ; wird default-mässig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get(finalURL).asJson();
				
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			google = mapper.readValue(body, QueryGoogle.class);
			
			int sizeResults = google.getResults().size();
			
			String googleName = google.getResults().get(0).getFormatted_address(); //muss noch geändert werden siehe untere for-schlaufe
			setExtAddress(googleName);
			System.out.println("ExtAddress: " + getExtAddress());
			
			for(int k = 0; k < sizeResults; k++){
				
				googleName = google.getResults().get(k).getFormatted_address();
				System.out.println("formated_address: " + googleName);
				
				// for-Schlaufe falls mehrere Adressen zurückkommen erstellt ListNewAddressGoogle
				// Google-Adresse erstellen für Alignment -> evt. in Konstruktor QueryGoogle integrieren?
				System.out.println("start Methode createListNewAddressGoogle an der Stelle (k): " + k); //test-code
				google.getResults().get(k).createListNewAddress(k); 
				
				//get alle newlistaddress durch mit der methode check NewList
				int sizeNewAddress = google.getResults().get(k).getNewAddress().size();
				List<String> newAddress = google.getResults().get(k).getNewAddress();
				System.out.println("start Methode checkNewList bei Google"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (google.getResults().get(k) -> k) bei newAddressTrue hinzugefügt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer kInteger = new Integer(k);
					google.getNewAddressTrue().add(kInteger);
					System.out.println("fügt index bei List NewAddressTrue. index k: " + k + " , address: " + googleName);
				}
				else{
					System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
				}
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//wenn newNewAddressTrue leer ist gibt es false zurück, ansonsten true
		if(google.getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
			google.setStatusQuery(false); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für Google : " + google.getStatusQuery()); //test-code
			status = false;
		}
		else{
			google.setStatusQuery(true); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusQuery für Google : " + google.getStatusQuery()); //test-code
			status = true;
		}
				
		return status;
	}
	
	//überprüfen ob newlistaddress vollständig ist
	public Boolean checkNewList(int sizeNewAddress, List<String> newAddress){ //List<String> weil get(i) innerhalb der while-Methode sein muss
		System.out.println(); //test-code
		System.out.println("Start checkNewList() : "); //test-code
		Boolean status = true;
		int i = 0;
		
		//wenn position != null wird ein true zurückgegeben, ansonsten ein false und die schlaufe wird verlassen
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


