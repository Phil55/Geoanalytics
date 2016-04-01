
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream; //für inputstream
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


//import für Unirest
import org.json.*;
import org.apache.http.nio.client.*;
import org.apache.http.client.*;
import org.apache.http.entity.mime.*;
import com.mashape.unirest.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.logging.LogFactory;


//Import für Jackson
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.*;

import java.util.ArrayList;

import java.util.List;

public class AbfrageOSM {

	//private final String USER_AGENT = "Mozilla/5.0"; //für GET und POST
	
	private String place_id;
	private String licence;
	private String osm_type;
	private String osm_id;
	private List<String> boundingbox;
	private List<List<String>> polygonpoints;
	private String lat;
	private String lon;
	private String display_name;
	private String classe; //JSON request "class"
	private String type;
	private String importance;
	private Address address;

	
	public AbfrageOSM(String place_id, String licence, String osm_type, String osm_id, List<String> boundingbox,
			List<List<String>> polygonpoints, String lat, String lon, String display_name, String classe, String type,
			String importance, Address address) {
		super();
		this.place_id = place_id;
		this.licence = licence;
		this.osm_type = osm_type;
		this.osm_id = osm_id;
		this.boundingbox = boundingbox;
		this.polygonpoints = polygonpoints;
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
		this.classe = classe;
		this.type = type;
		this.importance = importance;
		this.address = address;
	}
	
	//test dummy constructor for objectmapper
	public AbfrageOSM(){	
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getOsm_type() {
		return osm_type;
	}

	public void setOsm_type(String osm_type) {
		this.osm_type = osm_type;
	}

	public String getOsm_id() {
		return osm_id;
	}

	public void setOsm_id(String osm_id) {
		this.osm_id = osm_id;
	}

	public List<String> getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(List<String> boundingbox) {
		this.boundingbox = boundingbox;
	}

	public List<List<String>> getPolygonpoints() {
		return polygonpoints;
	}

	public void setPolygonpoints(List<List<String>> polygonpoints) {
		this.polygonpoints = polygonpoints;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	//DL/Service wird abgefragt und man erhält eine "externe Adresse"
	//public String AbfServiceOSM (String rawAddress) {
		
		/*
		//Serialization
		Unirest.setObjectMapper(new ObjectMapper() {
		    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
		                = new com.fasterxml.jackson.databind.ObjectMapper();

		    public <T> T readValue(String value, Class<T> valueType) {
		        try {
		            return jacksonObjectMapper.readValue(value, valueType);
		        } catch (Exception e) {
		            throw new RuntimeException(e);
		        }
		    }

		    public String writeValue(Object value) {
		        try {
		            return jacksonObjectMapper.writeValueAsString(value);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException(e);
		        }
		    }
		});
		*/
		
		//try {
			/*	
			//URL url = new URL("http://open.mapquestapi.com/geocoding/v1/address?key=V7QcX4ax6960SWrbde2ssAaAd6A92zMm&callback=renderOptions&inFormat=kvp&outFormat=json&location=" + rawAddress);
			//URL url = new URL("http://open.mapquestapi.com/geocoding/v1/address?key=V7QcX4ax6960SWrbde2ssAaAd6A92zMm&outFormat=json&location=" + rawAddress);
			//URL url = new URL("http://nominatim.openstreetmap.org/search/135%20pilkington%20avenue,%20birmingham?format=json&polygon=1&addressdetails=1");
			URL url = new URL("http://nominatim.openstreetmap.org/search?q=Bachstrasse+39,+8912+Obfelden&format=json&polygon=1&addressdetails=1");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				System.out.println("ServiceabfrageOSM: " + strTemp);
			
			JSONArray array = new JSONArray(strTemp);
			JSONObject jsonobj = new JSONObject(array.getJSONObject(0));
			String [] display = new String[array.length()];
			for(int i = 0; i < array.length(); i++){
				display[i] = jsonobj.getString("display_name");
				System.out.println("display: " + display);
			}
			String lat = jsonobj.getString("lat");
			System.out.println("lat: " + lat);
			
			//JSON in Java Objekt
			ObjectMapper mapper = new ObjectMapper();
			AbfrageOSM osm = mapper.readValue(url, AbfrageOSM.class);
			System.out.println("JavaObjekt Abfragedienst n: ");
			}
			*/
			//URL url = new URL("http://nominatim.openstreetmap.org/search?q=Bachstrasse+39,+8912+Obfelden&format=json&polygon=1&addressdetails=1");
			//ObjectMapper mapper = new ObjectMapper();
			//Data osm = mapper.readValue(url, Data.class);
/*			
			HttpResponse<JsonNode> response = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
				routeParam("address", "Bachstrasse+39,+8912+Obfelden").
				//header("accept", "application/json").
				//queryString("display_name", "display_name").
				asJson();
			response = Unirest.get("http://nominatim.openstreetmap.org/search?q=Bachstrasse+39,+8912+Obfelden&format=json&polygon=1&addressdetails=1").asJson();
			String body = response.getBody().toString();
			System.out.println("response: " + response);
			System.out.println("response_status: " + response.getStatusText());
			System.out.println("bodyReal: " + body);
			//String class in classe umbenennen
			String newBody = body.replaceAll("class", "classe");
			System.out.println("bodyNew: " + newBody);
			
			ObjectMapper mapper = new ObjectMapper();
			//List<AbfrageOSM> osm = mapper.readValue(body, ArrayList.class);
			//AbfrageOSM [] osm = mapper.readValue(newBody, AbfrageOSM[].class);
			List<AbfrageOSM> osm = mapper.readValue(newBody, new TypeReference<List<AbfrageOSM>>(){});
			//System.out.println("JavaObjekt Abfragedienst n: " + mapper.readValue(newBody, AbfrageOSM[].class)) ;
			System.out.println("display: " + osm.get(0).getDisplay_name());
*/			
			
			/*
			JsonFactory jsonf = new JsonFactory();
			List<AbfrageOSM> abfrage = null;
			JsonParser jp = jsonf.createJsonParser(newBody);
			TypeReference<List<AbfrageOSM>> tRef = new TypeReference<List<AbfrageOSM>>(){};
			abfrage = mapper.readValue(jp, tRef);
			for (AbfrageOSM p : abfrage){
				System.out.println("test: " + p.toString()) ;
			}
			*/
			
			//String display = mapper.writeValueAsString(osm);
			//System.out.println("display: " + display);
			
			//JSONObject jsonnode = response.getBody().getObject().getJSONObject("address");
			//JSONObject jsonnode = response.getBody().getArray().getJSONObject(0).getJSONObject("address");
			
			//String licence = jsonnode.getString("licence");
			//JSONArray jsonArray = jsonnode.getJSONArray("boundingbox");
			//String[] name = new String[jsonArray.length()];
			
			//JSONArray array = jsonnode.getArray();
			//System.out.println("jsonString: " + jsonnode);
			//System.out.println("displayName: " + type);
			//System.out.println("licence: " + licence);	
			//System.out.println("jsonarrayname: " + name);
			
			//ObjectMapper mapper = new ObjectMapper();
			//Unirest.setObjectMapper(mapper);
			
			/*
			Unirest.setObjectMapper(new ObjectMapper() {
			    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
			                = new com.fasterxml.jackson.databind.ObjectMapper();

			    public <T> T readValue(String value, Class<T> valueType) {
			        try {
			            return jacksonObjectMapper.readValue(value, valueType);
			        } catch (IOException e) {
			            throw new RuntimeException(e);
			        }
			    }

			    public String writeValue(Object value) {
			        try {
			            return jacksonObjectMapper.writeValueAsString(value);
			        } catch (JsonProcessingException e) {
			            throw new RuntimeException(e);
			        }
			    }
			});
			
			
			HttpResponse<Data> result = Unirest.get("http://nominatim.openstreetmap.org/search?q={address}&format=json&polygon=1&addressdetails=1").
					routeParam("address", "Bachstrasse+39,+8912+Obfelden").
					asObject(Data.class);
			
			Data dataObject = result.getBody();
			*/
			
			/*
			JSONArray mainNode = response.getBody().getArray() ;
			System.out.println("mainNode: " + mainNode);
			
			JSONObject jsonObject = response.getBody().getObject();
			AbfrageOSM p = new AbfrageOSM(2);
			p.display_name = jsonObject.getString("display_name");
			System.out.println("display name: " + p.display_name);
			System.out.println("jsonObject: " + jsonObject);
			p.display_name = jsonObject.toString();
			
			for (int i = -1; i < mainNode.length(); i++) { //i = -1, weil die länge des Arrays bei 0 anfängt
				JSONObject jsonObject = response.getBody().getObject();
				AbfrageOSM p = new AbfrageOSM(2);
				p.display_name = jsonObject.getString("display_name");
				System.out.println("display name: " + p.display_name);
				System.out.println("jsonObject: " + jsonObject);
			}
			*/
			
/*			
			System.out.println("response_status2: " + response.getStatusText());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
				
		String extAddressDienst = "";	
		extAddressDienst = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
		return extAddressDienst;
	}
	
*/
	
/*	
	// HTTP POST request
		private void sendPost(String rawAddress) throws Exception {

			String url = "http://open.mapquestapi.com/geocoding/v1/address?key=V7QcX4ax6960SWrbde2ssAaAd6A92zMm&callback=renderOptions&inFormat=kvp&outFormat=json&location=" + rawAddress;
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
				
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
				
			//print result
			System.out.println(response.toString());

		}
*/

}
