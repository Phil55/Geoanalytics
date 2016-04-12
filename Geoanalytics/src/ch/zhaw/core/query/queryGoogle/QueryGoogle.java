package ch.zhaw.core.query.queryGoogle;

import java.util.ArrayList;
import java.util.List;

//import ch.zhaw.core.query.queryBing.QueryBing;

public class QueryGoogle {
	
	private List<Results> results;
	private String status;
	// für QueryGoogle() benötigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind
	
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
	
}
