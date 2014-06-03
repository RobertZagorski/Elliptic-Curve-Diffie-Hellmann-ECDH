package dh;
import dh.Window; 

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

	private static final long serialVersionUID = 1L;
	public Dimension dims;
	private JPanel Window;
	
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
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
				new mainFrame("ecdh").setVisible(true);
            }
         });
		//	start();
		///Fp/////
//		ECPunkt P = new ECPunkt(224, /*m*/
//								96,  /*k*/
//							/*a2*/	new BigInteger("-3", 16), // mo¿e byæ dowolne
//							/*a6*/	new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16), // mo¿e byæ dowolne
//							/*Gx*/	new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16), // mo¿e byæ dowolne
//							/*Gy*/	new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16)); // mo¿e byæ dowolne
//		BigInteger k = new BigInteger("109152686005085420690232543094048315327522624287776483263939659338377009");
//		P = P.wielokrotnoscPunktu(P,k);
//		System.out.println(P.getPoint());
//		ECPunkt Q = new ECPunkt(P);
//		P = P.podwojeniePunktu();
//		ECPunkt P2 = new ECPunkt(P);
//		P = Q.sumaPunktow(P2);
//		P = P.sumaPunktow(P2);
//		System.out.println(P.getPoint().getX().toBigInteger());
//		System.out.println(P.getPoint().getY().toBigInteger());
//		GF2Elem bgfe = new GF2Elem(new BigInteger("16"),4,1);
//		GF2Elem bgfe2 = new GF2Elem(new BigInteger("13"),4,1);
//		GF2Elem bgfe3 = new GF2Elem(new BigInteger("4"),4,1);
//		GF2Elem bgfe4 = new GF2Elem(new BigInteger("4"),4,1);
//		int i;
//		for (i=0;i<bgfe.bB.length;i++)
//			System.out.print(bgfe.bB[i]);
//		System.out.println("");
//		for (i=0;i<bgfe2.bB.length;i++)
//			System.out.print(bgfe2.bB[i]);
//		System.out.println("");
//		bgfe.multiply(bgfe2.bB);
//		for (i=0;i<bgfe.bB.length;i++)
//			System.out.print(bgfe.bB[i]);
//		System.out.println("");
//		System.out.println(bgfe.b.toString());
//		
//		bgfe.multiply(bgfe3.bB);
//		//bgfe.square();
//		for (i=0;i<bgfe.bB.length;i++)
//			System.out.print(bgfe.bB[i]);
//		System.out.println("");
//		System.out.println(bgfe.b.toString());
//		bgfe.add(bgfe4.bB);
//		for (i=0;i<bgfe.bB.length;i++)
//			System.out.print(bgfe.bB[i]);
//		System.out.println("");
//		System.out.println(bgfe.b.toString());
//		
//		System.out.println("Algorytm inwersji");
//		GF2Elem bgfe5 = new GF2Elem(new BigInteger("6"),4,1);
//		bgfe5.inverse();
//		for (i=0;i<bgfe5.bB.length;i++)
//			System.out.print(bgfe5.bB[i]);
//		System.out.println("");
//		System.out.println(bgfe5.b.toString());		
	}
}
