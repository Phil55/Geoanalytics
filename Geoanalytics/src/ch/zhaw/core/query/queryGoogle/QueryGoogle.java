package ch.zhaw.core.query.queryGoogle;

import java.util.ArrayList;
import java.util.List;

public class QueryGoogle {
	
	private List<Results> results;
	private String status;
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); 
	private Boolean statusValidation = null; 
	private List <Double> listScore = new ArrayList<Double>(); 
	private List <Integer> listScoreTrue = new ArrayList<Integer>();
	private int defIndex; 
	
	//Konstruktor für Object Mapper
	public QueryGoogle(){	
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
