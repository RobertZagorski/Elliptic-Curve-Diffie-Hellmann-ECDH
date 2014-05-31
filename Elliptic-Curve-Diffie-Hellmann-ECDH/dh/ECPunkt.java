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
	BigInteger X;
	BigInteger Y;
	BigInteger modulo;
	BigInteger a2;
	BigInteger a6;
	int m;
	int k;
	
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
		X=xG;
		Y=yG;
		a2=A2;
		a6=A6;
		m=M;
		k=K;
		modulo = TWO.pow(m).subtract(TWO.pow(k)).add(BigInteger.ONE);
	}
	
	/**
	 * Konstruktor kopiuj¹cy klasy ECPunkt
	 * @param Punkt punkt nale¿¹cy do tej samej krzywej eliptycznej, który nale¿y skopiowaæ
	 */
	public ECPunkt(ECPunkt Punkt)
	{
		gx=Punkt.X;
		gy=Punkt.Y;
		a2=Punkt.a2;
		a6=Punkt.a6;
		m=Punkt.m;
		k=Punkt.k;
		X=Punkt.X;
		Y=Punkt.Y;
		modulo = Punkt.modulo;
	}
	
	/**
	 * Funkcja pozwalaj¹ca na operacjê podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, który nale¿y podwoiæ
	 * @return wynik operacji podwajania punktu
	 */
	private ECPunkt podwojeniePunktu (ECPunkt P)
	{
		
		if (P.X == BigInteger.ZERO && P.Y == BigInteger.ZERO)
		{
			return P;
		}
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
		BigInteger lambda =(P.X.modPow(TWO,modulo)).multiply(TWO.add(BigInteger.ONE)).mod(modulo);
		lambda=lambda.add(a2);
		lambda=lambda.multiply((P.Y.multiply(TWO)).mod(modulo).modInverse(modulo)).mod(modulo);
		BigInteger x3 = lambda.modPow(TWO,modulo).subtract(P.X.multiply(TWO).mod(modulo)).mod(modulo);
		BigInteger y3 = P.X.subtract(x3).mod(modulo).multiply(lambda).mod(modulo).subtract(P.Y).mod(modulo);
		P.X=x3;
		P.Y=y3;
		return P;
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
		if (Q.X == P.X && Q.Y == P.Y)
		{
			return podwojeniePunktu(Q);
		}
		if (Q.X == BigInteger.ZERO && Q.Y == BigInteger.ZERO)
		{
			return new ECPunkt(P);
		}
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
		BigInteger lambda =P.Y.subtract(Q.Y).mod(modulo);
		lambda=lambda.multiply(P.X.subtract(Q.X).mod(modulo).modInverse(modulo)).mod(modulo);
		BigInteger x3 = lambda.modPow(TWO,modulo).subtract(Q.X).subtract(P.X).mod(modulo);
		BigInteger y3 = Q.X.subtract(x3).mod(modulo).multiply(lambda).mod(modulo).subtract(Q.Y).mod(modulo);
		Q.X=x3;
		Q.Y=y3;
		return Q;
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
		
		ECPunkt Q = new ECPunkt(this.m, this.k, this.a2, this.a6, BigInteger.ZERO, BigInteger.ZERO);
		//ECPunkt Q = new ECPunkt(P);
		//Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)curve.getInfinity());
		String kInBits = k.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
			Q = podwojeniePunktu(Q);
			if(kInBits.charAt(j) == '1')
				Q = sumaPunktow(Q,P);
		}
		return Q;
	}
}
