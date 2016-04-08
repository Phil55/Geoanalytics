
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Abfrage {

	private String rawAddress;
	private int dienstID; //evt. Array
	private String extAddress; //evt. Array
	private List<AbfrageOSM> osm;
	private HttpResponse<JsonNode> response;
	
	public Abfrage(String rawAddress) {
		//super();
		this.rawAddress = rawAddress;
		abfrage(rawAddress);
	}

	public String getRawAddress() {
		return rawAddress;
	}

	public void setRawAddressAbfr(String rawAddress) {
		this.rawAddress = rawAddress;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getExtAddress() {
		return extAddress;
	}

	public void setExtAddress(String extAddress) {
		this.extAddress = extAddress;
	}
	
	public List<AbfrageOSM> getOsm() {
		return osm;
	}

	public void setOsm(List<AbfrageOSM> osm) {
		this.osm = osm;
	}

	public HttpResponse<JsonNode> getResponse() {
		return response;
	}

	public void setResponse(HttpResponse<JsonNode> response) {
		this.response = response;
	}
	
	
	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	//Methode überprüft welche Services bereits überprüft wurden und ruft dann die Methode für den entsprechenden Service der als nächster kommt auf
	public String abfrage(String rawAddress) {
		System.out.println("Beginn abfrage: rawAddress " + rawAddress); //test-code
		//OSM Service abfragen und auf Bollean statusOSM setzen, um zu überprüfen ob abfrage vollständig war
		Boolean statusOSM = abfrageOSM(rawAddress); //Boolean für OSM ob anfrage i.O ist
		Boolean statusBing = abfrageBing(rawAddress); //Boolean für Bing ob anfrage i.O ist
		
		System.out.println("statusOSM : " + statusOSM); //test-code
		System.out.println("statusBing : " + statusBing); //test-code
		
		//Code noch nicht vollständig: es wird überprüft ob vorherige abfrage positiv war. 
		//wenn positiv beginnt Validierung, wenn negativ wird anderer Service aufgerufen
		if(statusOSM == true){
			System.out.println("statusOSM true, Validierung beginnt"); //test-code
			return extAddress;
		}
		else{
			//anderer Service aufrufen
			System.out.println("anderer Service aufrufen "); //test-code
		}
		
		return extAddress;
	}

	//Methode erhält eine externe Adresse (extAddress) und gibt Boolean zurück Wenn True war Abfrage Erfolgreich und vollständig, ansonsten false
	public Boolean abfrageOSM(String rawAddress){
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
			osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
			System.out.println("display: " + osm.get(0).getDisplay_name());
			setExtAddress(osm.get(0).getDisplay_name());
			System.out.println("ExtAddress: " + Abfrage.this.getExtAddress());
			osm.get(0).createListNewAddressOSM(osm);
			
			//überprüfen ob newlistaddress von OSM vollständig ist
			int i = 0; //lokale Variable
			System.out.println("osm.get(0).listNewAddressOSM.size() : " + osm.get(0).listNewAddressOSM.size()); //test-code
			//wenn position != null wird ein true zurückgegeben, ansonsten ein false und die schlaufe wird verlassen
			while(i < osm.get(0).listNewAddressOSM.size() && status == true){
				if( osm.get(0).listNewAddressOSM.get(i) != null){
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
	
	public Boolean abfrageBing(String rawAddress){
		
		System.out.println("Beginn abfrage bei OSM: rawAddress " + rawAddress); //test-code
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		System.out.println("Beginn abfrage bei OSM: urlVar " + urlVar); //test-code
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
			osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
			System.out.println("display: " + osm.get(0).getDisplay_name());
			setExtAddress(osm.get(0).getDisplay_name());
			System.out.println("ExtAddress: " + Abfrage.this.getExtAddress());
			osm.get(0).createListNewAddressOSM(osm);
				
			//überprüfen ob newlistaddress von OSM vollständig ist
			int i = 0; //lokale Variable
			System.out.println("osm.get(0).listNewAddressOSM.size() : " + osm.get(0).listNewAddressOSM.size()); //test-code
			//wenn position != null wird ein true zurückgegeben, ansonsten ein false und die schlaufe wird verlassen
			while(i < osm.get(0).listNewAddressOSM.size() && status == true){
				if( osm.get(0).listNewAddressOSM.get(i) != null){
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


