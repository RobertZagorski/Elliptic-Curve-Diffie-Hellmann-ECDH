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
	
	public ECPunkt(BigInteger x,BigInteger y,BigInteger a)
	{
		gx=x;
		gy=y;
		a2=a;
	}
	
	public ECPunkt(ECPunkt punkt)
	{
		gx=punkt.getX();
		gy=punkt.getY();
		a2=punkt.a2;
	}
	
	public BigInteger getX()
	{
		return this.gx;
	}
	
	public BigInteger getY()
	{
		return this.gy;
	}
	
	public void setX(BigInteger x)
	{
		this.gx=x;
	}
	
	public void setY(BigInteger y)
	{
		this.gy=y;
	}
	
	/**
	 * Obliczenie wielokrotnoœci punktu krzywej eliptycznej metod¹ 
	 * przesuwaj¹cych siê okienek (sliding windows)
	 * Q = [k]P 
	 * @param punkt punkt startowy obliczeñ
	 * @param k liczba, o jak¹ punkt ma byæ zwielokrotniony
	 * @return zwieloktrotniony punkt
	 */
	public ECPunkt wielokrotnoscPunktu(ECPunkt punkt, BigInteger k)
	{
		//Inicjalizacja zmiennych
		// Zwielokrotniony punkt
		ECPunkt Q;
		// liczba o jak¹ punkt ma byæ zwieloktrotniony przestawiony
		// w systemie dwójkowym
		String kInBits = k.toString(2);
		int R = 4;
		int j;
		int t;
		int index;
		
		// Obliczenia wstêpne
		ECPunkt[] pArray = new ECPunkt[(int) Math.pow(2, R-1)*2 ];
		pArray[1] = new ECPunkt(punkt);
		pArray[2] = new ECPunkt(podwojeniePunktu(punkt));
		for(int i=1 ; i<=(int) (Math.pow(2, R-1)-1) ; i++)
		{
			pArray[2*i+1] = sumaPunktow(pArray[2*i-1],pArray[2]);
		}
		j=kInBits.length()-1;
		Q = new ECPunkt(punkt);
		
		while (j >= 0)
		{
			System.out.println("kInBits.charAt(j)="+kInBits.charAt(j));
			System.out.println("Na pocz¹tku "+System.currentTimeMillis());
			if (kInBits.charAt(j) == '0')
			{
				Q = podwojeniePunktu(Q);
				j--;
				System.out.println("Po podwojeniu "+System.currentTimeMillis());
			}
			else
			{
				t=j-4;
				while(j-t+1 > R)
				{
					t++;
				}
				while (kInBits.charAt(t) == '0')
				{
					t++;
				}
				System.out.println(System.currentTimeMillis());
				System.out.println("kInBits.subSequence(t,j).toString()= "+kInBits.subSequence(t,j+1).toString());
				index = Integer.parseInt(kInBits.subSequence(t,j+1).toString(),2);
				System.out.println("index= "+index);
				System.out.println("Przed suma punktów "+System.currentTimeMillis());
				if (index>0) Q = sumaPunktow( wielerazyPunktbinarnie(Q,new BigInteger( Integer.toString((int)Math.pow(2,j-t+1)) ) ) ,pArray[index]);
				System.out.println(System.currentTimeMillis());
				j=t-1;
				System.out.println(j);
				System.out.println(Q.getX());
				System.out.println(Q.getY());
			}
		}
		return Q;
	}
	
	/**
	 * Funkcja pozwalaj¹ca na operacjê podwojenia punktu na krzywej eliptycznej
	 * Q=[2]P
	 * @param p punkt, który nale¿y podwoiæ
	 * @return wynik operacji podwajania punktu
	 */
	private ECPunkt podwojeniePunktu (ECPunkt p)
	{
		BigInteger lambda = p.getY().divide(p.getX());
		lambda=lambda.add(p.getX());
		BigInteger x3 = lambda.multiply(lambda);
		x3=x3.add(lambda);
		x3=x3.add(a2);
		BigInteger y3 = p.getX().add(x3);
		y3=y3.multiply(lambda);
		y3=y3.add(x3);
		y3=y3.add(p.getY());
		p.setX(x3);
		p.setY(y3);
		return p;
	}
	
	/**
	 * Suma dwóch ró¿nych punktów nale¿¹cych do krzywej eliptycznej
	 * Q = P1 + P2 
	 * @param p pierwszy punkt
	 * @param q drugi punkt
	 * @return punkt bêd¹cy sum¹ dwóch punktów krzywej eliptycznej
	 */
	private ECPunkt sumaPunktow (ECPunkt p, ECPunkt q)
	{
		BigInteger lambda = ( p.getY().add(q.getY()) ).divide( (p.getX().add(q.getX())) );
		BigInteger x3 = lambda.multiply(lambda);
		x3=x3.add(lambda);
		x3=x3.add(p.getX());
		x3=x3.add(q.getX());
		x3=x3.add(this.a2);
		BigInteger y3 = p.getX().add(x3);
		y3=y3.multiply(lambda);
		y3=y3.add(x3);
		y3=y3.add(p.getY());
		p.setX(x3);
		p.setY(y3);
		return p;
	}
	
	private ECPunkt wieleRazyPunkt(ECPunkt Q,int ile)
	{
		ECPunkt Qpom = podwojeniePunktu(Q);
		for (int i=0;i<ile;i++)
		{
			Qpom = sumaPunktow(Qpom,Q);
		}
		return Qpom;
	}
	
	public ECPunkt wielerazyPunktbinarnie(ECPunkt P, BigInteger ile)
	{
		ECPunkt Q = new ECPunkt(BigInteger.ZERO,BigInteger.ZERO,BigInteger.ONE);
		String kInBits = ile.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
			Q = podwojeniePunktu(P);
			if(kInBits.charAt(j) == '1')
			{
				Q = sumaPunktow(Q,P);
			}
			System.out.println(j);
		}
		return Q;
	}
}
