package ch.zhaw.core.query.queryBing;

import java.util.ArrayList;
import java.util.List;

public class ResourceSetsObj {
	
	private String estimatedTotal;
	private List<ResourcesObj> resources;
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); 
	private Boolean statusValidation = null; 
	private List <Double> listScore = new ArrayList<Double>(); 
	private List <Integer> listScoreTrue = new ArrayList<Integer>(); 
	private int defIndex;
	
	//Konstruktor für Object Mapper
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
