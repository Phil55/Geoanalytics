package ch.zhaw.core.query.queryGoogle;

public class Location {
	
	private String lat;
	private String lng;
	
	//Konstruktor für Object Mapper
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
