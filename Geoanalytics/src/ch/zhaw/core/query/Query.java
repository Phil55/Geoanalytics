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
	private List<QueryOSM> osm;
	private QueryBing bing;
	private QueryGoogle google;
	private HttpResponse<JsonNode> response;
	private List<Boolean> statusList; // f�r �berpr�fung bei Struktur n�tig
	private Boolean statusOSM; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	private Boolean statusBing; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	private Boolean statusGoogle; // ersetzt statusQuery da es bei den Abfragen zu problemen kommt und so der einfachere weg ist
	
	public Query(String rawAddress) {
		this.rawAddress = rawAddress;
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
	//Reihenfolge und Bedingungen k�nnen sich noch �ndern
	//Methode �berpr�ft welche Services bereits �berpr�ft wurden und ruft dann die Methode f�r den entsprechenden Service der als n�chster kommt auf
	//Methode gibt vordefinierten String zur�ck, des aussagt welcher Service aufgerufen wurde (z.B. osm)
	public String query() {
		
		System.out.println(); //test-code
		System.out.println("start query(), rawAddress :" + rawAddress); //test-code
		
		//�berpr�fen welche Services bereits genutzt wurden 
		//Code noch nicht vollst�ndig: es wird �berpr�ft ob vorherige abfrage positiv war. 
		//wenn positiv beginnt Validierung, wenn negativ wird anderer Service aufgerufen
		if(statusOSM == null){
			System.out.println("statusOSM null, Query beginnt"); //test-code
			statusOSM = queryOSM(rawAddress);
			System.out.println("statusOSM neu : " + statusOSM); //test-code
			//.set weil der Wert an dieser Position ersetzt werden soll
			statusList.set(0, statusOSM); //statusOSM-wert in statusList hinzuf�gen. OSM hat den platz 1 bei statusList
			return "osm";
		}
		else{
			if(statusBing == null){
				System.out.println("statusBing null, Query beginnt"); //test-code
				statusBing = queryBing(rawAddress);
				System.out.println("statusBing neu : " + statusBing); //test-code
				//.set weil der Wert an dieser Position ersetzt werden soll
				statusList.set(1, statusBing); //statusBing-wert in statusList hinzuf�gen. Bing hat den platz 2 bei statusList
				return "bing";
			}
			else{
				if(statusGoogle == null){
					System.out.println("statusGoogle null, Query beginnt"); //test-code
					statusGoogle = queryGoogle(rawAddress);
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
		System.out.println(); //test-code
		System.out.println("start queryOSM()"); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		String finalURL = "http://nominatim.openstreetmap.org/search?q=" + urlVar + "&format=json&polygon=1&addressdetails=1";
		System.out.println("URL : " + finalURL); //test-code
		System.out.println("Beginn abfrage bei OSM: urlVar " + urlVar); //test-code
		Boolean status = null; //Boolean f�r OSM ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
			
			/*
			response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
					routeParam("address", urlVar). //routeParam will so nicht funktionieren. Fehler noch nicht gefunden
					asJson();
			*/
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);
			//String class in classe umbenennen
			String newBody = body.replaceAll("class", "classe");
			System.out.println("bodyNew: " + newBody);
				
			ObjectMapper mapper = new ObjectMapper();
			osm = mapper.readValue(newBody, new TypeReference<List<QueryOSM>>(){});
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		int sizeResults = osm.size();
		// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressBing
		for(int i = 0; i < sizeResults; i++){
			
			String display_name = osm.get(i).getDisplay_name(); //nur f�r println "f�gt index bei List NewAddressTrue. index i:" ben�tigt
			System.out.println("start Methode createListNewAddressOSM an der Stelle (i): " + i); //test-code
			osm.get(i).getAddress().createListNewAddress(i, osm);
			
			//get alle newlistaddress durch mit der methode check NewList
			int sizeNewAddress = osm.get(i).getAddress().getListNewAddress().size();
			List<String> newAddress = osm.get(i).getAddress().getListNewAddress();
			System.out.println("start Methode checkNewList bei OSM"); //test-code
			Boolean checkList = checkNewList(sizeNewAddress, newAddress);
			
			//wenn checkList true ist, wird die Stelle (osm.get(k) -> k) bei newAddressTrue hinzugef�gt 
			//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
			if(checkList == true){
				Integer iInteger = new Integer(i);
				osm.get(0).getNewAddressTrue().add(iInteger);
				System.out.println("f�gt index bei List NewAddressTrue. index : " + i + " , address: " + display_name);
			}
			else{
				System.out.println("nichts wird hinzugef�gt -> checkList: " + checkList);
			}
		}
		
		/*
		//print newAddressTrue f�r Testzwecke
		for(int i = 0; i < osm.get(0).getNewAddressTrue().size(); i++){
			System.out.println("newAddressTrue an der Stelle " + i + " : " + osm.get(0).getNewAddressTrue().get(i));
		}
		*/
		
		//�berpr�ft ob �berhaupt ein Resultat von OSM zur�ckgegeben wurde
		if(osm.isEmpty() == true){
			System.out.println("Kein Result bei OSM erhalten"); //test-code
			status = false;
		}
		else{
			//wenn newNewAddressTrue leer ist gibt es false zur�ck, ansonsten true
			if(osm.get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
				osm.get(0).setStatusQuery(false); //Boolean f�r OSM ob anfrage i.O ist
				System.out.println("statusQuery f�r OSM : " + osm.get(0).getStatusQuery()); //test-code
				status = false;
			}
			else{
				osm.get(0).setStatusQuery(true); //Boolean f�r OSM ob anfrage i.O ist
				System.out.println("statusQuery f�r OSM : " + osm.get(0).getStatusQuery()); //test-code
				status = true;
			}
		}
		return status;
	}
	
	public Boolean queryBing(String rawAddress){
		System.out.println(); //test-code
		System.out.println("start queryBing()"); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		String finalURL = "http://dev.virtualearth.net/REST/v1/Locations/" + urlVar + "?&key=" + bingKey +"&include=ciso2&includeNeighborhood=1";
		System.out.println("URL : " + finalURL); //test-code
		System.out.println("Beginn abfrage bei Bing: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean f�r Bing ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try { //siehe Java Handbuch ab Seite 301 wird Exeption erkl�rt -> vor allem f�r GET ben�tigt
					
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			bing = mapper.readValue(body, QueryBing.class);
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		int sizeResults = bing.getResourceSets().get(0).getResources().size();
		// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressBing
		for(int i = 0; i < sizeResults; i++){
			
			String bingName = bing.getResourceSets().get(0).getResources().get(i).getName();
			System.out.println("name: " + bingName);
			System.out.println("start Methode createListNewAddressBing an der Stelle (i): " + i); //test-code
			bing.getResourceSets().get(0).getResources().get(i).createListNewAddress(i, bing);
			
			//get alle newlistaddress durch mit der methode check NewList
			//noch nicht getestet aber vermute, dass l�nge von resourceSets immer 0 ist
			int sizeNewAddress = bing.getResourceSets().get(0).getResources().get(i).getNewAddress().size();
			List<String> newAddress = bing.getResourceSets().get(0).getResources().get(i).getNewAddress();
			System.out.println("start Methode checkNewList bei Bing"); //test-code
			Boolean checkList = checkNewList(sizeNewAddress, newAddress);
			
			//wenn checkList true ist, wird die Stelle (bing.getResourceSets().get(0).getResources().get(k) -> k) bei newAddressTrue hinzugef�gt 
			//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
			if(checkList == true){
				Integer iInteger = new Integer(i);
				bing.getResourceSets().get(0).getNewAddressTrue().add(iInteger);
				System.out.println("f�gt index bei List NewAddressTrue. index i: " + i + " , address: " + bingName);
			}
			else{
				System.out.println("nichts wird hinzugef�gt -> checkList: " + checkList);
			}
		}
		
		//�berpr�ft ob �berhaupt ein Resultat von Bing zur�ckgegeben wurde
		if(bing == null){
			System.out.println("Kein Result bei Bing erhalten"); //test-code
			status = false;
		}
		else{
			//wenn newNewAddressTrue leer ist gibt es false zur�ck, ansonsten true
			if(bing.getResourceSets().get(0).getNewAddressTrue().isEmpty() == true){ // Achtung ! newAddressTrue wird bewusst nur bei stelle 0 erstellt
				bing.getResourceSets().get(0).setStatusQuery(false); //Boolean f�r OSM ob anfrage i.O ist
				System.out.println("statusQuery f�r Bing : " + bing.getResourceSets().get(0).getStatusQuery()); //test-code
				status = false;
			}
			else{
				bing.getResourceSets().get(0).setStatusQuery(true); //Boolean f�r OSM ob anfrage i.O ist
				System.out.println("statusQuery f�r Bing : " + bing.getResourceSets().get(0).getStatusQuery()); //test-code
				status = true;
			}
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
		Boolean status = true; //Boolean f�r Google ob anfrage i.O ist ; wird default-m�ssig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);

			ObjectMapper mapper = new ObjectMapper();
			google = mapper.readValue(body, QueryGoogle.class);
			
			int sizeResults = google.getResults().size();
			// for-Schlaufe falls mehrere Adressen zur�ckkommen erstellt ListNewAddressGoogle
			for(int i = 0; i < sizeResults; i++){
				
				String googleName = google.getResults().get(i).getFormatted_address();
				System.out.println("formated_address: " + googleName);
				System.out.println("start Methode createListNewAddressGoogle an der Stelle (i): " + i); //test-code
				google.getResults().get(i).createListNewAddress(i, google); 
				
				//get alle newlistaddress durch mit der methode check NewList
				int sizeNewAddress = google.getResults().get(i).getNewAddress().size();
				List<String> newAddress = google.getResults().get(i).getNewAddress();
				System.out.println("start Methode checkNewList bei Google"); //test-code
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (google.getResults().get(k) -> k) bei newAddressTrue hinzugef�gt 
				//kann evt. zu einer Methode umgewandelt werden. wird aber eher schwierig
				if(checkList == true){
					Integer iInteger = new Integer(i);
					google.getNewAddressTrue().add(iInteger);
					System.out.println("f�gt index bei List NewAddressTrue. index i: " + i + " , address: " + googleName);
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
			google.setStatusQuery(false); //Boolean f�r OSM ob anfrage i.O ist
			System.out.println("statusQuery f�r Google : " + google.getStatusQuery()); //test-code
			status = false;
		}
		else{
			google.setStatusQuery(true); //Boolean f�r OSM ob anfrage i.O ist
			System.out.println("statusQuery f�r Google : " + google.getStatusQuery()); //test-code
			status = true;
		}	
		return status;
	}
	
	//�berpr�fen ob newlistaddress vollst�ndig ist
	public Boolean checkNewList(int sizeNewAddress, List<String> newAddress){ //List<String> weil get(i) innerhalb der while-Methode sein muss
		System.out.println(); //test-code
		System.out.println("Start checkNewList() : "); //test-code
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


