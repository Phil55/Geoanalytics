public class AbfrageDienst {

	private int dienstID; //ID soll Aufschluss geben, welche Dienstleistung in anspruch genommen wird
	private String extAddressDienst;
	
	public AbfrageDienst(int dienstID) {
		this.dienstID = dienstID;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getExtAddressDienst() {
		return extAddressDienst;
	}

	public void setExtAddressDienst(String extAddressDienst) {
		this.extAddressDienst = extAddressDienst;
	}
	
	//DL/Service wird abgefragt und man erhält eine "externe Adresse"
	public String AbfServiceOSM (String rawAddress) {
		extAddressDienst = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
		return extAddressDienst;
	}
	
}