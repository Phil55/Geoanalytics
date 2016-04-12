package ch.zhaw.core.query.queryGoogle;

public class Viewport {
	
	private Northeast northeast;
	private Southwest southwest;
	
	//constructor for objectmapper
	public Viewport(){	
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
