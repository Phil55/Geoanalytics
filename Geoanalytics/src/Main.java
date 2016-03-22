import java.sql.*; //wird für die Verbindung zur Datenbank benötigt
import java.util.Scanner; //für Eingabe in Konsole benötigt

public class Main {

	// Instanzierung von Name und URL von JDBC driver
	static final String jdbcTreiber = "com.mysql.jdbc.Driver";  
	static final String url = "jdbc:mysql://160.85.104.27/patstat";

	//  Instanzierung von Einlog-Daten für Datenbank
	static final String user = getUsername();
	static final String password = getPassword();
	
	//Methode um Username einzugeben und in Variable zu speichern
	public static String getUsername(){
		
		//Eingabe von Username und Passwort in Konsole
		System.out.println("Enter username: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		System.out.println("The username is: " + name);
		return name;
	}
	
	//Methode um Passwort einzugeben und in Variable zu speichern
	public static String getPassword(){
		
		//Eingabe von Passwort in Konsole
		System.out.println("Enter password: ");
		Scanner sc = new Scanner(System.in);
		String pass = sc.next();
		System.out.println("The password is: " + pass);
		return pass;
	}
	
	public static void main(String[] args) {
		
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
			sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 4;";
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
				System.out.print("Person Orig ID: " + personOrigId);
				System.out.print(", Person ID: " + personId);
				System.out.print(", Name Freeform: " + nameFreeform);
				System.out.println(", Adress_1: " + addressOne);
				System.out.println(", Adress_2: " + addressTwo);
				
				//Werte Instanzieren in Struktur
				Struktur f = new Struktur(personOrigId, personId, nameFreeform, addressOne, addressTwo);
				
				//Abfrage bei Dienstleisungen starten
				f.startAbfrage(f.getRawAddress());
				
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
