
public class Address {

	private String house_number;
	private String road;
	private String village;
	private String county;
	private String state;
	private String postcode;
	private String country;
	private String country_code;
	
	public Address(String house_number, String road, String village, String county, String state, String postcode,
			String country, String country_code) {
		//super();
		this.house_number = house_number;
		this.road = road;
		this.village = village;
		this.county = county;
		this.state = state;
		this.postcode = postcode;
		this.country = country;
		this.country_code = country_code;
	}
	
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
	
	
}
