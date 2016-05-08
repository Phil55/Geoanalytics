package ch.zhaw.core.alignment;
import java.util.ArrayList;
import java.util.List;

//für Methode align- (-OSM, -Bing und -Google) benötigt
import ch.zhaw.core.query.queryBing.*;
import ch.zhaw.core.query.queryGoogle.*;
import ch.zhaw.core.query.queryOSM.*;

public class Alignment {

	//Tabelle Address -> FK werden automatisch generiert keine Attribute benötigt
	private int person_orig_id;
	private String place_id;
	private String service_source;
	private String name_freeform;
	private String address_freeform;
	private String address_type;
	private String country_code;
	private String country_name;
	private String address_line;
	private String road;
	private String road_number;
	private String locality;
	private String postal_code;
	private String building_description;
	private String neighborhood;
	private String area_level1;
	private String area_level2;
	private String area_level3;
	
	//Tabelle Geometry
	private String geometric_quality;
	private double point_lat;
	private double point_lng;
	private double bbox_lat_north;
	private double bbox_lng_east;
	private double bbox_lat_south;
	private double bbox_lng_west;
	
	//Tabelle Polygoncoordinates
	private List<List<Double>> polygonpointsList;
	
	private List<String> sqlList;
	
	public Alignment(int personOrigId) {
		this.person_orig_id = personOrigId; //id wird über Klasse Struktur bei der Initialisierung zum Konstruktor weitergegeben, damit es nicht zur Methode align weitergegeben werden muss
		this.sqlList = new ArrayList<String>();
	}
	
	public int getPerson_orig_id() {
		return person_orig_id;
	}

	public void setPerson_orig_id(int person_orig_id) {
		this.person_orig_id = person_orig_id;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getService_source() {
		return service_source;
	}

	public void setService_source(String service_source) {
		this.service_source = service_source;
	}

	public String getName_freeform() {
		return name_freeform;
	}

	public void setName_freeform(String name_freeform) {
		this.name_freeform = name_freeform;
	}

	public String getAddress_freeform() {
		return address_freeform;
	}

	public void setAddress_freeform(String address_freeform) {
		this.address_freeform = address_freeform;
	}

	public String getAddress_type() {
		return address_type;
	}

	public void setAddress_type(String address_type) {
		this.address_type = address_type;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getAddress_line() {
		return address_line;
	}

	public void setAddress_line(String address_line) {
		this.address_line = address_line;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getRoad_number() {
		return road_number;
	}

	public void setRoad_number(String road_number) {
		this.road_number = road_number;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getBuilding_description() {
		return building_description;
	}

	public void setBuilding_description(String building_description) {
		this.building_description = building_description;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getArea_level1() {
		return area_level1;
	}

	public void setArea_level1(String area_level1) {
		this.area_level1 = area_level1;
	}

	public String getArea_level2() {
		return area_level2;
	}

	public void setArea_level2(String area_level2) {
		this.area_level2 = area_level2;
	}

	public String getArea_level3() {
		return area_level3;
	}

	public void setArea_level3(String area_level3) {
		this.area_level3 = area_level3;
	}

	public String getGeometric_quality() {
		return geometric_quality;
	}

	public void setGeometric_quality(String geometric_quality) {
		this.geometric_quality = geometric_quality;
	}

	public double getPoint_lat() {
		return point_lat;
	}

	public void setPoint_lat(double point_lat) {
		this.point_lat = point_lat;
	}

	public double getPoint_lng() {
		return point_lng;
	}

	public void setPoint_lng(double point_lng) {
		this.point_lng = point_lng;
	}

	public double getBbox_lat_north() {
		return bbox_lat_north;
	}

	public void setBbox_lat_north(double bbox_lat_north) {
		this.bbox_lat_north = bbox_lat_north;
	}

	public double getBbox_lng_east() {
		return bbox_lng_east;
	}

	public void setBbox_lng_east(double bbox_lng_east) {
		this.bbox_lng_east = bbox_lng_east;
	}

	public double getBbox_lat_south() {
		return bbox_lat_south;
	}

	public void setBbox_lat_south(double bbox_lat_south) {
		this.bbox_lat_south = bbox_lat_south;
	}

	public double getBbox_lng_west() {
		return bbox_lng_west;
	}

	public void setBbox_lng_west(double bbox_lng_west) {
		this.bbox_lng_west = bbox_lng_west;
	}

	public List<String> getSqlList() {
		return sqlList;
	}

	public void setSqlList(List<String> sqlList) {
		this.sqlList = sqlList;
	}
	
	public List<List<Double>> getPolygonpointsList() {
		return polygonpointsList;
	}

	public void setPolygonpointsList(List<List<Double>> polygonpointsList) {
		this.polygonpointsList = polygonpointsList;
	}

	//falls etwas null ist muss bei int 0, und bei String "" gegeben werden gemäss patstat_data_catalog S. 23
	//strukturiert Adresse

	public List <String> alignOSM(List<QueryOSM> osm){
		
		int index = osm.get(0).getDefIndex();
		QueryOSM obj = osm.get(index);
		ch.zhaw.core.query.queryOSM.Address addressObj = osm.get(index).getAddress();
		
		//Daten in die Attribute bei Klasse Alignment einfügen
		place_id = obj.getPlace_id();
		service_source = "OSM";
		//überprüft ob Industrial oder Address29 vorhanden ist. falls nicht wird "" gesetzt
		if (addressObj.getIndustrial() != null){
			name_freeform = addressObj.getIndustrial();
		}
		else{
			if (addressObj.getAddress29() != null){
				name_freeform = addressObj.getAddress29();
			}
			else {
				name_freeform = "";
			}
		}
		address_freeform = obj.getDisplay_name();
		address_type = obj.getOsm_type();
		country_code = addressObj.getCountry_code();
		country_name = addressObj.getCountry();
		road = addressObj.getRoad();
		road_number = addressObj.getHouse_number();
		address_line = road_number + " " + road;
		//es wird so gemacht, weil sonst die gleichen if-Bedingungen hier nochmals implementiert werden müsste
		locality = addressObj.getListNewAddress().get(2); //siehe bei Klasse Address Methode createListNewAddress()
		postal_code = addressObj.getPostcode();
		building_description = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		neighborhood = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		area_level1 = addressObj.getState();
		area_level2 = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		area_level3 = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		geometric_quality = obj.getImportance();
		point_lat = Double.parseDouble(obj.getLat());
		point_lng = Double.parseDouble(obj.getLon());
		bbox_lat_north = Double.parseDouble(obj.getBoundingbox().get(1)); // North Latitude ist an der 2. Stelle -> also 1
		bbox_lng_east = Double.parseDouble(obj.getBoundingbox().get(3)); // East Longitude ist an der 4. Stelle -> also 3
		bbox_lat_south = Double.parseDouble(obj.getBoundingbox().get(0)); // South Latitude ist an der 1. Stelle -> also 0
		bbox_lng_west = Double.parseDouble(obj.getBoundingbox().get(2)); // West Longitude ist an der 3. Stelle -> also 2
		polygonpointsList = new ArrayList<List<Double>>();
		for (int i = 0; i < obj.getPolygonpoints().size(); i++){
			List<Double> list = new ArrayList<Double>();
			for(int x = 0; x < obj.getPolygonpoints().get(i).size(); x++){
				list.add(x, Double.parseDouble(obj.getPolygonpoints().get(i).get(x)));
			}
			polygonpointsList.add(i, list); // i weil reihenfolge gleich bleiben sollte
		}
		
		// die frage ist, ob eine neue Klasse für Polygonpoint erstellt werden sollte oder ob es als List<List<String>> abgespeichert werden sollte
		// schlussendlich stellt sich die frage wie man diie polygonpoints hier speichern möchte -> eher mit List
		// obj.getPolygonpoints().get(1).get(0);
		
		// SQL-Statements erstellen
		int sqlIndex = 0; //wird gemacht, um sicherzustellen, dass sie SQL-Statements in korrekter Reihenfolge durchgeführt werden
		
		//INSERT INTO bei Tabelle tls320_enriched_address
		String insertAddress = createInsertAddress();
		sqlList.add(sqlIndex, insertAddress);
		sqlIndex++;
		
		//INSERT INTO bei Tabelle tls321_geometry
		String insertGeometry = createInsertGeometry();		
		sqlList.add(sqlIndex, insertGeometry);
		sqlIndex++;
		
		for(int i = 0; i < polygonpointsList.size(); i++){
			
			//INSERT INTO bei Tabelle tls322_polygon_points
			String insertPolygonpoints = createInsertPolygonpoints(i);
			sqlList.add(sqlIndex, insertPolygonpoints);
			sqlIndex++;
			
			// INSERT INTO bei Tabelle tls323_polygon_coordinates 
			String insertPolygonCoordinates = createInsertPolygonCoordinates(i);
			sqlList.add(sqlIndex, insertPolygonCoordinates);
			sqlIndex++;
		}
		
		System.out.println("sqlList size: " + sqlList.size());
		return sqlList;
	}
	
	public List <String> alignBing(QueryBing bing){
		
		int index = bing.getResourceSets().get(0).getDefIndex();
		ResourcesObj obj = bing.getResourceSets().get(0).getResources().get(index);
		//ch.zhaw.core.query.queryBing.Address muss angegeben werden, weil QueryBing und QueryOSM die Klasse Address haben
		ch.zhaw.core.query.queryBing.Address addressObj = obj.getAddress(); 
		
		//Daten in die Attribute bei Klasse Alignment einfügen
		place_id = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		service_source = "Bing";
		name_freeform = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		address_freeform = addressObj.getFormattedAddress();
		address_type = obj.getEntityType();
		country_code = addressObj.getCountryRegionIso2();
		country_name = addressObj.getCountryRegion();
		road = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		road_number = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		address_line = addressObj.getAddressLine();
		locality = addressObj.getLocality();
		postal_code = addressObj.getPostalCode();
		building_description = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		neighborhood = addressObj.getNeighborhood(); //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		area_level1 = addressObj.getAdminDistrict();
		area_level2 = addressObj.getAdminDistrict2();
		area_level3 = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		geometric_quality = obj.getConfidence();
		point_lat = Double.parseDouble(obj.getPoint().getCoordinates().get(0));
		point_lng = Double.parseDouble(obj.getPoint().getCoordinates().get(1));
		bbox_lat_north = Double.parseDouble(obj.getBbox().get(2)); // North Latitude ist an der 3. Stelle -> also 2
		bbox_lng_east = Double.parseDouble(obj.getBbox().get(3)); // East Longitude ist an der 4. Stelle -> also 3
		bbox_lat_south = Double.parseDouble(obj.getBbox().get(0)); // South Latitude ist an der 1. Stelle -> also 0
		bbox_lng_west = Double.parseDouble(obj.getBbox().get(1)); // West Longitude ist an der 2. Stelle -> also 1
		polygonpointsList = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		
		// SQL-Statements erstellen
		int sqlIndex = 0; //wird gemacht, um sicherzustellen, dass sie SQL-Statements in korrekter Reihenfolge durchgeführt werden
		//INSERT INTO bei Tabelle tls320_enriched_address
		String insertAddress = createInsertAddress();
		sqlList.add(sqlIndex, insertAddress);
		sqlIndex++;
		
		//INSERT INTO bei Tabelle tls321_geometry
		String insertGeometry = createInsertGeometry();		
		sqlList.add(sqlIndex, insertGeometry);
		sqlIndex++;
		
		return sqlList;
	}
	
	public List <String> alignGoogle(QueryGoogle google){
		
		int index = google.getDefIndex();
		Results obj = google.getResults().get(index); 
		//hier wird kein addressObj benötigt, da alle Variablen vorher bei Results also hier obj abgespeichert sind
		Geometry GeoObj = obj.getGeometry();
		
		//Daten in die Attribute bei Klasse Alignment einfügen
		place_id = obj.getPlace_id(); //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		service_source = "Google";
		name_freeform = ""; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		address_freeform = obj.getFormatted_address();
		address_type = obj.getTypes().get(0); //Achtung hier ist eigentlich eine Liste aber man kann nur eines auswählen für die "Ablage"
		country_code = obj.getCountry_code();
		country_name = obj.getCountry_name();
		road = obj.getRoute();
		road_number = obj.getAddressNumber(); 
		address_line = road_number + " " + road;
		locality = obj.getLocality();
		postal_code = obj.getPostalCode();
		building_description = obj.getPremise();
		neighborhood = obj.getNeighborhood(); 
		area_level1 = obj.getAreaLevel1();
		area_level2 = obj.getAreaLevel2();
		area_level3 = obj.getAreaLevel3();
		geometric_quality = GeoObj.getLocation_type();
		point_lat = Double.parseDouble(GeoObj.getLocation().getLat());
		point_lng = Double.parseDouble(GeoObj.getLocation().getLng());
		bbox_lat_north = Double.parseDouble(GeoObj.getViewport().getNortheast().getLat()); // North Latitude
		bbox_lng_east = Double.parseDouble(GeoObj.getViewport().getNortheast().getLng()); // East Longitude
		bbox_lat_south = Double.parseDouble(GeoObj.getViewport().getSouthwest().getLat()); // South Latitude
		bbox_lng_west = Double.parseDouble(GeoObj.getViewport().getSouthwest().getLng()); // West Longitude
		polygonpointsList = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		
		// SQL-Statements erstellen
		int sqlIndex = 0; //wird gemacht, um sicherzustellen, dass sie SQL-Statements in korrekter Reihenfolge durchgeführt werden
		//INSERT INTO bei Tabelle tls320_enriched_address
		String insertAddress = createInsertAddress();
		sqlList.add(sqlIndex, insertAddress);
		sqlIndex++;
		
		//INSERT INTO bei Tabelle tls321_geometry
		String insertGeometry = createInsertGeometry();	
		sqlList.add(sqlIndex, insertGeometry);
		sqlIndex++;
		
		return sqlList;
	}
	
	public String createInsertAddress(){
		String insertAddress = 
			"INSERT INTO tls320_enriched_address (" +
				"person_orig_id, place_id, service_source, name_freeform, address_freeform, address_type, country_code, country_name, address_line, " +
				"house_number, road, locality, postal_code, building_description, neighborhood, area_level1, area_level2, area_level3" +
			")" +
			"VALUES (" + 
				person_orig_id + ", '" + place_id + "', '" + service_source + "', '" + name_freeform + "', '" + address_freeform + "', '" + address_type + "', '" + 
				country_code + "', '" + country_name + "', '" + address_line + "', '" + road_number + "', '" + road + "', '" + locality + "', '" + postal_code + "', '" + 
				building_description + "', '" + neighborhood + "', '" + area_level1 + "', '" + area_level2 + "', '" + area_level3 + 
			"');";
		return insertAddress;
	}
	
	public String createInsertGeometry(){
		String insertGeometry = 
			"INSERT INTO tls321_geometry (" +
				"geometry_id, geometric_quality, point_lat, point_lng, bbox_lat_north, bbox_lng_east, bbox_lat_south, bbox_lng_west" +
			")" +
			"VALUES (" + 
				"(SELECT geometry_id FROM tls320_enriched_address WHERE person_orig_id = " + person_orig_id + "), '" + geometric_quality + "', " + 
				point_lat + ", " + point_lng + ", " + bbox_lat_north + ", " + bbox_lng_east + ", " + bbox_lat_south + ", " + bbox_lng_west +  
			");";
		return insertGeometry;
	}
	
	public String createInsertPolygonpoints(int i){
		String insertPolygonpoints = 
			"INSERT INTO tls322_polygon_points (" +
				"polygonpoints_fk, polygoncoordinates_id" +
				")" +
			"VALUES (" + 
				"(SELECT polygonpoints_fk FROM tls321_geometry WHERE geometry_id = " +
					"(SELECT geometry_id FROM tls320_enriched_address WHERE person_orig_id = " + person_orig_id + "))" + ", CONCAT(polygonpoints_fk, '-" + i +
			"'));";
		return insertPolygonpoints;
	}
	
	public String createInsertPolygonCoordinates(int i){
		String insertPolygonCoordinates = 
			"INSERT INTO tls323_polygon_coordinates (" +
				"polygoncoordinates_id, lat, lng" +
			")" +
			"VALUES (" + 
				"(SELECT polygoncoordinates_id FROM patstat.tls322_polygon_points WHERE polygoncoordinates_id =" + 
					"CONCAT((SELECT polygonpoints_fk FROM tls321_geometry WHERE geometry_id = " +
						"(SELECT geometry_id FROM tls320_enriched_address WHERE person_orig_id = " + person_orig_id + ")), '-" + i + "'))" + ", " + polygonpointsList.get(i).get(0) + ", " + polygonpointsList.get(i).get(1) +
			");";
		return insertPolygonCoordinates;
	}
}
