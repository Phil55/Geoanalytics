package ch.zhaw.core;
import java.util.ArrayList;
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
	
	public String createRawAddress(String addressOne, String addressTwo){
		String combinedString = addressOne + " " + addressTwo;
		return combinedString;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public String startQuery(String rawAddress) {
		System.out.println("BeginnstartAbfrage: " + rawAddress); //test-code
		
		Query m = new Query(getRawAddress()); //Query-klasse instanzieren und rawAddress setzen
		Validation f = new Validation(getRawAddress(), m.getExtAddress(), m.getOsm()); //Initiiere Validation
		Alignment l = new Alignment(m.getExtAddress()); //Inittiere Alignment
		
		//überprüfen welche Services bereits genutzt wurden
		//bei false wird die Abfrage gestartet, bei true 
		if(checkUsedQueries(m.getStatusList()) == false){
			System.out.println("noch nicht alle Services abgefragt. starte neue abfrage"); //test-code
			m.query(); // Abfrage-Methode iniziieren
		}
		else{
			System.out.println("alle Services abgefragt. starte neue abfrage"); //test-code
		}
		
		//Prüfung der neu gewonnenen Adresse starten
		startValidation(f, m);
		
		
		//Durchführung der Strukturierung der Adresse
		startAlignment(l, m.getOsm());
		System.out.println("StructAddress : " + l.getStructAdress()); //test-code
		return m.getExtAddress();
	}
	
	//überprüfen welche Services bereits genutzt wurden
	//bei true wurden alle Services bereits abgefragt, bei false noch nicht
	public Boolean checkUsedQueries(List<Boolean> statusList){
		System.out.println("Start checkUsedQueries"); //test-code
		Boolean check = null;
		
		for(int i = 0; i < statusList.size(); i++){ // bedingung einbauen falls statusList leer ist
			if(statusList.get(i) == null){
				check = false;
				System.out.println("status == null bei i : " + i); //test-code
				return check;
			}
			else{
				check = true;
				System.out.println("status != null bei i : " + i); //test-code
			}
		}
		return check;
	}
	
	public Boolean startValidation(Validation f, Query m){
		
		f.pruefen(m.getRawAddress(), m.getExtAddress(), m.getOsm());
		
		Boolean statusValidation = null;
	
		if (f.getScore() >= 60){
			System.out.println("Score der Adresse ist gleich/grösser 60 : " + f.getScore()); //test-code
			statusValidation = true;
		}
		else {
			System.out.println("Score der Adresse ist kleiner 60 : " + f.getScore()); //test-code
			statusValidation = false;
		}
		
		return statusValidation;
	}
	
	public void startAlignment(Alignment l, List<QueryOSM> osm){
		l.align(osm.get(0).getDisplay_name());
		//resultat wird abgespeichert
		
	}
	
	
}
