package ch.zhaw.core.query.queryGoogle;

public class Bounds {

	private Northeast northeast;
	private Southwest southwest;
	
	//Konstruktor für Object Mapper
	public Bounds(){	
	}

	public Northeast getNortheast() {
		return northeast;
	}

	public void setNortheast(Northeast northeast) {
		this.northeast = northeast;
	}

	public Southwest getSouthwest() {
		return southwest;
	}

	public void setSouthwest(Southwest southwest) {
		this.southwest = southwest;
	}
	
}
