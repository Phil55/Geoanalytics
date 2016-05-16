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
	private String locality;
	private String industrial;
	private String address29;
	private String building;
	private String commercial;
	private String region;
	private String hamlet;
	private String station;
	private String river;
	private List<String> listNewAddress = new ArrayList<String>(); 
	private Boolean status = null; 
	private Double score; 
	
	//Konstruktor für Object Mapper
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
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getIndustrial() {
		return industrial;
	}

	public void setIndustrial(String industrial) {
		this.industrial = industrial;
	}

	public String getAddress29() {
		return address29;
	}

	public void setAddress29(String address29) {
		this.address29 = address29;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getCommercial() {
		return commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getHamlet() {
		return hamlet;
	}

	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getRiver() {
		return river;
	}

	public void setRiver(String river) {
		this.river = river;
	}

	public void createListNewAddress(int i, List<QueryOSM> osmList){
		System.out.println();
		System.out.println("Start createListNewAddress() (OSM)");
		
		String road = getRoad();
		String addressNumber = getHouse_number();
		//überprüft ob road oder addressNumber null ist
		String addressLine = null;
		if(addressNumber == null){
			addressLine = road;
		}
		else if(road == null){
			addressLine = null;
		}
		else{
			addressLine = road + " " + addressNumber;
		}
		
		//AddressLine
		listNewAddress.add(addressLine);
		System.out.println("listNewAddress.add(addressLine) = " + addressLine);
		
		//Postal Code
		listNewAddress.add(postcode);
		System.out.println("listNewAddress.add(postcode) = " + postcode);
		
		//Locality
		if (village != null){
			listNewAddress.add(village);
			System.out.println("listNewAddress.add(village) = " + village);
		}
		else{
			if (town != null){
				listNewAddress.add(town);
				System.out.println("listNewAddress.add(town) = " + town);
			}
			else{
				if (residential != null){
					listNewAddress.add(residential);
					System.out.println("listNewAddress.add(residential) = " + residential);
				}
				else{
					if (city != null){
						listNewAddress.add(city);
						System.out.println("listNewAddress.add(city) = " + city);
					}
					else{
						if(state != null){
							listNewAddress.add(state);
							System.out.println("listNewAddress.add(state) = " + state);
						}
						else{
						System.out.println("Es wurde keine Ortschaft bei listNewAddress hinzugefügt");
						}
					}
				}
			}
		}
		printJsonResponseOSM(i, osmList);
	}
	
	//Strings und Print wird erstellt um testing übersichtlicher zu machen
	public void printJsonResponseOSM(int i, List<QueryOSM> osmList){
		System.out.println();
		System.out.println("Start printJsonResponseOSM() : ");
		
		QueryOSM osm = osmList.get(i);
		
		String display_name = osm.getDisplay_name();
		String place_id = osm.getPlace_id();
		String osm_type = osm.getOsm_type();
		String osm_id = osm.getOsm_id();
		String lat = osm.getLat();
		String lon = osm.getLon();
		String classe = osm.getClasse();
		String type = osm.getType();
		String importance = osm.getImportance();
		String icon = osm.getIcon();
		List<String> bbox = osm.getBoundingbox();
		List<List<String>> polygonpoints = osm.getPolygonpoints();
		
		//Printet alle Elemente, die man als Json erhält:
		System.out.println("Alle Elemente von Json-Respond von OSM: ");
		System.out.println();
		System.out.println("Display_name: " + display_name);
		System.out.println("place_id: " + place_id);
		System.out.println("osm_type: " + osm_type);
		System.out.println("osm_id: " + osm_id);
		System.out.println("lat: " + lat);
		System.out.println("lon: " + lon);
		System.out.println("classe: " + classe);
		System.out.println("type: " + type);
		System.out.println("importance: " + importance);
		System.out.println("house_number: " + house_number);
		System.out.println("road: " + road);
		System.out.println("village: " + village);
		System.out.println("county: " + county);
		System.out.println("state: " + state);
		System.out.println("postcode: " + postcode);
		System.out.println("country: " + country);
		System.out.println("country_code: " + country_code);
		System.out.println("residential: " + residential);
		System.out.println("town: " + town);
		System.out.println("state_district: " + state_district);
		System.out.println("city: " + city);
		System.out.println("city_district: " + city_district);
		System.out.println("construction: " + construction);
		System.out.println("continent: " + continent);
		System.out.println("neighbourhood: " + neighbourhood);
		System.out.println("public_building: " + public_building);
		System.out.println("suburb: " + suburb);
		System.out.println("bakery: " + bakery);
		System.out.println("locality: " + locality);
		System.out.println("industrial: " + industrial);
		System.out.println("address29: " + address29);
		System.out.println("building: " + building);
		System.out.println("commercial: " + commercial);
		System.out.println("region: " + region);
		System.out.println("icon: " + icon);
		System.out.println("hamlet: " + hamlet);
		System.out.println("station: " + station);
		System.out.println("river: " + river);
		System.out.println();
		System.out.println("Boundingbox: ");
		for(int k = 0; k < bbox.size(); k++){
			System.out.println(bbox.get(k));
		}
		System.out.println();
		if(polygonpoints == null){
			System.out.println("Polygonpoints: null (es wurden keine Polygonpoints zurückgegeben)");
		}
		else{
			System.out.println("Polygonpoints: ");
			for(int k = 0; k < polygonpoints.size(); k++){
				System.out.println("polygonpoint " + k);
				for (int y = 0; y < polygonpoints.get(k).size(); y++){
					System.out.println(polygonpoints.get(k).get(y));
				}
				System.out.println();
			}
		}
		System.out.println();
	}
}
