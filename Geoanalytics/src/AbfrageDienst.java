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
		extAdress = rawAdress + ",extern";
		return extAdress;
	}
	
}