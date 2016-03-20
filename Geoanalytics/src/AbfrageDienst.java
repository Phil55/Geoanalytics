public class AbfrageDienst {

	private int dienstID;
	private String rawAdress;
	private String extAdress;
	
	public AbfrageDienst(int dienstID) {
		this.dienstID = dienstID;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getRawAdress() {
		return rawAdress;
	}

	public void setRawAdress(String rawAdress) {
		this.rawAdress = rawAdress;
	}

	public String getExtAdress() {
		return extAdress;
	}

	public void setExtAdress(String extAdress) {
		this.extAdress = extAdress;
	}
	
	//DL/Service wird abgefragt und man erhält eine "externe Adresse"
	public String AbfServiceOSM (String rawAdress) {
		extAdress = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
		return extAdress;
	}
	
}