package ch.zhaw.core.validation;

public class Option {
	
	private String component;
	private int countTrue; // f�r Validation ben�tigt
	private int countFalse; // f�r Validation ben�tigt
	private int provScore; // f�r Validation ben�tigt
	
	public Option(){
		this.countTrue = 0;
		this.countFalse = 0;
		this.provScore = 0;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public int getCountTrue() {
		return countTrue;
	}

	public void setCountTrue(int countTrue) {
		this.countTrue = countTrue;
	}

	public int getCountFalse() {
		return countFalse;
	}

	public void setCountFalse(int countFalse) {
		this.countFalse = countFalse;
	}

	public int getProvScore() {
		return provScore;
	}

	public void setProvScore(int provScore) {
		this.provScore = provScore;
	}

}
