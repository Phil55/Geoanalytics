package ch.zhaw.core.query.queryGoogle;

import java.util.List;

public class AddressComponents {
	
	private String long_name;
	private String short_name;
	private List<String> types;
	
	//Konstruktor f�r Object Mapper
	public AddressComponents(){	
	}

	public String getLong_name() {
		return long_name;
	}

	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
}
