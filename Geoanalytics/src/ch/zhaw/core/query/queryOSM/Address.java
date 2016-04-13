package ch.zhaw.core.query.queryOSM;

import java.util.ArrayList;
import java.util.List;

public class Address {

	private String house_number;
	private String road;
	private String village;
	private String county;
	private String state;
	private String postcode;
	private String country;
	private String country_code;
	private String residential;
	private String town;
	private String state_district;
	private String city;
	private String city_district;
	private String construction;
	private String continent;
	private String neighbourhood;
	private String public_building;
	private String suburb;
	private String bakery;
	private List<String> listNewAddress = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	private Boolean status = null; //für Validation benötigt
	private int score; //für Validation benötigt
	
	/* für individuelle erstellung der Addresse möglich
	public Address(String house_number, String road, String village, String county, String state, String postcode,
			String country, String country_code, String residential, String town, String state_district) {
		//super();
		this.house_number = house_number;
		this.road = road;
		this.village = village;
		this.county = county;
		this.state = state;
		this.postcode = postcode;
		this.country = country;
		this.country_code = country_code;
		this.residential = residential;
		this.town = town;
		this.state_district = state_district;
	}
	*/
	
	//test dummy constructor for Objectmapper
	public Address(){
	}

	public String getHouse_number() {
		return house_number;
	}

	public void setHouse_number(String house_number) {
		this.house_number = house_number;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
	
	public String getState_district() {
		return state_district;
	}

	public void setState_district(String state_district) {
		this.state_district = state_district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getBakery() {
		return bakery;
	}

	public void setBakery(String bakery) {
		this.bakery = bakery;
	}

	public String getCity_district() {
		return city_district;
	}

	public void setCity_district(String city_district) {
		this.city_district = city_district;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getPublic_building() {
		return public_building;
	}

	public void setPublic_building(String public_building) {
		this.public_building = public_building;
	}

	public List<String> getListNewAddress() {
		return listNewAddress;
	}

	public void setListNewAddress(List<String> listNewAddress) {
		this.listNewAddress = listNewAddress;
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
		
		String road = getRoad();
		String addressNumber = getHouse_number();
		String addressLine = road + " " + addressNumber;
		String plz = getPostcode();
		String village = getVillage();
		String town = getTown();
		String city = getCity();
		/*
		String road = osm.get(k).getAddress().getRoad();
		String addressNumber = osm.get(k).getAddress().getHouse_number();
		String plz = osm.get(k).getAddress().getPostcode();
		String village = osm.get(k).getAddress().getVillage();
		String town = osm.get(k).getAddress().getTown();
		String city = osm.get(k).getAddress().getCity();
		*/
		System.out.println("road: " + road);
		System.out.println("addressNumber: " + addressNumber);
		System.out.println("addressLine: " + addressLine);
		System.out.println("plz: " + plz);
		System.out.println("village: " + village);
		System.out.println("town: " + town);
		System.out.println("city: " + city);
		
		// listNewAddress muss pro Service gleich strukturiert sein!! -> road, nr, plz, ort
		// Struktur kann noch ergänzt bzw. verändert werden
		
		//addressLine
		listNewAddress.add(addressLine);
		System.out.println("listNewAddress.add(addressLine) = " + addressLine);
		
		/*
		//nr
		listNewAddressOSM.add(addressNumber);
		System.out.println("listNewAddress.add(addressNumber) = " + addressNumber);
		*/
		
		//plz
		listNewAddress.add(plz);
		System.out.println("listNewAddress.add(plz) = " + plz);
		
		//Ort
		if (village != null){
			listNewAddress.add(village);
			System.out.println("listNewAddress.add(village) = " + village);
		}
		if (town != null){
			listNewAddress.add(town);
			System.out.println("listNewAddress.add(town) = " + town);
		}
		if (city != null){
			listNewAddress.add(city);
			System.out.println("listNewAddress.add(city) = " + city);
		}
	}
}
