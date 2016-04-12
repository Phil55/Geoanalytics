package ch.zhaw.core.query.queryBing;

import java.util.ArrayList;
import java.util.List;

public class QueryBing { //Literaturverweis finden um zu begründen wieso Package anstatt innere Klassen
	
	private String authenticationResultCode;
	private String brandLogoUri;
	private String copyright;
	private String statusCode;
	private String statusDescription;
	private String traceId;
	private List<ResourceSetsObj> resourceSets;
	// für QueryGoogle() benötigt
	private List<Integer> newAddressTrue = new ArrayList<Integer>(); //Listet die Stelle der Adressen auf, die vollständig sind
	
	//test dummy constructor for objectmapper
	public QueryBing(){	
	}

	public String getAuthenticationResultCode() {
		return authenticationResultCode;
	}

	public void setAuthenticationResultCode(String authenticationResultCode) {
		this.authenticationResultCode = authenticationResultCode;
	}

	public String getBrandLogoUri() {
		return brandLogoUri;
	}

	public void setBrandLogoUri(String brandLogoUri) {
		this.brandLogoUri = brandLogoUri;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public List<ResourceSetsObj> getResourceSets() {
		return resourceSets;
	}

	public void setResourceSets(List<ResourceSetsObj> resourceSets) {
		this.resourceSets = resourceSets;
	}
	
	public List<Integer> getNewAddressTrue() {
		return newAddressTrue;
	}

	public void setNewAddressTrue(List<Integer> newAddressTrue) {
		this.newAddressTrue = newAddressTrue;
	}


}
