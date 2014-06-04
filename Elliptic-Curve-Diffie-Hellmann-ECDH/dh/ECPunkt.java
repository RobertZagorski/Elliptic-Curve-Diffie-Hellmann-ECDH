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

	private static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE); 
	/**Wsp�rz�dna horyzontalna generatora liczb w ciele.*/
	BigInteger gx;
	/**Wsp�rz�dna wertykalna generatora liczb w ciele.*/
	BigInteger gy;
	/**Wielomian modulo w ciele (x^m + x^k + 1).*/
	BigInteger modulo;
	/**Parametr krzywej eliptycznej.*/
	BigInteger a2;
	/**Parametr krzywej eliptycznej - wolny wyraz.*/
	BigInteger a6;
	/**D�ugo�� bitowa cia�a binarnego.*/
	int m;
	/**Wyk�adnik �rodkowy wielomianu modulo w ciele (x^m + x^k + 1).*/
	int k;
	/**Obiekt reprezentuj�cy wsp�rz�dn� X we wsp�rz�dnych afiniczych*/
	GF2Elem X;
	/**Obiekt reprezentuj�cy wsp�rz�dn� Y we wsp�rz�dnych afinicznych*/
	GF2Elem Y;
	/**Obiekt reprezentuj�cy wsp�rz�dn� Y we wsp�rz�dnych rzutowych*/
	GF2Elem Z;
	
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
		modulo = TWO.pow(m).subtract(TWO.pow(k)).add(BigInteger.ONE);
		X=new GF2Elem(xG,M,K);
		Y=new GF2Elem(yG,M,K);
		Z = new GF2Elem(new BigInteger("2"), m, k);
	}
	
	/**
	 * Konstruktor kopiuj�cy klasy ECPunkt
	 * @param Punkt punkt nale��cy do tej samej krzywej eliptycznej, kt�ry nale�y skopiowa�
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
		modulo = Punkt.modulo;
	}
	
	/**
	 * Funkcja pozwalaj�ca na operacj� podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, kt�ry nale�y podwoi�
	 * @return wynik operacji podwajania punktu
	 */
	public ECPunkt podwojeniePunktu (ECPunkt P)
	{	
		if (P.X.b.equals(BigInteger.ZERO) && P.Y.b.equals(BigInteger.ZERO))
			return P;
		///////////////////F2m-wsp�rz�dne rzutowe/////////
//		ECPunkt Q = new ECPunkt(P);
//		
//		GF2Elem dwa = new GF2Elem(new BigInteger("2"), m, k);
//		GF2Elem trzy = new GF2Elem(new BigInteger("3"), m, k);
//		GF2Elem cztery = new GF2Elem(new BigInteger("4"), m, k);
//		GF2Elem osiem = new GF2Elem(new BigInteger("8"), m, k);
//		GF2Elem a2 = new GF2Elem(Q.a2, m, k);
//		GF2Elem a6 = new GF2Elem(Q.a6, m, k);
//		
//		Q.Z = new GF2Elem(P.Z);				Q.Z.square().multiply(P.X.bB);
//		
//		GF2Elem A2 = new GF2Elem(Q.Z);		( A2.square() ).multiply(a2.bB);
//		GF2Elem A = new GF2Elem(A1);		A.add(A2.bB);
//		GF2Elem B = new GF2Elem(Q.Y);		B.multiply(Q.Z.bB);
//		GF2Elem C = new GF2Elem(Q.X);		( C.multiply(Q.Y.bB) ).multiply(B.bB);
//		GF2Elem D1 = new GF2Elem(A);		D1.square();
//		GF2Elem D2 = new GF2Elem(C);		D2.multiply(osiem.bB);
//		GF2Elem D = new GF2Elem(D1);		D.add(D2.bB);
//		
//		Q.X = new GF2Elem(dwa);				( Q.X.multiply(B.bB) ).multiply(D.bB);
//		GF2Elem temp1 = new GF2Elem(C);		temp1.multiply(cztery.bB);
//											temp1.add(D.bB);
//											temp1.multiply(A.bB);
//		GF2Elem temp2 = new GF2Elem(Q.Y);	( temp2.square() ).multiply(osiem.bB);
//											temp2.multiply(B.square().bB);
//		Q.Y = new GF2Elem(temp1);			Q.Y.add(temp2.bB);
//		Q.Z = new GF2Elem(B);				(( Q.Z.square() ).multiply(B.bB)).multiply(osiem.bB);
//		return Q;
		////////////////////F2m///////////////////////////
		ECPunkt Q = new ECPunkt(P);
		GF2Elem a6 = new GF2Elem(Q.a6, m, k);
		GF2Elem temp1 = new GF2Elem(P.X);		try {
													temp1.inverse();
												} catch (Exception e) {
													e.printStackTrace();
												}
		GF2Elem lambda = new GF2Elem(P.Y);		lambda.multiply(temp1).add(P.X);
		GF2Elem x1squared = new GF2Elem(P.X);	x1squared.square();	
		GF2Elem temp3 = new GF2Elem(x1squared);	try {
													temp3.inverse();
												} catch (Exception e) {
													e.printStackTrace();
												}
		Q.X = new GF2Elem(a6);					Q.X.multiply(temp3).add(x1squared);
		Q.Y = new GF2Elem(lambda);				Q.Y.multiply(Q.X).add(x1squared).add(Q.X);
		return Q;
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
	 * Suma dw�ch r�nych punkt�w nale��cych do krzywej eliptycznej
	 * Q = P1 + P2 
	 * @param p pierwszy punkt
	 * @param q drugi punkt
	 * @return punkt b�d�cy sum� dw�ch punkt�w krzywej eliptycznej
	 */
	public ECPunkt sumaPunktow (ECPunkt Q,ECPunkt P)
	{
		if (Q.X.b.equals(P.X.b) && Q.Y.b.equals(P.Y.b))
			return podwojeniePunktu(Q);
		if (Q.X.b.equals(BigInteger.ZERO) && Q.Y.b.equals(BigInteger.ZERO))
			return new ECPunkt(P);
		else if (P.X.b.equals(BigInteger.ZERO) && P.Y.b.equals(BigInteger.ZERO))
			return new ECPunkt(Q);
		/////////////////F2m - wsp�rz�dne rzutowe////////////////
//		ECPunkt wynik = new ECPunkt(P);
//		
//		GF2Elem lambda1 = new GF2Elem(P.Z);		lambda1.square().multiply(Q.X.bB);
//		GF2Elem lambda2 = new GF2Elem(Q.Z);		lambda2.square().multiply(P.X.bB);
//		GF2Elem lambda3 = new GF2Elem(lambda1);	lambda3.add(lambda2.bB);
//		GF2Elem lambda4 = new GF2Elem(P.Z);		lambda4.square().multiply(P.Z.bB).multiply(Q.Y.bB);
//		GF2Elem lambda5 = new GF2Elem(Q.Z);		lambda5.square().multiply(Q.Z.bB).multiply(P.Y.bB);
//		GF2Elem lambda6 = new GF2Elem(lambda4);	lambda6.add(lambda5.bB);
//		GF2Elem lambda7 = new GF2Elem(Q.Z);		lambda7.multiply(lambda3.bB);
//		GF2Elem temp1 = new GF2Elem(P.X);		temp1.multiply(lambda6.bB);
//		GF2Elem temp2 = new GF2Elem(P.Y);		temp2.multiply(lambda7.bB);
//		GF2Elem lambda8 = new GF2Elem(temp1);	temp1.multiply(temp2.bB);
//		
//		wynik.Z = new GF2Elem(P.Z);			wynik.Z.multiply(lambda7.bB);
//		GF2Elem lambda9 = new GF2Elem(wynik.Z);	lambda9.add(lambda6.bB);
//		
//		GF2Elem a2 = new GF2Elem(Q.a2, m, k);
//		GF2Elem temp3 = new GF2Elem(wynik.Z);	temp3.square().multiply(a2.bB);
//		GF2Elem temp4 = new GF2Elem(lambda6);	temp4.multiply(lambda9.bB);
//		wynik.X = new GF2Elem(lambda3);			wynik.X.square().multiply(lambda3.bB);
//												wynik.X.add(temp4.bB).add(temp3.bB);
//		
//		GF2Elem temp5 = new GF2Elem(lambda7);	temp5.square().multiply(lambda8.bB);
//		wynik.Y = new GF2Elem(wynik.X);			wynik.Y.multiply(lambda9.bB);
//												wynik.Y.add(temp5.bB);
//		return wynik;
//		///////////////////////F2m/////////////////////////////////
		GF2Elem a2 = new GF2Elem(Q.a2, m, k);
		GF2Elem temp1 = new GF2Elem(Q.Y);		temp1.add(P.Y);
		GF2Elem temp2 = new GF2Elem(Q.X);		temp2.add(P.X);
												try {
													temp2.inverse();
												} catch (Exception e) {
													e.printStackTrace();
												}
		GF2Elem lambda = new GF2Elem(temp1);	lambda.multiply(temp2);
		ECPunkt wynik = new ECPunkt(P);
		wynik.X = new GF2Elem(lambda);			wynik.X.square().add(lambda).add(Q.X).add(P.X).add(a2);
		wynik.Y = new GF2Elem(Q.X);				wynik.Y.add(wynik.X).multiply(lambda).add(wynik.X).add(Q.Y);
		return wynik;
		///////////////////////Fp///////////////////////////////
//		BigInteger lambda =P.Y.subtract(Q.Y).mod(modulo);
//		lambda=lambda.multiply(P.X.subtract(Q.X).mod(modulo).modInverse(modulo)).mod(modulo);
//		BigInteger x3 = lambda.modPow(TWO,modulo).subtract(Q.X).subtract(P.X).mod(modulo);
//		BigInteger y3 = Q.X.subtract(x3).mod(modulo).multiply(lambda).mod(modulo).subtract(Q.Y).mod(modulo);
//		Q.X=x3;
//		Q.Y=y3;
//		return Q;
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
//		//P.wspAfiniczneNaRzutowe();
		ECPunkt Q = new ECPunkt(this.m, this.k, this.a2, this.a6, BigInteger.ZERO, BigInteger.ZERO);
		String kInBits = k.toString(2);
		for (int j=0;j<kInBits.length();j++)
		{
			Q = podwojeniePunktu(Q);
			if(kInBits.charAt(j) == '1')
			{
				Q = sumaPunktow(Q,P);
			}
		}
		//Q.wspRzutoweNaAfiniczne();
		return Q;
	}
	
	/**
	 * Konwersja wsp�rz�dnych z rzutowych na afiniczne: P(X*Z,Y*Z)
	 */
	public void wspRzutoweNaAfiniczne()
	{
		this.X = this.X.multiply(this.Z);
		this.Y = this.Y.multiply(this.Z);
	}
	
	/**
	 * Konwesja wsp�rz�dnych z afinicznych na rzutowe: P(X/Z,Y,Z)
	 */
	public void wspAfiniczneNaRzutowe()
	{
		try {
			this.X = this.X.multiply((this.Z.inverse()));
			this.Y = this.Y.multiply((this.Z.inverse()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sprawdzenie, czy punkt okre�lony przez wsp�rz�dne ({@link ECPunkt#X},{@link ECPunkt#Y}) le�y na krzywej
	 * okre�lonej przez r�wnanie Y^2 + XY = X^3 + a2X^2 + a6
	 * @return
	 */
	public boolean naEC() {	 		
	 		GF2Elem left = new GF2Elem(this.Y);
	 		GF2Elem right = new GF2Elem(this.X);
	 		GF2Elem a2 = new GF2Elem(this.a2,m,k);
	 		GF2Elem a6 = new GF2Elem(this.a6,m,k);
	 		GF2Elem temp = new GF2Elem(this.X);
	 		GF2Elem temp2 = new GF2Elem(this.X);
	 		
	 		left.square();
	 		temp.multiply(this.Y);
	 		left.add(temp);
	 		
	 		right.square().multiply(this.X);
	 		temp2.square().multiply(a2);
	 		right.add(temp2).add(a6);
	 		
	 		if (left.b.equals(right.b)) 
	 			return true; 
	 		else 
	 			return false;
	 	}
}
