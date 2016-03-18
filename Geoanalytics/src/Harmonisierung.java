import java.util.ArrayList;

public class Harmonisierung {

	private String extAdress;
	private String adressName;
	private String adressNummer; //string wegen split
	private String adressOrt;
	private String adressPLZ; //string wegen split
	private String adressLand;
	private String CoordinateLat;
	private String CoordinateLong;
	private ArrayList <String> structAdress;
	
	
	public Harmonisierung(String extAdress) {
		this.extAdress = extAdress;
		align(extAdress);
	}
	public String getExtAdress() {
		return extAdress;
	}

	public void setExtAdress(String extAdress) {
		this.extAdress = extAdress;
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
		return CoordinateLat;
	}

	public void setCoordinateLat(String coordinateLat) {
		CoordinateLat = coordinateLat;
	}

	public String getCoordinateLong() {
		return CoordinateLong;
	}

	public void setCoordinateLong(String coordinateLong) {
		CoordinateLong = coordinateLong;
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
				System.out.println("AdressName: " + i); //test-code
				//structAdress.add(i);
			}
			else if (adressNummer == null){
				setAdressNummer(i);
				System.out.println("AdressNummer: " + i); //test-code
				//structAdress.add(i);
			}
			else if (adressOrt == null){
				setAdressOrt(i);
				System.out.println("AdressOrt: " + i); //test-code
				//structAdress.add(i);
			}
			else if (adressPLZ == null){
				setAdressPLZ(i);
				System.out.println("AdressPLZ: " + i); //test-code
				//structAdress.add(i);
			}
			else {
				System.out.println("else: " + i); //test-code
				//structAdress.add(i);
			}
		}
		//multiple Strings in a list
		String[] provList = { getAdressName(), getAdressNummer(), getAdressOrt(), getAdressPLZ()};
		for(int a = 0; a < provList.length; a++){
			//structAdress.add(provList[a]);
			System.out.println("testliste: " + a + provList[a]); //test-code
		}
		
		
		
		return structAdress;
	}
	
	
	
}
