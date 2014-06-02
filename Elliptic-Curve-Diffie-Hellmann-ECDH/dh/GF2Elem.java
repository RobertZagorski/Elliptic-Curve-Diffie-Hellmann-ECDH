package dh;

import java.math.BigInteger;

/**
 * Klasa implementująca element ciała {@link GF(2^m)}. 
 * W klasie zdefiniowano operacje na ciele binarnym: dodawanie, mnożenie, podnoszenie
 * do kwadratu. Dodatkowo zaimplementowano konwersję liczby w postaci binarnej 
 * zaimplementowanej jako {@link int}[] na liczbę typu {@link BigInteger}. <p>
 * DO implementacji operacji modulo wykorzystywany jest trójmian postaci {@link x^m+x^k+1}
 */

public class GF2Elem {
	/**Rozmiar cyklicznej grupy multiplikatywnej, na której zaimplementowano dany element*/
	private static final int FIELDSIZE = 2;
	/**Liczba opisywana przez obiekt tej klasy typu {@link BigInteger}*/
	BigInteger b;
	/**Liczba opisywana przez obiekt tej klasy w postaci {@link int}[]*/
	int[] bB;
	/**Wykładnik najwyższego składnika wielomianu modulo dla ciała - rząd wielomianu*/
	int m;
	/**Element środkowy wielomianu modulo dla ciała*/
	int k;
	
	/***
	 * Konstruktor klasy {@link GF2Elem}
	 * @param bi Liczba typu {@link BigInteger} przechowująca obiekt w formanie innym niż binarnym
	 * @param mm Wykładnik najwyższego składnika wielomianu modulo dla ciała - rząd wielomianu
	 * @param kk Element środkowy wielomianu modulo dla ciała
	 */
	public GF2Elem(BigInteger bi, int mm, int kk)
	{
		if (bi.equals(BigInteger.ZERO))
			b=BigInteger.ZERO;
		else
			b=new BigInteger(bi.toString());
		m=mm;
		k=kk;
		bB=bigIntToIntArray(b);
	}
	
	public GF2Elem(GF2Elem copy)
	{
		if (copy.b.equals(BigInteger.ZERO))
			this.b=BigInteger.ZERO;
		else
			this.b=new BigInteger(copy.b.toString());
		this.m=copy.m;
		this.k=copy.k;
		bB=bigIntToIntArray(b);
	}

	  
	  /**
	   * Funkcja zwracająca liczbę elementów w ciele
	   * @return liczba elementów w ciele (domyślnie 2)
	   */
	  public int getFieldSize() {
	    return FIELDSIZE;
	  }
	  
	  /***
	   * Funkcja zwracająca długość binarną liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   * @return długość binarna liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   */
	  public int getLength()
	  {
		  return bB.length;
	  }
	  
	  /**
	   * Dodawanie binarne dwóch liczb typu {@link int}
	   * @param x liczba typu {@link int} - pierwszy składnik
	   * @param y liczba typu {@link int} - drugi składnik
	   * @return wynik sumy binarnej liczb {@link x} oraz {@link y}
	   */
	  public int add(int x, int y) {
		    assert(x >= 0 && x < getFieldSize() && y >= 0 && y < getFieldSize());
		    return x ^ y; ///// Operacja XOR w java
	  }
	  
	/**
	   * Obliczanie sumy dwóch wielomianów. Indeks w tablicy typu {@link int} odpowiada 
	   * wykładnikowi elementowi wejściowemu. Na przykład element {@link p[0]} odpowiada
	   * składnikowi wolnemu wielomianu. Implementacja operacji XOR w ciele GF{2^m}
	   * @param p wielomian wejściowy
	   * @param q wielomian wejściowy
	   * @return wielomina reprezenujący wynik operacji p+q
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
	   * wykładnikowi elementowi wejściowemu. Na przykład element {@link p[0]} odpowiada
	   * składnikowi wolnemu wielomianu. Implementacja operacji AND w ciele GF{2^m}
	   * @param p wielomian wejściowy
	   * @param q wielomian wejściowy
	   * @return wielomina reprezenujący wynik operacji p*q
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
	   * wielomian modulo zdefiniowany przez wykładniki {@link GF2Elem#m} oraz {@link GF2Elem#k}
	   * @return
	   */
	  public GF2Elem reduce()
	  {
		  for (int i = 0 ; i<=m; i++)
		  {
			  this.bB[m+i] = add(this.bB[m+i],this.bB[i]);
			  this.bB[m-k+i] = add(this.bB[m-k+i],this.bB[i]);
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
	   * @return obiekt typu {@link GF2Elem} przechowujący liczbę w formacie binarnym
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
	   * @param input tablica zawierająca bity liczby przeznaczonej do konwersji
	   * @return obiekt {@link BigInteger} zawierający przechowywaną liczbę
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
	  
	  /**
	   * Inwersja modulo liczby zawartej w {@link GF2Elem#bB}.
	   * Inwersja przeprowadzana jest przy pomocy rozszerzonego algorytmu Euklidesa
	   * Wielomian modulo zdefiniowany jest poprzez wykładniki {@link GF2Elem#m}
	   * oraz {@link GF2Elem#k}.
	   * @return obiekt {@link GF2Elem} przechowujący zmienną {@link GF2Elem#bB} w postaci
	   * binarnej będący inwersją modulo wielomianu pierwotnego
	   */
	  public GF2Elem inverse()
	  {
		  int[] u=this.bB;
		  int[] v=new int[u.length];
		  int[] g1=new int[u.length];
		  int[] g2=new int[u.length];
		  int[] temp=new int[u.length];
		  int[] z=new int[u.length];
		  for (int i=0;i<v.length;i++)
		  {
			  v[i]=0;
			  g1[i]=0;
			  g2[i]=0;
			  temp[i]=0;
			  z[i]=0;
		  }
		  v[0]=1;
		  v[this.m]=1;
		  v[this.m-this.k]=1;
		  g1[m]=1;
		  boolean end=false;
		  int degv,degu,j;
		  while(end==false)
		  {
			  degu=0;degv=0;
			  while(u[degu++]==0);
			  while(v[degv++]==0);
			  //j=deg(u)-deg(v)
			  degu=this.m-degu;
			  degv=this.m-degv;
			  j=degu-degv;
			  if (j<0)
			  {
				  //u↔v
				  temp=u;
				  u=v;
				  v=temp;
				  //g1↔g2
				  temp=g1;
				  g1=g2;
				  g2=temp;
				  //j←-j
				  j=0-j;
			  }
			  //u←u+z^j*v;
			  z[m-j]=1;
			  u=reduce(add(u,multiply(z,v)));
			  //g1←g1+z^j*g2;
			  g1=reduce(add(g1,multiply(z,g2)));		  
			  //u!=1
			  int sum=0;
			  for (int i=0;i<=m;i++)
				  sum+=u[i];
			  if (u[m]==1)
				  end=true;
			  if (sum==1 && end==true)
				  end=true;
			  else
				  end=false;
			  for (int i=0; i< z.length; i++)
				  z[i]=0;
		  }
		  this.bB = g1;
		  this.b = intArraytoBigInteger(this.bB);
		  return this;
	  }
	  
	  /**
	   * Prywatna metoda służąca do obliczenia iloczynu dwóch wielomianów
	   * Wykorzystywana w funkcji {@link GF2Elem#inverse}
	   * @param p wielomian
	   * @param q wielomian
	   * @return wynik mnożenia p*q
	   */
	  private int[] multiply(int[] p, int[] q) 
	  {
		  int len = p.length + q.length - 1;
		  int[] result = new int[len];
		  for (int i = 0; i < len; i++) 
		      result[i] = 0;
		  for (int i = 0; i < p.length; i++) 
		      for (int j = 0; j < q.length; j++)
		    	  result[i + j] = result[i + j]^p[i]*q[j];
		  return result;
	}
	  
	/**
	 * Redukcja wielomianu modulo wielomian zdefiniowany przez wykładniki
	 * {@link GF2Elem#m} oraz {@link GF2Elem#k}. Wykorzystywana w algorytmie
	 * {@link GF2Elem#inverse}.
	 * @param p wielomian przeznaczony do redukcji
	 * @return wielomian zredukowany
	 */
	private int[] reduce(int[] p)
	{
		for (int i = 0 ; i<=m; i++)
		{
			p[m+i] = add(p[m+i],p[i]);
			p[m-k+i] = add(p[m-k+i],p[i]);
		}
		int[] result = new int[m+1];
		for (int i=0;i<m;i++)
			result[i+1] = p[i+m+1];
		return result;
	}
	
	/**
	 * Suma dwóch wielomianów. Wykorzystywana w funkcji {@link GF2Elem#inverse}
	 * @param p pierwszy składnik
	 * @param q drugi składnik
	 * @return suma dwóch wielomianów
	 */
	private int[] add(int[] p, int[] q) 
	{
		int len = Math.max(p.length, q.length);
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) 
	    {
	      if (i < p.length && i < q.length) 
	    	  result[len-i-1] = add(p[p.length-i-1], q[q.length-i-1]);
	      else if (i < p.length) 
	    	  result[len-i-1] = p[p.length-i-1];
	      else 
	    	  result[len-i-1] = q[q.length-i-1];
	    }
	    return result;
	 }
}
