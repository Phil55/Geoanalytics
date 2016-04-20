package ch.zhaw.core;
import java.io.IOException;
import java.sql.*; //wird für die Verbindung zur Datenbank benötigt
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; //für Eingabe in Konsole benötigt

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;



public class Main {
	
	//USER_AGENT festlegen
	private final String USER_AGENT = "Mozilla/5.0";
	
	// Instanzierung von Name und URL von JDBC driver
	static final String jdbcTreiber = "com.mysql.jdbc.Driver";  
	static final String url = "jdbc:mysql://160.85.104.27/patstat";

	//  Instanzierung von Einlog-Daten für Datenbank
	
	//Für Testzwecke verwenden
	static final String user = "";
	static final String password = "";
	
	/*// Code für am Schluss (User muss Passwort eingeben
	static final String user = getUsername();
	static final String password = getPassword();
	*/
	
	//Methode um Username einzugeben und in Variable zu speichern
	public static String getUsername(){
		
		//Eingabe von Username und Passwort in Konsole
		System.out.println("Enter username: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		System.out.println("The username is: " + name);
		sc.close();
		return name;
	}
	
	//Methode um Passwort einzugeben und in Variable zu speichern
	public static String getPassword(){
		
		//Eingabe von Passwort in Konsole
		System.out.println("Enter password: ");
		Scanner sc = new Scanner(System.in);
		String password = sc.next();
		System.out.println("The password is: " + password);
		sc.close();
		return password;
	}
	
	public static String getLimit(Scanner sc){
		//Eingabe des Limits der SQL-Abfrage in Konsole
		//Ein Limit muss eingegeben werden. 
		//Bei so einer grossen Anzahl an Datensätzen wäre die Gefahr grosse, dass man zu viel Datensätze selektiert und dadurch der Prozess extrem lange geht
		System.out.println();
		System.out.println("SQL-Bedingung Limit:");
		System.out.println("Eingabe von Limit ist zwingend!");
		System.out.println("Enter the Limit of Output: ");
		String limit = sc.next();
		System.out.println("The Limit of Output is: " + limit);
		limit = "LIMIT " + limit;
		return limit;
	}
	
	public static String getSource(Scanner sc){
		//Eingabe des Limits der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung Datenquelle (source):");
		System.out.println("Wenn Sie keine Einschränkung bei source machen möchten geben Sie '-' ein");
		System.out.println("Enter the source: ");
		String source = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		if(source == "-"){
			return null;
		}
		else{
			System.out.println("The source is: " + source);
			source = "source = '" + source + "'";
			return source;
		}
	}
	
	public static void main(String[] args) {
		
		/*
		//Eingabe des Limits der SQL-Abfrage in Konsole
		//Ein Limit muss eingegeben werden. 
		//Bei so einer grossen Anzahl an Datensätzen wäre die Gefahr grosse, dass man zu viel Datensätze selektiert und dadurch der Prozess extrem lange geht
		System.out.println("Enter the Limit of Output: ");
		//Scanner sc = new Scanner(System.in);
		String pass = sc.next();
		System.out.println("The Output is: " + pass);
		String limit = "LIMIT " + pass;
		//sc.close();
		*/
		Scanner sc = new Scanner(System.in);
		
		String source = getSource(sc);
		String limit = getLimit(sc);
		
		//Scanner wird geschlossen sonst wird eine Warnung aufkommen
		sc.close();
		
		//zu testzwecken wird das geprintet
		System.out.println("SQL-Element source: " + source);
		System.out.println("SQL-Element limit: " + limit);
		
		
		
		//Instanzierung von Connection und Statement für Verbindung zur Datenbank
		Connection conn = null;
		Statement stmt = null;
		try{
			//JDBC Treiber initialisieren
			Class.forName("com.mysql.jdbc.Driver");

			//Verbindung zur Datenbank erstellen
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(url,user,password);

			//SQL Query ausführen
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 426;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 450;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 500;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 412;";
			sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2, person_ctry_code FROM tls226_person_orig WHERE person_orig_id = 134943 " + limit + ";";
			ResultSet rs = stmt.executeQuery(sql);

			//Daten von result set in Variablen extrahieren
			while(rs.next()){
				//Daten pro Spalte erhalten (Reihenfolge gemäss SQL Query)
				int personOrigId  = rs.getInt("person_orig_id");
				int personId = rs.getInt("person_id");
				String nameFreeform = rs.getString("name_freeform");
				String addressOne = rs.getString("address_1");
				String addressTwo = rs.getString("address_2");
				String countryCode = rs.getString("person_ctry_code");

				//Werte anzeigen
				System.out.println("Person Orig ID: " + personOrigId);
				System.out.println("Person ID: " + personId);
				System.out.println("Name Freeform: " + nameFreeform);
				System.out.println("Adress_1: " + addressOne);
				System.out.println("Adress_2: " + addressTwo);
				System.out.println("countryCode: " + countryCode);
				
				//Werte Instanzieren in Struktur
				Struktur f = new Struktur(personOrigId, personId, nameFreeform, addressOne, addressTwo, countryCode);
				
				//Abfrage Initiieren
				f.mainQuery(f.getRawAddress());
				
			}
			//ResultSet rs, Statement bereinigen und Verbindung schliessen
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Fehler von JDBC behandeln
			se.printStackTrace();
		}catch(Exception e){
			//Fehler von Class.forName behandeln
			e.printStackTrace();
		}finally{
			//finally-Teil benutzen, um Ressourcen zu schliessen
			try{
				if(stmt!=null)
					stmt.close();
				}catch(SQLException se2){
				}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
				}catch(SQLException se){
					se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("DB Connection closed");
		
		// Resultat ausgeben	
		//System.out.println("Resultat: " + f.startAbfrage(f.getAdress()));
		
	}
}
