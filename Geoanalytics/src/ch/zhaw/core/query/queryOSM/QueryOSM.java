package ch.zhaw.core.query.queryOSM;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream; //für inputstream
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


//import für Unirest
import org.json.*;
import org.apache.http.nio.client.*;
import org.apache.http.client.*;
import org.apache.http.entity.mime.*;
import com.mashape.unirest.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.HttpResponse;

import org.apache.commons.logging.LogFactory;


//Import für Jackson
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.*;

import java.util.ArrayList;
import java.util.List;

public class QueryOSM {

	//private final String USER_AGENT = "Mozilla/5.0"; //für GET und POST
	
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
	private Address address;
	private List<String> listNewAddressOSM = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	// für QueryGoogle() benötigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind

	
	public QueryOSM(String place_id, String licence, String osm_type, String osm_id, List<String> boundingbox,
			List<List<String>> polygonpoints, String lat, String lon, String display_name, String classe, String type,
			String importance, Address address) {
		super();
		this.place_id = place_id;
		this.licence = licence;
		this.osm_type = osm_type;
		this.osm_id = osm_id;
		this.boundingbox = boundingbox;
		this.polygonpoints = polygonpoints;
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
		this.classe = classe;
		this.type = type;
		this.importance = importance;
		this.address = address;
	}
	
	//test dummy constructor for objectmapper
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

	public List<String> getListNewAddressOSM() {
		return listNewAddressOSM;
	}

	public void setListNewAddressOSM(List<String> listNewAddressOSM) {
		this.listNewAddressOSM = listNewAddressOSM;
	}
	
	public List<Integer> getNewAddressTrue() {
		return newAddressTrue;
	}

	public void setNewAddressTrue(List<Integer> newAddressTrue) {
		this.newAddressTrue = newAddressTrue;
	}

	public void createListNewAddressOSM (){
		
		String road = address.getRoad();
		String addressNumber = address.getHouse_number();
		String plz = address.getPostcode();
		String village = address.getVillage();
		String town = address.getTown();
		String city = address.getCity();
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
		System.out.println("plz: " + plz);
		System.out.println("village: " + village);
		System.out.println("town: " + town);
		System.out.println("city: " + city);
		
		// listNewAddress muss pro Service gleich strukturiert sein!! -> road, nr, plz, ort
		// Struktur kann noch ergänzt bzw. verändert werden
		
		//road
		listNewAddressOSM.add(road);
		System.out.println("listNewAddress.add(road) = " + road);
		//nr
		listNewAddressOSM.add(addressNumber);
		System.out.println("listNewAddress.add(addressNumber) = " + addressNumber);
		//plz
		listNewAddressOSM.add(plz);
		System.out.println("listNewAddress.add(plz) = " + plz);
		//Ort
		if (village != null){
			listNewAddressOSM.add(village);
			System.out.println("listNewAddress.add(village) = " + village);
		}
		if (town != null){
			listNewAddressOSM.add(town);
			System.out.println("listNewAddress.add(town) = " + town);
		}
		if (city != null){
			listNewAddressOSM.add(city);
			System.out.println("listNewAddress.add(city) = " + city);
		}
	}

}
