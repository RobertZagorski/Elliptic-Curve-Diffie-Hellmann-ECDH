package dh;
import java.math.BigInteger; 
/**
 * Klasa symbolizuj¹ca punkt krzywej eliptycznej przechowuj¹ca 
 * i daj¹ca dostêp wspó³rzêdnych punktów
 * @author Vitali Karpinski
 * @author Robert Zagórski
 *
 */
public class ECPunkt {

	BigInteger gx;
	BigInteger gy;
	
	public ECPunkt(BigInteger x,BigInteger y)
	{
		this.gx=x;
		this.gy=y;
	}
	
	public BigInteger getX()
	{
		return this.gx;
	}
	
	public BigInteger getY()
	{
		return this.gy;
	}
	
	public ECPunkt wielokrotnoscPunktu(ECPunkt punkt, BigInteger n)
	{
		
		return punkt;
	}
	
}
