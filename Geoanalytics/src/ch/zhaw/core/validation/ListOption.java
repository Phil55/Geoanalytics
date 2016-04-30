package ch.zhaw.core.validation;
import java.util.ArrayList;
import java.util.List;

public class ListOption {

	private List<Option> address_component;

	public ListOption() {
		this.address_component = new ArrayList<Option>();
	}

	public List<Option> getAddress_component() {
		return address_component;
	}

	public void setAddress_component(List<Option> address_component) {
		this.address_component = address_component;
	}
	
}
