package dh;

import java.math.BigInteger;

/**
 * Klasa implementuj¹ca element cia³a {@link GF(2^m)}. 
 * W klasie zdefiniowano operacje na ciele binarnym: dodawanie, mno¿enie, podnoszenie
 * do kwadratu. Dodatkowo zaimplementowano konwersjê liczby w postaci binarnej 
 * zaimplementowanej jako {@link int}[] na liczbê typu {@link BigInteger}. <p>
 * DO implementacji operacji modulo wykorzystywany jest trójmian postaci {@link x^m+x^k+1}
 */

public class GF2Elem {
	/**Rozmiar cyklicznej grupy multiplikatywnej, na której zaimplementowano dany element*/
	private static final int FIELDSIZE = 2;
	/**Liczba opisywana przez obiekt tej klasy typu {@link BigInteger}*/
	BigInteger b;
	/**Liczba opisywana przez obiekt tej klasy w postaci {@link int}[]*/
	int[] bB;
	/**Wyk³adnik najwy¿szego sk³adnika wielomianu modulo dla cia³a - rz¹d wielomianu*/
	int m;
	/**Element œrodkowy wielomianu modulo dla cia³a*/
	int k;
	
	/***
	 * Konstruktor klasy {@link GF2Elem}
	 * @param bi Liczba typu {@link BigInteger} przechowuj¹ca obiekt w formanie innym ni¿ binarnym
	 * @param mm Wyk³adnik najwy¿szego sk³adnika wielomianu modulo dla cia³a - rz¹d wielomianu
	 * @param kk Element œrodkowy wielomianu modulo dla cia³a
	 */
	public GF2Elem(BigInteger bi, int mm, int kk)
	{
		b=bi;
		m=mm;
		k=kk;
		bB=bigIntToIntArray(b);
	}

	  
	  /**
	   * Funkcja zwracaj¹ca liczbê elementów w ciele
	   * @return liczba elementów w ciele (domyœlnie 2)
	   */
	  public int getFieldSize() {
	    return FIELDSIZE;
	  }
	  
	  /***
	   * Funkcja zwracaj¹ca d³ugoœæ binarn¹ liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   * @return d³ugoœæ binarna liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   */
	  public int getLength()
	  {
		  return bB.length;
	  }
	  
	  /**
	   * Dodawanie binarne dwóch liczb typu {@link int}
	   * @param x liczba typu {@link int} - pierwszy sk³adnik
	   * @param y liczba typu {@link int} - drugi sk³adnik
	   * @return wynik sumy binarnej liczb {@link x} oraz {@link y}
	   */
	  public int add(int x, int y) {
		    assert(x >= 0 && x < getFieldSize() && y >= 0 && y < getFieldSize());
		    return x ^ y; ///// Operacja XOR w java
	  }
	  
	/**
	   * Obliczanie sumy dwóch wielomianów. Indeks w tablicy typu {@link int} odpowiada 
	   * wyk³adnikowi elementowi wejœciowemu. Na przyk³ad element {@link p[0]} odpowiada
	   * sk³adnikowi wolnemu wielomianu. Implementacja operacji XOR w ciele GF{2^m}
	   * @param p wielomian wejœciowy
	   * @param q wielomian wejœciowy
	   * @return wielomina reprezenuj¹cy wynik operacji p+q
	   */
	  public GF2Elem add(int[] q) {
	    int len = Math.max(this.getLength(), q.length);
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) {
	      if (i < this.getLength() && i < q.length) {
	        result[i] = add(this.bB[i], q[i]);
	      } else if (i < this.getLength()) {
	        result[i] = this.bB[i];
	      } else {
	        result[i] = q[i];
	      }
	    }
	    this.bB = result;
	    this.b = intArraytoBigInteger(this.bB);
	    return this;
	  }
	  
	  /**
	   * Obliczanie iloczynu dwóch wielomianów. Indeks w tablicy typu {@link int} odpowiada 
	   * wyk³adnikowi elementowi wejœciowemu. Na przyk³ad element {@link p[0]} odpowiada
	   * sk³adnikowi wolnemu wielomianu. Implementacja operacji AND w ciele GF{2^m}
	   * @param p wielomian wejœciowy
	   * @param q wielomian wejœciowy
	   * @return wielomina reprezenuj¹cy wynik operacji p*q
	   */
	  public GF2Elem multiply(int[] q) {
	    int len = this.getLength() + q.length - 1;
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) {
	      result[i] = 0;
	    }
	    for (int i = 0; i < this.getLength(); i++) {
	      for (int j = 0; j < q.length; j++) {
	        result[i + j] = add(result[i + j], this.bB[i]*q[j]);
	      }
	    }
	    this.bB = result;
	    reduce();
	    this.b = intArraytoBigInteger(this.bB);
	    return this;
	  }
	  
	  /**
	   * Redukcja wielomianu przechowywanego w elemenencie {@link GF2Elem#bB} przez
	   * wielomian modulo zdefiniowany przez wyk³adniki {@link GF2Elem#m} oraz {@link GF2Elem#k}
	   * @return
	   */
	  public GF2Elem reduce()
	  {
		  for (int i = 0 ; i<=m; i++)
		  {
			  this.bB[m+i] = add(this.bB[m+i],this.bB[i]);
			  this.bB[m-k+i] = add(this.bB[m-k+i],this.bB[i]);
			  //this.bB[2*m-2-(i-m)] = add(this.bB[2*m-2-(i-m)],this.bB[2*m-2-i]);
			  //this.bB[2*m-2-(i-m+k)] = add(this.bB[2*m-2-(i-m+k)],this.bB[2*m-2-i]);
		  }
		  int[] result = new int[m+1];
		  for (int i=0;i<m;i++)
		  {
			  result[i+1] = this.bB[i+m+1];
		  }
		  this.bB = result;
		  return this;
	  }

	  /**
	   * Operacja podnoszenia wielomianu przechowywanego w elemenencie {@link GF2Elem#bB}
	   * @return obiekt typu {@link GF2Elem} przechowuj¹cy liczbê w formacie binarnym
	   */
	  public GF2Elem square()
	  {
		  int[] result = new int[2*m+1];
		  for (int i = 0; i <= 2*m; i++) {
		      result[i] = 0;
		  }
		  for (int i = 1; i <= m; i++) 
		  {
		      result[2*i] = this.bB[i];
		  }
		  this.bB = result;
		  reduce();
		  this.b = intArraytoBigInteger(this.bB);
		  return this;
	  }
	  
	  
	  /**
	   * Konwersja liczby przechowywanej w obiekcie {@link BigInteger} do liczby
	   * w formacie binarnym
	   * @return liczba w postaci binarnej  
	   */
	  
	  public int[] bigIntToIntArray(BigInteger bi)
	  {
		  String biStr = bi.toString(2);
		  int[] ints = new int[m+1];
		  for(int i=0; i<m+1; i++) 
		  {
			  if (i<m+1-biStr.length())
				  ints[i]=0;
			  else
				  ints[i] = Integer.parseInt(String.valueOf(biStr.charAt(i- (m+1-biStr.length()) )));
		  }
		  return ints;
	  }
	  
	  /**
	   * Konwersja liczby z postaci binarnej do obiektu {@link BigInteger}
	   * @param input tablica zawieraj¹ca bity liczby przeznaczonej do konwersji
	   * @return obiekt {@link BigInteger} zawieraj¹cy przechowywan¹ liczbê
	   */
	  public BigInteger intArraytoBigInteger (int[] input)
	  {
		  String bigInt = "";
		  for(int i : input) 
		  {
			  bigInt=bigInt.concat(Integer.toString(i));
		  }
		  BigInteger bi = new BigInteger(bigInt,2);
		  return bi;
	  }

}
