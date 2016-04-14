package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

public class ListOption {

	//private List<String> listOldOption = new ArrayList<String>();
	//private Option option;
	private List<Option> address_component;
	/*
	private int countTrue; // für Validation benötigt
	private int countFalse; // für Validation benötigt
	private int provScore; // für Validation benötigt
	*/

	public ListOption() {
		//this.option = new Option();
		this.address_component = new ArrayList<Option>();
		/*
		this.countTrue = 0;
		this.countFalse = 0;
		this.provScore = 0;
		*/
	}

	/*
	public List<String> getListOldOption() {
		return listOldOption;
	}

	public void setListOldOption(List<String> listOldOption) {
		this.listOldOption = listOldOption;
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
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
	*/

	public List<Option> getAddress_component() {
		return address_component;
	}

	public void setAddress_component(List<Option> address_component) {
		this.address_component = address_component;
	}
	
}
