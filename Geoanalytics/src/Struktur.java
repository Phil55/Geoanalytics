import java.util.ArrayList;

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
		String combinedString = addressOne + ", " + addressTwo;
		return combinedString;
	}
	
	//die Abfrage nach einer Adresse wird gestartet
	public String startAbfrage(String rawAddress) {
		System.out.println("BeginnstartAbfrage: " + rawAddress); //test-code
		
		Abfrage m = new Abfrage(getRawAddress());
		
		//Prüfung der neu gewonnenen Adresse starten
		Validierung f = new Validierung(getRawAddress(), m.getExtAddress(), m.getOsm());
		if (f.getScore() >= 60){
			System.out.println("Score der Adresse ist gleich/grösser 60 : " + f.getScore()); //test-code
			
		}
		else {
			System.out.println("Score der Adresse ist kleiner 60 : " + f.getScore()); //test-code
			// evt. Andere Dienstleistung abfragen
		}
		
		//Durchführung der Strukturierung der Adresse
		Harmonisierung l = new Harmonisierung(m.getExtAddress());
		System.out.println("StructAddress : " + l.getStructAdress()); //test-code
		return m.getExtAddress();
	}
	
	
}
