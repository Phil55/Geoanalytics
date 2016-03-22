import java.util.ArrayList;

public class Harmonisierung {

	private String extAdressHarm;
	private String adressName;
	private String adressNummer; //string wegen split
	private String adressOrt;
	private String adressPLZ; //string wegen split
	private String adressLand;
	private String coordinateLat;
	private String coordinateLong;
	private ArrayList <String> structAdress;
	
	
	public Harmonisierung(String extAdress) {
		this.extAdressHarm = extAdress;
		this.structAdress = new ArrayList<String>();
		align(extAdress);
	}
	public String getExtAdressHarm() {
		return extAdressHarm;
	}

	public void setExtAdressHarm(String extAdressHarm) {
		this.extAdressHarm = extAdressHarm;
	}

	public String getAdressName() {
		return adressName;
	}

	public void setAdressName(String adressName) {
		this.adressName = adressName;
	}

	public String getAdressNummer() {
		return adressNummer;
	}

	public void setAdressNummer(String adressNummer) {
		this.adressNummer = adressNummer;
	}

	public String getAdressOrt() {
		return adressOrt;
	}

	public void setAdressOrt(String adressOrt) {
		this.adressOrt = adressOrt;
	}

	public String getAdressPLZ() {
		return adressPLZ;
	}

	public void setAdressPLZ(String adressPLZ) {
		this.adressPLZ = adressPLZ;
	}

	public String getAdressLand() {
		return adressLand;
	}

	public void setAdressLand(String adressLand) {
		this.adressLand = adressLand;
	}

	public String getCoordinateLat() {
		return coordinateLat;
	}

	public void setCoordinateLat(String coordinateLat) {
		this.coordinateLat = coordinateLat;
	}

	public String getCoordinateLong() {
		return coordinateLong;
	}

	public void setCoordinateLong(String coordinateLong) {
		this.coordinateLong = coordinateLong;
	}
	
	public ArrayList<String> getStructAdress() {
		return structAdress;
	}

	public void setStructAdress(ArrayList<String> structAdress) {
		this.structAdress = structAdress;
	}
	
	//strukturiert Adresse
	public ArrayList <String> align(String extAdress){
		
		String[] listextAdr = extAdress.split("\\,", 10);
		
		for (String i: listextAdr){
			if (adressName == null){
				setAdressName(i);
				structAdress.add(i);
			}
			else if (adressNummer == null){
				setAdressNummer(i);
				structAdress.add(i);
			}
			else if (adressOrt == null){
				setAdressOrt(i);
				structAdress.add(i);
			}
			else if (adressPLZ == null){
				setAdressPLZ(i);
				structAdress.add(i);
			}
			else if (adressLand == null){
				setAdressLand(i);
				structAdress.add(i);
			}
			else if (coordinateLat == null){
				setCoordinateLat(i);
				structAdress.add(i);
			}
			else if (coordinateLong == null){
				setCoordinateLong(i);
				structAdress.add(i);
			}
			else {
				structAdress.add(i);
			}
		}		
		
		return structAdress;
	}
	
	
	
}
