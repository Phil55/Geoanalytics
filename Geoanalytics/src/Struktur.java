import java.util.ArrayList;

public class Struktur {

	private String adress;
	private int PersonID;
	private ArrayList<Coordinates> coordinate;
	
	
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
	
	public String getCoordinates() {
		
		
		return Coordinates;
	}
	
	
	
	
}
