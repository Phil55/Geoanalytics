
public class Abfrage {

	private String rawAddressAbfr;
	private int dienstID; //evt. Array
	private String extAddress; //evt. Array
	
	public Abfrage(String rawAddress) {
		//super();
		this.rawAddressAbfr = rawAddress;
		abfrage(rawAddress);
	}

	public String getRawAddressAbfr() {
		return rawAddressAbfr;
	}

	public void setRawAddressAbfr(String rawAddressAbfr) {
		this.rawAddressAbfr = rawAddressAbfr;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getExtAddress() {
		return extAddress;
	}

	public void setExtAddress(String extAddress) {
		this.extAddress = extAddress;
	}
	
	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	public String abfrage(String rawAddress) {
		System.out.println("Beginn abfrage: rawAddress " + rawAddress); //test-code
		if (rawAddress.contains("strasse") == true) {
			AbfrageDienst n = new AbfrageDienst(1);
			String newAddress = n.AbfServiceOSM(rawAddress);
			setExtAddress(newAddress);
			System.out.println("Ende abfrage: newaddress " + newAddress); //test-code
			return newAddress;
		}
		else if (rawAddress == "strasse 2") {
			//wird später erweitert
		}
		else {
			//Code ist im Moment Platzhalter, damit der ganze Prozess läuft
			AbfrageDienst n = new AbfrageDienst(1);
			String newAddress = n.AbfServiceOSM(rawAddress);
			setExtAddress(newAddress);
			System.out.println("Ende abfrage: newaddress " + newAddress); //test-code
			return newAddress;
		}
		
		return extAddress;
	}
	
	
	
	
	
	
	
	
	
	
}
