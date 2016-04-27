package ch.zhaw.core.alignment;
import java.util.ArrayList;
import java.util.List;

//für Methode align- (-OSM, -Bing und -Google) benötigt
import ch.zhaw.core.query.queryBing.QueryBing;
import ch.zhaw.core.query.queryGoogle.QueryGoogle;
import ch.zhaw.core.query.queryOSM.*;

public class Alignment {

	//Tabelle Address
	private int person_orig_id; //PK
	private int geometry_id;	//FK -> PK bei Geometry
	private int place_id;
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
	private int postal_code;
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
	private int polygonpoints_id;	//FK -> PK bei Polygonpoints
	
	//Zwischentabelle Polygonpoints
	private int polygoncoordinates_id; //FK -> PK bei Polygoncoordinates
	
	//Tabelle Polygoncoordinates
	private double polygon_lat;
	private double polygon_lng;
	
	private List<String> sqlList;
	
	/*
	private String extAdressHarm;
	private String adressName;
	private String adressNummer; //string wegen split
	private String adressOrt;
	private String adressPLZ; //string wegen split
	private String adressLand;
	private String coordinateLat;
	private String coordinateLong;
	private ArrayList <String> structAdress;
	
	
	public Alignment(String extAdress) {
		this.extAdressHarm = extAdress;
		this.structAdress = new ArrayList<String>();
	}
	*/
	public Alignment(int personOrigId) {
		this.person_orig_id = personOrigId; //id wird über Klasse Struktur bei der Initialisierung zum Konstruktor weitergegeben, damit es nicht zur Methode align weitergegeben werden muss
		this.sqlList = new ArrayList<String>();
	}
	
	
	
	/*
	public String getExtAdressHarm() {
		return extAdressHarm;
	}

	public void setExtAdressHarm(String extAdressHarm) {
		this.extAdressHarm = extAdressHarm;
	}

	public String getAdressName() {
		return adressName;
	}

	public void setAdressName(String adressName) {
		this.adressName = adressName;
	}

	public String getAdressNummer() {
		return adressNummer;
	}

	public void setAdressNummer(String adressNummer) {
		this.adressNummer = adressNummer;
	}

	public String getAdressOrt() {
		return adressOrt;
	}

	public void setAdressOrt(String adressOrt) {
		this.adressOrt = adressOrt;
	}

	public String getAdressPLZ() {
		return adressPLZ;
	}

	public void setAdressPLZ(String adressPLZ) {
		this.adressPLZ = adressPLZ;
	}

	public String getAdressLand() {
		return adressLand;
	}

	public void setAdressLand(String adressLand) {
		this.adressLand = adressLand;
	}

	public String getCoordinateLat() {
		return coordinateLat;
	}

	public void setCoordinateLat(String coordinateLat) {
		this.coordinateLat = coordinateLat;
	}

	public String getCoordinateLong() {
		return coordinateLong;
	}

	public void setCoordinateLong(String coordinateLong) {
		this.coordinateLong = coordinateLong;
	}
	
	public ArrayList<String> getStructAdress() {
		return structAdress;
	}

	public void setStructAdress(ArrayList<String> structAdress) {
		this.structAdress = structAdress;
	}
	*/
	
	public int getPerson_orig_id() {
		return person_orig_id;
	}

	public void setPerson_orig_id(int person_orig_id) {
		this.person_orig_id = person_orig_id;
	}

	public int getGeometry_id() {
		return geometry_id;
	}

	public void setGeometry_id(int geometry_id) {
		this.geometry_id = geometry_id;
	}

	public int getPlace_id() {
		return place_id;
	}

	public void setPlace_id(int place_id) {
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

	public int getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(int postal_code) {
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

	public int getPolygonpoints_id() {
		return polygonpoints_id;
	}

	public void setPolygonpoints_id(int polygonpoints_id) {
		this.polygonpoints_id = polygonpoints_id;
	}

	public int getPolygoncoordinates_id() {
		return polygoncoordinates_id;
	}

	public void setPolygoncoordinates_id(int polygoncoordinates_id) {
		this.polygoncoordinates_id = polygoncoordinates_id;
	}

	public double getPolygon_lat() {
		return polygon_lat;
	}

	public void setPolygon_lat(double polygon_lat) {
		this.polygon_lat = polygon_lat;
	}

	public double getPolygon_lng() {
		return polygon_lng;
	}

	public void setPolygon_lng(double polygon_lng) {
		this.polygon_lng = polygon_lng;
	}

	public List<String> getSqlList() {
		return sqlList;
	}

	public void setSqlList(List<String> sqlList) {
		this.sqlList = sqlList;
	}



	//strukturiert Adresse
	public List <String> alignOSM(List<QueryOSM> osm){
		
		int index = osm.get(0).getDefIndex();
		QueryOSM obj = osm.get(index);
		Address addressObj = osm.get(index).getAddress();
		
		//Daten in die Attribute bei Klasse Alignment einfügen
		place_id = Integer.parseInt(obj.getPlace_id());
		service_source = "OSM";
		//überprüft ob Industrial oder Address29 vorhanden ist. falls nicht wird null gesetzt
		if (addressObj.getIndustrial() != null){
			name_freeform = addressObj.getIndustrial();
		}
		else{
			if (addressObj.getAddress29() != null){
				name_freeform = addressObj.getAddress29();
			}
			else {
				name_freeform = null;
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
		locality = addressObj.getListNewAddress().get(3); //siehe bei Klasse Address Methode createListNewAddress()
		postal_code = Integer.parseInt(addressObj.getPostcode());
		building_description = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		neighborhood = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		area_level1 = addressObj.getState();
		area_level2 = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		area_level3 = null; //falls nichts gespeichert werden soll kann diese Zeile gelöscht werden
		geometric_quality = obj.getImportance();
		point_lat = Double.parseDouble(obj.getLat());
		point_lng = Double.parseDouble(obj.getLon());
		bbox_lat_north = Double.parseDouble(obj.getBoundingbox().get(1)); // North Latitude ist an der 2. Stelle -> also 1
		bbox_lng_east = Double.parseDouble(obj.getBoundingbox().get(3)); // East Longitude ist an der 4. Stelle -> also 3
		bbox_lat_south = Double.parseDouble(obj.getBoundingbox().get(0)); // South Latitude ist an der 1. Stelle -> also 0
		bbox_lng_west = Double.parseDouble(obj.getBoundingbox().get(2)); // West Longitude ist an der 3. Stelle -> also 2
		// die frage ist, ob eine neue Klasse für Polygonpoint erstellt werden sollte oder ob es als List<List<String>> abgespeichert werden sollte
		// schlussendlich stellt sich die frage wie man diie polygonpoints hier speichern möchte -> eher mit List
		// obj.getPolygonpoints().get(1).get(0);
		
		// SQL-Statements erstellen
		String insertAddress = "INSERT INTO tls326_detailed_address (place_id, service_source, name_freeform, address_freeform, address_type, country_code, country_name, address_line, "
				+ "house_number, road, locality, postal_code, ";
		
		String insertGeometry = "SELECT * FROM patstat.tls206_person WHERE person_id = (SELECT person_id FROM patstat.tls226_person_orig WHERE person_orig_id = 2205);";
		
		/*
		String[] listextAdr = extAdress.split("\\,", 10);
		
		for (String i: listextAdr){
			if (adressName == null){
				setAdressName(i);
				structAdress.add(i);
			}
			else if (adressNummer == null){
				setAdressNummer(i);
				structAdress.add(i);
			}
			else if (adressOrt == null){
				setAdressOrt(i);
				structAdress.add(i);
			}
			else if (adressPLZ == null){
				setAdressPLZ(i);
				structAdress.add(i);
			}
			else if (adressLand == null){
				setAdressLand(i);
				structAdress.add(i);
			}
			else if (coordinateLat == null){
				setCoordinateLat(i);
				structAdress.add(i);
			}
			else if (coordinateLong == null){
				setCoordinateLong(i);
				structAdress.add(i);
			}
			else {
				structAdress.add(i);
			}
		}		
		*/
		return sqlList;
	}
	
	public List <String> alignBing(QueryBing bing){
		
		return sqlList;
	}
	
	public List <String> alignGoogle(QueryGoogle google){
		
		return sqlList;
	}
	
	
}
