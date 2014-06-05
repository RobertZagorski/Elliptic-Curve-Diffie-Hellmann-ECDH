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
	 * Domyœlna wartoœæ rzêdu punktu G. Jest u¿ywana je¿eli u¿ytkownik nie poda³ inn¹.
	 * Zestandaryzowana dla krzywej eleptycznej B-163.
	 */
	public static final BigInteger DEFAULT_N = 
			new BigInteger("5846006549323611672814742442876390689256843201587");
	
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
	 * o zadanej liczbie bitów)
	 * @param b liczba bitów
	 */
	public void genKluczaPrywatnego(int b)
	{
			SecureRandom random = new SecureRandom();
			
			/**
			 * Klucz prywatny jest losowany z zakresu [0; n-1]
			 */
			//BigInteger maks = n.subtract(BigInteger.ONE);
			while (true) {
				BigInteger nowyKlucz = new BigInteger(b-1, random);
				/*if (nowyKlucz.compareTo(maks) >= 0) {
					continue;
				}*/
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
	 * @return wspólny dla obu klientów klucz sesji
	 */
	public String genklucz01()
	{
		return kluczTajny.X.dodaj(kluczTajny.Y).b.toString(2);
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
