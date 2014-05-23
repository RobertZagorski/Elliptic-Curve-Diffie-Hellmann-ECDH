package dh;
import java.math.BigInteger;

/**
 * Klasa symbolizuj¹ca punkt krzywej eliptycznej przechowuj¹ca 
 * i daj¹ca dostêp wspó³rzêdnych punktów oraz obliczaj¹ca
 * wielokrotnoœæ punktu Q = [k]P
 * @author Vitali Karpinski
 * @author Robert Zagórski
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
	 * Konstruktor punktu klasy ECPunkt. W celu wygenerowania punktu nale¿y podaæ równie¿
	 * parametry cia³a binarnego, do którego punkt ma nale¿eæ
	 * @param M d³ugoœæ bitowa cia³a binarnego 
	 * @param K wyk³adnik œrodkowy wielomianu modulo w ciele (x^m + x^k + 1)
	 * @param A2 parametr krzywej eliptycznej
	 * @param A6 parametr krzywej eliptycznej - wolny wyraz
	 * @param xG wspó³rzêdna horyzontalna generatora liczb w ciele 
	 * @param yG wspó³rzêdna wertykalna generatora liczb w ciele
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
	 * Konstruktor kopiuj¹cy klasy ECPunkt
	 * @param Punkt punkt nale¿¹cy do tej samej krzywej eliptycznej, który nale¿y skopiowaæ
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
	 * Funkcja pozwalaj¹ca na operacjê podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, który nale¿y podwoiæ
	 * @return wynik operacji podwajania punktu
	 */
	private ECPunkt podwojeniePunktu (ECPunkt P)
	{
		ECPunkt Q = new ECPunkt(P);
		Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)Q.getPoint().twice());
		return Q;
	}
	
	/**
	 * Suma dwóch ró¿nych punktów nale¿¹cych do krzywej eliptycznej
	 * Q = P1 + P2 
	 * @param p pierwszy punkt
	 * @param q drugi punkt
	 * @return punkt bêd¹cy sum¹ dwóch punktów krzywej eliptycznej
	 */
	private ECPunkt sumaPunktow (ECPunkt P,ECPunkt Q)
	{
		ECPunkt wynik = new ECPunkt(P);
		wynik.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)P.getPoint().add(Q.getPoint()));
		return wynik;
	}
	
	/**
	 * Obliczenie wielokrotnoœci punktu krzywej eliptycznej metod¹ 
	 * przesuwaj¹cych siê okienek (sliding windows)
	 * Q = [k]P 
	 * @param P punkt startowy obliczeñ
	 * @param k liczba, o jak¹ punkt ma byæ zwielokrotniony
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
	 * Funkcja zwracaj¹ca wspó³rzêdn¹ horyzontaln¹ generatora liczb w ciele binarnym
	 * @return wspó³rzêdna horyzontalna generatora liczb
	 */
	public BigInteger getX()
	{
		return this.gx;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ wspó³rzêdnej horyzontaln¹ generatora liczb w ciele binarnym
	 * @param x liczba, która ma byæ now¹ wspó³rzêdn¹ horyzontaln¹ generatora liczb
	 */
	public void setX(BigInteger x)
	{
		this.gx=x;
	}

	/**
	 * Funkcja zwracaj¹ca wspó³rzêdn¹ horyzontaln¹ generatora liczb w ciele binarnym
	 * @return wspó³rzêdna horyzontalna generatora liczb
	 */
	public BigInteger getY()
	{
		return this.gy;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ wspó³rzêdnej wertykaln¹ generatora liczb w ciele binarnym
	 * @param x liczba, która ma byæ now¹ wspó³rzêdn¹ wertykaln¹ generatora liczb
	 */
	public void setY(BigInteger y)
	{
		this.gy=y;
	}
	
	/**
	 * Fukcja zwracaj¹ca parametr a2 krzywej eliptycznej
	 * @return parametr a2 krzywej eliptycznej
	 */
	public BigInteger getA2()
	{
		return this.a2;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ parametru a2 krzywej eliptycznej
	 * @param a2 nowa wartoœæ parametru a2 krzywej eliptycznej
	 */
	public void setA2(BigInteger a2)
	{
		this.a2=a2;
	}
	
	/**
	 * Fukcja zwracaj¹ca parametr a2 krzywej eliptycznej
	 * @return parametr a2 krzywej eliptycznej
	 */
	public BigInteger getA6()
	{
		return this.a6;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ parametru a6 krzywej eliptycznej
	 * @param a2 nowa wartoœæ parametru a6 krzywej eliptycznej
	 */
	public void setA6(BigInteger a6)
	{
		this.a6=a6;
	}
	
	/**
	 * Funkcja zwracaj¹ca maksymaln¹ liczbê bitów cia³a binarnego
	 * @return liczba bitów cia³a binarnego
	 */
	public int getm()
	{
		return this.m;
	}
	
	/**
	 * Funkcja ustalaj¹ca maksymaln¹ liczbê bitów cia³a binarnego
	 * @param m liczba bitów cia³a binarnego
	 */
	public void setm(int m)
	{
		this.m=m;
	}
	
	/**
	 * Funkcja zwracaj¹ca wyk³adnik œrodkowego wyrazu wielomianu modulo
	 * @return wyk³adnik œrodkowego wyrazu wielomianu modulo
	 */
	public int getk()
	{
		return this.k;
	}
	
	/**
	 * Funkcja ustalaj¹ca wyk³adnik œrodkowego wyrazu wielomianu modulo
	 * @param k wyk³adnik œrodkowego wyrazu wielomianu modulo
	 */
	public void setk(int k)
	{
		this.k=k;
	}
	
	/**
	 * Funkcja zwracaj¹ca obiekt krzywej eliptycznej
	 * @return obiekt krzywej eliptycznej
	 */
	public org.bouncycastle.math.ec.ECCurve getCurve()
	{
		return this.curve;
	}
	
	/**
	 * Funkcja ustalaj¹ca obiekt krzywej eliptycznej 
	 * @param curve nowa krzywa elityczna
	 */
	public void setCurve(org.bouncycastle.math.ec.ECCurve curve)
	{
		this.curve=curve;
	}
	
	/**
	 * Funkcja zwracaj¹ca punkt krzywej eliptycznej nale¿¹cy do obiektu tej klasy
	 * @return punkt krzywej eliptycznej
	 */
	public org.bouncycastle.math.ec.ECPoint.F2m getPoint()
	{
		return this.point;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ punktu krzywej elitycznej tego obiektu klasy ECPunkt
	 * @param point2 nowy punkt
	 */
	public void setPoint(org.bouncycastle.math.ec.ECPoint.F2m point2)
	{
		point=point2;
	}
}

