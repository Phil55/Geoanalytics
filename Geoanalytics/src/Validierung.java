
public class Validierung {

	private int score;
	private String rawAdressVal;
	private String extAdressVal;
	
	public Validierung(String rawAdress, String extAdress) {
		this.rawAdressVal = rawAdress;
		this.extAdressVal = extAdress;
		pruefen(rawAdress, extAdress);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getRawAdressVal() {
		return rawAdressVal;
	}

	public void setRawAdressVal(String rawAdressVal) {
		this.rawAdressVal = rawAdressVal;
	}

	public String getExtAdressVal() {
		return extAdressVal;
	}

	public void setExtAdressVal(String extAdressVal) {
		this.extAdressVal = extAdressVal;
	}
	
	public int pruefen(String rawAdress, String extAdress){
		if (rawAdress.equals(extAdress) == true){
			setScore(100);
		}
		if (rawAdress.equalsIgnoreCase(extAdress) == true){
			setScore(75);
		}
		else {
			setScore(30);
		}
		
		System.out.println("Ende pruefen: score " + score); //test-code
		return score;
	}
	
}
