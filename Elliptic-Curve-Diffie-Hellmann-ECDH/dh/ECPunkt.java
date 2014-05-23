package dh;
import java.math.BigInteger;

/**
 * Klasa symbolizuj�ca punkt krzywej eliptycznej przechowuj�ca 
 * i daj�ca dost�p wsp�rz�dnych punkt�w oraz obliczaj�ca
 * wielokrotno�� punktu Q = [k]P
 * @author Vitali Karpinski
 * @author Robert Zag�rski
 *
 */
public class ECPunkt {

	private BigInteger gx;
	private BigInteger gy;
	private BigInteger a2;
	private BigInteger a6;
	private int m;
	private int k;
	org.bouncycastle.math.ec.ECCurve curve;
	org.bouncycastle.math.ec.ECPoint.F2m point;
	
	/***
	 * Konstruktor punktu klasy ECPunkt. W celu wygenerowania punktu nale�y poda� r�wnie�
	 * parametry cia�a binarnego, do kt�rego punkt ma nale�e�
	 * @param M d�ugo�� bitowa cia�a binarnego 
	 * @param K wyk�adnik �rodkowy wielomianu modulo w ciele (x^m + x^k + 1)
	 * @param A2 parametr krzywej eliptycznej
	 * @param A6 parametr krzywej eliptycznej - wolny wyraz
	 * @param xG wsp�rz�dna horyzontalna generatora liczb w ciele 
	 * @param yG wsp�rz�dna wertykalna generatora liczb w ciele
	 */
	public ECPunkt(int M,int K,BigInteger A2, BigInteger A6,BigInteger xG,BigInteger yG)
	{
		gx=xG;
		gy=yG;
		a2=A2;
		a6=A6;
		m=M;
		k=K;
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		curve = new org.bouncycastle.math.ec.ECCurve.F2m(m,k,a2,a6);
		point = (org.bouncycastle.math.ec.ECPoint.F2m) curve.createPoint(gx,gy,false);
	}
	
	/**
	 * Konstruktor kopiuj�cy klasy ECPunkt
	 * @param Punkt punkt nale��cy do tej samej krzywej eliptycznej, kt�ry nale�y skopiowa�
	 */
	public ECPunkt(ECPunkt Punkt)
	{
		gx=Punkt.getX();
		gy=Punkt.getY();
		a2=Punkt.getA2();
		a6=Punkt.getA6();
		m=Punkt.getm();
		k=Punkt.getk();
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		curve = Punkt.getCurve();
		point = Punkt.getPoint();//(org.bouncycastle.math.ec.ECPoint.F2m) curve.createPoint(Punkt.getX(),Punkt.getY(),false);
	}
	
	/**
	 * Funkcja pozwalaj�ca na operacj� podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, kt�ry nale�y podwoi�
	 * @return wynik operacji podwajania punktu
	 */
	private ECPunkt podwojeniePunktu (ECPunkt P)
	{
		ECPunkt Q = new ECPunkt(P);
		Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)Q.getPoint().twice());
		return Q;
	}
	
	/**
	 * Suma dw�ch r�nych punkt�w nale��cych do krzywej eliptycznej
	 * Q = P1 + P2 
	 * @param p pierwszy punkt
	 * @param q drugi punkt
	 * @return punkt b�d�cy sum� dw�ch punkt�w krzywej eliptycznej
	 */
	private ECPunkt sumaPunktow (ECPunkt P,ECPunkt Q)
	{
		ECPunkt wynik = new ECPunkt(P);
		wynik.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)P.getPoint().add(Q.getPoint()));
		return wynik;
	}
	
	/**
	 * Obliczenie wielokrotno�ci punktu krzywej eliptycznej metod� 
	 * przesuwaj�cych si� okienek (sliding windows)
	 * Q = [k]P 
	 * @param P punkt startowy oblicze�
	 * @param k liczba, o jak� punkt ma by� zwielokrotniony
	 * @return zwieloktrotniony punkt
	 */
	public ECPunkt wielokrotnoscPunktu(ECPunkt P, BigInteger k)
	{
		ECPunkt Q = new ECPunkt(P);
		Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)curve.getInfinity());
		String kInBits = k.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
			Q = podwojeniePunktu(Q);
			if(kInBits.charAt(j) == '1') 
				Q = sumaPunktow(Q,P);
		}
		return Q;
	}
	
	/**
	 * Funkcja zwracaj�ca wsp�rz�dn� horyzontaln� generatora liczb w ciele binarnym
	 * @return wsp�rz�dna horyzontalna generatora liczb
	 */
	public BigInteger getX()
	{
		return this.gx;
	}
	
	/**
	 * Funkcja ustalaj�ca warto�� wsp�rz�dnej horyzontaln� generatora liczb w ciele binarnym
	 * @param x liczba, kt�ra ma by� now� wsp�rz�dn� horyzontaln� generatora liczb
	 */
	public void setX(BigInteger x)
	{
		this.gx=x;
	}

	/**
	 * Funkcja zwracaj�ca wsp�rz�dn� horyzontaln� generatora liczb w ciele binarnym
	 * @return wsp�rz�dna horyzontalna generatora liczb
	 */
	public BigInteger getY()
	{
		return this.gy;
	}
	
	/**
	 * Funkcja ustalaj�ca warto�� wsp�rz�dnej wertykaln� generatora liczb w ciele binarnym
	 * @param x liczba, kt�ra ma by� now� wsp�rz�dn� wertykaln� generatora liczb
	 */
	public void setY(BigInteger y)
	{
		this.gy=y;
	}
	
	/**
	 * Fukcja zwracaj�ca parametr a2 krzywej eliptycznej
	 * @return parametr a2 krzywej eliptycznej
	 */
	public BigInteger getA2()
	{
		return this.a2;
	}
	
	/**
	 * Funkcja ustalaj�ca warto�� parametru a2 krzywej eliptycznej
	 * @param a2 nowa warto�� parametru a2 krzywej eliptycznej
	 */
	public void setA2(BigInteger a2)
	{
		this.a2=a2;
	}
	
	/**
	 * Fukcja zwracaj�ca parametr a2 krzywej eliptycznej
	 * @return parametr a2 krzywej eliptycznej
	 */
	public BigInteger getA6()
	{
		return this.a6;
	}
	
	/**
	 * Funkcja ustalaj�ca warto�� parametru a6 krzywej eliptycznej
	 * @param a2 nowa warto�� parametru a6 krzywej eliptycznej
	 */
	public void setA6(BigInteger a6)
	{
		this.a6=a6;
	}
	
	/**
	 * Funkcja zwracaj�ca maksymaln� liczb� bit�w cia�a binarnego
	 * @return liczba bit�w cia�a binarnego
	 */
	public int getm()
	{
		return this.m;
	}
	
	/**
	 * Funkcja ustalaj�ca maksymaln� liczb� bit�w cia�a binarnego
	 * @param m liczba bit�w cia�a binarnego
	 */
	public void setm(int m)
	{
		this.m=m;
	}
	
	/**
	 * Funkcja zwracaj�ca wyk�adnik �rodkowego wyrazu wielomianu modulo
	 * @return wyk�adnik �rodkowego wyrazu wielomianu modulo
	 */
	public int getk()
	{
		return this.k;
	}
	
	/**
	 * Funkcja ustalaj�ca wyk�adnik �rodkowego wyrazu wielomianu modulo
	 * @param k wyk�adnik �rodkowego wyrazu wielomianu modulo
	 */
	public void setk(int k)
	{
		this.k=k;
	}
	
	/**
	 * Funkcja zwracaj�ca obiekt krzywej eliptycznej
	 * @return obiekt krzywej eliptycznej
	 */
	public org.bouncycastle.math.ec.ECCurve getCurve()
	{
		return this.curve;
	}
	
	/**
	 * Funkcja ustalaj�ca obiekt krzywej eliptycznej 
	 * @param curve nowa krzywa elityczna
	 */
	public void setCurve(org.bouncycastle.math.ec.ECCurve curve)
	{
		this.curve=curve;
	}
	
	/**
	 * Funkcja zwracaj�ca punkt krzywej eliptycznej nale��cy do obiektu tej klasy
	 * @return punkt krzywej eliptycznej
	 */
	public org.bouncycastle.math.ec.ECPoint.F2m getPoint()
	{
		return this.point;
	}
	
	/**
	 * Funkcja ustalaj�ca warto�� punktu krzywej elitycznej tego obiektu klasy ECPunkt
	 * @param point2 nowy punkt
	 */
	public void setPoint(org.bouncycastle.math.ec.ECPoint.F2m point2)
	{
		point=point2;
	}
}

