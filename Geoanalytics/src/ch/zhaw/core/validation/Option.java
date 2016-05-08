package ch.zhaw.core.validation;

public class Option {
	
	private String component;
	private double countTrue; // f�r Validation ben�tigt, wegen n-gram muss es double sein ->wird nie gebraucht evt l�schen
	private double countFalse; // f�r Validation ben�tigt, wegen n-gram muss es double sein ->wird nie gebraucht evt l�schen
	private double provScore; // f�r Validation ben�tigt, wegen n-gram muss es double sein
	
	
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

	public double getCountTrue() {
		return countTrue;
	}

	public void setCountTrue(double countTrue) {
		this.countTrue = countTrue;
	}

	public double getCountFalse() {
		return countFalse;
	}

	public void setCountFalse(double countFalse) {
		this.countFalse = countFalse;
	}

	public double getProvScore() {
		return provScore;
	}

	public void setProvScore(double provScore) {
		this.provScore = provScore;
	}

}
