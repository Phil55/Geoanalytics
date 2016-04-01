
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Abfrage {

	private String rawAddressAbfr;
	private int dienstID; //evt. Array
	private String extAddress; //evt. Array
	
	public Abfrage(String rawAddress) {
		//super();
		this.rawAddressAbfr = rawAddress;
		abfrage(rawAddress);
	}

	public String getRawAddressAbfr() {
		return rawAddressAbfr;
	}

	public void setRawAddressAbfr(String rawAddressAbfr) {
		this.rawAddressAbfr = rawAddressAbfr;
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
	
	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	public String abfrage(String rawAddress) {
		System.out.println("Beginn abfrage: rawAddress " + rawAddress); //test-code
		if (rawAddress.contains("strasse") == true) {
			//AbfrageDienst n = new AbfrageDienst(1);
			//String newAddress = n.AbfServiceOSM(rawAddress);
			
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
				List<AbfrageOSM> osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
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
				List<AbfrageOSM> osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
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
			System.out.println("Ende abfrage: extaddress " + extAddress); //test-code
			return extAddress;
		}
		
		return extAddress;
	}
	
	
	
	
	
	
	
	
	
	
}
