package dh;
import dh.ECPunkt;
import java.math.BigInteger;
/**
 * Klasa klienta odopowiadaj�ca za generowanie kluczy publicznych 
 * i prywatnych klient�w
 * @author Vitali Karpinski
 * @author Robert Zag�rski
 *
 */
public class Klient {

	BigInteger kluczPrywatny;
	ECPunkt kluczPubliczny;
	ECPunkt G;
	BigInteger n;
	BigInteger h;
	ECPunkt kluczTajny;
	/**
	 * Konstuktor klasy Client pozwalaj�cy na zapisanie parametr�w
	 * pocz�tkowych algorytmu obliczania kluczy ECDH
	 * @param G Generator grupy punkt�w krzywej eliptycznej
	 * @param n rz�d generatora, du�a liczba pierwsza
	 * @param h liczba warstw podgrupy
	 * Liczba punkt�w generowanych przez dany generator jest r�wna n*h
	 */
	public Klient(ECPunkt G,BigInteger n,BigInteger h)
	{
		this.G=G;
		this.n=n;
		this.h=h;
	}
	
	/**
	 * Funkcja generuj�ca klucz prywatny u�ytkownika (du�a liczb� pierwsz� 
	 * o zadanej liczbie bit�w)
	 * @param b liczba bit�w
	 */
	public void genKluczaPrywatnego(int b)
	{
			
	}
	
	/**
	 * Generacja klucza publicznego z klucza prywatnego i generatora
	 */
	public void oblKluczaPublicznego()
	{
		
	}
	
	/**
	 * Generacja wsp�lnego klucza tajnego z klucza prywatnego i klucza 
	 * publicznego drugiej strony
	 */
	public void oblKluczaTajnego()
	{
		
	}
	
	/**
	 * Funkcja przekszta�caj�ca klucz tajny uzgodniony w trakcie sesji 
	 * (punkt na krzywej eliptycznej) na ci�g bitowy
	 */
	public void genklucz01()
	{
		
	}
	
}
