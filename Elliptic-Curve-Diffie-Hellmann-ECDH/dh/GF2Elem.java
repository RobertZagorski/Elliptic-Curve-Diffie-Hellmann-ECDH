package dh;

import java.math.BigInteger;

/**
 * Klasa implementuj�ca element cia�a {@code GF(2^m)}. 
 * W klasie zdefiniowano operacje na ciele binarnym: dodawanie, mno�enie, podnoszenie
 * do kwadratu. Dodatkowo zaimplementowano konwersj� liczby w postaci binarnej 
 * zaimplementowanej jako {@code int[]} na liczbE typu {@link BigInteger}. <p>
 * Do implementacji operacji modulo wykorzystywany jest tr�jmian postaci {@code x^m+x^k+1}
 */

public class GF2Elem {
	/**Rozmiar cyklicznej grupy multiplikatywnej, na kt�rej zaimplementowano dany element*/
	private static final int FIELDSIZE = 2;
	/**Liczba opisywana przez obiekt tej klasy typu {@link BigInteger}*/
	BigInteger b;
	/**Liczba opisywana przez obiekt tej klasy w postaci {@code int[]}*/
	int[] bB;
	/**Wyk�adnik najwy�szego sk�adnika wielomianu modulo dla cia�a - rz�d wielomianu*/
	int m;
	/**Element �rodkowy wielomianu modulo dla cia�a*/
	int k;
	
	/***
	 * Konstruktor klasy {@link GF2Elem}
	 * @param bi Liczba typu {@link BigInteger} przechowuj�ca obiekt w formanie innym ni� binarnym
	 * @param mm Wyk�adnik najwy�szego sk�adnika wielomianu modulo dla cia�a - rz�d wielomianu
	 * @param kk Element �rodkowy wielomianu modulo dla cia�a
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
	
	/**
	 * Konstruktor kopiuj�cy klasy {@link GF2Elem}
	 * @param copy obiekt kopiowany
	 */
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
	   * Funkcja zwracaj�ca liczb� element�w w ciele
	   * @return liczba element�w w ciele (domy�lnie 2)
	   */
	  public int rozmiarGrupySkonczonej() {
	    return FIELDSIZE;
	  }
	  
	  /***
	   * Funkcja zwracaj�ca d�ugo�� binarn� liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   * @return d�ugo�� binarna liczby przechowywanej w obiekcie typu {@link GF2Elem}
	   */
	  public int dlugoscBitowa()
	  {
		  return bB.length;
	  }
	  
	  /**
	   * Dodawanie binarne dw�ch liczb typu {@code int}
	   * @param x liczba typu {@code int} - pierwszy sk�adnik
	   * @param y liczba typu {@code int} - drugi sk�adnik
	   * @return wynik sumy binarnej liczb
	   */
	  public int dodaj(int x, int y) {
		    assert(x >= 0 && x < rozmiarGrupySkonczonej() && y >= 0 && y < rozmiarGrupySkonczonej());
		    return x ^ y; ///// Operacja XOR w java
	  }
	  
	/**
	   * Obliczanie sumy dw�ch wielomianów. Indeks w tablicy typu {@code int} odpowiada 
	   * wyk�adnikowi elementowi wej�ciowemu. Na przyk�ad element {@code p[m]} odpowiada
	   * sk�adnikowi wolnemu wielomianu. Implementacja operacji XOR w ciele GF{2^m}
	   * @param Q wielomian, kt�rego warto�� jest sk�adnikiem sumy
	   * @return wielomina reprezenuj�cy wynik operacji p+q
	   */
	  public GF2Elem dodaj(GF2Elem Q) 
	  {
		int[] q = Q.bB;
	    int len = Math.max(this.dlugoscBitowa(), q.length);
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) {
	      if (i < this.dlugoscBitowa() && i < q.length) {
	        result[i] = dodaj(this.bB[i], q[i]);
	      } else if (i < this.dlugoscBitowa()) {
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
	   * Obliczanie iloczynu dw�ch wielomian�w. Indeks w tablicy typu {@code int} odpowiada 
	   * wyk�adnikowi elementowi wej�ciowemu. Na przyk�ad element {@code p[m]} odpowiada
	   * sk�adnikowi wolnemu wielomianu. Implementacja operacji AND w ciele GF{2^m}
	   * @param Q element cia�a, kt�rego warto�� jest sk�adnikiem iloczynu
	   * @return wielomina reprezenuj�cy wynik operacji p*q
	   */
	  public GF2Elem pomnoz(GF2Elem Q) 
	  {
		int[] q = Q.bB;
	    int len = this.dlugoscBitowa() + q.length - 1;
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) {
	      result[i] = 0;
	    }
	    for (int i = 0; i < this.dlugoscBitowa(); i++) {
	      for (int j = 0; j < q.length; j++) {
	        result[i + j] = dodaj(result[i + j], this.bB[i]*q[j]);
	      }
	    }
	    this.bB = result;
	    redukuj();
	    this.b = intArraytoBigInteger(this.bB);
	    return this;
	  }
	  
	  /**
	   * Redukcja wielomianu przechowywanego w elemenencie {@link GF2Elem#bB} przez
	   * wielomian modulo zdefiniowany przez wyk�adniki {@link GF2Elem#m} oraz {@link GF2Elem#k}
	   * @return Obiekt typu {@link GF2Elem} reprezentuj�cy zredukowany wielomian
	   */
	  public GF2Elem redukuj()
	  {
		  for (int i = 0 ; i<=m; i++)
		  {
			  this.bB[m+i] = dodaj(this.bB[m+i],this.bB[i]);
			  this.bB[m-k+i] = dodaj(this.bB[m-k+i],this.bB[i]);
		  }
		  int[] result = new int[m+1];
		  result[0]=0;
		  for (int i=1;i<=m;i++)
		  {
			  result[i] = this.bB[i+m];
		  }
		  this.bB = result;
		  return this;
	  }

	  /**
	   * Operacja podnoszenia do kwadratu wielomianu przechowywanego w elemenencie {@link GF2Elem#bB}
	   * @return obiekt typu {@link GF2Elem} przechowuj�cy liczb� w formacie binarnym
	   */
	  public GF2Elem doKwadratu()
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
		  redukuj();
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
	   * @param input tablica zawieraj�ca bity liczby przeznaczonej do konwersji
	   * @return obiekt {@link BigInteger} zawieraj�cy przechowywan� liczb�
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
	   * Inwersja modulo liczby zawartej w {@link GF2Elem#bB} 
	   * (szukanie liczby spe�niaj�cej r�wnanie {@code ax=1(mod n)}).
	   * Inwersja przeprowadzana jest przy pomocy rozszerzonego algorytmu Euklidesa.
	   * Wielomian modulo zdefiniowany jest poprzez wyk�adniki {@link GF2Elem#m}
	   * oraz {@link GF2Elem#k}.
	   * @return obiekt {@link GF2Elem} przechowuj�cy zmienn� {@link GF2Elem#bB} w postaci
	   * binarnej b�d�cy inwersj� modulo wielomianu pierwotnego
	 * @throws Exception wyj�tek rzucany jest je�li wielomian przeznaczony do inwersji nie jest wzgl�dnie pierwsza z wielomianem modulo
	   */
	  public GF2Elem odwrocModulo() throws Exception
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
		  // Je�li liczby a oraz n (wielomian modulo) nie s� wzgl�dnie pierwsze
		  if (!this.b.gcd(intArraytoBigInteger(v)).equals(BigInteger.ONE))
			  throw new Exception("Wielomian, kt�ry nale�y odwr�ci� nie jest wzgl�dnie pierwszy z wielomianem modulo");
		  g1[m]=1;
		  boolean end=false;
		  int degv,degu,j;
		  while(end==false)
		  {
			  degu=0;degv=0;
			  while(u[degu]==0) 
			  {
				  degu++;
				  if(degu == u.length)
				  {
					  degu=u.length-1;
					  break;
				  }
			  }
			  while(v[degv]==0)
			  {
				  degv++;
				  if(degv == v.length)
				  {
					  degv=v.length-1;
					  break;
				  }
			  }
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
				  //j<- -j
				  j=0-j;
			  }
			  //u<- u+z^j*v;
			  z[m-j]=1;
			  u=redukuj(dodaj(u,pomnoz(z,v)));
			  //g1<- g1+z^j*g2;
			  g1=redukuj(dodaj(g1,pomnoz(z,g2)));		  
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
	   * Prywatna metoda s�u��ca do obliczenia iloczynu dw�ch wielomian�w
	   * Wykorzystywana w funkcji {@link GF2Elem#odwrocModulo}
	   * @param p wielomian
	   * @param q wielomian
	   * @return wynik mnożenia p*q
	   */
	  private int[] pomnoz(int[] p, int[] q) 
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
	 * Redukcja wielomianu modulo wielomian zdefiniowany przez wyk�adniki
	 * {@link GF2Elem#m} oraz {@link GF2Elem#k}. Wykorzystywana w algorytmie
	 * {@link GF2Elem#odwrocModulo}.
	 * @param p wielomian przeznaczony do redukcji
	 * @return wielomian zredukowany
	 */
	private int[] redukuj(int[] p)
	{
		for (int i = 0 ; i<=m; i++)
		{
			p[m+i] = dodaj(p[m+i],p[i]);
			p[m-k+i] = dodaj(p[m-k+i],p[i]);
		}
		int[] result = new int[m+1];
		for (int i=0;i<m;i++)
			result[i+1] = p[i+m+1];
		return result;
	}
	
	/**
	 * Suma dw�ch wielomian�w. Wykorzystywana w funkcji {@link GF2Elem#odwrocModulo}
	 * @param p pierwszy sk�adnik
	 * @param q drugi sk�adnik
	 * @return suma dw�ch wielomian�w
	 */
	private int[] dodaj(int[] p, int[] q) 
	{
		int len = Math.max(p.length, q.length);
	    int[] result = new int[len];
	    for (int i = 0; i < len; i++) 
	    {
	      if (i < p.length && i < q.length) 
	    	  result[len-i-1] = dodaj(p[p.length-i-1], q[q.length-i-1]);
	      else if (i < p.length) 
	    	  result[len-i-1] = p[p.length-i-1];
	      else 
	    	  result[len-i-1] = q[q.length-i-1];
	    }
	    return result;
	 }
}
