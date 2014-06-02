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

	private static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE); 
	BigInteger gx;
	BigInteger gy;
	BigInteger modulo;
	BigInteger a2;
	BigInteger a6;
	int m;
	int k;
	
	GF2Elem X;
	GF2Elem Y;
	GF2Elem Z;
	
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
		//TODO sprawdzaæ czy d³ugoœæ bitowa liczby jest <= m
		gx=xG;
		gy=yG;
		X=new GF2Elem(xG,M,K);
		Y=new GF2Elem(yG,M,K);
		a2=A2;
		a6=A6;
		m=M;
		k=K;
		modulo = TWO.pow(m).subtract(TWO.pow(k)).add(BigInteger.ONE);
		Z = new GF2Elem(new BigInteger("2"), m, k);
	}
	
	/**
	 * Konstruktor kopiuj¹cy klasy ECPunkt
	 * @param Punkt punkt nale¿¹cy do tej samej krzywej eliptycznej, który nale¿y skopiowaæ
	 */
	public ECPunkt(ECPunkt Punkt)
	{
		gx=Punkt.gx;
		gy=Punkt.gy;
		a2=Punkt.a2;
		a6=Punkt.a6;
		m=Punkt.m;
		k=Punkt.k;
		X=new GF2Elem(Punkt.X);
		Y=new GF2Elem(Punkt.Y);
		Z=new GF2Elem(Punkt.Z);
		//modulo = Punkt.modulo;
	}
	
	/**
	 * Funkcja pozwalaj¹ca na operacjê podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, który nale¿y podwoiæ
	 * @return wynik operacji podwajania punktu
	 */
	private ECPunkt podwojeniePunktu (ECPunkt P)
	{
		
		if (P.X.b.equals(BigInteger.ZERO) && P.Y.b.equals(BigInteger.ZERO))
		{
			return P;
		}
		///////////////////F2m-wspó³rzêdne rzutowe/////////
		ECPunkt Q = new ECPunkt(P);
		
		GF2Elem dwa = new GF2Elem(new BigInteger("2"), m, k);
		GF2Elem trzy = new GF2Elem(new BigInteger("3"), m, k);
		GF2Elem cztery = new GF2Elem(new BigInteger("4"), m, k);
		GF2Elem osiem = new GF2Elem(new BigInteger("8"), m, k);
		GF2Elem a2 = new GF2Elem(Q.a2, m, k);
		
		GF2Elem A1 = new GF2Elem(Q.X);		( A1.square() ).multiply(trzy.bB);
		GF2Elem A2 = new GF2Elem(Q.Z);		( A2.square() ).multiply(a2.bB);
		GF2Elem A = new GF2Elem(A1);		A.add(A2.bB);
		GF2Elem B = new GF2Elem(Q.Y);		B.multiply(Q.Z.bB);
		GF2Elem C = new GF2Elem(Q.X);		( C.multiply(Q.Y.bB) ).multiply(B.bB);
		GF2Elem D1 = new GF2Elem(A);		D1.square();
		GF2Elem D2 = new GF2Elem(C);		D2.multiply(osiem.bB);
		GF2Elem D = new GF2Elem(D1);		D.add(D2.bB);
		
		Q.X = dwa;							( Q.X.multiply(B.bB) ).multiply(D.bB);
		GF2Elem temp1 = C;					temp1.multiply(cztery.bB);
		temp1.add(D.bB);
		temp1.multiply(A.bB);
		GF2Elem temp2 = Q.Y;				( temp2.square() ).multiply(osiem.bB);
											temp2.multiply(B.square().bB);
		Q.Y = temp1;						Q.Y.add(temp2.bB);
		Q.Z = B;							(( Q.Z.square() ).multiply(B.bB)).multiply(osiem.bB);
		return Q;
		////////////////////F2m///////////////////////////
//		BigInteger lambda = P.getY().divide(P.getX()).mod(modulo);
//		lambda=lambda.add(P.getX()).mod(modulo);
//		BigInteger x3 = lambda.modPow(TWO,modulo);
//		x3=x3.add(lambda).mod(modulo);
//		x3=x3.add(a2).mod(modulo);
//		BigInteger y3 = lambda.add(BigInteger.ONE).mod(modulo);
//		y3=y3.multiply(x3).mod(modulo);
//		y3 =y3.add(P.getX().modPow(TWO,modulo)).mod(modulo);
//		P.setX(x3);
//		P.setY(y3);
//		return P;
		/////////////////////Fp//////////////////////////
//		BigInteger lambda =(P.X.modPow(TWO,modulo)).multiply(TWO.add(BigInteger.ONE)).mod(modulo);
//		lambda=lambda.add(a2);
//		lambda=lambda.multiply((P.Y.multiply(TWO)).mod(modulo).modInverse(modulo)).mod(modulo);
//		BigInteger x3 = lambda.modPow(TWO,modulo).subtract(P.X.multiply(TWO).mod(modulo)).mod(modulo);
//		BigInteger y3 = P.X.subtract(x3).mod(modulo).multiply(lambda).mod(modulo).subtract(P.Y).mod(modulo);
//		P.X=x3;
//		P.Y=y3;
//		return P;
	}
	
	/**
	 * Suma dwóch ró¿nych punktów nale¿¹cych do krzywej eliptycznej
	 * Q = P1 + P2 
	 * @param p pierwszy punkt
	 * @param q drugi punkt
	 * @return punkt bêd¹cy sum¹ dwóch punktów krzywej eliptycznej
	 */
	private ECPunkt sumaPunktow (ECPunkt Q,ECPunkt P)
	{
		if (Q.X.b.equals(P.X.b) && Q.Y.b.equals(P.Y.b))
		{
			return podwojeniePunktu(Q);
		}
		if (Q.X.b.equals(BigInteger.ZERO) && Q.Y.b.equals(BigInteger.ZERO))
		{
			return new ECPunkt(P);
		}
		/////////////////F2m - wspó³rzêdne rzutowe////////////////
		ECPunkt wynik = new ECPunkt(P);
		GF2Elem dwa = new GF2Elem(new BigInteger("2"), m, k);
		
		GF2Elem A1 = new GF2Elem(Q.Y);		A1.multiply(P.Z.bB);
		GF2Elem A2 = new GF2Elem(P.Y);		A2.multiply(Q.Z.bB);
		GF2Elem A = new GF2Elem(A1);		A.add(A2.bB);
		GF2Elem B1 = new GF2Elem(Q.X);		B1.multiply(P.Z.bB);
		GF2Elem B2 = new GF2Elem(P.X);		B2.multiply(Q.Z.bB);
		GF2Elem B = new GF2Elem(B1);		B.add(B2.bB);
		GF2Elem C1 = new GF2Elem(A);		( ( C1.square() ).multiply(P.Z.bB) ).multiply(Q.Z.bB);
		GF2Elem C2 = new GF2Elem(B);		( C2.square() ).multiply(B.bB);
		GF2Elem C3 = new GF2Elem(B);		( ( ( C3.square() ).multiply(dwa.bB) ).multiply(P.X.bB) ).multiply(Q.Z.bB);
		GF2Elem C = new GF2Elem(C1);		( C.add(C2.bB) ).add(C3.bB);
		
		wynik.X = new GF2Elem(B);			X.multiply(C.bB);
		wynik.Z = new GF2Elem(B);			( ( ( Z.square() ).multiply(B.bB) ).multiply(P.Z.bB) ).multiply(Q.Z.bB);
		GF2Elem temp1 = new GF2Elem(B);		( ( temp1.square() ).multiply(P.X.bB) ).multiply(Q.Z.bB);
		temp1.add(C.bB);
		temp1.multiply(A.bB);
		GF2Elem temp2 = new GF2Elem(B);		( ( ( temp2.square() ).multiply(B.bB) ).multiply(P.Y.bB) ).multiply(Q.Y.bB);
		wynik.Y = new GF2Elem(temp1);		wynik.Y.add(temp2.bB);
		return wynik;
//		///////////////////////F2m/////////////////////////////////
//		BigInteger lambda = (Q.getY().add(P.getY()).mod(modulo) );
//		lambda = lambda.divide((Q.getX().add(P.getX()))).mod(modulo);
//		BigInteger x3 = lambda.modPow(TWO,modulo);
//		x3=x3.add(lambda).mod(modulo);
//		x3=x3.add(Q.getX()).mod(modulo);
//		x3=x3.add(P.getX()).mod(modulo);
//		x3=x3.add(this.a2).mod(modulo);
//		BigInteger y3 = Q.getX().add(x3).mod(modulo);
//		y3=y3.multiply(lambda).mod(modulo);
//		y3=y3.add(x3).mod(modulo);
//		y3=y3.add(Q.getY()).mod(modulo);
//		Q.setX(x3);
//		Q.setY(y3);
//		return Q;
		///////////////////////Fp///////////////////////////////
//		BigInteger lambda =P.Y.subtract(Q.Y).mod(modulo);
//		lambda=lambda.multiply(P.X.subtract(Q.X).mod(modulo).modInverse(modulo)).mod(modulo);
//		BigInteger x3 = lambda.modPow(TWO,modulo).subtract(Q.X).subtract(P.X).mod(modulo);
//		BigInteger y3 = Q.X.subtract(x3).mod(modulo).multiply(lambda).mod(modulo).subtract(Q.Y).mod(modulo);
//		Q.X=x3;
//		Q.Y=y3;
//		return Q;
	}
	
//	private BigInteger addShOp(BigInteger a, BigInteger b)
//	{
//		String atemp = a.toString(2);
//		String btemp = b.toString(2);
//		int[] aInBits = new int[a.bitLength()];
//		int[] bInBits = new int[b.bitLength()];
//		for (int i=0;i<this.getX().bitLength();i++)
//		{	
//			aInBits[i] = (int)atemp.charAt(i);
//			bInBits[i] = (int)btemp.charAt(i);
//		}
//		for (int i = 0; i < b.bitLength(); i++)
//		{
//			aInBits[i] ^= bInBits[i];
//		}
//		a = new BigInteger(Arrays.toString(aInBits));
//		return a;
//	}
	
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
		P.wspAfiniczneNaRzutowe();
		ECPunkt Q = new ECPunkt(this.m, this.k, this.a2, this.a6, BigInteger.ZERO, BigInteger.ZERO);
		String kInBits = k.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
			Q = podwojeniePunktu(Q);
			if(kInBits.charAt(j) == '1')
				Q = sumaPunktow(Q,P);
		}
		Q.wspRzutoweNaAfiniczne();
		return Q;
	}
	
	/**
	 * Konwersja wspó³rzêdnych z rzutowych na afiniczne 
	 */
	public void wspRzutoweNaAfiniczne()
	{
		this.X = this.X.multiply(this.Z.bB);
		this.Y = this.Y.multiply(this.Z.bB);
	}
	
	/**
	 * Konwesja wspó³rzêdnych z afinicznych na rzutowe
	 */
	public void wspAfiniczneNaRzutowe()
	{
		this.X = this.X.multiply((this.Z.inverse()).bB);
		this.Y = this.Y.multiply((this.Z.inverse()).bB);
	}
}
