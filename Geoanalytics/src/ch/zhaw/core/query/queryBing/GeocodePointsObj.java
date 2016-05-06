package ch.zhaw.core.query.queryBing;

import java.util.List;

public class GeocodePointsObj {
	
	private String type;
	private String calculationMethod;
	private List<String> coordinates;
	private List<String> usageTypes;
	
	//Konstruktor für Object Mapper
	public GeocodePointsObj(){	
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public List<String> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}

	public List<String> getUsageTypes() {
		return usageTypes;
	}

	public void setUsageTypes(List<String> usageTypes) {
		this.usageTypes = usageTypes;
	}
	
}
