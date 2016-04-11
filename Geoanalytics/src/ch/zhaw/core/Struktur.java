package ch.zhaw.core;

import java.util.List;
import ch.zhaw.core.alignment.Alignment;
import ch.zhaw.core.query.*;
import ch.zhaw.core.query.queryOSM.QueryOSM;
import ch.zhaw.core.validation.Validation;

public class Struktur {
	
	private String rawAddress;
	private int personOrigId;
	private int personId;
	private String nameFreeform;
	private String addressOne;
	private String addressTwo;
	private int scoreOSM; //für Validation benötigt
	private int scoreBing; //für Validation benötigt
	private Boolean valOSM; //für Validation benötigt
	private Boolean valBing; //für Validation benötigt
	private Boolean allServices; //wird zum überprüfen genutzt ob alle services benutzt wurden
	
	
	public Struktur(int personOrigId, int personId, String nameFreeform, String addressOne, String addressTwo) {
		this.personOrigId = personOrigId;
		this.personId = personId;
		this.nameFreeform = nameFreeform;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
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
	
	public int getScoreOSM() {
		return scoreOSM;
	}

	public void setScoreOSM(int scoreOSM) {
		this.scoreOSM = scoreOSM;
	}

	public int getScoreBing() {
		return scoreBing;
	}

	public void setScoreBing(int scoreBing) {
		this.scoreBing = scoreBing;
	}
	
	public Boolean getValOSM() {
		return valOSM;
	}

	public void setValOSM(Boolean valOSM) {
		this.valOSM = valOSM;
	}

	public Boolean getValBing() {
		return valBing;
	}

	public void setValBing(Boolean valBing) {
		this.valBing = valBing;
	}

	public String createRawAddress(String addressOne, String addressTwo){
		String combinedString = addressOne + " " + addressTwo;
		return combinedString;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public String mainQuery(String rawAddress) {
		System.out.println("BeginnstartAbfrage: " + rawAddress); //test-code
		
		Query m = new Query(getRawAddress()); //Query-klasse instanzieren und rawAddress setzen
		Validation v = new Validation(getRawAddress(), m.getExtAddress(), m.getOsm()); //Initiiere Validation
		Alignment l = new Alignment(m.getExtAddress()); //Inittiere Alignment
		
		//Schlaufe die überprüft, ob abfrage vollständig und valide ist. 
		//solange Abfrage nicht true ist wird Methode wiederholt bis keine Services mehr vorhanden sind
		Boolean complete = false; // default false sonst geht while nicht
		while(complete != true){
			complete = testCheck(v, m);
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
		System.out.println("Start checkUsedQueries"); //test-code
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
	public Boolean testCheck (Validation v, Query m){
		
		Boolean check = false;
		String checkedQuery = null; // String für überprüfung benötigt welche Query gemacht wurde
		
		if(checkUsedQueries(m.getStatusList()) == false){
			System.out.println("noch nicht alle Services abgefragt. starte neue abfrage"); //test-code
			checkedQuery = m.query(); // Abfrage-Methode iniziieren
			System.out.println("überprüfung beendet für Abfrage : " + checkedQuery); //test-code
			
			if(checkedQuery == "osm"){
				System.out.println("m.getStatusOSM() : " + m.getStatusOSM()); //test-code
				//bei true wird die Abfrage gestartet, bei false wird Methode wiederholt 
				if(m.getStatusOSM() == true){
					//Prüfung der neu gewonnenen Adresse starten
					setValOSM(startValidation(v, m, m.getOsm(), m.getOsm().get(0).getListNewAddressOSM())); //validierung wird gestartet und ergebins false/true bei valOSM gespeichert
					setScoreOSM(v.getScore()); //Score ergebnis wird bei scoreOSM gespeichert -> nicht sicher ob das benötigt wird
					System.out.println("Validation OSM :" + valOSM + " , Score: " + scoreOSM); //test-code
					check = valOSM; //setzt check auf false oder true nach ergebnis der validierung
				}
				else{
					//testCheck wiederholen (schlaufe)
					check = false;
				}
			}
			else{
				//bei true wird die Abfrage gestartet, bei false wird Methode wiederholt 
				if(checkedQuery == "bing"){
					if(m.getStatusBing() == true){
						//Prüfung der neu gewonnenen Adresse starten
						setValBing(startValidation(v, m, m.getBing(), m.getBing().getListNewAddressBing())); //validierung wird gestartet und ergebins false/true bei valBing gespeichert
						setScoreBing(v.getScore()); //Score ergebnis wird bei scoreBing gespeichert -> nicht sicher ob das benötigt wird
						System.out.println("Validation Bing :" + valBing + " , Score: " + scoreBing); //test-code
						check = valBing; //setzt check auf false oder true nach ergebnis der validierung
					}
					else{
						//testCheck wiederholen (schlaufe)
						check = false;
					}
				}
				else{
					//anderer Service aufrufen
					System.out.println("Struktur: anderer Service aufrufen -> Prozess sollte nicht bis hierher kommen "); //test-code
					check = true;
					allServices = true;
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
	
	public Boolean startValidation(Validation v, Query m, Object obj, List<String> listNewAddress){
		
		v.validate(m.getRawAddress(), obj, listNewAddress);
		
		Boolean statusValidation = null;
	
		if (v.getScore() >= 60){
			System.out.println("Score der Adresse ist gleich/grösser 60 : " + v.getScore()); //test-code
			statusValidation = true;
		}
		else {
			System.out.println("Score der Adresse ist kleiner 60 : " + v.getScore()); //test-code
			statusValidation = false;
		}
		
		return statusValidation;
	}
	
	public void startAlignment(Alignment l, List<QueryOSM> osm){
		l.align(osm.get(0).getDisplay_name());
		//resultat wird abgespeichert
		
	}
	
	
}
