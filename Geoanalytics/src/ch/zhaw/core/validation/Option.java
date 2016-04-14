package ch.zhaw.core.validation;

public class Option {
	
	private String component;
	private int countTrue; // für Validation benötigt
	private int countFalse; // für Validation benötigt
	private int provScore; // für Validation benötigt
	
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
