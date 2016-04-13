package ch.zhaw.core.query.queryGoogle;

import java.util.ArrayList;
import java.util.List;

//import ch.zhaw.core.query.queryBing.QueryBing;

public class QueryGoogle {
	
	private List<Results> results;
	private String status;
	// f�r QueryGoogle() ben�tigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollst�ndig sind
	private Boolean statusValidation = null; //f�r Validation ben�tigt
	private Boolean statusQuery = null; //f�r Query ben�tigt, auf null gesetzt damit Bedingung simpler abzufragen ist
	private List <Integer> listScore = new ArrayList<Integer>(); //f�r Validation ben�tigt
	private List <Integer> listScoreTrue = new ArrayList<Integer>(); //f�r Validation ben�tigt
	private int defIndex; //f�r Validation ben�tigt, verweist auf die Stelle, wo die Adresse mit dem besten Score ist
	
	//constructor for objectmapper
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

	public Boolean getStatusQuery() {
		return statusQuery;
	}

	public void setStatusQuery(Boolean statusQuery) {
		this.statusQuery = statusQuery;
	}

	public List<Integer> getListScore() {
		return listScore;
	}

	public void setListScore(List<Integer> listScore) {
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
