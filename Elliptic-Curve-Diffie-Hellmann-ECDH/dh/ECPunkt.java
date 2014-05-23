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
	
	public ECPunkt(int M,int K,BigInteger A2, BigInteger A6,BigInteger x,BigInteger y)
	{
		gx=x;
		gy=y;
		a2=A2;
		a6=A6;
		m=M;
		k=K;
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		curve = new org.bouncycastle.math.ec.ECCurve.F2m(m,k,a2,a6);
		point = (org.bouncycastle.math.ec.ECPoint.F2m) curve.createPoint(gx,gy,false);
	}
	
	public ECPunkt(ECPunkt Punkt)
	{
		gx=new BigInteger(Punkt.getX().toByteArray());
		gy=new BigInteger(Punkt.getY().toByteArray());
		a2=new BigInteger(Punkt.getA2().toByteArray());
		a6=new BigInteger(Punkt.getA6().toByteArray());
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
	public ECPunkt podwojeniePunktu (ECPunkt P)
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
	public ECPunkt sumaPunktow (ECPunkt P,ECPunkt Q)
	{
		ECPunkt wynik = new ECPunkt(P);
		wynik.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)P.getPoint().add(Q.getPoint()));
		return wynik;
	}
	
	public ECPunkt wielokrotnoscPunktu(ECPunkt P, BigInteger ile)
	{
		ECPunkt Q = new ECPunkt(P);
		Q.setPoint((org.bouncycastle.math.ec.ECPoint.F2m)curve.getInfinity());
		String kInBits = ile.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
			Q = podwojeniePunktu(Q);
			if(kInBits.charAt(j) == '1') 
				Q = sumaPunktow(Q,P);
		}
		return Q;
	}
	
	public BigInteger getX()
	{
		return this.gx;
	}
	
	public void setX(BigInteger x)
	{
		this.gx=x;
	}
	
	public BigInteger getY()
	{
		return this.gy;
	}
	
	public void setY(BigInteger y)
	{
		this.gy=y;
	}
	
	public BigInteger getA2()
	{
		return this.a2;
	}
	
	public void setA2(BigInteger a2)
	{
		this.a2=a2;
	}
	
	public BigInteger getA6()
	{
		return this.a6;
	}
	
	public void setA6(BigInteger a6)
	{
		this.a6=a6;
	}
	
	public int getm()
	{
		return this.m;
	}
	
	public void setm(int m)
	{
		this.m=m;
	}
	
	public int getk()
	{
		return this.k;
	}
	
	public void setk(int k)
	{
		this.k=k;
	}
	
	public org.bouncycastle.math.ec.ECCurve getCurve()
	{
		return this.curve;
	}
	
	public void setCurve(org.bouncycastle.math.ec.ECCurve curve)
	{
		this.curve=curve;
	}
	
	public org.bouncycastle.math.ec.ECPoint.F2m getPoint()
	{
		return this.point;
	}
	
	public void setPoint(org.bouncycastle.math.ec.ECPoint.F2m point2)
	{
		point=point2;
	}
}

