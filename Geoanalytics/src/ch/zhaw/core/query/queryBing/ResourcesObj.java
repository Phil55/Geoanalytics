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
	private List<String> listNewAddressBing = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	
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

	public List<String> getListNewAddressBing() {
		return listNewAddressBing;
	}

	public void setListNewAddressBing(List<String> listNewAddressBing) {
		this.listNewAddressBing = listNewAddressBing;
	}
	
	public void createListNewAddressBing (){
		
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
		
		//road
		listNewAddressBing.add(addressLine);
		System.out.println("listNewAddress.add(addressLine) = " + addressLine);
		/*
		//nr
		listNewAddressOSM.add(addressNumber);
		System.out.println("listNewAddress.add(addressNumber) = " + addressNumber);
		*/
		//plz
		listNewAddressBing.add(plz);
		System.out.println("listNewAddress.add(plz) = " + plz);
		//Ort
		if (locality != null){
			listNewAddressBing.add(locality);
			System.out.println("listNewAddress.add(locality) = " + locality);
		}
		
		//evt. nicht nötig
		/*
		if (district != null){
			listNewAddressBing.add(district);
			System.out.println("listNewAddress.add(district) = " + district);
		}
		if (district2 != null){
			listNewAddressBing.add(district2);
			System.out.println("listNewAddress.add(district2) = " + district2);
		}
		*/
	}

}
