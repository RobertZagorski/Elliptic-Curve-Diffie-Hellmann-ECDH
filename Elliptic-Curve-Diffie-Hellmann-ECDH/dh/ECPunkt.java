package dh;
import java.math.BigInteger;
import java.util.BitSet;

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
	
	private GF2Elem x;
	private GF2Elem y;
	private GF2Elem z;
	
	
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
		
		//gx, gy przerobiæ na wsp rzutowe (gxR, gyR)
		//x = GF2Elem(gxR, m, k);
		//y = GF2Elem(gyR, m, k);
		//z = ????
	}
	
	/**
	 * Konstruktor kopiuj¹cy klasy ECPunkt
	 * @param Punkt punkt nale¿¹cy do tej samej krzywej eliptycznej, który nale¿y skopiowaæ
	 */
	public ECPunkt(ECPunkt Punkt)
	{
		gx=Punkt.getGx();
		gy=Punkt.getGy();
		a2=Punkt.getA2();
		a6=Punkt.getA6();
		m=Punkt.getm();
		k=Punkt.getk();
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		curve = Punkt.getCurve();
		point = Punkt.getPoint();//(org.bouncycastle.math.ec.ECPoint.F2m) curve.createPoint(Punkt.getX(),Punkt.getY(),false);
		
		//x = Punkt.x;
		//y = Punkt.y;
		//z = Punkt.z;
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
		//Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)Q.getPoint().twice());
		
		GF2Elem dwa = new GF2Elem(new BigInteger("2"), getm(), getk());
		GF2Elem trzy = new GF2Elem(new BigInteger("3"), getm(), getk());
		GF2Elem cztery = new GF2Elem(new BigInteger("4"), getm(), getk());
		GF2Elem osiem = new GF2Elem(new BigInteger("8"), getm(), getk());
		GF2Elem a2 = new GF2Elem(Q.a2, getm(), getk());
		
		GF2Elem A1 = ( Q.x.square() ).multiply(trzy.bB);
		GF2Elem A2 = ( Q.z.square() ).multiply(a2.bB);
		GF2Elem A = A1.add(A2.bB);
		GF2Elem B = Q.y.multiply(Q.z.bB);
		GF2Elem C = ( Q.x.multiply(Q.y.bB) ).multiply(B.bB);
		GF2Elem D1 = A.square();
		GF2Elem D2 = C.multiply(osiem.bB);
		GF2Elem D = D1.add(D2.bB);
		
		Q.x = ( dwa.multiply(B.bB) ).multiply(D.bB);
		GF2Elem temp1 = C.multiply(cztery.bB);
		temp1 = temp1.add(D.bB);
		temp1 = temp1.multiply(A.bB);
		GF2Elem temp2 = ( Q.y.square() ).multiply(osiem.bB);
		temp2 = ( B.square() ).multiply(temp2.bB);
		Q.y = temp1.add(temp2.bB);
		Q.z = (( B.square() ).multiply(B.bB)).multiply(osiem.bB);
		
		//x, y przerobiæ na wsp afiniczne (xA, yA)
		//Q.setGx(Q.xA.b);
		//Q.setGy(Q.yA.b);
		
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
		//wynik.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)P.getPoint().add(Q.getPoint()));
		
		GF2Elem dwa = new GF2Elem(new BigInteger("2"), getm(), getk());
		
		GF2Elem A1 = Q.y.multiply(P.z.bB);
		GF2Elem A2 = P.y.multiply(Q.z.bB);
		GF2Elem A = A1.add(A2.bB);
		GF2Elem B1 = Q.x.multiply(P.z.bB);
		GF2Elem B2 = P.x.multiply(Q.z.bB);
		GF2Elem B = B1.add(B2.bB);
		GF2Elem C1 = ( ( A.square() ).multiply(P.z.bB) ).multiply(Q.z.bB);
		GF2Elem C2 = ( B.square() ).multiply(B.bB);
		GF2Elem C3 = ( ( ( B.square() ).multiply(dwa.bB) ).multiply(P.x.bB) ).multiply(Q.z.bB);
		GF2Elem C = ( C1.add(C2.bB) ).add(C3.bB);
		
		wynik.x = B.multiply(C.bB);
		wynik.z = ( ( ( B.square() ).multiply(B.bB) ).multiply(P.z.bB) ).multiply(Q.z.bB);
		GF2Elem temp1 = ( ( B.square() ).multiply(P.x.bB) ).multiply(Q.z.bB);
		temp1 = temp1.add(C.bB);
		temp1 = temp1.multiply(A.bB);
		GF2Elem temp2 = ( ( ( B.square() ).multiply(B.bB) ).multiply(P.y.bB) ).multiply(Q.y.bB);
		wynik.y = temp1.add(temp2.bB);
		
		//x, y przerobiæ na wsp afiniczne (xA, yA)
		//Q.setGx(Q.xA.b);
		//Q.setGy(Q.yA.b);
		
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
	public BigInteger getGx()
	{
		return this.gx;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ wspó³rzêdnej horyzontaln¹ generatora liczb w ciele binarnym
	 * @param x liczba, która ma byæ now¹ wspó³rzêdn¹ horyzontaln¹ generatora liczb
	 */
	public void setGx(BigInteger x)
	{
		this.gx=x;
	}

	/**
	 * Funkcja zwracaj¹ca wspó³rzêdn¹ horyzontaln¹ generatora liczb w ciele binarnym
	 * @return wspó³rzêdna horyzontalna generatora liczb
	 */
	public BigInteger getGy()
	{
		return this.gy;
	}
	
	/**
	 * Funkcja ustalaj¹ca wartoœæ wspó³rzêdnej wertykaln¹ generatora liczb w ciele binarnym
	 * @param x liczba, która ma byæ now¹ wspó³rzêdn¹ wertykaln¹ generatora liczb
	 */
	public void setGy(BigInteger y)
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
	
	
	/*public boolean lezyNaEC() {
		BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
		BigInteger THREE = TWO.add(BigInteger.ONE);
		BigInteger modulo = TWO.pow(m).add(TWO.pow(k)).add(BigInteger.ONE);
		
		BigInteger left;
		BigInteger right;
		
		
		left = gy.modPow(TWO, modulo);
		left = left.add(gx.multiply(gy).mod(modulo)).mod(modulo);
		
		right = gx.modPow(THREE, modulo);
		right = right.add(a2.multiply(gx.modPow(TWO, modulo)).mod(modulo)).mod(modulo);
		right = right.add(a6).mod(modulo);
		
		left = gy.pow(2);
		left = left.add(gx.multiply(gy));
		
		right = gx.pow(3);
		right = right.add(a2.multiply(gx.pow(2)));
		right = right.add(a6);
		
		if (left == right) {
			return true;
		} else {
			return false;
		}
	}*/
	
}

