import java.util.ArrayList;

public class Struktur {
	
	private String adress;
	private int PersonID; //nötig?
	
	public Struktur(String adress, int personID) {
		this.adress = adress;
		this.PersonID = personID;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getPersonID() {
		return PersonID;
	}

	public void setPersonID(int personID) {
		PersonID = personID;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public String startAbfrage(String adress) {
		System.out.println("BeginnstartAbfrage: " + adress); //test-code
		Abfrage m = new Abfrage(getAdress());
		
		//Prüfung der neu gewonnenen Adresse starten
		Validierung f = new Validierung(getAdress(), m.getExtAdress());
		if (f.getScore() >= 60){
			System.out.println("Score der Adresse ist gleich/grösser 60 : " + f.getScore()); //test-code
			
		}
		else {
			System.out.println("Score der Adresse ist kleiner 60 : " + f.getScore()); //test-code
			// evt. Andere Dienstleistung abfragen
		}
		
		//Durchführung der Strukturierung der Adresse
		Harmonisierung l = new Harmonisierung(m.getExtAdress());
		System.out.println("StructAdress : " + l.getStructAdress()); //test-code
		return m.getExtAdress();
	}
	
	
}
