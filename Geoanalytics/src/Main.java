
public class Main {

	public static void main(String[] args) {
		Struktur f = new Struktur("strasse , 1, Ort, 1234, CH", 1);		
		System.out.println("Resultat: " + f.startAbfrage(f.getAdress()));
		
	}
}
