package ch.zhaw.core.query.queryOSM;

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
	
	
}
