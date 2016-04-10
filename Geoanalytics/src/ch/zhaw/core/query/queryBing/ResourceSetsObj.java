package ch.zhaw.core.query.queryBing;

import java.util.List;

public class ResourceSetsObj {
	
	private String estimatedTotal;
	private List<ResourcesObj> resources;
	
	//test dummy constructor for objectmapper
	public ResourceSetsObj(){	
	}

	public String getEstimatedTotal() {
		return estimatedTotal;
	}

	public void setEstimatedTotal(String estimatedTotal) {
		this.estimatedTotal = estimatedTotal;
	}

	public List<ResourcesObj> getResources() {
		return resources;
	}

	public void setResources(List<ResourcesObj> resources) {
		this.resources = resources;
	}

}
