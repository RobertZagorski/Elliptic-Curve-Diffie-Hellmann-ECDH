package dh;
import dh.Window; 

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * Klasa startowa projektu ECDH - protoko³u wymiany kluczy wed³ug
 * Diffiego i Hellmana na krzywych eliptycznych nad cia³em binarnym GF(2^m)
 * @author Vitali Karpinski
 * @author Robert Zagórski
 *
 */
public class mainFrame extends JFrame {

	/**Domyœlny numer ID klasy*/
	private static final long serialVersionUID = 1L;
	/**Obiekt okna programu typu {@link Window}*/
	private JPanel Window;
	
	/**
	 * Konstruktor domyœlny klasy {@link mainFrame}
	 * @param name nazwa okna programu  
	 */
	public mainFrame(String name)	{
		super(name);
		//Tworzenie nowego okna i dodanie funkcjonalnoï¿½ci zamykania
		addWindowListener(new WindowAdapter() {
            /**
             * Handles window closing events.
             * @param evt window event
             */
            public void windowClosing(WindowEvent evt) {
                /** terminate the program */
                System.exit(0);
            }
        	public void windowActivated(WindowEvent e)	{
        		repaint();
        	}
            
        });
		
		/*Tworzenie i dodawanie panelu g³ówwnego menu przy pomocy klasy {@link Menu}*/
		Window = new Window(this);
		add(Window);
		this.pack();
		this.setFocusable(true);
	}

	/**
	 * Punkt startowy programu
	 * @param args [0] Jeœli podana nazwa pliku - zostanie uruchomiona automatyczna 
	 * procedura przeprowdzenia protoko³u ECDH
	 */
	public static void main(String[] args) {
		if (args.length == 0)
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try 
					{
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					} 
					catch (Exception ex) 
					{
						ex.printStackTrace();
					}
					new mainFrame("ECDH").setVisible(true);
				}
			});
		}
		else
			autoECDH(args[0]);
	}
	
	/**
	 * funkcja pozwalaj¹ca na automatyczne przeprowadzanie protoko³u ECDH. Przyjmuje 
	 * parametry krzywej eliptycznej z pliku i zapisuje do pliku o takiej samej nazwie z przyrostkiem
	 * <i>output<i>
	 * @param filename nazwa pliku, z którego maj¹ zostaæ pobrane parametry
	 */
	private static void autoECDH(String filename)
	{
		try
		{
			  FileInputStream fstream = new FileInputStream(filename+".txt");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  String strOut = new String();
			  int m = 0;
			  int k = 0;
			  BigInteger gx = BigInteger.ZERO;
			  BigInteger gy = BigInteger.ZERO;
			  BigInteger a2 = BigInteger.ZERO;
			  BigInteger a6 = BigInteger.ZERO;
			  Klient klientA;
			  Klient klientB;
			  ECPunkt punktG = null;
			  while ((strLine = br.readLine()) != null)   
			  {
				  try
				  {
					  if(strLine.startsWith("k: "))
					  {
						  k=Integer.parseInt(strLine.substring(3));
						  while(!(strLine = br.readLine()).equals(""))
						  {
							  if (strLine.startsWith("m: "))
							  {
								  m=Integer.parseInt(strLine.substring(3));
								  continue;
							  }
							  else if (strLine.startsWith("Gx: "))
							  {
								  gx=new BigInteger(strLine.substring(4),16);
								  continue;
							  }
							  else if (strLine.startsWith("Gy: "))
							  {
								  gy=new BigInteger(strLine.substring(4),16);
								  continue;
							  }
							  else if (strLine.startsWith("a2: "))
							  {
								  a2=new BigInteger(strLine.substring(4),16);
								  continue;
							  }
							  else if (strLine.startsWith("a6: "))
							  {
								  a6=new BigInteger(strLine.substring(4),16);
								  continue;
							  }
						  }
					  }
					  if (m==0 || k==0 || (gx.equals(BigInteger.ZERO)) || (gy.equals(BigInteger.ZERO))
							  || (a2.equals(BigInteger.ZERO)) || (a6.equals(BigInteger.ZERO)))
						  throw new IllegalArgumentException("Zmienne nie zosta³y zainicjalizowane");
					  if (k>=m)
							throw new IllegalArgumentException("Wyk³adnik k nie mo¿e byæ wiêkszy ni¿ m");
					  if (m<=1)
							throw new IllegalArgumentException("Wyk³adnik k nie mo¿e byæ wiêkszy ni¿ m");
					  if (gx.toString(2).length() > m || gy.toString(2).length() > m || 
							a2.toString(2).length() > m || a6.toString(2).length() > m)
							throw new IllegalArgumentException("Wartoœci parametrów wykraczaj¹ poza zakres");
					  punktG = new ECPunkt(m,k,a2,a6,gx,gy);
					  if (!punktG.naEC())
							throw new IllegalArgumentException("Generator nie nale¿y do zbioru punktów krzywej eliptycznej");
						//////////////////////////////Sprawdzenie nieprzywiedlnoœci wielomianu modulo
//						Polynomial p = new Polynomial();
//						p=p.setDegree(new BigInteger(Integer.toString(m)));
//						p=p.setDegree(new BigInteger(Integer.toString(k)));
//						p=p.setDegree(BigInteger.ZERO);
//						if (p.isReducible())
//							throw new IllegalArgumentException("Wielomian modulo cia³a nie jest nieprzywiedlny");
//						/////////////////////////////////////////////////////////////////////////////
					  	klientA = new Klient(punktG);
					  	klientA.genKluczaPrywatnego(m);
					  	klientB = new Klient(punktG);
					  	klientB.genKluczaPrywatnego(m);
					  	klientB.ustawKluczPublicznyB(klientA.oblKluczaPublicznego());
					  	klientA.ustawKluczPublicznyB(klientB.oblKluczaPublicznego());
					  	klientA.oblKluczaTajnego();
					  	klientB.oblKluczaTajnego();
					  	GF2Elem xCord = new GF2Elem(klientA.kluczTajny.X);
					  	if (xCord.add(klientB.kluczTajny.X).b.equals(BigInteger.ZERO))
					  	{
					  		///Algorytm zadzia³a³ poprawnie
					  		strOut = strOut.concat("////////////////////////////////////////"+"\r\n");
					  		strOut = strOut.concat("k: "+k+"\r\n");
					  		strOut = strOut.concat("m: "+m+"\r\n");
					  		strOut = strOut.concat("Gx: "+gx.toString(16)+"\r\n");
					  		strOut = strOut.concat("Gy: "+gy.toString(16)+"\r\n");
					  		strOut = strOut.concat("a2: "+a2.toString(16)+"\r\n");
					  		strOut = strOut.concat("a6: "+a6.toString(16)+"\r\n");
					  		strOut = strOut.concat("\nWygenerowane parametry sesji:"+"\r\n");
					  		strOut = strOut.concat("APub.X: "+klientA.kluczPubliczny.X.b.toString(16)+"\r\n");
					  		strOut = strOut.concat("APub.Y: "+klientA.kluczPubliczny.Y.b.toString(16)+"\r\n");
					  		strOut = strOut.concat("BPub.X: "+klientB.kluczPubliczny.X.b.toString(16)+"\r\n");
					  		strOut = strOut.concat("BPub.Y: "+klientB.kluczPubliczny.Y.b.toString(16)+"\r\n");
					  		strOut = strOut.concat("Wspólny klucz sesji: "+klientA.genklucz01()+"\r\n");
					  		strOut = strOut.concat("////////////////////////////////////////"+"\r\n");
					  	}
					  	else
					  	{
					  		///Algorytm nie zadzia³a³ poprawnie
					  		strOut = strOut.concat("////////////////////////////////////////"+"\r\n");
					  		strOut = strOut.concat("k: "+k+"\r\n");
					  		strOut = strOut.concat("m: "+m+"\r\n");
					  		strOut = strOut.concat("Gx: "+gx.toString(16)+"\r\n");
					  		strOut = strOut.concat("Gy: "+gy.toString(16)+"\r\n");
					  		strOut = strOut.concat("a2: "+a2.toString(16)+"\r\n");
					  		strOut = strOut.concat("a6: "+a6.toString(16)+"\r\n");
					  		strOut = strOut.concat("\nAlgorytm nie zadzia³a³ poprawnie"+"\r\n");
					  		strOut = strOut.concat("////////////////////////////////////////"+"\r\n");
					  	}
					}
					catch (NumberFormatException e)
					{
						System.out.println("Wprowadzone parametry nie s¹ w kodzie HEX");
					}
					catch (IllegalArgumentException e)
					{
						System.out.println("B³êdnie sformatowane dane w pliku");
					}
				  FileOutputStream foutstream = new FileOutputStream(filename+"output.txt",true);
				  DataOutputStream out = new DataOutputStream(foutstream);
				  BufferedWriter boutr = new BufferedWriter(new OutputStreamWriter(out));
				  boutr.write(strOut);
				  m = 0;
				  k = 0;
				  gx = BigInteger.ZERO;
				  gy = BigInteger.ZERO;
				  a2 = BigInteger.ZERO;
				  a6 = BigInteger.ZERO;
				  strOut = null;
				  boutr.close();
				  out.close();
			  }
			  in.close();
			  
		}
		catch (Exception e)
		{
			  System.err.println("Error: " + e.getMessage());
			  e.printStackTrace();
		}
	}
}
