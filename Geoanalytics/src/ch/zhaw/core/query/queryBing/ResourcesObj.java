package ch.zhaw.core.query.queryBing;

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

}
