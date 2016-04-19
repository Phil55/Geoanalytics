package ch.zhaw.core.query.queryGoogle;

import java.util.ArrayList;
import java.util.List;

public class Results {
	
	private String formatted_address;
	private String place_id;
	private String partial_match;
	private List<String> types;
	private List<AddressComponents> address_components;
	private Geometry geometry;
	private List<String> newAddress = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	private Boolean status = null; //für Validation benötigt
	private Double score; //für Validation benötigt
	
	
	//constructor for objectmapper
	public Results(){	
	}

	public List<AddressComponents> getAddress_components() {
		return address_components;
	}

	public void setAddress_components(List<AddressComponents> address_components) {
		this.address_components = address_components;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public List<String> getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(List<String> newAddress) {
		this.newAddress = newAddress;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getPartial_match() {
		return partial_match;
	}

	public void setPartial_match(String partial_match) {
		this.partial_match = partial_match;
	}

	public void createListNewAddress(int i, QueryGoogle google){ //Objekt wegen Methode printJsonResponse() notwendig
		System.out.println(); //test-code
		System.out.println("Start createListNewAddress() (Google)"); //test
		
		//Strings werden default-mässig auf null gesetzt
		String route = null;
		String addressNumber = null;
		String plz = null;
		String locality = null;
		String sublocalityLevel1 = null;
		String areaLevel1 = null; //evt. nicht nötig
		String areaLevel2 = null; //evt. nicht nötig
		String country = null;
		String postalCodeSuffix = null; // ist bei US-Adressen aufgetreten
		
		// index wo das gesuchte element bei address_component ist wird als int gespeichert
		int routeIndex = getIndex("route", i);
		int addressNumberIndex = getIndex("street_number", i);
		int plzIndex = getIndex("postal_code", i);
		int localityIndex = getIndex("locality", i);
		int sublocalityLevel1Index = getIndex("sublocality_level_1", i);
		int areaLevel1Index = getIndex("administrative_area_level_1", i);
		int areaLevel2Index = getIndex("administrative_area_level_2", i);
		int countryIndex = getIndex("country", i);
		int postalCodeSuffixIndex = getIndex("postal_code_suffix", i);
		
		//if-schleifen für alle Strings die ober erstellt wurden, es wird überprüft ob etwas bei address_component gefunden wurde
		//wenn der index -1 bleibt der String bei null da nichts gefunden wurde, falls etwas gefunden wurde wird dieses Element auf den String übernommen
		if(routeIndex == -1){
			//route = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -route-, String route wird auf null gesetzt");
		}
		else{
			route = address_components.get(routeIndex).getLong_name();
		}
		if(addressNumberIndex == -1){
			//addressNumber = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -street_number-, String addressNumber wird auf null gesetzt");
		}
		else{
			addressNumber = address_components.get(addressNumberIndex).getLong_name();
		}
		if(plzIndex == -1){
			//plz = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -postal_code-, String plz wird auf null gesetzt");
		}
		else{
			plz = address_components.get(plzIndex).getLong_name();
		}
		if(localityIndex == -1){
			//locality = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -locality-, String locality wird auf null gesetzt");
		}
		else{
			locality = address_components.get(localityIndex).getLong_name();
		}
		if(sublocalityLevel1Index == -1){
			//sublocalityLevel1 = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -sublocality_level_1-, String sublocalityLevel1 wird auf null gesetzt");
		}
		else{
			sublocalityLevel1 = address_components.get(sublocalityLevel1Index).getLong_name();
		}
		if(areaLevel1Index == -1){
			//areaLevel1 = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -administrative_area_level_1-, String areaLevel1 wird auf null gesetzt");
		}
		else{
			areaLevel1 = address_components.get(areaLevel1Index).getLong_name();
		}
		if(areaLevel2Index == -1){
			//areaLevel2 = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -administrative_area_level_2-, String areaLevel2 wird auf null gesetzt");
		}
		else{
			areaLevel2 = address_components.get(areaLevel2Index).getLong_name();
		}
		if(countryIndex == -1){
			//country = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -country-, String country wird auf null gesetzt");
		}
		else{
			country = address_components.get(countryIndex).getLong_name();
		}
		if(postalCodeSuffixIndex == -1){
			//postalCodeSuffix = null; //wird nicht benötig, da String schon am anfang auf null gesetzt ist
			System.out.println("kein address_component gefunden mit -postalCodeSuffix-, String postalCodeSuffix wird auf null gesetzt");
		}
		else{
			postalCodeSuffix = address_components.get(postalCodeSuffixIndex).getLong_name();
		}
		
		//überprüft ob road oder addressNumber null ist. 
		//Falls die nummer null ist wird nur die strasse bei der addressLine hinzugefügt. Falls die strasse oder beides null ist wird addressLine auf null gesetzt
		String addressLine = null;
		if(addressNumber == null){
			addressLine = route;
		}
		else if(route == null){
			addressLine = null;
		}
		else{
			addressLine = route + " " + addressNumber;
		}
		
		//String addressLine = route + " " + addressNumber;
		/*
		String route = address_components.get(getIndex("route", i)).getLong_name();
		String addressNumber = address_components.get(getIndex("street_number", i)).getLong_name();
		String plz = address_components.get(getIndex("postal_code", i)).getLong_name();
		String locality = address_components.get(getIndex("locality", i)).getLong_name();
		String sublocalityLevel1 = address_components.get(getIndex("sublocality_level_1", i)).getLong_name();
		String areaLevel1 = address_components.get(getIndex("administrative_area_level_1", i)).getLong_name(); //evt. nicht nötig
		String areaLevel2 = address_components.get(getIndex("administrative_area_level_2", i)).getLong_name(); //evt. nicht nötig
		*/
				
		// listNewAddress muss pro Service gleich strukturiert sein!! -> road, nr, plz, ort
		// Struktur kann noch ergänzt bzw. verändert werden
		
		//addressline
		newAddress.add(addressLine);
		System.out.println("newAddress.add(addressLine) = " + addressLine);
		
		/*
		//nr
		newAddress.add(addressNumber);
		System.out.println("newAddress.add(addressNumber) = " + addressNumber);
		*/
		
		//plz
		newAddress.add(plz);
		System.out.println("newAddress.add(plz) = " + plz);
		
		//Ort
		if (locality != null){
			newAddress.add(locality);
			System.out.println("newAddress.add(locality) = " + locality);
		}
		
		//evt. nicht nötig
		/*
		if (areaLevel1 != null){
			newAddress.add(areaLevel1);
			System.out.println("newAddress.add(areaLevel1) = " + areaLevel1);
		}
		if (areaLevel2 != null){
			newAddress.add(areaLevel2);
			System.out.println("newAddress.add(areaLevel2) = " + areaLevel2);
		}
		*/
		
		printJsonResponseGoogle(i, google, route, addressNumber, plz, locality, sublocalityLevel1, areaLevel1, areaLevel2, country, postalCodeSuffix);
	}
	
	//findet die stelle, wo die gesuchte componente ist (z.B. street_number) falls nichts gefunden wurde gibt es -1 zurück
	public int getIndex(String component, int i){
		System.out.println(); //test-code
		System.out.println("Start getIndex() (Google)"); //test
		int index = -1; // nicht null da erste Position von list 0 ist
		int sizeComponent = address_components.size();
		System.out.println("sizeComponent = " + sizeComponent);
		int p = 0;
		
		while(index == -1 && p < sizeComponent){ // (index == -1 || p < sizeComponent) funktioniert nicht, deshalb rausgenommen, index == -1 && p < sizeComponent funktioniert bis jetzt 
			System.out.println("p = " + p + " , index = " + index);
			String long_name = address_components.get(p).getLong_name();
			String short_name = address_components.get(p).getShort_name();
			//list erstellt, damit nächste for-schlaufe übersichtlicher ist
			List <String> types = address_components.get(p).getTypes(); 
			System.out.println("long_name = " + long_name);
			System.out.println("short_name = " + short_name);
			
			//überprüft ob bei der Liste type den String component beinhaltet
			for (int o = 0; o < types.size(); o++){
				//schlaufe wird verlassen, wenn component gefunden wurde oder länge von types.size() erreicht wurde
				if(types.get(o).equals(component) == true){
					index = p;
					System.out.println("component gefunden, set index = " + index + " , types.get(o) = "+ types.get(o));
				}
				else{
					System.out.println("component nicht gefunden, o = " + o + " , types.get(o) = "+ types.get(o));
				}
			}
			p++;
		}
		return index;
	}
	
	//Strings und Print wird erstellt um testing übersichtlicher zu machen
	public void printJsonResponseGoogle(int i, QueryGoogle google, String route, String addressNumber, String plz, String locality, String sublocalityLevel1, String areaLevel1, String areaLevel2, String country, String postalCodeSuffix){
		System.out.println();
		System.out.println("Start printJsonResponseGoogle() : "); //test-code
			
		String status = google.getStatus();
		String lat = geometry.getLocation().getLat();
		String lng = geometry.getLocation().getLng();
		String location_type = geometry.getLocation_type();
		Viewport viewport = geometry.getViewport();
		Bounds bounds = geometry.getBounds();

		//Printet alle Elemente, die man als Json erhält:
		System.out.println("Alle Elemente von Json-Respond von Google: ");
		System.out.println();
		System.out.println("status: " + status);
		System.out.println("formatted_address: " + formatted_address);
		System.out.println("route: " + route);
		System.out.println("addressNumber: " + addressNumber);
		System.out.println("plz: " + plz);
		System.out.println("locality: " + locality);
		System.out.println("sublocalityLevel1: " + sublocalityLevel1);
		System.out.println("areaLevel1: " + areaLevel1);
		System.out.println("areaLevel2: " + areaLevel2);
		System.out.println("country: " + country);
		System.out.println("postalCodeSuffix: " + postalCodeSuffix);
		System.out.println("place_id: " + place_id);
		System.out.println("partial_match: " + partial_match);
		System.out.println("lat: " + lat);
		System.out.println("lng: " + lng);
		System.out.println("location_type: " + location_type);
		System.out.println();
		System.out.println("Viewport: ");
		System.out.println("latNortheast: " + viewport.getNortheast().getLat());
		System.out.println("lngNortheast: " + viewport.getNortheast().getLng());
		System.out.println("latSouthwest: " + viewport.getSouthwest().getLat());
		System.out.println("lngSouthwest: " + viewport.getSouthwest().getLng());
		System.out.println();
		//überprüft ob bounds vorhanden ist und gibt es aus falls ja
		if(bounds == null){
			System.out.println("kein Bounds-Objekt vorhanden");
		}
		else{
			System.out.println("Bounds (optional): ");
			System.out.println("latNortheast: " + bounds.getNortheast().getLat());
			System.out.println("lngNortheast: " + bounds.getNortheast().getLng());
			System.out.println("latSouthwest: " + bounds.getSouthwest().getLat());
			System.out.println("lngSouthwest: " + bounds.getSouthwest().getLng());
		}
		System.out.println();
		System.out.println("Types: ");
		for(int k = 0; k < types.size(); k++){
			System.out.println(types.get(k));
		}
	}
}
