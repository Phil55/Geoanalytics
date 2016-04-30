package ch.zhaw.core.query.queryBing;

import java.util.ArrayList;
import java.util.List;

public class ResourcesObj {
	
	private String __type;
	private String name;
	private String confidence;
	private String entityType;
	private List<String> bbox;
	private List<String> matchCodes;
	private Point point;
	private Address address;
	private List<GeocodePointsObj> geocodePoints;
	private List<String> newAddress = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	private Boolean status = null; //für Validation benötigt
	private Double score; //für Validation benötigt
	
	//test dummy constructor for objectmapper
	public ResourcesObj(){	
	}

	public String get__type() {
		return __type;
	}

	public void set__type(String __type) {
		this.__type = __type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public List<String> getBbox() {
		return bbox;
	}

	public void setBbox(List<String> bbox) {
		this.bbox = bbox;
	}

	public List<String> getMatchCodes() {
		return matchCodes;
	}

	public void setMatchCodes(List<String> matchCodes) {
		this.matchCodes = matchCodes;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<GeocodePointsObj> getGeocodePoints() {
		return geocodePoints;
	}

	public void setGeocodePoints(List<GeocodePointsObj> geocodePoints) {
		this.geocodePoints = geocodePoints;
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

	public void createListNewAddress(int i, QueryBing bing){
		System.out.println(); //test-code
		System.out.println("Start createListNewAddress() (Bing)"); //test
		
		String addressLine = address.getAddressLine();
		String plz = address.getPostalCode();
		String locality = address.getLocality();
		String district = address.getAdminDistrict(); //evt. nicht nötig
		String district2 = address.getAdminDistrict2(); //evt. nicht nötig
		/*
		String addressLine = bing.getResourceSets().get(0).getResources().get(k).getAddress().getAddressLine();
		String plz = bing.getResourceSets().get(0).getResources().get(k).getAddress().getPostalCode();
		String locality = bing.getResourceSets().get(0).getResources().get(k).getAddress().getLocality();
		String district = bing.getResourceSets().get(0).getResources().get(k).getAddress().getAdminDistrict(); //evt. nicht nötig
		String district2 = bing.getResourceSets().get(0).getResources().get(k).getAddress().getAdminDistrict2(); //evt. nicht nötig
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
		if (district != null){
			newAddress.add(district);
			System.out.println("newAddress.add(district) = " + district);
		}
		if (district2 != null){
			newAddress.add(district2);
			System.out.println("newAddress.add(district2) = " + district2);
		}
		*/
		printJsonResponseBing(i, bing, addressLine, plz, locality, district, district2);
		
	}
	
	//Strings und Print wird erstellt um testing übersichtlicher zu machen
	public void printJsonResponseBing(int i, QueryBing bing, String addressLine, String plz, String locality, String district, String district2){
		System.out.println();
		System.out.println("Start printJsonResponseBing() : "); //test-code
		
		//Strings und Print wird erstellt um testing übersichtlicher zu machen
		String authenticationResultCode = bing.getAuthenticationResultCode();
		String statusCode = bing.getStatusCode();
		String statusDescription = bing.getStatusDescription();
		String traceID = bing.getTraceId();
		String estimatedTotal = bing.getResourceSets().get(0).getEstimatedTotal();
		List<String> point_coordinates = point.getCoordinates();
		String point_type = point.getType();
		String countryRegion = address.getCountryRegion();
		String formattedAddress = address.getFormattedAddress();
		String countryCode = address.getCountryRegionIso2();
		String neighborhood = address.getNeighborhood();		
		
		//Printet alle Elemente, die man als Json erhält:
		System.out.println("Alle Elemente von Json-Respond von Bing: ");
		System.out.println();
		System.out.println("bingName: " + name);
		System.out.println("authenticationResultCode: " + authenticationResultCode);
		System.out.println("statusCode: " + statusCode);
		System.out.println("statusDescription: " + statusDescription);
		System.out.println("traceID: " + traceID);
		System.out.println("estimatedTotal: " + estimatedTotal);
		System.out.println("__type: " + __type);
		System.out.println("confidence: " + confidence);
		System.out.println("entityType: " + entityType);
		System.out.println("addressLine: " + addressLine);
		System.out.println("locality: " + locality);
		System.out.println("postalCode: " + plz);
		System.out.println("adminDistrict: " + district);
		System.out.println("adminDistrict2: " + district2);
		System.out.println("neighborhood: " + neighborhood);
		System.out.println("countryRegion: " + countryRegion);
		System.out.println("countryCode: " + countryCode);
		System.out.println("formattedAddress: " + formattedAddress);
		System.out.println("point_type: " + point_type);
		System.out.println();
		System.out.println("Point_coordinates: ");
		for(int k = 0; k < point_coordinates.size(); k++){
			System.out.println(point_coordinates.get(k));
		}
		System.out.println();
		System.out.println("Boundingbox: ");
		for(int k = 0; k < bbox.size(); k++){
			System.out.println(bbox.get(k));
		}
		System.out.println();
		System.out.println("MatchCodes: ");
		for(int k = 0; k < matchCodes.size(); k++){
			System.out.println(matchCodes.get(k));
		}
		System.out.println();
		System.out.println("GeocodePoints: ");
		for(int k = 0; k < geocodePoints.size(); k++){
			System.out.println("geocodePoint: " + k);
			String geocode_type = geocodePoints.get(k).getType();
			String geocode_calculationMethod = geocodePoints.get(k).getCalculationMethod();
			List<String> geocode_coordinates = geocodePoints.get(k).getCoordinates();
			List<String> geocode_usageTypes = geocodePoints.get(k).getUsageTypes();
			System.out.println("geocode_type: " + geocode_type);
			System.out.println("geocode_calculationMethod: " + geocode_calculationMethod);
			System.out.println();
			System.out.println("Geocode_coordinates: ");
			for (int y = 0; y < geocode_coordinates.size(); y++){
				System.out.println(geocode_coordinates.get(y));
			}
			System.out.println();
			System.out.println("Geocode_usageTypes: ");
			for (int y = 0; y < geocode_usageTypes.size(); y++){
				System.out.println(geocode_usageTypes.get(y));
			}
		}
		System.out.println();
	}

}
