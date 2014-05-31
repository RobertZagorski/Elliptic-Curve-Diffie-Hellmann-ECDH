package dh;
import dh.Window; 

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * 
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
		
		//Pobranie rozdzielczoï¿½ci ekranu
		dims = new Dimension();
		dims = getResolution(dims);
		
		/*Ustawienie rozmiarï¿½w ramki na odpowiednie i w odpowiednim
		miejscu na ekranie*/
		//setPreferredSize(dims);
		setLocation(100, 0);
		
		/**Tworzenie i dodawanie panelu g³ówwnego menu przy pomocy klasy {@link Menu}*/
		Window = new Window(this);
		add(Window);
		this.pack();
		this.setFocusable(true);
	}
	
	private Dimension getResolution (Dimension dims)	{
		dims.width = (Toolkit.getDefaultToolkit().getScreenSize().width);
		dims.width /= 2;
		dims.width -=14;
		dims.height = (Toolkit.getDefaultToolkit().getScreenSize().height)*5;
		dims.height /= 6;
		dims.height -= 10;
		return dims;
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
	}
}
