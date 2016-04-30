package ch.zhaw.testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.core.Scheme;


//beide Testing-Klassen werden wahrscheinlich gar nicht benötigt (siehe To-Do-Liste: 17.04.16)
public class Systemtest {
	
	//Instanzierung von Name und URL von JDBC driver
	static final String jdbcTreiber = "com.mysql.jdbc.Driver";  
	static final String url = "jdbc:mysql://160.85.104.27/patstat";

	//  Instanzierung von Einlog-Daten für Datenbank
		
	//Für Testzwecke verwenden
	static final String user = "patentdb";
	static final String password = "tRNSBI7n7VHOPK7n";
	
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
		String pass = sc.next();
		System.out.println("The password is: " + pass);
		sc.close();
		return pass;
	}

	@Before
	public void setUp() {
		
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
			sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 500;";
			ResultSet rs = stmt.executeQuery(sql);

			//Daten von result set in Variablen extrahieren
			while(rs.next()){
				//Daten pro Spalte erhalten (Reihenfolge gemäss SQL Query)
				int personOrigId  = rs.getInt("person_orig_id");
				int personId = rs.getInt("person_id");
				String nameFreeform = rs.getString("name_freeform");
				String addressOne = rs.getString("address_1");
				String addressTwo = rs.getString("address_2");

				//Werte anzeigen
				System.out.println("Person Orig ID: " + personOrigId);
				System.out.println(", Person ID: " + personId);
				System.out.println(", Name Freeform: " + nameFreeform);
				System.out.println(", Adress_1: " + addressOne);
				System.out.println(", Adress_2: " + addressTwo);
				
				//Werte Instanzieren in Struktur
				//Struktur f = new Struktur(personOrigId, personId, nameFreeform, addressOne, addressTwo,);
				
				//test(f);
			
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
		
	}
	
	@Test
	public void test(Scheme f) {
		
		f.mainQuery(f.getRawAddress());
		fail("Not yet implemented");
	}

}
