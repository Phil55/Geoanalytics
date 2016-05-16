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
	private List<Boolean> statusList; 
	private Boolean statusOSM; 
	private Boolean statusBing; 
	private Boolean statusGoogle; 
	
	public Query(String rawAddress) {
		this.rawAddress = rawAddress;
		this.statusList = new ArrayList<Boolean>();
		/*
		Folgende Reihenfolge wird bei statusList festgelegt:
		1 -> osm, 2 -> bing, 3 -> google
		Die Länge von statusList repräsentiert die Anzahl Services
		*/
		for(int i = 0; i < 3; i++){
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

	//Methode überprüft welche Services bereits genutzt wurden
	public String query() {
		
		System.out.println(); 
		System.out.println("start query(), rawAddress :" + rawAddress);
		
		//überprüfen welche Services bereits genutzt wurden 
		if(statusOSM == null){
			statusOSM = queryOSM(rawAddress);
			statusList.set(0, statusOSM);
			return "osm";
		}
		else{
			if(statusBing == null){
				statusBing = queryBing(rawAddress);
				statusList.set(1, statusBing);
				return "bing";
			}
			else{
				if(statusGoogle == null){
					statusGoogle = queryGoogle(rawAddress);
					statusList.set(2, statusGoogle);
					return "google";
				}
				else{
					System.out.println("Query: anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); 
				}
			}
		}
	System.out.println("return error -> kein vordefinierter service konnte ausgewählt werden ");
	return "error";
	}

	public Boolean queryOSM(String rawAddress){
		System.out.println(); 
		System.out.println("start queryOSM()"); 
		System.out.println("rawAddress: " + rawAddress);
		String urlVar = rawAddress.replaceAll(" ", "+");
		String finalURL = "http://nominatim.openstreetmap.org/search?q=" + urlVar + "&format=json&polygon=1&addressdetails=1";
		System.out.println("URL : " + finalURL);
		Boolean status = null; 
		
		try {
			
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response_status: " + response.getStatusText());
			//String class in classe umbenennen
			String newBody = body.replaceAll("class", "classe");
			ObjectMapper mapper = new ObjectMapper();
			osm = mapper.readValue(newBody, new TypeReference<List<QueryOSM>>(){});
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		int sizeResults = osm.size();
		
		for(int i = 0; i < sizeResults; i++){
			String display_name = osm.get(i).getDisplay_name();
			osm.get(i).getAddress().createListNewAddress(i, osm);
			int sizeNewAddress = osm.get(i).getAddress().getListNewAddress().size();
			List<String> newAddress = osm.get(i).getAddress().getListNewAddress();
			Boolean checkList = checkNewList(sizeNewAddress, newAddress);
			
			//Wenn checkList true ist, wird die Stelle (osm.get(k) -> k) bei newAddressTrue hinzugefügt 
			if(checkList == true){
				Integer iInteger = new Integer(i);
				osm.get(0).getNewAddressTrue().add(iInteger);
				System.out.println("fügt index bei List NewAddressTrue. index : " + i + " , address: " + display_name);
			}
			else{
				System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
			}
		}
		
		/*
		//print newAddressTrue für Testzwecke
		for(int i = 0; i < osm.get(0).getNewAddressTrue().size(); i++){
			System.out.println("newAddressTrue an der Stelle " + i + " : " + osm.get(0).getNewAddressTrue().get(i));
		}
		*/
		
		//überprüft ob überhaupt ein Resultat von OSM zurückgegeben wurde
		if(osm.isEmpty() == true){
			System.out.println("Kein Result bei OSM erhalten");
			status = false;
		}
		else{
			if(osm.get(0).getNewAddressTrue().isEmpty() == true){ 
				status = false;
			}
			else{
				status = true;
			}
		}
		return status;
	}
	
	public Boolean queryBing(String rawAddress){
		System.out.println();
		System.out.println("start queryBing()"); 
		System.out.println("rawAddress: " + rawAddress); 
		String urlVar = rawAddress.replaceAll(" ", "%20");
		String bingKey = "AoJzcR56eRmy0CW6xaxTkzvkb3cTLjb6UWgMj2fu_4gt87yatP7oTZ1tcs1wIcx3";
		String finalURL = "http://dev.virtualearth.net/REST/v1/Locations/" + urlVar + "?&key=" + bingKey +"&include=ciso2&includeNeighborhood=1";
		System.out.println("URL : " + finalURL); //test-code
		Boolean status = true;
		
		try {	
			
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response_status: " + response.getStatusText());
			ObjectMapper mapper = new ObjectMapper();
			bing = mapper.readValue(body, QueryBing.class);
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		int sizeResults = bing.getResourceSets().get(0).getResources().size();
		
		for(int i = 0; i < sizeResults; i++){
			
			String bingName = bing.getResourceSets().get(0).getResources().get(i).getName();
			bing.getResourceSets().get(0).getResources().get(i).createListNewAddress(i, bing);
			int sizeNewAddress = bing.getResourceSets().get(0).getResources().get(i).getNewAddress().size();
			List<String> newAddress = bing.getResourceSets().get(0).getResources().get(i).getNewAddress();
			Boolean checkList = checkNewList(sizeNewAddress, newAddress);
			
			//Wenn checkList true ist, wird die Stelle (bing.getResourceSets().get(0).getResources().get(k) -> k) bei newAddressTrue hinzugefügt 
			if(checkList == true){
				Integer iInteger = new Integer(i);
				bing.getResourceSets().get(0).getNewAddressTrue().add(iInteger);
				System.out.println("fügt index bei List NewAddressTrue. index i: " + i + " , address: " + bingName);
			}
			else{
				System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
			}
		}
		
		//überprüft ob überhaupt ein Resultat von Bing zurückgegeben wurde
		if(bing == null){
			System.out.println("Kein Result bei Bing erhalten");
			status = false;
		}
		else{
			if(bing.getResourceSets().get(0).getNewAddressTrue().isEmpty() == true){
				status = false;
			}
			else{
				status = true;
			}
		}
		return status;
	}
	
	public Boolean queryGoogle(String rawAddress){
		System.out.println(); 
		System.out.println("start queryGoogle()"); 
		System.out.println("rawAddress: " + rawAddress);
		String urlVar = rawAddress.replaceAll(" ", "+");
		String googleKey = "AIzaSyAzpsj4yxTE4bQ8R3WysueyAHUNmJ9Chzw";
		String finalURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlVar + "&key=" + googleKey;
		System.out.println("URL : " + finalURL);
		Boolean status = true;
		
		try {
					
			response = Unirest.get(finalURL).asJson();
			String body = response.getBody().toString();
			System.out.println("response_status: " + response.getStatusText());
			ObjectMapper mapper = new ObjectMapper();
			google = mapper.readValue(body, QueryGoogle.class);
			
			int sizeResults = google.getResults().size();
			
			for(int i = 0; i < sizeResults; i++){
				String googleName = google.getResults().get(i).getFormatted_address();
				google.getResults().get(i).createListNewAddress(i, google); 
				int sizeNewAddress = google.getResults().get(i).getNewAddress().size();
				List<String> newAddress = google.getResults().get(i).getNewAddress();
				Boolean checkList = checkNewList(sizeNewAddress, newAddress);
				
				//wenn checkList true ist, wird die Stelle (google.getResults().get(k) -> k) bei newAddressTrue hinzugefügt 
				if(checkList == true){
					Integer iInteger = new Integer(i);
					google.getNewAddressTrue().add(iInteger);
					System.out.println("fügt index bei List NewAddressTrue. index i: " + i + " , address: " + googleName);
				}
				else{
					System.out.println("nichts wird hinzugefügt -> checkList: " + checkList);
				}
			}
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(google.getNewAddressTrue().isEmpty() == true){ 
			status = false;
		}
		else{
			status = true;
		}	
		return status;
	}
	
	//Methode überprüft, ob newListAddress vollständig ist
	public Boolean checkNewList(int sizeNewAddress, List<String> newAddress){ 
		System.out.println(); 
		System.out.println("Start checkNewList() : "); 
		Boolean status = true;
		int i = 0;
		
		while(i < sizeNewAddress && status == true){
			String address = newAddress.get(i);
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