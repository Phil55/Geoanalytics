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

public class Query {

	private String rawAddress;
	private String extAddress; //evt. Array
	private List<QueryOSM> osm;
	private QueryBing bing;
	private HttpResponse<JsonNode> response;
	private Boolean statusOSM;
	private Boolean statusBing;
	private List<Boolean> statusList; // für überprüfung bei Struktur nötig
	
	public Query(String rawAddress) {
		//super();
		this.rawAddress = rawAddress;
		this.statusOSM = null; //auf null gesetzt damit Bedingung simpler abzufragen ist
		this.statusBing = null; //auf null gesetzt damit Bedingung simpler abzufragen ist
		this.statusList = new ArrayList<Boolean>(); 
		//folgende Reihenfolge wird bei statusList festgelegt:
		//1 -> osm, 2 -> bing
		for(int i = 0; i < 2; i++){ //zahl 2 repräsentiert die anzahl Services, die es gibt
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

	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	//Methode überprüft welche Services bereits überprüft wurden und ruft dann die Methode für den entsprechenden Service der als nächster kommt auf
	//Methode gibt vordefinierten String zurück, des aussagt welcher Service aufgerufen wurde (z.B. osm)
	public String query() {
		String rawAddress = getRawAddress();
		System.out.println("Beginn abfrage: rawAddress " + rawAddress); //test-code
		//OSM Service abfragen und auf Bollean statusOSM setzen, um zu überprüfen ob abfrage vollständig war
		//überprüfen welche Services bereits genutzt wurden
		
		 
		//Code noch nicht vollständig: es wird überprüft ob vorherige abfrage positiv war. 
		//wenn positiv beginnt Validierung, wenn negativ wird anderer Service aufgerufen
		if(statusOSM == null){
			System.out.println("statusOSM false, Query beginnt"); //test-code
			statusOSM = queryOSM(rawAddress); //Boolean für OSM ob anfrage i.O ist
			System.out.println("statusOSM neu : " + statusOSM); //test-code
			//.set weil der Wert an dieser Position ersetzt werden soll
			statusList.set(0, statusOSM); //statusOSM-wert in statusList hinzufügen. OSM hat den platz 1 bei statusList
			return "osm";
		}
		else{
			if(statusBing == null){
				System.out.println("statusBing false, Query beginnt"); //test-code
				statusBing = queryBing(rawAddress); //Boolean für Bing ob anfrage i.O ist
				System.out.println("statusBing neu : " + statusBing); //test-code
				//.set weil der Wert an dieser Position ersetzt werden soll
				statusList.set(1, statusBing); //statusBing-wert in statusList hinzufügen. Bing hat den platz 2 bei statusList
				return "bing";
			}
			else{
				//anderer Service aufrufen
				System.out.println("Query: anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
			}
		}
	System.out.println("return error -> kein vordefinierter service konnte ausgewählt werden "); //test-code
	return "error";
	}

	//Methode erhält eine externe Adresse (extAddress) und gibt Boolean zurück Wenn True war Abfrage Erfolgreich und vollständig, ansonsten false
	public Boolean queryOSM(String rawAddress){
		System.out.println("Beginn abfrage bei OSM: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "+");
		System.out.println("Beginn abfrage bei OSM: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean für OSM ob anfrage i.O ist ; wird default-mässig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		//DL/Service wird abgefragt und man erhält eine "externe Adresse"
		
		try {
			
			/*
			response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
				routeParam("address", "Bachstrasse+39,+8912+Obfelden").
				//header("accept", "application/json").
				//queryString("display_name", "display_name").
				asJson();
			*/
				
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
			System.out.println("display: " + osm.get(0).getDisplay_name());
			setExtAddress(osm.get(0).getDisplay_name());
			System.out.println("ExtAddress: " + getExtAddress());
			osm.get(0).createListNewAddressOSM(osm);
			
			//überprüfen ob newlistaddress von OSM vollständig ist
			int i = 0; //lokale Variable
			int sizeNewAddress = osm.get(0).getListNewAddressOSM().size();
			String getNewAddress = osm.get(0).getListNewAddressOSM().get(i);
			System.out.println("osm.get(0).getListNewAddressOSM().size() : " + sizeNewAddress); //test-code
			//wenn position != null wird ein true zurückgegeben, ansonsten ein false und die schlaufe wird verlassen
			while(i < sizeNewAddress && status == true){
				if( getNewAddress != null){
					status = true;
					i++;
				}
				else{
					status = false;
				}
			}
			
			//List<AbfrageOSM> osm = mapper.readValue(body, ArrayList.class);
			//AbfrageOSM [] osm = mapper.readValue(newBody, AbfrageOSM[].class);
			//System.out.println("JavaObjekt Abfragedienst n: " + mapper.readValue(newBody, AbfrageOSM[].class)) ;
				
			System.out.println("response_status2: " + response.getStatusText());
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
						
		//extAddress = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
		return status;
		
		
		/*
		if (rawAddress.contains("strasse") == true) {
			//AbfrageDienst n = new AbfrageDienst(1);
			//String newAddress = n.AbfServiceOSM(rawAddress);
			
			//DL/Service wird abgefragt und man erhält eine "externe Adresse"
				
			try {
				
				response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
					routeParam("address", "Bachstrasse+39,+8912+Obfelden").
					//header("accept", "application/json").
					//queryString("display_name", "display_name").
					asJson();
					
				response = Unirest.get("http://nominatim.openstreetmap.org/search?q=Bachstrasse+39,+8912+Obfelden&format=json&polygon=1&addressdetails=1").asJson();
				String body = response.getBody().toString();
				System.out.println("response: " + response);
				System.out.println("response_status: " + response.getStatusText());
				System.out.println("bodyReal: " + body);
				//String class in classe umbenennen
				String newBody = body.replaceAll("class", "classe");
				System.out.println("bodyNew: " + newBody);
					
				ObjectMapper mapper = new ObjectMapper();
				osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
				System.out.println("display: " + osm.get(0).getDisplay_name());
				setExtAddress(osm.get(0).getDisplay_name());
				System.out.println("ExtAddress: " + Abfrage.this.getExtAddress());
				
				//List<AbfrageOSM> osm = mapper.readValue(body, ArrayList.class);
				//AbfrageOSM [] osm = mapper.readValue(newBody, AbfrageOSM[].class);
				//System.out.println("JavaObjekt Abfragedienst n: " + mapper.readValue(newBody, AbfrageOSM[].class)) ;
					
				System.out.println("response_status2: " + response.getStatusText());
					
			} catch (Exception ex) {
				ex.printStackTrace();
			}
							
			//extAddress = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
			return extAddress;
			
			
		}
		else if (rawAddress == "strasse 2") {
			//wird später erweitert
		}
		//Code ist im Moment Platzhalter, damit der ganze Prozess läuft
		else {
			
			//DL/Service wird abgefragt und man erhält eine "externe Adresse"
			
			try {
				
				
				HttpResponse<JsonNode> response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
					routeParam("address", "Bachstrasse+39,+8912+Obfelden").
					//header("accept", "application/json").
					//queryString("display_name", "display_name").
					asJson();
				
					
				response = Unirest.get("http://nominatim.openstreetmap.org/search?q=Bachstrasse+39,+8912+Obfelden&format=json&polygon=1&addressdetails=1").asJson();
				String body = response.getBody().toString();
				System.out.println("response: " + response);
				System.out.println("response_status: " + response.getStatusText());
				System.out.println("bodyReal: " + body);
				//String class in classe umbenennen
				String newBody = body.replaceAll("class", "classe");
				System.out.println("bodyNew: " + newBody);
				
				ObjectMapper mapper = new ObjectMapper();
				List<AbfrageOSM> osmOne = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
				System.out.println("display: " + osmOne.get(0).getDisplay_name());
				
				setExtAddress(osmOne.get(0).getDisplay_name());
				System.out.println("ExtAddress: " + Abfrage.this.getExtAddress());
				
				//List<AbfrageOSM> osm = mapper.readValue(body, ArrayList.class);
				//AbfrageOSM [] osm = mapper.readValue(newBody, AbfrageOSM[].class);
				//System.out.println("JavaObjekt Abfragedienst n: " + mapper.readValue(newBody, AbfrageOSM[].class)) ;
					
				System.out.println("response_status2: " + response.getStatusText());
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			//extAddress = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
			System.out.println("Ende abfrage: extaddress " + extAddress); //test-code
			return extAddress;
		}
		
		
		return extAddress;
		*/
	}
	
	public Boolean queryBing(String rawAddress){
		
		System.out.println("Beginn abfrage bei Bing: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		System.out.println("Beginn abfrage bei Bing: urlVar " + urlVar); //test-code
		Boolean status = true; //Boolean für OSM ob anfrage i.O ist ; wird default-mässig auf true gesetzt weil sonst while-schlaufe nicht funktioniert
		
		try {
					
			response = Unirest.get("http://dev.virtualearth.net/REST/v1/Locations/" + urlVar + "?&key=" + bingKey).asJson();
				
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);
			String newBody = body.replaceAll("class", "classe");
			System.out.println("bodyNew: " + newBody);
			
					
			ObjectMapper mapper = new ObjectMapper();
			bing = mapper.readValue(newBody, QueryBing.class);
			
			//achtung nicht vollständig -> get(0) muss noch mit int ersetzt werden sonst wird immer das gleiche aufgerufen
			String bingName = bing.getResourceSets().get(0).getResources().get(0).getName();
			System.out.println("name: " + bingName);
			
			
			setExtAddress(bingName);
			System.out.println("ExtAddress: " + getExtAddress());
			bing.createListNewAddressBing(bing); // Bing-Adresse erstellen für Alignment -> evt. in Konstruktor QueryBing integrieren?
				
			//überprüfen ob newlistaddress von Bing vollständig ist -> evt. überprüfung gleich? -> neue Methode
			int i = 0; //lokale Variable
			int sizeNewAddress = bing.getListNewAddressBing().size();
			String getNewAddress = bing.getListNewAddressBing().get(i);
			System.out.println("bing.getListNewAddressBing().size() : " + sizeNewAddress); //test-code
			//wenn position != null wird ein true zurückgegeben, ansonsten ein false und die schlaufe wird verlassen
			while(i < sizeNewAddress && status == true){
				if( getNewAddress != null){
					status = true;
					i++;
				}
				else{
					status = false;
				}
			}
				
			System.out.println("response_status2: " + response.getStatusText());
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;
	}
}


