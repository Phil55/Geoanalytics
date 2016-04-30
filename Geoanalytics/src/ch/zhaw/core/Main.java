package ch.zhaw.core;

import java.sql.*; //wird für die Verbindung zur Datenbank benötigt
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; //für Eingabe in Konsole benötigt

public class Main {
	
	/*
	//USER_AGENT festlegen
	private final String USER_AGENT = "Mozilla/5.0";
	*/
	
	// Instanzierung von Name und URL von JDBC driver
	static final String jdbcTreiber = "com.mysql.jdbc.Driver";  
	static final String url = "jdbc:mysql://160.85.104.27/patstat";

	// Instanzierung von Einlog-Daten für Datenbank
	
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
		limit = " LIMIT " + limit;
		return limit;
	}
	
	public static String getSource(Scanner sc){
		//Eingabe des Sources der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung Datenquelle (source):");
		System.out.println("Wenn Sie keine Einschränkung bei source machen möchten geben Sie '-' ein");
		System.out.println("Enter the source: ");
		String source = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(source.equals("-")){
			return null;
		}
		else{
			System.out.println("The source is: " + source);
			source = "source = '" + source + "'";
			return source;
		}
	}
	
	public static String getNameLike(Scanner sc){
		//Eingabe des Namens (Like) der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung enthält (LIKE) bei Name (name_freeform):");
		System.out.println("Wenn Sie keine Einschränkung bei Name machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei LIKE / NOT LIKE:");
		System.out.println("'_' steht für ein beliebiges einzelnes Zeichen, das an der betreffenden Stelle vorkommen kann");
		System.out.println("'%' steht für eine beliebige Zeichenkette mit 0 oder mehr Zeichen");
		System.out.println("Beispiel1: 'Nokia%' alles was mit Nokia anfängt, Inhalt dahinter ist beliebig");
		System.out.println("Beispiel2: '%Nokia%' alles was Nokia enthält, Inhalt davor und dahinter ist beliebig");
		System.out.println("Enter the name (LIKE): ");
		String nameLike = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(nameLike.equals("-")){
			return null;
		}
		else{
			System.out.println("The name is: " + nameLike);
			nameLike = "name_freeform LIKE '" + nameLike + "'";
			return nameLike;
		}		
	}
	
	public static String getNameNotLike(Scanner sc){
		//Eingabe des Namens (Not Like) der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung enthält (NOT LIKE) bei Name (name_freeform):");
		System.out.println("Wenn Sie keine Einschränkung bei Name machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei LIKE / NOT LIKE:");
		System.out.println("'_' steht für ein beliebiges einzelnes Zeichen, das an der betreffenden Stelle vorkommen kann");
		System.out.println("'%' steht für eine beliebige Zeichenkette mit 0 oder mehr Zeichen");
		System.out.println("Beispiel1: 'Nokia%' alles was mit Nokia anfängt, Inhalt dahinter ist beliebig");
		System.out.println("Beispiel2: '%Nokia%' alles was Nokia enthält, Inhalt davor und dahinter ist beliebig");
		System.out.println("Enter the name (NOT LIKE): ");
		String nameNotLike = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(nameNotLike.equals("-")){
			return null;
		}
		else{
			System.out.println("The name is: " + nameNotLike);
			nameNotLike = "name_freeform NOT LIKE '" + nameNotLike + "'";
			return nameNotLike;
		}		
	}
	
	public static String getAddressLike(Scanner sc){
		//Eingabe der Adresse (Like) der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung enthält (LIKE) bei Adresse (address_freeform):");
		System.out.println("Wenn Sie keine Einschränkung bei Adresse machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei LIKE / NOT LIKE:");
		System.out.println("'_' steht für ein beliebiges einzelnes Zeichen, das an der betreffenden Stelle vorkommen kann");
		System.out.println("'%' steht für eine beliebige Zeichenkette mit 0 oder mehr Zeichen");
		System.out.println("Beispiel1: 'Bach%' alles was mit Nokia anfängt, Inhalt dahinter ist beliebig");
		System.out.println("Beispiel2: '%strasse%' alles was Nokia enthält, Inhalt davor und dahinter ist beliebig");
		System.out.println("Enter the address (LIKE): ");
		String addressLike = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(addressLike.equals("-")){
			return null;
		}
		else{
			System.out.println("The address is: " + addressLike);
			addressLike = "address_freeform LIKE '" + addressLike + "'";
			return addressLike;
		}				
	}
	
	public static String getAddressNotLike(Scanner sc){
		//Eingabe der Adresse (Not Like) der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung enthält (NOT LIKE) bei Adresse (address_freeform):");
		System.out.println("Wenn Sie keine Einschränkung bei Adresse machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei LIKE / NOT LIKE:");
		System.out.println("'_' steht für ein beliebiges einzelnes Zeichen, das an der betreffenden Stelle vorkommen kann");
		System.out.println("'%' steht für eine beliebige Zeichenkette mit 0 oder mehr Zeichen");
		System.out.println("Beispiel1: 'Bach%' alles was mit Nokia anfängt, Inhalt dahinter ist beliebig");
		System.out.println("Beispiel2: '%strasse%' alles was Nokia enthält, Inhalt davor und dahinter ist beliebig");
		System.out.println("Enter the address (NOT LIKE): ");
		String addressNotLike = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(addressNotLike.equals("-")){
			return null;
		}
		else{
			System.out.println("The address is: " + addressNotLike);
			addressNotLike = "address_freeform NOT LIKE '" + addressNotLike + "'";
			return addressNotLike;
		}				
		
	}
	
	public static String getPersonId(Scanner sc){
		//Eingabe der PersonId der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung PersonID (BETWEEN):");
		System.out.println("Wenn Sie keine Einschränkung bei PersonID machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei BETWEEN:");
		System.out.println("Sie geben eine 'anfangs'-ID und eine 'schluss'-ID ein");
		System.out.println("es werden alle ID ausgewählt die zwischen der anfangs- und schlussID (inkl. der anfangs- und schlussID)");
		System.out.println("Beispiel1: 2-5 -> Datensätze bei denen die PersonID im Bereich 2 bis 5 liegt (inkl. 2 und 5)");
		System.out.println("Beispiel2: 3-3 -> Datensätze bei denen die PersonID 3 ist");
		System.out.println("Enter the PersonID (BETWEEN): ");
		String personId = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(personId.equals("-")){
			return null;
		}
		else{
			//splitet den String in zwei String (anfangs- und schlussID)
			String[] inputs = personId.split("-", 2);
			String anfangsID = inputs[0];
			String schlussID = inputs[1];
			personId = "person_id BETWEEN " + anfangsID + " AND " + schlussID;
			System.out.println("The PersonId (BETWEEN) is: " + personId);
			return personId;
		}				
	}
	
	public static String getPersonOrigId(Scanner sc){
		//Eingabe der PersonOrigId der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung PersonOrigId (BETWEEN):");
		System.out.println("Wenn Sie keine Einschränkung bei PersonOrigId machen möchten geben Sie '-' ein");
		System.out.println("Syntax bei BETWEEN:");
		System.out.println("Sie geben eine 'anfangs'-ID und eine 'schluss'-ID ein");
		System.out.println("es werden alle ID ausgewählt die zwischen der anfangs- und schlussID (inkl. der anfangs- und schlussID)");
		System.out.println("Beispiel1: 2-5 -> Datensätze bei denen die PersonOrigID im Bereich 2 bis 5 liegt (inkl. 2 und 5)");
		System.out.println("Beispiel2: 3-3 -> Datensätze bei denen die PersonOrigID 3 ist");
		System.out.println("Enter the PersonOrigId (BETWEEN): ");
		String personOrigId = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(personOrigId.equals("-")){
			return null;
		}
		else{
			//splitet den String in zwei String (anfangs- und schlussID)
			String[] inputs = personOrigId.split("-", 2);
			String anfangsID = inputs[0];
			String schlussID = inputs[1];
			personOrigId = "person_orig_id BETWEEN " + anfangsID + " AND " + schlussID;
			System.out.println("The PersonOrigId (BETWEEN) is: " + personOrigId);
			return personOrigId;
		}				
	}
	
	public static String getCountryCode(Scanner sc){
		//Eingabe des Countrycodes der SQL-Abfrage in Konsole
		System.out.println();
		System.out.println("SQL-Bedingung Land (person_ctry_code):");
		System.out.println("Wenn Sie keine Einschränkung bei countrycode machen möchten geben Sie '-' ein");
		System.out.println("Enter the countrycode: ");
		String countryCode = sc.next();
		//überprüft ob etwas eingegeben wurde, wenn nicht wird null zurückgegeben ansonsten den eingegebene Wert source
		//Strings werden mit der Funktion equals() verglichen nicht mit ==
		if(countryCode.equals("-")){
			return null;
		}
		else{
			System.out.println("The countrycode is: " + countryCode);
			countryCode = "person_ctry_code = '" + countryCode + "' ";
			return countryCode;
		}
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		//filter-Elemente beim User abfragen und in String abspeichern
		String limit = getLimit(sc);
		String source = getSource(sc);
		String ctryCode = getCountryCode(sc);
		String nameLike = getNameLike(sc);
		String nameNotLike = getNameNotLike(sc);
		String addressLike = getAddressLike(sc);
		String addressNotLike = getAddressNotLike(sc);
		String personIdBetween = getPersonId(sc);
		String personOrigIdBetween = getPersonOrigId(sc);
		
		//Scanner wird geschlossen sonst wird eine Warnung aufkommen
		sc.close();
		
		//zu testzwecken wird das geprintet
		System.out.println("SQL-Element source: " + source);
		System.out.println("SQL-Element ctryCode: " + ctryCode);
		System.out.println("SQL-Element nameLike: " + nameLike);
		System.out.println("SQL-Element nameNotLike: " + nameNotLike);
		System.out.println("SQL-Element addressLike: " + addressLike);
		System.out.println("SQL-Element addressNotLike: " + addressNotLike);
		System.out.println("SQL-Element personIdBetween: " + personIdBetween);
		System.out.println("SQL-Element personOrigIdBetween: " + personOrigIdBetween);
		System.out.println("SQL-Element limit: " + limit);
		
		//Filterliste instanzieren für die nächsten schritte
		List<String> filterList = new ArrayList<String>();
		
		//Filter-Elemente hinzufügen falls sie nicht null sind. limit wird immer hinzugefügt
		//Limit ist immer zuletzt und wird nicht in die liste hinzugefügt sondern in sql eingefügt, da es zwingend ist
		if(source != null){
			filterList.add(source);
			System.out.println("source != null, source: " + source);
		}
		if(ctryCode != null){
			filterList.add(ctryCode);
			System.out.println("ctryCode != null, ctryCode: " + ctryCode);
		}
		if(nameLike != null){
			filterList.add(nameLike);
			System.out.println("nameLike != null, nameLike: " + nameLike);
		}
		if(nameNotLike != null){
			filterList.add(nameNotLike);
			System.out.println("nameNotLike != null, nameNotLike: " + nameNotLike);
		}
		if(addressLike != null){
			filterList.add(addressLike);
			System.out.println("addressLike != null, addressLike: " + addressLike);
		}
		if(addressNotLike != null){
			filterList.add(addressNotLike);
			System.out.println("addressNotLike != null, addressNotLike: " + addressNotLike);
		}
		if(personIdBetween != null){
			filterList.add(personIdBetween);
			System.out.println("personIdBetween != null, personIdBetween: " + personIdBetween);
		}
		if(personOrigIdBetween != null){
			filterList.add(personOrigIdBetween);
			System.out.println("personOrigIdBetween != null, personOrigIdBetween: " + personOrigIdBetween);
		}
		
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

			if(filterList.isEmpty() == true){
				sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2, person_ctry_code FROM tls226_person_orig" + limit + ";";
			}
			else{
				String filterElements = null;
				for(int i = 0; i < filterList.size(); i++){
					//überprüft ob es das erste Element ist und fügt "WHERE", bei den anderen wird "AND" hinzugefügt
					if(i == 0){
						filterElements = " WHERE " + filterList.get(i);
						System.out.println("filterElements : " + filterElements);
					}
					else{
						filterElements = filterElements + " AND " + filterList.get(i);
						System.out.println("filterElements : " + filterElements);
					}
				}
				sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2, person_ctry_code FROM tls226_person_orig" + filterElements + limit + ";";
			}
			System.out.println("SQL-Statement : " + sql);
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 426;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 450;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 500;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2 FROM tls226_person_orig WHERE person_orig_id = 412;";
			//sql = "SELECT person_orig_id, person_id, name_freeform, address_1, address_2, person_ctry_code FROM tls226_person_orig " + limit + ";";
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
				Scheme f = new Scheme(personOrigId, personId, nameFreeform, addressOne, addressTwo, countryCode);
				
				//Abfrage Initiieren
				List<String> sqlList = f.mainQuery(f.getRawAddress());
				
				//erhaltene sql-Statements ausführen
				for(int i = 0; i < sqlList.size(); i++){
					stmt.executeQuery(sqlList.get(i));
					System.out.println("stmt.executeQuery(sqlList.get(i)), sql: " + sqlList.get(i));
				}
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
