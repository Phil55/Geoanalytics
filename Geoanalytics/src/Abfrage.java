
public class Abfrage {

	private String rawAdress;
	private String structAdress;
	private int dienstID; //evt. Array
	private String extAdress; //evt. Array
	
	public Abfrage(String rawAdress) {
		//super();
		this.rawAdress = rawAdress;
		abfrage(rawAdress);
	}

	public String getRawAdress() {
		return rawAdress;
	}

	public void setRawAdress(String rawAdress) {
		this.rawAdress = rawAdress;
	}

	public String getStructAdress() {
		return structAdress;
	}

	public void setStructAdress(String structAdress) {
		this.structAdress = structAdress;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getExtAdress() {
		return extAdress;
	}

	public void setExtAdress(String extAdress) {
		this.extAdress = extAdress;
	}
	
	//je nach Gegebenheit der Adresse werden jeweils andere Services angefragt
	//Reihenfolge und Bedingungen können sich noch ändern
	public String abfrage(String rawAdress) {
		System.out.println("Beginn abfrage: rawAdress " + rawAdress); //test-code
		if (rawAdress.contains("strasse") == true) {
			AbfrageDienst n = new AbfrageDienst(1);
			String newAdress = n.AbfServiceOSM(rawAdress);
			setExtAdress(newAdress);
			System.out.println("Ende abfrage: newadress " + newAdress); //test-code
			return newAdress;
		}
		else if (rawAdress == "strasse 2") {
			//wird später erweitert
		}
		else {
			//wird später erweitert
		}
		
		return extAdress;
	}
	
	
	
	
	
	
	
	
	
	
}
