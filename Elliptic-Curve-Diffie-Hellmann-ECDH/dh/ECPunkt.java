package dh;
import java.math.BigInteger; 
/**
 * Klasa symbolizuj�ca punkt krzywej eliptycznej przechowuj�ca 
 * i daj�ca dost�p wsp�rz�dnych punkt�w
 * @author Vitali Karpinski
 * @author Robert Zag�rski
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
