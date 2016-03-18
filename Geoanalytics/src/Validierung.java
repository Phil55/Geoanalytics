
public class Validierung {

	private int score;
	private String rawAdress;
	private String extAdress;
	
	public Validierung(String rawAdress, String extAdress) {
		this.rawAdress = rawAdress;
		this.extAdress = extAdress;
		pruefen(rawAdress, extAdress);
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
