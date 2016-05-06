package ch.zhaw.core.query.queryGoogle;

public class Geometry {
	
	private Location location;
	private Viewport viewport;
	private Bounds bounds;
	private String location_type;
	
	//Konstruktor für Object Mapper
	public Geometry(){	
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public String getLocation_type() {
		return location_type;
	}

	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
}
