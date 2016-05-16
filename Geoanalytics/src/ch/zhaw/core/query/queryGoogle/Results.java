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
	private List<String> newAddress = new ArrayList<String>(); 
	private Boolean status = null; 
	private Double score; 
	private String route = null;
	private String addressNumber = null;
	private String postalCode = null;
	private String locality = null;
	private String sublocalityLevel1 = null;
	private String areaLevel1 = null;
	private String areaLevel2 = null;
	private String areaLevel3 = null;
	private String country_name = null;
	private String country_code = null;
	private String neighborhood = null;
	private String premise = null;
	private String postalCodeSuffix = null;
	
	//Konstruktor für Object Mapper
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

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getSublocalityLevel1() {
		return sublocalityLevel1;
	}

	public void setSublocalityLevel1(String sublocalityLevel1) {
		this.sublocalityLevel1 = sublocalityLevel1;
	}

	public String getAreaLevel1() {
		return areaLevel1;
	}

	public void setAreaLevel1(String areaLevel1) {
		this.areaLevel1 = areaLevel1;
	}

	public String getAreaLevel2() {
		return areaLevel2;
	}

	public void setAreaLevel2(String areaLevel2) {
		this.areaLevel2 = areaLevel2;
	}

	public String getAreaLevel3() {
		return areaLevel3;
	}

	public void setAreaLevel3(String areaLevel3) {
		this.areaLevel3 = areaLevel3;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getPostalCodeSuffix() {
		return postalCodeSuffix;
	}

	public void setPostalCodeSuffix(String postalCodeSuffix) {
		this.postalCodeSuffix = postalCodeSuffix;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	public void createListNewAddress(int i, QueryGoogle google){ 
		System.out.println(); 
		System.out.println("Start createListNewAddress() (Google)");
		
		int routeIndex = getIndex("route", i);
		int addressNumberIndex = getIndex("street_number", i);
		int postalCodeIndex = getIndex("postal_code", i);
		int localityIndex = getIndex("locality", i);
		int sublocalityLevel1Index = getIndex("sublocality_level_1", i);
		int areaLevel1Index = getIndex("administrative_area_level_1", i);
		int areaLevel2Index = getIndex("administrative_area_level_2", i);
		int areaLevel3Index = getIndex("administrative_area_level_3", i);
		int neighborhoodIndex = getIndex("neighborhood", i);
		int premiseIndex = getIndex ("premise", i);
		int country_Index = getIndex("country", i); 
		int postalCodeSuffixIndex = getIndex("postal_code_suffix", i);
		
		if(routeIndex == -1){
			System.out.println("kein address_component gefunden mit -route-, String route wird auf null gesetzt");
		}
		else{
			route = address_components.get(routeIndex).getLong_name();
		}
		if(addressNumberIndex == -1){
			System.out.println("kein address_component gefunden mit -street_number-, String addressNumber wird auf null gesetzt");
		}
		else{
			addressNumber = address_components.get(addressNumberIndex).getLong_name();
		}
		if(postalCodeIndex == -1){
			System.out.println("kein address_component gefunden mit -postal_code-, String postalCode wird auf null gesetzt");
		}
		else{
			postalCode = address_components.get(postalCodeIndex).getLong_name();
		}
		if(localityIndex == -1){
			System.out.println("kein address_component gefunden mit -locality-, String locality wird auf null gesetzt");
		}
		else{
			locality = address_components.get(localityIndex).getLong_name();
		}
		if(sublocalityLevel1Index == -1){
			System.out.println("kein address_component gefunden mit -sublocality_level_1-, String sublocalityLevel1 wird auf null gesetzt");
		}
		else{
			sublocalityLevel1 = address_components.get(sublocalityLevel1Index).getLong_name();
		}
		if(areaLevel1Index == -1){
			System.out.println("kein address_component gefunden mit -administrative_area_level_1-, String areaLevel1 wird auf null gesetzt");
		}
		else{
			areaLevel1 = address_components.get(areaLevel1Index).getLong_name();
		}
		if(areaLevel2Index == -1){
			System.out.println("kein address_component gefunden mit -administrative_area_level_2-, String areaLevel2 wird auf null gesetzt");
		}
		else{
			areaLevel2 = address_components.get(areaLevel2Index).getLong_name();
		}
		if(areaLevel3Index == -1){
			System.out.println("kein address_component gefunden mit -administrative_area_level_3-, String areaLevel3 wird auf null gesetzt");
		}
		else{
			areaLevel3 = address_components.get(areaLevel3Index).getLong_name();
		}
		if(country_Index == -1){
			System.out.println("kein address_component gefunden mit -country-, String country_name wird auf null gesetzt");
			System.out.println("kein address_component gefunden mit -country-, String country_code wird auf null gesetzt");
		}
		else{
			country_name = address_components.get(country_Index).getLong_name();
			country_code = address_components.get(country_Index).getShort_name();
		}
		if(postalCodeSuffixIndex == -1){
			System.out.println("kein address_component gefunden mit -postalCodeSuffix-, String postalCodeSuffix wird auf null gesetzt");
		}
		else{
			postalCodeSuffix = address_components.get(postalCodeSuffixIndex).getLong_name();
		}
		if(neighborhoodIndex == -1){
			System.out.println("kein address_component gefunden mit -neighborhood-, String neighborhood wird auf null gesetzt");
		}
		else{
			neighborhood = address_components.get(neighborhoodIndex).getLong_name();
		}
		if(premiseIndex == -1){
			System.out.println("kein address_component gefunden mit -premise-, String premise wird auf null gesetzt");
		}
		else{
			premise = address_components.get(premiseIndex).getLong_name();
		}
		
		//überprüft ob road oder addressNumber null ist. 
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
		
		//AddressLine
		newAddress.add(addressLine);
		System.out.println("newAddress.add(addressLine) = " + addressLine);
		
		//Postal Code
		newAddress.add(postalCode);
		System.out.println("newAddress.add(postalCode) = " + postalCode);
		
		//Locality
		if (locality != null){
			newAddress.add(locality);
			System.out.println("newAddress.add(locality) = " + locality);
		}
		
		printJsonResponseGoogle(i, google);
	}
	
	//sucht die stelle, wo die gesuchte componente ist. Falls nichts gefunden wurde, gibt es -1 zurück
	public int getIndex(String component, int i){
		System.out.println();
		System.out.println("Start getIndex() (Google)"); 
		int index = -1; 
		int sizeComponent = address_components.size();
		int p = 0;
		
		while(index == -1 && p < sizeComponent){ 
			System.out.println("p = " + p + " , index = " + index);
			String long_name = address_components.get(p).getLong_name();
			String short_name = address_components.get(p).getShort_name();
			List <String> types = address_components.get(p).getTypes(); 
			System.out.println("long_name = " + long_name);
			System.out.println("short_name = " + short_name);
			
			//überprüft ob bei der Liste type den String component beinhaltet
			for (int o = 0; o < types.size(); o++){
				//Schlaufe wird verlassen, wenn component gefunden wurde oder die Länge von Liste types erreicht wurde
				if(types.get(o).equals(component) == true){
					index = p;
				}
			}
			p++;
		}
		return index;
	}
	
	//Strings und Print wird erstellt um testing übersichtlicher zu machen
	public void printJsonResponseGoogle(int i, QueryGoogle google){
		System.out.println();
		System.out.println("Start printJsonResponseGoogle() : ");
			
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
		System.out.println("postalCode: " + postalCode);
		System.out.println("locality: " + locality);
		System.out.println("sublocalityLevel1: " + sublocalityLevel1);
		System.out.println("areaLevel1: " + areaLevel1);
		System.out.println("areaLevel2: " + areaLevel2);
		System.out.println("areaLevel3: " + areaLevel3);
		System.out.println("country_name: " + country_name);
		System.out.println("country_code: " + country_code);
		System.out.println("postalCodeSuffix: " + postalCodeSuffix);
		System.out.println("neighborhood: " + neighborhood);
		System.out.println("premise: " + premise);
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
		//überprüft ob bounds vorhanden ist
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
