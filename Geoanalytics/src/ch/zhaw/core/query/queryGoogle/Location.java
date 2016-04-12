package ch.zhaw.core.query.queryGoogle;

public class Location {
	
	private String lat;
	private String lng;
	
	//constructor for objectmapper
	public Location(){	
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
}
