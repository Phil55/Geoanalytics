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
	private int score; //für Validation benötigt
	
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void createListNewAddress(){
		
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
		System.out.println("addressLine: " + addressLine);
		System.out.println("plz: " + plz);
		System.out.println("locality: " + locality);
		System.out.println("district: " + district);
		System.out.println("district2: " + district2);
		
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
	}

}
