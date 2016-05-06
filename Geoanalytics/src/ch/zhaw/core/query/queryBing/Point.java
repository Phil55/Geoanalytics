package ch.zhaw.core.query.queryBing;

import java.util.List;

public class Point {
	
	private String type;
	private List<String> coordinates;
	
	//Konstruktor für Object Mapper
	public Point(){	
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}

}
