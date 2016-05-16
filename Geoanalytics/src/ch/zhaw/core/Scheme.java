package ch.zhaw.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import ch.zhaw.core.alignment.Alignment;
import ch.zhaw.core.query.*;
import ch.zhaw.core.query.queryBing.*;
import ch.zhaw.core.query.queryGoogle.QueryGoogle;
import ch.zhaw.core.query.queryGoogle.Results;
import ch.zhaw.core.query.queryOSM.*;
import ch.zhaw.core.query.queryOSM.Address;
import ch.zhaw.core.validation.Validation;

public class Scheme {
	
	private String rawAddress;
	private int personOrigId;
	private int personId;
	private String nameFreeform;
	private String addressOne;
	private String addressTwo;
	private String countryCode;
	private Boolean allServices; 
	private List<String> us_state_code; 
	private List<String> us_state_name;
	private String checkedVal;
	
	public Scheme(int personOrigId, int personId, String nameFreeform, String addressOne, String addressTwo, String countryCode) {
		this.personOrigId = personOrigId;
		this.personId = personId;
		this.nameFreeform = nameFreeform;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.countryCode = countryCode;
		this.allServices = false; 
		setRawAddress(createRawAddress(addressOne, addressTwo));
	}

	public String getRawAddress() {
		return rawAddress;
	}

	public void setRawAddress(String rawAddress) {
		this.rawAddress = rawAddress;
	}
	
	public int getPersonOrigId() {
		return personOrigId;
	}

	public void setPersonOrigId(int personOrigId) {
		this.personOrigId = personOrigId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getNameFreeform() {
		return nameFreeform;
	}

	public void setNameFreeform(String nameFreeform) {
		this.nameFreeform = nameFreeform;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Boolean getAllServices() {
		return allServices;
	}

	public void setAllServices(Boolean allServices) {
		this.allServices = allServices;
	}

	public List<String> getUs_state_code() {
		return us_state_code;
	}

	public void setUs_state_code(List<String> us_state_code) {
		this.us_state_code = us_state_code;
	}

	public List<String> getUs_state_name() {
		return us_state_name;
	}

	public void setUs_state_name(List<String> us_state_name) {
		this.us_state_name = us_state_name;
	}

	public String getCheckedVal() {
		return checkedVal;
	}

	public void setCheckedVal(String checkedVal) {
		this.checkedVal = checkedVal;
	}

	public String createRawAddress(String addressOne, String addressTwo){
		String combinedString = addressOne + " " + addressTwo;
		return combinedString;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public List<String> mainQuery(String rawAddress) {
		System.out.println();
		System.out.println("Start mainQuery: "); 
		System.out.println("rawAddress: " + rawAddress);
		
		//Spezialfälle für US-Adressen
		if(countryCode.contains("US") == true){ //if(countryCode == "US") funktioniert nicht
			this.rawAddress = specialCaseUS(rawAddress);
		}
		
		Query m = new Query(getRawAddress());
		String checkedQuery = null;
		List<String> sqlList = new ArrayList<String>();
		
		//Schlaufe die überprüft, ob Abfrage vollständig und valide ist. 
		//solange Abfrage nicht true ist wird Methode wiederholt bis keine Services mehr vorhanden sind
		Boolean complete = false;
		while(complete != true){
			complete = checkQuery(m, checkedQuery);
		}
		
		//überprüft ob Adresse i.O zum speichern ist und ob alle Services bereits überprüft wurden
		if(complete == true && allServices == true){
			System.out.println("allServices == true, neue Addresse wird abgefragt falls vorhanden "); //test-code
		}
		else{
			sqlList = startAlignment(m);
			System.out.println("sqlList size: " + sqlList.size());
		}
		return sqlList;
	}
	
	//überprüft, welche Services bereits genutzt wurden
	public Boolean checkUsedQueries(List<Boolean> statusList){
		System.out.println(); //test-code
		System.out.println("Start checkUsedQueries()");
		Boolean check = null;
		
		if(statusList.isEmpty()){
			check = false;
			System.out.println("statusList is Empty d.h. noch keine Abfrage gemacht "); 
		}
		else {
			for(int i = 0; i < statusList.size(); i++){
				if(statusList.get(i) == null){
					check = false;
					System.out.println("status == null bei i : " + i);
				}
				else{
					check = true;
					System.out.println("status != null bei i : " + i);
				}
			}
		}
		return check;
	}
	
	//überprüfen welche Services bereits genutzt wurden und startet Methode query und checkValidation
	public Boolean checkQuery (Query m, String checkedQuery){
		System.out.println(); 
		System.out.println("start checkQuery()"); 
		Boolean check = false;
		
		if(checkUsedQueries(m.getStatusList()) == false){
			System.out.println("noch nicht alle Services abgefragt. starte neue abfrage"); 
			checkedQuery = m.query();
			System.out.println("überprüfung beendet für Abfrage : " + checkedQuery);
			
			Boolean statusOSM = m.getStatusOSM();
			Boolean statusBing = m.getStatusBing();
			Boolean statusGoogle = m.getStatusGoogle();
			
			if(checkedQuery == "osm"){
				System.out.println("checkedQuery == osm"); 
				System.out.println("statusOSM : " + statusOSM); 
				//Bei True wird die Abfrage gestartet, bei False wird Methode checkQuery wiederholt 
				if(statusOSM == true){
					check = checkValidation(m, checkedQuery);
				}
				else{
					check = false;
				}
			}
			else{
				//Bei True wird die Abfrage gestartet, bei False wird Methode checkQuery wiederholt 
				if(checkedQuery == "bing"){
					System.out.println("checkedQuery == bing"); 
					System.out.println("statusBing : " + statusBing);
					if(statusBing == true){
						check = checkValidation(m, checkedQuery);
					}
					else{
						check = false;
					}
				}
				else{
					//Bei True wird die Abfrage gestartet, bei False wird Methode checkQuery wiederholt  
					if(checkedQuery == "google"){
						System.out.println("checkedQuery == google"); 
						System.out.println("statusGoogle : " + statusGoogle); 
						if(statusGoogle == true){
							check = checkValidation(m, checkedQuery); 
						}
						else{
							check = false;
						}
					}
					else{
						System.out.println("Struktur checkQuery(): anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen ");
						check = true;
						allServices = true;
					}
				}
			}
		}
		else{
			System.out.println("alle Services abgefragt. starte neue abfrage");
			allServices = true;
			check = true;
		}
		return check;
	}
	
	public Boolean checkValidation(Query m, String checkedQuery){
		System.out.println(); 
		System.out.println("start checkValidation()");
		Boolean check = false;
		
		if(checkedQuery == "osm"){
			System.out.println("checkedQuery == osm");
			
			for(int i = 0; i < m.getOsm().size(); i++){
				
			}
			//Prüfung der neu gewonnenen Adresse starten
			QueryOSM obj = m.getOsm().get(0);
			List<Integer> newAddressTrue = obj.getNewAddressTrue();
			List<Double> listScore = obj.getListScore();
			List<Integer> listScoreTrue = obj.getListScoreTrue();
			
			//geht mit jedem List<String> die validation durch 
			for(int i = 0; i < newAddressTrue.size(); i++){
				int index = newAddressTrue.get(i);
				Address addressObj = m.getOsm().get(index).getAddress();
				List<String> newAddress = addressObj.getListNewAddress(); 
				
				Validation v = startValidation(m, newAddress); 
				addressObj.setScore(v.getScore()); 
				addressObj.setStatus(v.getVal());
				Double score = addressObj.getScore();
				Boolean statusAddress = addressObj.getStatus();
				
				//überprüft ob berechneter Score gut genug ist. 
				if(statusAddress == true){
					listScore.add(score); 
					listScoreTrue.add(index); 
					System.out.println("Validation OSM genügend");
				}
				else{
					System.out.println("Validation OSM ungenügend");
				}
			}
			
			obj.setListScore(listScore);
			obj.setListScoreTrue(listScoreTrue);

			/*
			//für testzecken eingesetzt, um zu sehen was die Listen beinhalten
			for(int i = 0; i < newAddressTrue.size(); i++){
				System.out.println("newAddressTrue bei OSM: " + newAddressTrue.get(i)); 
			}
			for(int i = 0; i < listScoreTrue.size(); i++){
				System.out.println("listScoreTrue bei OSM:" + listScoreTrue.get(i)); 
			}
			*/
			
			//es wird überprüft ob newAddressTrue leer ist
			if(listScoreTrue.isEmpty() == false){
				int indexBest = bestScoreIndex(listScore, listScoreTrue);
				Double scoreBest = obj.getListScore().get(indexBest);
				obj.setStatusValidation(true);
				obj.setDefIndex(indexBest);
				System.out.println("listScoreTrue ist nicht leer, defIndexOSM:" + indexBest + " , Score: " + scoreBest); 
				check = true; 
				checkedVal = checkedQuery;
			}
			else{
				obj.setStatusValidation(false);
				System.out.println("listScoreTrue ist leer d.h. keine Adresse hat einen guten Score"); //test-code
				check = false;
			}
		}
		else{
			if(checkedQuery == "bing"){	
				System.out.println("checkedQuery == bing");
				//Prüfung der neu gewonnenen Adresse starten
				ResourceSetsObj obj = m.getBing().getResourceSets().get(0);
				List<Integer> newAddressTrue = obj.getNewAddressTrue();;
				List<Double> listScore = obj.getListScore();
				List<Integer> listScoreTrue = obj.getListScoreTrue();
				
				//get mit jedem List<String> die validation durch 
				for(int i = 0; i < newAddressTrue.size(); i++){
					int index = newAddressTrue.get(i);
					ResourcesObj addressObj = m.getBing().getResourceSets().get(0).getResources().get(index); 
					List<String> newAddress = addressObj.getNewAddress();
					
					Validation v = startValidation(m, newAddress); 
					addressObj.setScore(v.getScore()); 
					addressObj.setStatus(v.getVal());
					Double score = addressObj.getScore();
					Boolean statusAddress = addressObj.getStatus();
					
					//überprüft ob berechneter Score gut genut ist. 
					if(statusAddress == true){
						listScore.add(score); 
						listScoreTrue.add(index); 
						System.out.println("Validation Bing genügend");
					}
					else{
						System.out.println("Validation Bing ungenügend"); 
						check = false;
					}
				}
				
				obj.setListScore(listScore);
				obj.setListScoreTrue(listScoreTrue);
				
				/*
				//für testzecken eingesetzt um zu sehen was die Listen beinhalten
				for(int i = 0; i < newAddressTrue.size(); i++){
					System.out.println("newAddressTrue bei Bing: " + newAddressTrue.get(i)); 
				}
				for(int i = 0; i < listScoreTrue.size(); i++){
					System.out.println("listScoreTrue bei Bing:" + listScoreTrue.get(i)); 
				}
				*/
				
				//es wird überprüft ob newAddressTrue leer ist
				if(listScoreTrue.isEmpty() == false){
					int indexBest = bestScoreIndex(listScore, listScoreTrue);
					Double scoreBest = obj.getListScore().get(indexBest);
					obj.setStatusValidation(true);
					obj.setDefIndex(indexBest);
					System.out.println("newAddressTrue ist nicht leer, defIndexBing:" + indexBest + " , Score: " + scoreBest);
					check = true; 
					checkedVal = checkedQuery;
				}
				else{
					obj.setStatusValidation(false);
					System.out.println("newAddressTrue ist leer d.h. keine Adresse hat einen guten Score");
					check = false;
				}
			}
			else{
				if(checkedQuery == "google"){
					System.out.println("checkedQuery == google"); 
					//Prüfung der neu gewonnenen Adresse starten
					QueryGoogle obj = m.getGoogle();
					List<Integer> newAddressTrue = obj.getNewAddressTrue();;
					List<Double> listScore = obj.getListScore();
					List<Integer> listScoreTrue = obj.getListScoreTrue();
					
					//get mit jedem List<String> die validation durch 
					for(int i = 0; i < newAddressTrue.size(); i++){
						int index = newAddressTrue.get(i);
						Results addressObj = m.getGoogle().getResults().get(index); 
						List<String> newAddress = addressObj.getNewAddress();
						
						Validation v = startValidation(m, newAddress); 
						addressObj.setScore(v.getScore()); 
						addressObj.setStatus(v.getVal());
						Double score = addressObj.getScore();
						Boolean statusAddress = addressObj.getStatus();
						
						//überprüft ob berechneter Score gut genut ist. 
						if(statusAddress == true){
							listScore.add(score); 
							listScoreTrue.add(index);
							System.out.println("Validation Google genügend");
						}
						else{
							System.out.println("Validation Google ungenügend");
							check = false;
						}
					}
					
					obj.setListScore(listScore);
					obj.setListScoreTrue(listScoreTrue);
					
					/*
					//für testzecken eingesetzt um zu sehen was die Listen beinhalten
					for(int i = 0; i < newAddressTrue.size(); i++){
						System.out.println("newAddressTrue bei Google: " + newAddressTrue.get(i)); 
					}
					for(int i = 0; i < listScoreTrue.size(); i++){
						System.out.println("listScoreTrue bei Google:" + listScoreTrue.get(i));
					}
					*/
					
					//es wird überprüft ob newAddressTrue leer ist
					if(listScoreTrue.isEmpty() == false){
						int indexBest = bestScoreIndex(listScore, listScoreTrue);
						Double scoreBest = obj.getListScore().get(indexBest);
						obj.setStatusValidation(true);
						obj.setDefIndex(indexBest);
						System.out.println("newAddressTrue ist nicht leer, defIndexGoogle:" + indexBest + " , Score: " + scoreBest);
						check = true; 
						checkedVal = checkedQuery;
					}
					else{
						obj.setStatusValidation(false);
						System.out.println("newAddressTrue ist leer d.h. keine Adresse hat einen guten Score");
						check = false;
					}
				}
				else{
					System.out.println("Struktur checkValidation(): anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen ");
					check = true;
				}
			}
		}
		return check;
	}
	
	//Instanziert Validation und startet Validierung
	public Validation startValidation(Query m, List<String> newAddress){
		System.out.println();
		System.out.println("start startValidation()"); 
		String rawAddress = m.getRawAddress().toLowerCase(); 
		List<String> newAddressLow = new ArrayList<String>();
		for(int i = 0; i < newAddress.size(); i++){
			newAddressLow.add(i, newAddress.get(i).toLowerCase()); 
			System.out.println("newAddressLow.add :" + newAddressLow.get(i));
		}
		Validation v = new Validation(rawAddress);
		v.validate(rawAddress, newAddressLow);
		return v;
	}
	
	// sucht aus der Liste den besten Score und gibt die Stelle zurück, die auf die Adresse verweisen soll
	public int bestScoreIndex(List<Double> listScore, List<Integer> listTrue){
		System.out.println(); //test-code
		System.out.println("start bestScoreIndex()");
		int index = 0;
		Double max = 0.0;
		for (int x = 0; x < listTrue.size(); x++){
			if (max < listScore.get(x)){
				max = listScore.get(x);
				index = x;
			}
		}
		return index;
	}
	
	//Resultat wird als SQL-Statement vorbereitet für die Abspeicherung in die Datenbank
	public List<String> startAlignment(Query m){
		System.out.println(); 
		System.out.println("start startAlignment()");
		
		Alignment l = new Alignment(personOrigId); 
		List<String> sqlList = new ArrayList <String>(); 
		
		//es wird eine Liste erstellt die alle SQL-Statement beinhaltet, 
		if(checkedVal == "osm"){
			List<QueryOSM> osm = m.getOsm();
			sqlList = l.alignOSM(osm);
		}
		else{
			if(checkedVal == "bing"){	
				QueryBing bing = m.getBing();
				sqlList = l.alignBing(bing);
			}
			else{
				if(checkedVal == "google"){
					QueryGoogle google = m.getGoogle();
					sqlList = l.alignGoogle(google);
				}
				else{
					System.out.println("Fehler: Es konnte kein Service zugewiesen werden!");
				}
			}
		}
		return sqlList;
	}
	
	public String specialCaseUS(String rawAddress){
		System.out.println(); 
		System.out.println("start specialCaseUS()"); 
		
		//Spezialfall: One zu 1 umwandeln
		if(rawAddress.contains("One") ){
			rawAddress = rawAddress.replaceFirst("One", "1"); 
			System.out.println("One mit 1 ersetzt, rawAddress neu: " + rawAddress);
		}
		
		//Spezialfall: Bundesstaat-Abkürzung umwandeln
		us_state_code = Arrays.asList(
			"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", 
			"NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD","TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "AS", "FM", "GU", "MH", "MP", "PW", "PR", "UM", "VI" 
		);
		us_state_name = Arrays.asList(
			"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", 
			"Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
			"Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
			"South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "American Samoa", "Micronesia", "Guam",
			"Marshall Islands", "Northern Mariana Islands", "Palau", "Puerto Rico", "Minor Outlying Islands", "Virgin Islands"
		);
		
		/*
		//prints zu testzwecken
		System.out.println("us_state_code.size(): " + us_state_code.size()); 
		System.out.println("us_state_name.size(): " + us_state_name.size()); 
		for (int i = 0; i < us_state_code.size(); i++){
			System.out.println("us_state_code.get(i) : " + us_state_code.get(i)); 
		}
		for (int i = 0; i < us_state_name.size(); i++){
			System.out.println("us_state_name.get(i) : " + us_state_name.get(i));
		}
		*/
		
		for (int i = 0; i < us_state_name.size(); i++){
			String name = us_state_name.get(i).toLowerCase();
			rawAddress = rawAddress.toLowerCase();
			if(rawAddress.contains(name) == true){
				rawAddress = rawAddress.replaceAll(name, us_state_code.get(i));
			}
		}
		return rawAddress;
	}
}
