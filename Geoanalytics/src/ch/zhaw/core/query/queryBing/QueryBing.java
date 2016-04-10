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
	List<String> listNewAddressBing = new ArrayList<String>(); // Liste erstellen um bei der Validierung strukturiert vorzugehen
	
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
	
	public List<String> getListNewAddressBing() {
		return listNewAddressBing;
	}

	public void setListNewAddressBing(List<String> listNewAddressBing) {
		this.listNewAddressBing = listNewAddressBing;
	}

	public void createListNewAddressBing (QueryBing bing){
		
		String addressLine = bing.getResourceSets().get(0).getResources().get(0).getAddress().getAddressLine();
		String plz = bing.getResourceSets().get(0).getResources().get(0).getAddress().getPostalCode();
		String locality = bing.getResourceSets().get(0).getResources().get(0).getAddress().getLocality();
		String district = bing.getResourceSets().get(0).getResources().get(0).getAddress().getAdminDistrict(); //evt. nicht nötig
		String district2 = bing.getResourceSets().get(0).getResources().get(0).getAddress().getAdminDistrict2(); //evt. nicht nötig
		System.out.println("addressLine: " + addressLine);
		System.out.println("plz: " + plz);
		System.out.println("locality: " + locality);
		System.out.println("district: " + district);
		System.out.println("district2: " + district2);
		
		// listNewAddress muss pro Service gleich strukturiert sein!! -> road, nr, plz, ort
		// Struktur kann noch ergänzt bzw. verändert werden
		
		//road
		listNewAddressBing.add(addressLine);
		System.out.println("listNewAddress.add(addressLine) = " + addressLine);
		/*
		//nr
		listNewAddressOSM.add(addressNumber);
		System.out.println("listNewAddress.add(addressNumber) = " + addressNumber);
		*/
		//plz
		listNewAddressBing.add(plz);
		System.out.println("listNewAddress.add(plz) = " + plz);
		//Ort
		if (locality != null){
			listNewAddressBing.add(locality);
			System.out.println("listNewAddress.add(locality) = " + locality);
		}
		
		//evt. nicht nötig
		/*
		if (district != null){
			listNewAddressBing.add(district);
			System.out.println("listNewAddress.add(district) = " + district);
		}
		if (district2 != null){
			listNewAddressBing.add(district2);
			System.out.println("listNewAddress.add(district2) = " + district2);
		}
		*/
	}

}
