package ch.zhaw.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/*
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
*/

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
	private Boolean allServices; //wird zum überprüfen genutzt ob alle services benutzt wurden
	private List<String> us_state_code; //Abkürzung eines Bundesstaates nach FIPS-Code Reihenfolge wird für die überprüfung von Spezialfällen von US-Adressen gebraucht -> Methode specialCaseUS()
	private List<String> us_state_name; // Vollständiger Name eines Bundesstaates nach FIPS-Code Reihenfolge
	private String checkedVal; //wird für die Methode startAlignment gebraucht, da checkedQuery nicht abgespeichert wird
	
	public Scheme(int personOrigId, int personId, String nameFreeform, String addressOne, String addressTwo, String countryCode) {
		this.personOrigId = personOrigId;
		this.personId = personId;
		this.nameFreeform = nameFreeform;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.countryCode = countryCode;
		this.allServices = false; //wird defaultmässig auf false gesetzt
		setRawAddress(createRawAddress(addressOne, addressTwo)); //Instanzierung von String rawAddress
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
		System.out.println(); //test-code
		System.out.println("Start mainQuery: "); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		
		//Spezialfälle für US-Adressen
		//Bundesstaat-name wird falls nötig in kurz-form (code) umgewandelt
		//Strassenbezeichnungen, die mit One anfangen werden zu 1 umgewandelt
		if(countryCode.contains("US") == true){ //if(countryCode == "US") funktioniert nicht
			this.rawAddress = specialCaseUS(rawAddress);
		}
		
		Query m = new Query(getRawAddress()); //Query-klasse instanzieren und rawAddress setzen
		
		String checkedQuery = null; // für überprüfung bei checkQuery() und checkValidation() nötig,für überprüfung benötigt welche Query gemacht wurde
		
		//Schlaufe die überprüft, ob abfrage vollständig und valide ist. 
		//solange Abfrage nicht true ist wird Methode wiederholt bis keine Services mehr vorhanden sind
		Boolean complete = false; // default false sonst geht while nicht
		while(complete != true){
			complete = checkQuery(m, checkedQuery);
			/*
			if(complete == true){
				Boolean completeVal = false;
				while(completeVal != true){
					completeVal = checkValidation(m, checkedQuery);
					complete = completeVal;
				}
			}
			else{
				
			}
			*/
		}
		
		List<String> sqlList = new ArrayList<String>();
		//überprüft ob addresse i.O zum speichern ist und ob alle Services bereits überprüft wurden
		//Neue Positionierung von startAlignment wahrscheinlich nötig
		if(complete == true && allServices == true){
			System.out.println("allServices == true, neue Addresse wird abgefragt falls vorhanden "); //test-code
		}
		else{
			//Durchführung der Strukturierung der Adresse
			sqlList = startAlignment(m);
			System.out.println("sqlList size: " + sqlList.size());
		}
		return sqlList; //evt. muss die SQL-Statements zurückgegeben werden
	}
	
	//überprüfen welche Services bereits genutzt wurden
	//bei true wurden alle Services bereits abgefragt, bei false noch nicht
	public Boolean checkUsedQueries(List<Boolean> statusList){
		System.out.println(); //test-code
		System.out.println("Start checkUsedQueries()"); //test-code
		Boolean check = null;
		
		//überprüft ob liste leer ist
		if(statusList.isEmpty()){
			check = false;
			System.out.println("statusList is Empty d.h. noch keine Abfrage gemacht "); //test-code
		}
		else {
			//statusList wird mit vordefinierten länge erstellt (alles defaultmässig auf null)
			for(int i = 0; i < statusList.size(); i++){
				if(statusList.get(i) == null){
					check = false;
					System.out.println("status == null bei i : " + i); //test-code
				}
				else{
					check = true;
					System.out.println("status != null bei i : " + i); //test-code
					// allServices = true; // ACHTUNG: aufpassen muss evt. anders platziert werden! fehler gefunden wegen Test erste 10 Testobjekte, testobjekt 1
				}
			}
		}
		return check;
	}
	
	//Output noch nicht ganz sicher (Boolean?)
	//überprüfen welche Services bereits genutzt wurden und startet query. wenn alle Services genutzt wurden, geht man auf mainQuery zurück
	//danach wird überprüft, ob query vollständig ist und falls ja wird validation gestartet, wenn false wird nächster service abgefragt
	public Boolean checkQuery (Query m, String checkedQuery){
		System.out.println(); //test-code
		System.out.println("start checkQuery()"); //test-code
		Boolean check = false;
		//String checkedQuery = null; // String für überprüfung benötigt welche Query gemacht wurde
		
		if(checkUsedQueries(m.getStatusList()) == false){
			System.out.println("noch nicht alle Services abgefragt. starte neue abfrage"); //test-code
			checkedQuery = m.query(); // Abfrage-Methode iniziieren
			System.out.println("überprüfung beendet für Abfrage : " + checkedQuery); //test-code
			
			Boolean statusOSM = m.getStatusOSM();
			Boolean statusBing = m.getStatusBing();
			Boolean statusGoogle = m.getStatusGoogle();
			
			if(checkedQuery == "osm"){
				System.out.println("checkedQuery == osm"); //test-code
				System.out.println("statusOSM : " + statusOSM); //test-code
				//bei true wird die Abfrage gestartet, bei false wird Methode wiederholt 
				if(statusOSM == true){
					//je nach dem ob die validierung gut war, setzt es check auf true oder false
					check = checkValidation(m, checkedQuery); //Prüfung der neu gewonnenen Adresse starten
				}
				else{
					//testCheck wiederholen (schlaufe)
					check = false;
				}
			}
			else{
				//bei true wird die Abfrage gestartet, bei false wird Methode wiederholt 
				if(checkedQuery == "bing"){
					System.out.println("checkedQuery == bing"); //test-code
					System.out.println("statusBing : " + statusBing); //test-code
					if(statusBing == true){
						//je nach dem ob die validierung gut war, setzt es check auf true oder false
						check = checkValidation(m, checkedQuery); //Prüfung der neu gewonnenen Adresse starten
					}
					else{
						//testCheck wiederholen (schlaufe)
						//prints wurden eingebaut um zu sehen ob wirklich kein resultat zurückgekommen ist
						System.out.println("estimatedTotal: " + m.getBing().getResourceSets().get(0).getEstimatedTotal()); //test-code
						System.out.println("StatusCode : " + m.getBing().getStatusCode()); //test-code
						check = false;
					}
				}
				else{
					//bei true wird die Abfrage gestartet, bei false wird Methode wiederholt 
					if(checkedQuery == "google"){
						System.out.println("checkedQuery == google"); //test-code
						System.out.println("statusGoogle : " + statusGoogle); //test-code
						if(statusGoogle == true){
							//je nach dem ob die validierung gut war, setzt es check auf true oder false
							check = checkValidation(m, checkedQuery); //Prüfung der neu gewonnenen Adresse starten
						}
						else{
							//testCheck wiederholen (schlaufe)
							check = false;
						}
					}
					else{
						//anderer Service aufrufen
						System.out.println("Struktur checkQuery(): anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
						check = true;
						allServices = true;
					}
				}
			}
		}
		else{
			//hier zurück zur mainQuery gehen und neue Adresse abfragen falls vorhanden
			System.out.println("alle Services abgefragt. starte neue abfrage"); //test-code
			allServices = true; //wenn man hierherkommt hat man alle Services abgerufen -> Code später evt. anpassen
			check = true;
		}
		return check;
	}
	
	public Boolean checkValidation(Query m, String checkedQuery){
		System.out.println(); //test-code
		System.out.println("start checkValidation()"); //test-code
		Boolean check = false;
		
		if(checkedQuery == "osm"){
			System.out.println("checkedQuery == osm"); //test-code
			
			for(int i = 0; i < m.getOsm().size(); i++){
				
			}
			//Prüfung der neu gewonnenen Adresse starten
			//newAddressTrue wird bei OSM bei der stelle 0 gemacht
			QueryOSM obj = m.getOsm().get(0); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
			List<Integer> newAddressTrue = obj.getNewAddressTrue();
			List<Double> listScore = obj.getListScore();
			List<Integer> listScoreTrue = obj.getListScoreTrue();
			
			//geht mit jedem List<String> die validation durch 
			for(int i = 0; i < newAddressTrue.size(); i++){
				int index = newAddressTrue.get(i);
				Address addressObj = m.getOsm().get(index).getAddress();
				List<String> newAddress = addressObj.getListNewAddress(); // wird erstellt damit Code leserlicher ist
				
				Validation v = startValidation(m, newAddress); //validierung wird gestartet
				addressObj.setScore(v.getScore()); // Score bei adresse speichern falls es später gebraucht wird
				addressObj.setStatus(v.getVal());
				Double score = addressObj.getScore();
				Boolean statusAddress = addressObj.getStatus();
				
				//überprüft ob berechneter Score gut genug ist. 
				//wenn true wird er bei listScor hinzugefügt, wenn false wird nächster Service aufgerufen
				if(statusAddress == true){
					listScore.add(score); //erhaltener Score wird in der List hinzugefügt
					listScoreTrue.add(index); //List mit der Stelle wo listScoreOSM den Score hat
					System.out.println("Validation OSM genügend:" + statusAddress + " , Score: " + score + " , bei Stelle index: " + index); //test-code
					//listTrueExist = true;
				}
				else{
					System.out.println("Validation OSM ungenügend:" + statusAddress + " , Score: " + score); //test-code
					//testCheck wiederholen (schlaufe)
					//listTrueExist = false;
				}
			}
			
			//listScore und listScoreTrue "speichern"
			obj.setListScore(listScore);
			obj.setListScoreTrue(listScoreTrue);

			//für testzecken eingesetzt um zu sehen was die Listen beinhalten
			for(int i = 0; i < newAddressTrue.size(); i++){
				System.out.println("newAddressTrue bei OSM: " + newAddressTrue.get(i)); //test-code
			}
			for(int i = 0; i < listScoreTrue.size(); i++){
				System.out.println("listScoreTrue bei OSM:" + listScoreTrue.get(i)); //test-code
			}
			
			//es wird überprüft ob newAddressTrue leer ist
			//wenn true war keine Adresse geeignet und check wird auf false gesetzt, wenn false wird die beste adresse gesucht und true zurückgegeben
			if(listScoreTrue.isEmpty() == false){
				int indexBest = bestScoreIndex(listScore, listScoreTrue); // gibt die Stelle zurück, wo der beste Score erzielt wurde
				Double scoreBest = obj.getListScore().get(indexBest);
				obj.setStatusValidation(true);
				obj.setDefIndex(indexBest);
				System.out.println("listScoreTrue ist nicht leer, defIndexOSM:" + indexBest + " , Score: " + scoreBest); //test-code
				check = true; //setzt check auf false oder true nach ergebnis der validierung
				checkedVal = checkedQuery; //wird für die Methode startAlignment gebraucht, da checkedQuery nicht abgespeichert wird 
			}
			else{
				obj.setStatusValidation(false);
				System.out.println("listScoreTrue ist leer d.h. keine Adresse hat einen guten Score"); //test-code
				check = false;
			}
		}
		else{
			if(checkedQuery == "bing"){	
				System.out.println("checkedQuery == bing"); //test-code
				//Prüfung der neu gewonnenen Adresse starten
				ResourceSetsObj obj = m.getBing().getResourceSets().get(0); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
				List<Integer> newAddressTrue = obj.getNewAddressTrue();;
				List<Double> listScore = obj.getListScore();
				List<Integer> listScoreTrue = obj.getListScoreTrue();
				
				//get mit jedem List<String> die validation durch 
				for(int i = 0; i < newAddressTrue.size(); i++){
					int index = newAddressTrue.get(i);
					ResourcesObj addressObj = m.getBing().getResourceSets().get(0).getResources().get(index); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
					List<String> newAddress = addressObj.getNewAddress(); // wird erstellt damit Code leserlicher ist
					
					Validation v = startValidation(m, newAddress); //validierung wird gestartet
					addressObj.setScore(v.getScore()); // Score bei adresse speichern falls es später gebraucht wird
					addressObj.setStatus(v.getVal());
					Double score = addressObj.getScore();
					Boolean statusAddress = addressObj.getStatus();
					
					//überprüft ob berechneter Score gut genut ist. 
					//wenn true wird er bei listScor hinzugefügt, wenn false wird nächster Service aufgerufen
					if(statusAddress == true){
						listScore.add(score); //erhaltener Score wird in der List hinzugefügt
						listScoreTrue.add(index); //List mit der Stelle wo listScoreBing den Score hat
						System.out.println("Validation Bing genügend:" + statusAddress + " , Score: " + score + " , bei Stelle index: " + index); //test-code
					}
					else{
						System.out.println("Validation Bing ungenügend:" + statusAddress + " , Score: " + score); //test-code
						//testCheck wiederholen (schlaufe)
						check = false;
					}
				}
				
				//listScore und listScoreTrue "speichern"
				obj.setListScore(listScore);
				obj.setListScoreTrue(listScoreTrue);
				
				//für testzecken eingesetzt um zu sehen was die Listen beinhalten
				for(int i = 0; i < newAddressTrue.size(); i++){
					System.out.println("newAddressTrue bei Bing: " + newAddressTrue.get(i)); //test-code
				}
				for(int i = 0; i < listScoreTrue.size(); i++){
					System.out.println("listScoreTrue bei Bing:" + listScoreTrue.get(i)); //test-code
				}
				
				//es wird überprüft ob newAddressTrue leer ist
				//wenn true war keine Adresse geeignet und check wird auf false gesetzt, wenn false wird die beste adresse gesucht und true zurückgegeben
				if(listScoreTrue.isEmpty() == false){
					int indexBest = bestScoreIndex(listScore, listScoreTrue); // gibt die Stelle zurück, wo der beste Score erzielt wurde
					Double scoreBest = obj.getListScore().get(indexBest);
					obj.setStatusValidation(true);
					obj.setDefIndex(indexBest);
					System.out.println("newAddressTrue ist nicht leer, defIndexBing:" + indexBest + " , Score: " + scoreBest); //test-code
					check = true; //setzt check auf false oder true nach ergebnis der validierung
					checkedVal = checkedQuery; //wird für die Methode startAlignment gebraucht, da checkedQuery nicht abgespeichert wird
				}
				else{
					obj.setStatusValidation(false);
					System.out.println("newAddressTrue ist leer d.h. keine Adresse hat einen guten Score"); //test-code
					check = false;
				}
			}
			else{
				if(checkedQuery == "google"){
					System.out.println("checkedQuery == google"); //test-code
					//Prüfung der neu gewonnenen Adresse starten
					QueryGoogle obj = m.getGoogle(); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
					List<Integer> newAddressTrue = obj.getNewAddressTrue();;
					List<Double> listScore = obj.getListScore();
					List<Integer> listScoreTrue = obj.getListScoreTrue();
					
					//get mit jedem List<String> die validation durch 
					for(int i = 0; i < newAddressTrue.size(); i++){
						int index = newAddressTrue.get(i);
						Results addressObj = m.getGoogle().getResults().get(index); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
						List<String> newAddress = addressObj.getNewAddress(); // wird erstellt damit Code leserlicher ist
						
						Validation v = startValidation(m, newAddress); //validierung wird gestartet
						addressObj.setScore(v.getScore()); // Score bei adresse speichern falls es später gebraucht wird
						addressObj.setStatus(v.getVal());
						Double score = addressObj.getScore();
						Boolean statusAddress = addressObj.getStatus();
						
						//überprüft ob berechneter Score gut genut ist. 
						//wenn true wird er bei listScor hinzugefügt, wenn false wird nächster Service aufgerufen
						if(statusAddress == true){
							listScore.add(score); //erhaltener Score wird in der List hinzugefügt
							listScoreTrue.add(index); //List mit der Stelle wo listScoreBing den grössten Score hat
							System.out.println("Validation Google genügend:" + statusAddress + " , Score: " + score + " , bei Stelle index: " + index); //test-code
						}
						else{
							System.out.println("Validation Google ungenügend:" + statusAddress + " , Score: " + score); //test-code
							//testCheck wiederholen (schlaufe)
							check = false;
						}
					}
					
					//listScore und listScoreTrue "speichern"
					obj.setListScore(listScore);
					obj.setListScoreTrue(listScoreTrue);
					
					//für testzecken eingesetzt um zu sehen was die Listen beinhalten
					for(int i = 0; i < newAddressTrue.size(); i++){
						System.out.println("newAddressTrue bei Google: " + newAddressTrue.get(i)); //test-code
					}
					for(int i = 0; i < listScoreTrue.size(); i++){
						System.out.println("listScoreTrue bei Google:" + listScoreTrue.get(i)); //test-code
					}
					
					//es wird überprüft ob newAddressTrue leer ist
					//wenn true war keine Adresse geeignet und check wird auf false gesetzt, wenn false wird die beste adresse gesucht und true zurückgegeben
					if(listScoreTrue.isEmpty() == false){
						int indexBest = bestScoreIndex(listScore, listScoreTrue); // gibt die Stelle zurück, wo der beste Score erzielt wurde
						Double scoreBest = obj.getListScore().get(indexBest);
						obj.setStatusValidation(true);
						obj.setDefIndex(indexBest);
						System.out.println("newAddressTrue ist nicht leer, defIndexGoogle:" + indexBest + " , Score: " + scoreBest); //test-code
						check = true; //setzt check auf false oder true nach ergebnis der validierung
						checkedVal = checkedQuery; //wird für die Methode startAlignment gebraucht, da checkedQuery nicht abgespeichert wird
					}
					else{
						obj.setStatusValidation(false);
						System.out.println("newAddressTrue ist leer d.h. keine Adresse hat einen guten Score"); //test-code
						check = false;
					}
				}
				else{
					//anderer Service aufrufen
					System.out.println("Struktur checkValidation(): anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
					check = true;
					//all = true;
				}
			}
		}
		return check;
	}
	
	//Instanziert Validation und startet Validierung
	public Validation startValidation(Query m, List<String> newAddress){
		System.out.println(); //test-code
		System.out.println("start startValidation()"); //test-code
		String rawAddress = m.getRawAddress().toLowerCase(); // für die validierung werden beide Adressen auf LowerCase gestellt
		List<String> newAddressLow = new ArrayList<String>();
		for(int i = 0; i < newAddress.size(); i++){
			newAddressLow.add(i, newAddress.get(i).toLowerCase()); // index zur sicherheit eingefügt
			System.out.println("newAddressLow.add :" + newAddressLow.get(i));
		}
		Validation v = new Validation(rawAddress); //Initiiere Validation
		v.validate(rawAddress, newAddressLow);
		return v;
	}
	
	// sucht aus der Liste den besten Score und gibt die Stelle zurück, die auf die Adresse verweisen soll
	public int bestScoreIndex(List<Double> listScore, List<Integer> listTrue){
		System.out.println(); //test-code
		System.out.println("start bestScoreIndex()"); //test-code
		int index = 0;
		Double max = 0.0;
		for (int x = 0; x < listTrue.size(); x++){
			if (max < listScore.get(x)){
				max = listScore.get(x);
				index = x;
				System.out.println("min = listScore.get(x) :" + listScore.get(x));
			}
			else{
				System.out.println("listScore -> Score ist kleiner als der vorherige Score :" + listScore.get(x));
			}
		}
		return index;
	}
	
	//resultat wird als SQL-Statement vorbereitet für die Abspeicherung in die Datenbank
	public List<String> startAlignment(Query m){
		System.out.println(); //test-code
		System.out.println("start startAlignment()"); //test-code
		
		Alignment l = new Alignment(personOrigId); //Inittiere Alignment, person_orig_id wird beim Konstruktor gespeichert
		List<String> sqlList = new ArrayList <String>(); //Liste Instanzieren wird bei den if-Bedingungen weiter unten gebraucht
		
		System.out.println("checkedVal : " + checkedVal); //test-code
		//es wird eine Liste erstellt die alle SQL-Statement beinhaltet, 
		//vorher wird noch geprüft welcher service genutzt wurde und welche adresse abgespeicher werden soll
		if(checkedVal == "osm"){
			List<QueryOSM> osm = m.getOsm(); 	// wird als input für Methode alignOSM gebraucht
			sqlList = l.alignOSM(osm);
		}
		else{
			if(checkedVal == "bing"){	
				QueryBing bing = m.getBing();		// wird als input für Methode alignBing gebraucht
				sqlList = l.alignBing(bing);
			}
			else{
				if(checkedVal == "google"){
					QueryGoogle google = m.getGoogle();	// wird als input für Methode alignGoogle gebraucht
					sqlList = l.alignGoogle(google);
				}
				else{
					System.out.println("Fehler es konnte kein Service zugewiesen werden!"); //test-code
					System.out.println("checkedVal : " + checkedVal); //test-code
				}
			}
		}
		System.out.println("sqlList size: " + sqlList.size());
		return sqlList;
	}
	
	public String specialCaseUS(String rawAddress){
		System.out.println(); //test-code
		System.out.println("start specialCaseUS()"); //test-code
		
		//Spezialfall: One zu 1 umwandeln -> wichtig dieser Spezialfall sollte als erstes überprüft werden da hier die Gross- und Kleinschreibung von bedeutung für die If-Bedingung ist 
		//-> einfacher zu überprüfen und weniger fehler, da evt. "one" in einem anderen Element enthalten sein kann
		if(rawAddress.contains("One") ){
			rawAddress = rawAddress.replaceFirst("One", "1"); //replaceFirst, da dieser Fall immer als erstes kommt und nur einmal vorkommt
			System.out.println("One mit 1 ersetzt, rawAddress neu: " + rawAddress); //test-code
		}
		
		//Spezialfall: Bundesstaat-Abkürzung umwandeln
		// listen erstellen
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
		System.out.println("us_state_code.size(): " + us_state_code.size()); //test-code
		System.out.println("us_state_name.size(): " + us_state_name.size()); //test-code
		for (int i = 0; i < us_state_code.size(); i++){
			System.out.println("us_state_code.get(i) : " + us_state_code.get(i)); //test-code
		}
		for (int i = 0; i < us_state_name.size(); i++){
			System.out.println("us_state_name.get(i) : " + us_state_name.get(i)); //test-code
		}
		*/
		
		//Wichtig rawAddress wird als lowerCase zurückgegeben, da die Variable bei Validation sowieso mit lowercase gearbeteitet wird
		//und rawAddress schlussendlich danach nicht mehr gebraucht wird. Es ist bewusst, dass man es optimaler machen könnte.
		//der state_code wird später ist zwar hier dann noch in Grossbuchstaben, aber wird später auch zu lowerCase
		System.out.println("rawAddress vorher : " + rawAddress); //test-code
		for (int i = 0; i < us_state_name.size(); i++){
			String name = us_state_name.get(i).toLowerCase();
			rawAddress = rawAddress.toLowerCase();
			
			if(rawAddress.contains(name) == true){
				rawAddress = rawAddress.replaceAll(name, us_state_code.get(i));
				System.out.println("rawAddress geändert, neu : " + rawAddress); //test-code
			}
		}
		return rawAddress;
	}
}
