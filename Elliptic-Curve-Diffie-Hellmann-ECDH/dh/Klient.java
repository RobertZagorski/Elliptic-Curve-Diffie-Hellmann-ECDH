package dh;
import dh.ECPunkt;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Klasa klienta odopowiadaj¹ca za generowanie kluczy publicznych 
 * i prywatnych klientów
 * @author Vitali Karpinski
 * @author Robert Zagórski
 */
public class Klient {

	/**Klucz prywatny u¿ytkownika. Liczba typu {@link BigInteger} o d³ugoœci zadeklarowanej
	 * jako argument funkcji {@link Klient#genKluczaPrywatnego}*/
	BigInteger kluczPrywatny;
	/**Klucz publiczny u¿ytkownika. Obiekt typu {@link ECPunkt}. Obliczany w funkcji
	 *{@link Klient#oblKluczaPublicznego}*/
	ECPunkt kluczPubliczny;
	/**Klucz publiczny drugiej strony. Przekazywany w celu ustalenia wspólnego klucza.
	 * Ustawiany w funkcji {@link Klient#ustawKluczPublicznyB}*/
	ECPunkt kluczPublicznyB;
	/**Punkt startowy generowania punktów na krzywej eliptycznej*/
	ECPunkt G;
	/**Klucz bêd¹cy wynikem dzia³ania algorytmu. Ustawiany w funkcji 
	 * {@link Klient#oblKluczaTajnego}*/
	ECPunkt kluczTajny;
	
	/**
	 * Konstuktor klasy Klient pozwalaj¹cy na zapisanie parametrów
	 * pocz¹tkowych algorytmu obliczania kluczy ECDH
	 * @param G Generator grupy punktów krzywej eliptycznej
	 */
	public Klient(ECPunkt G)
	{
		this.G=G;
		
	}
	
	/**
	 * Konstruktor klasy Klient. Nie zapisuje parametrów algorytmu.
	 * Pozwala uruchomiæ algorytm z wartoœciami domyœlnymi.
	 */
	public Klient() {
		
	}
	
	/**
	 * Funkcja generuj¹ca klucz prywatny u¿ytkownika (du¿a liczbê pierwsz¹ 
	 * o zadanej liczbie bitów). Liczba jest losowana z przedzia³u [1, (2^m)-1].
	 * @param b liczba bitów
	 */
	public void genKluczaPrywatnego(int b)
	{
			SecureRandom random = new SecureRandom();
			
			BigInteger TWO = new BigInteger("2");
			BigInteger n = TWO.pow(b);
			BigInteger maks = n.subtract(BigInteger.ONE);
			while (true) {
				BigInteger nowyKlucz = new BigInteger(b-1, random);
				if (nowyKlucz.compareTo(maks) >= 0) {
					continue;
				}
				if (nowyKlucz.compareTo(BigInteger.ONE) <= 0 ) {
					continue;
				}
				kluczPrywatny = nowyKlucz;
				break;
			}
	}
	
	/**
	 * Generacja klucza publicznego z klucza prywatnego i generatora
	 */
	public ECPunkt oblKluczaPublicznego()
	{
		kluczPubliczny = new ECPunkt( G.wielokrotnoscPunktu(G, new BigInteger(kluczPrywatny.toString()) ) );
		return kluczPubliczny;
	}
	
	/**
	 * Generacja wspólnego klucza tajnego z klucza prywatnego i klucza 
	 * publicznego drugiej strony
	 */
	public void oblKluczaTajnego()
	{
		kluczTajny = new ECPunkt(G.wielokrotnoscPunktu( kluczPublicznyB, new BigInteger(kluczPrywatny.toString()) ) );
	}
	
	/**
	 * Funkcja przekszta³caj¹ca klucz tajny uzgodniony w trakcie sesji 
	 * (punkt na krzywej eliptycznej) na ci¹g bitowy
	 */
	public void genklucz01()
	{
		
	}
	
	/**
	 * Generacja wspólnego klucza tajnego z klucza prywatnego i klucza 
	 * publicznego drugiej strony
	 */
	public void ustawKluczPublicznyB(ECPunkt kluczB)
	{
		this.kluczPublicznyB = new ECPunkt(kluczB);
	}
}
