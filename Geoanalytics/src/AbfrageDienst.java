/*
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
 */

import org.json.*;
import org.apache.http.nio.client.*;
import org.apache.http.client.*;
import org.apache.http.entity.mime.*;
import com.mashape.unirest.*;


public class AbfrageDienst {

	//private final String USER_AGENT = "Mozilla/5.0"; //für GET und POST
	
	private int dienstID; //ID soll Aufschluss geben, welche Dienstleistung in anspruch genommen wird
	private String extAddressDienst;
	
	public AbfrageDienst(int dienstID) {
		this.dienstID = dienstID;
	}

	public int getDienstID() {
		return dienstID;
	}

	public void setDienstID(int dienstID) {
		this.dienstID = dienstID;
	}

	public String getExtAddressDienst() {
		return extAddressDienst;
	}

	public void setExtAddressDienst(String extAddressDienst) {
		this.extAddressDienst = extAddressDienst;
	}
	
	//DL/Service wird abgefragt und man erhält eine "externe Adresse"
	public String AbfServiceOSM (String rawAddress) {
		
		Unirest.post("http://httpbin.org/post").queryString("name", "Mark").field("last", "Polo").asJson();
		
		//extAddressDienst = "strasse , 1, Ort, 1234, CH, lat: 35.12378, long: 5.78735";
		return extAddressDienst;
	}
/*	
	// HTTP POST request
		private void sendPost(String rawAddress) throws Exception {

			String url = "http://open.mapquestapi.com/geocoding/v1/address?key=V7QcX4ax6960SWrbde2ssAaAd6A92zMm&callback=renderOptions&inFormat=kvp&outFormat=json&location=" + rawAddress;
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
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