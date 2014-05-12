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
	private BigInteger module;
	
	public ECPunkt(BigInteger x,BigInteger y,BigInteger a,BigInteger mod)
	{
		gx=x;
		gy=y;
		a2=a;
		module=mod;
	}
	
	public ECPunkt(ECPunkt punkt)
	{
		gx=punkt.getX();
		gy=punkt.getY();
		a2=punkt.a2;
		module=punkt.getModule();
		
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
	
	public BigInteger getModule()
	{
		return this.module;
	}
	
	public void setModule(BigInteger mod)
	{
		this.module=mod;
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
			if (kInBits.charAt(j) == '0')
			{
				Q = podwojeniePunktu(Q);
				j--;
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
				index = Integer.parseInt(kInBits.subSequence(t,j+1).toString(),2);
				if (index>0) Q = sumaPunktow( wieleRazyPunktbinarnie(Q,new BigInteger( Integer.toString((int)Math.pow(2,j-t+1)) ) ) ,pArray[index]);
				System.out.println(System.currentTimeMillis());
				j=t-1;
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
//		long time = System.currentTimeMillis();
//		System.out.println("[podwojeniepunktu] Punkt startowy: "+System.currentTimeMillis());
		
		BigInteger lambda = (p.getY().divide(p.getX())).mod(module);
		
//		System.out.println("[podwojeniepunktu]: lambda 1.krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		lambda=lambda.add(p.getX()).mod(module);
		
//		System.out.println("[podwojeniepunktu]: lambda koniec: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		BigInteger x3 = lambda.modPow(BigInteger.ONE.add(BigInteger.ONE), module);//lambda.multiply(lambda).mod(module);
		
//		System.out.println("[podwojeniepunktu]: x3 pierwszy krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(lambda);
		
//		System.out.println("[podwojeniepunktu]: x3 drugi krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(a2).mod(module);
		
//		System.out.println("[podwojeniepunktu]: x3 trzeci krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		BigInteger y3 = p.getX().add(x3);
		
//		System.out.println("[podwojeniepunktu]: y3 pierwszy krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.multiply(lambda).mod(module);
		
//		System.out.println("[podwojeniepunktu]: y3 drugi krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.add(x3);
		
//		System.out.println("[podwojeniepunktu]: y3 trzeci krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.add(p.getY()).mod(module);
		
//		System.out.println("[podwojeniepunktu]: y3 czwarty krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		p.setX(x3);
		p.setY(y3);
		
//		System.out.println("[podwojeniepunktu]: przypisanie do p; ostatni krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
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
//		long time = System.currentTimeMillis();
//		System.out.println("[sumaPunktow] Punkt startowy: "+System.currentTimeMillis());
		
		BigInteger lambda = ( p.getY().add(q.getY()) ).divide( (p.getX().add(q.getX())) ).mod(module);
		
//		System.out.println("[sumaPunktow]: lambda: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		BigInteger x3 = lambda.multiply(lambda).mod(module);
		
//		System.out.println("[sumaPunktow]: x3 pierwszy krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(lambda);
		
//		System.out.println("[sumaPunktow]: x3 drugi krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(p.getX());
		
//		System.out.println("[sumaPunktow]: x3 trzeci krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(q.getX());
		
//		System.out.println("[sumaPunktow]: x3 czwarty krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		x3=x3.add(this.a2).mod(module);
		
//		System.out.println("[sumaPunktow]: x3 pi¹ty krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		BigInteger y3 = p.getX().add(x3);
		
//		System.out.println("[sumaPunktow]: y3 pierwszy krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.multiply(lambda.mod(module));
		
//		System.out.println("[sumaPunktow]: y3 drugi krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.add(x3);
		
//		System.out.println("[sumaPunktow]: y3 trzeci krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		y3=y3.add(p.getY()).mod(module);
		
//		System.out.println("[sumaPunktow]: y3 czwarty krok: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		p.setX(x3);
		p.setY(y3);
		
//		System.out.println("[sumaPunktow]: przypisanie do p. Koniec: "+(System.currentTimeMillis()-time));
//		time = System.currentTimeMillis();
		
		return p;
	}
	
	public ECPunkt wieleRazyPunktbinarnie(ECPunkt P, BigInteger ile)
	{
//		long time = System.currentTimeMillis();
//		System.out.println("[wieleRazyPunktbinarnie] Punkt startowy: "+System.currentTimeMillis());
		
		ECPunkt Q = new ECPunkt(BigInteger.ZERO,BigInteger.ZERO,BigInteger.ONE,module);
		String kInBits = ile.toString(2);
		for (int j=kInBits.length()-1;j>=0;j--)
		{
//			System.out.println("[wieleRazyPunktbinarnie] "+j+" krok pêtli; przed podwojeniem "+(System.currentTimeMillis()-time));
//			time = System.currentTimeMillis();
			
			Q = podwojeniePunktu(P);
			
//			System.out.println("[wieleRazyPunktbinarnie] "+j+" krok pêtli; po podwojeniu "+(System.currentTimeMillis()-time));
//			time = System.currentTimeMillis();
			
			if(kInBits.charAt(j) == '1')
			{
				Q = sumaPunktow(Q,P);
				
//				System.out.println("[wieleRazyPunktbinarnie] "+j+" krok pêtli; suma punktow "+(System.currentTimeMillis()-time));
//				time = System.currentTimeMillis();
			}
//			System.out.println("[wieleRazyPunktbinarnie] "+j+" krok pêtli; koniec kroku pêtli "+(System.currentTimeMillis()-time));
//			time = System.currentTimeMillis();
		}
		return Q;
	}
}
