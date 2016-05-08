package ch.zhaw.core.query.queryOSM;

import java.util.ArrayList;
import java.util.List;

public class QueryOSM {
	
	private String place_id;
	private String licence;
	private String osm_type;
	private String osm_id;
	private List<String> boundingbox;
	private List<List<String>> polygonpoints;
	private String lat;
	private String lon;
	private String display_name;
	private String classe; //JSON request "class"
	private String type;
	private String importance;
	private String icon;
	private Address address;
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind
	private Boolean statusValidation = null; //für Validation benötigt
	private List <Double> listScore = new ArrayList<Double>(); //für Validation benötigt
	private List <Integer> listScoreTrue = new ArrayList<Integer>(); //für Validation benötigt
	private int defIndex; //für Validation benötigt, verweist auf die Stelle, wo die Adresse mit dem besten Score ist
		
	//Konstruktor für Object Mapper
	public QueryOSM(){	
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getOsm_type() {
		return osm_type;
	}

	public void setOsm_type(String osm_type) {
		this.osm_type = osm_type;
	}

	public String getOsm_id() {
		return osm_id;
	}

	public void setOsm_id(String osm_id) {
		this.osm_id = osm_id;
	}

	public List<String> getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(List<String> boundingbox) {
		this.boundingbox = boundingbox;
	}

	public List<List<String>> getPolygonpoints() {
		return polygonpoints;
	}

	public void setPolygonpoints(List<List<String>> polygonpoints) {
		this.polygonpoints = polygonpoints;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Integer> getNewAddressTrue() {
		return newAddressTrue;
	}

	public void setNewAddressTrue(List<Integer> newAddressTrue) {
		this.newAddressTrue = newAddressTrue;
	}
	
	public Boolean getStatusValidation() {
		return statusValidation;
	}

	public void setStatusValidation(Boolean statusValidation) {
		this.statusValidation = statusValidation;
	}

	public List<Double> getListScore() {
		return listScore;
	}

	public void setListScore(List<Double> listScore) {
		this.listScore = listScore;
	}

	public List<Integer> getListScoreTrue() {
		return listScoreTrue;
	}

	public void setListScoreTrue(List<Integer> listScoreTrue) {
		this.listScoreTrue = listScoreTrue;
	}

	public int getDefIndex() {
		return defIndex;
	}

	public void setDefIndex(int defIndex) {
		this.defIndex = defIndex;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
