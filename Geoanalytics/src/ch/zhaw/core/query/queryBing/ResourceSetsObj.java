package ch.zhaw.core.query.queryBing;

import java.util.ArrayList;
import java.util.List;

public class ResourceSetsObj {
	
	private String estimatedTotal;
	private List<ResourcesObj> resources;
	// für QueryBing() benötigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind
	
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

	public List<Integer> getNewAddressTrue() {
		return newAddressTrue;
	}

	public void setNewAddressTrue(List<Integer> newAddressTrue) {
		this.newAddressTrue = newAddressTrue;
	}

}
