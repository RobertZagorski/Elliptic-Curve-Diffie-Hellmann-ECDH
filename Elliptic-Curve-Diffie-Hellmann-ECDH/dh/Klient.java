package dh;
import dh.ECPunkt;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Klasa klienta odopowiadaj�ca za generowanie kluczy publicznych 
 * i prywatnych klient�w
 * @author Vitali Karpinski
 * @author Robert Zag�rski
 */
public class Klient {

	/**Klucz prywatny u�ytkownika. Liczba typu {@link BigInteger} o d�ugo�ci zadeklarowanej
	 * jako argument funkcji {@link Klient#genKluczaPrywatnego}*/
	BigInteger kluczPrywatny;
	/**Klucz publiczny u�ytkownika. Obiekt typu {@link ECPunkt}. Obliczany w funkcji
	 *{@link Klient#oblKluczaPublicznego}*/
	ECPunkt kluczPubliczny;
	/**Klucz publiczny drugiej strony. Przekazywany w celu ustalenia wsp�lnego klucza.
	 * Ustawiany w funkcji {@link Klient#ustawKluczPublicznyB}*/
	ECPunkt kluczPublicznyB;
	/**Punkt startowy generowania punkt�w na krzywej eliptycznej*/
	ECPunkt G;
	/**Klucz b�d�cy wynikem dzia�ania algorytmu. Ustawiany w funkcji 
	 * {@link Klient#oblKluczaTajnego}*/
	ECPunkt kluczTajny;
	
	/**
	 * Konstuktor klasy Klient pozwalaj�cy na zapisanie parametr�w
	 * pocz�tkowych algorytmu obliczania kluczy ECDH
	 * @param G Generator grupy punkt�w krzywej eliptycznej
	 */
	public Klient(ECPunkt G)
	{
		this.G=G;
		
	}
	
	/**
	 * Konstruktor klasy Klient. Nie zapisuje parametr�w algorytmu.
	 * Pozwala uruchomi� algorytm z warto�ciami domy�lnymi.
	 */
	public Klient() {
		
	}
	
	/**
	 * Funkcja generuj�ca klucz prywatny u�ytkownika (du�a liczb� pierwsz� 
	 * o zadanej liczbie bit�w). Liczba jest losowana z przedzia�u [1, (2^m)-1].
	 * @param b liczba bit�w
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
	 * Generacja wsp�lnego klucza tajnego z klucza prywatnego i klucza 
	 * publicznego drugiej strony
	 */
	public void oblKluczaTajnego()
	{
		kluczTajny = new ECPunkt(G.wielokrotnoscPunktu( kluczPublicznyB, new BigInteger(kluczPrywatny.toString()) ) );
	}
	
	/**
	 * Funkcja przekszta�caj�ca klucz tajny uzgodniony w trakcie sesji 
	 * (punkt na krzywej eliptycznej) na ci�g bitowy
	 */
	public void genklucz01()
	{
		
	}
	
	/**
	 * Generacja wsp�lnego klucza tajnego z klucza prywatnego i klucza 
	 * publicznego drugiej strony
	 */
	public void ustawKluczPublicznyB(ECPunkt kluczB)
	{
		this.kluczPublicznyB = new ECPunkt(kluczB);
	}
}
