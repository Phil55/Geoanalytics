package ch.zhaw.core;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zhaw.core.alignment.Alignment;
import ch.zhaw.core.query.*;
import ch.zhaw.core.query.queryBing.*;
import ch.zhaw.core.query.queryGoogle.QueryGoogle;
import ch.zhaw.core.query.queryGoogle.Results;
import ch.zhaw.core.query.queryOSM.*;
import ch.zhaw.core.query.queryOSM.Address;
import ch.zhaw.core.validation.Validation;

public class Struktur {
	
	private String rawAddress;
	private int personOrigId;
	private int personId;
	private String nameFreeform;
	private String addressOne;
	private String addressTwo;
	private Boolean allServices; //wird zum überprüfen genutzt ob alle services benutzt wurden
	
	
	public Struktur(int personOrigId, int personId, String nameFreeform, String addressOne, String addressTwo) {
		this.personOrigId = personOrigId;
		this.personId = personId;
		this.nameFreeform = nameFreeform;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
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

	public Boolean getAllServices() {
		return allServices;
	}

	public void setAllServices(Boolean allServices) {
		this.allServices = allServices;
	}

	public String createRawAddress(String addressOne, String addressTwo){
		String combinedString = addressOne + " " + addressTwo;
		return combinedString;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public String mainQuery(String rawAddress) {
		System.out.println(); //test-code
		System.out.println("Start mainQuery: "); //test-code
		System.out.println("rawAddress: " + rawAddress); //test-code
		
		Query m = new Query(getRawAddress()); //Query-klasse instanzieren und rawAddress setzen
		Alignment l = new Alignment(m.getExtAddress()); //Inittiere Alignment -> evt. nicht hier instanzieren
		
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
		
		//überprüft ob addresse i.O zum speichern ist und ob alle Services bereits überprüft wurden
		//Neue Positionierung von startAlignment wahrscheinlich nötig
		if(complete == true && allServices == true){
			System.out.println("allServices == true, neue Addresse wird abgefragt falls vorhanden "); //test-code
		}
		else{
			//Durchführung der Strukturierung der Adresse
			startAlignment(l, m.getOsm());
			System.out.println("StructAddress : " + l.getStructAdress()); //test-code
		}
		return m.getExtAddress();
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
					allServices = true;
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
			//Prüfung der neu gewonnenen Adresse starten
			QueryOSM obj = m.getOsm().get(0); // wird nur benötigt um code lesbarer zu machen -> import dafür benötigt
			List<Integer> newAddressTrue = obj.getNewAddressTrue();
			List<Double> listScore = obj.getListScore();
			List<Integer> listScoreTrue = obj.getListScoreTrue();
			
			//get mit jedem List<String> die validation durch 
			for(int i = 0; i < newAddressTrue.size(); i++){
				int index = newAddressTrue.get(i);
				Address addressObj = m.getOsm().get(index).getAddress();
				List<String> newAddress = addressObj.getListNewAddress(); // wird erstellt damit Code leserlicher ist
				
				Validation v = startValidation(m, newAddress); //validierung wird gestartet
				addressObj.setScore(v.getScore()); // Score bei adresse speichern falls es später gebraucht wird
				addressObj.setStatus(v.getVal());
				Double score = addressObj.getScore();
				Boolean statusAddress = addressObj.getStatus();
				
				//überprüft ob berechneter Score gut genut ist. 
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
					System.out.println("newAddressTrue ist nicht leer, defIndexBing:" + indexBest + " , Score: " + scoreBest); //test-code
					check = true; //setzt check auf false oder true nach ergebnis der validierung
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
							listScoreTrue.add(index); //List mit der Stelle wo listScoreBing den Score hat
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
						System.out.println("newAddressTrue ist nicht leer, defIndexGoogle:" + indexBest + " , Score: " + scoreBest); //test-code
						check = true; //setzt check auf false oder true nach ergebnis der validierung
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
				//scoreList.remove(x); 
				//checkList.remove(x);
			}
		}
		return index;
	}
	
	public void startAlignment(Alignment l, List<QueryOSM> osm){
		System.out.println(); //test-code
		System.out.println("start startAlignment()"); //test-code
		l.align(osm.get(0).getDisplay_name());
		//resultat wird abgespeichert
		
	}
	
	
}
