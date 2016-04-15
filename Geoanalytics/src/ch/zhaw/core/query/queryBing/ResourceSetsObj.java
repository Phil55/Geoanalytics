package ch.zhaw.core.query.queryBing;

import java.util.ArrayList;
import java.util.List;

public class ResourceSetsObj {
	
	private String estimatedTotal;
	private List<ResourcesObj> resources;
	// für QueryBing() benötigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind
	private Boolean statusValidation = null; //für Validation benötigt
	private Boolean statusQuery = null; //für Query benötigt, auf null gesetzt damit Bedingung simpler abzufragen ist
	private List <Double> listScore = new ArrayList<Double>(); //für Validation benötigt
	private List <Integer> listScoreTrue = new ArrayList<Integer>(); //für Validation benötigt
	private int defIndex; //für Validation benötigt, verweist auf die Stelle, wo die Adresse mit dem besten Score ist
	
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

	public Boolean getStatusValidation() {
		return statusValidation;
	}

	public void setStatusValidation(Boolean statusValidation) {
		this.statusValidation = statusValidation;
	}

	public Boolean getStatusQuery() {
		return statusQuery;
	}

	public void setStatusQuery(Boolean statusQuery) {
		this.statusQuery = statusQuery;
	}

	public List<Double> getListScore() {
		return listScore;
	}

	public void setListScore(List<Double> listScore) {
		this.listScore = listScore;
	}

	public List<Integer> getListScoreTrue() {
		return listScoreTrue;
	}

	public void setListScoreTrue(List<Integer> listScoreTrue) {
		this.listScoreTrue = listScoreTrue;
	}

	public int getDefIndex() {
		return defIndex;
	}

	public void setDefIndex(int defIndex) {
		this.defIndex = defIndex;
	}

}
