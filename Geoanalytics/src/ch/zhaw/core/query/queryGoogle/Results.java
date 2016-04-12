package ch.zhaw.core.query.queryGoogle;

import java.util.ArrayList;
import java.util.List;

public class Results {
	
	private String formatted_address;
	private String place_id;
	private List<String> types;
	private List<AddressComponents> address_components;
	private Geometry geometry;
	private List<String> listNewAddressGoogle = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	
	
	//constructor for objectmapper
	public Results(){	
	}

	public List<AddressComponents> getAddress_components() {
		return address_components;
	}

	public void setAddress_components(List<AddressComponents> address_components) {
		this.address_components = address_components;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public List<String> getListNewAddressGoogle() {
		return listNewAddressGoogle;
	}

	public void setListNewAddressGoogle(List<String> listNewAddressGoogle) {
		this.listNewAddressGoogle = listNewAddressGoogle;
	}

	public void createListNewAddressGoogle(int i){
		
		String route = address_components.get(getIndex("route", i)).getLong_name();
		System.out.println("route: " + route);
		String addressNumber = address_components.get(getIndex("street_number", i)).getLong_name();
		String plz = address_components.get(getIndex("postal_code", i)).getLong_name();
		String locality = address_components.get(getIndex("locality", i)).getLong_name();
		String areaLevel1 = address_components.get(getIndex("administrative_area_level_1", i)).getLong_name(); //evt. nicht nötig
		String areaLevel2 = address_components.get(getIndex("administrative_area_level_2", i)).getLong_name(); //evt. nicht nötig
		System.out.println("route: " + route);
		System.out.println("addressNumber: " + addressNumber);
		System.out.println("plz: " + plz);
		System.out.println("locality: " + locality);
		System.out.println("areaLevel1: " + areaLevel1);
		System.out.println("areaLevel2: " + areaLevel2);
		
		// listNewAddress muss pro Service gleich strukturiert sein!! -> road, nr, plz, ort
		// Struktur kann noch ergänzt bzw. verändert werden
		
		//road
		listNewAddressGoogle.add(route);
		System.out.println("listNewAddress.add(route) = " + route);
		
		//nr
		listNewAddressGoogle.add(addressNumber);
		System.out.println("listNewAddress.add(addressNumber) = " + addressNumber);
		
		//plz
		listNewAddressGoogle.add(plz);
		System.out.println("listNewAddress.add(plz) = " + plz);
		//Ort
		if (locality != null){
			listNewAddressGoogle.add(locality);
			System.out.println("listNewAddress.add(locality) = " + locality);
		}
		
		//evt. nicht nötig
		/*
		if (areaLevel1 != null){
			listNewAddressGoogle.add(areaLevel1);
			System.out.println("listNewAddress.add(areaLevel1) = " + areaLevel1);
		}
		if (areaLevel2 != null){
			listNewAddressGoogle.add(areaLevel2);
			System.out.println("listNewAddress.add(areaLevel2) = " + areaLevel2);
		}
		*/
	}
	
	//findet die stelle, wo die gesuchte componente ist (z.B. street_number) falls nichts gefunden wurde gibt es -1 zurück
	public int getIndex(String component, int i){
		int index = -1; // nicht null da erste Position von list 0 ist
		int sizeComponent = address_components.size();
		System.out.println("sizeComponent = " + sizeComponent);
		int p = 0;
		
		while(index == -1 ){ // (index == -1 || p < sizeComponent) funktioniert nicht, deshalb rausgenommen
			System.out.println("p = " + p + " , index = " + index);
			String long_name = address_components.get(p).getLong_name();
			String short_name = address_components.get(p).getShort_name();
			//list erstellt, damit nächste for-schlaufe übersichtlicher ist
			List <String> types = address_components.get(p).getTypes(); 
			System.out.println("long_name = " + long_name);
			System.out.println("short_name = " + short_name);
			
			//überprüft ob bei der Liste type den String component beinhaltet
			for (int o = 0; o < types.size(); o++){
				//schlaufe wird verlassen, wenn component gefunden wurde oder länge von types.size() erreicht wurde
				if(types.get(o).contains(component) == true){
					index = p;
					System.out.println("component gefunden, set index = " + index + " , types.get(o) = "+ types.get(o));
				}
				else{
					System.out.println("component nicht gefunden, o = " + o + " , types.get(o) = "+ types.get(o));
				}
			}
			p++;
		}
		return index;
	}
}
