package ch.zhaw.core.query.queryBing;

public class Address {
	
	private String addressLine;
	private String adminDistrict;
	private String adminDistrict2;
	private String countryRegion;
	private String formattedAddress;
	private String locality;
	private String postalCode;
	
	//test dummy constructor for objectmapper
	public Address(){	
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getAdminDistrict() {
		return adminDistrict;
	}

	public void setAdminDistrict(String adminDistrict) {
		this.adminDistrict = adminDistrict;
	}

	public String getAdminDistrict2() {
		return adminDistrict2;
	}

	public void setAdminDistrict2(String adminDistrict2) {
		this.adminDistrict2 = adminDistrict2;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
