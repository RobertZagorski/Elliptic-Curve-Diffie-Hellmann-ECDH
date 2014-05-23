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
 * 
 * @author Vitali Karpinski
 * @author Robert Zagórski
 *
 */
public class main extends JFrame {

	private static final long serialVersionUID = 1L;
	public Dimension dims;
	private JPanel Window;
	
	public main (String name)	{
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
				new main("ecdh").setVisible(true);
            }
         });
		//	start();
//		ECPunkt P = new ECPunkt(239,36,
//									new BigInteger("32010857077C5431123A46B808906756F543423E8D27877578125778AC76", 16),
//									new BigInteger("790408F2EEDAF392B012EDEFB3392F30F4327C0CA3F31FC383C422AA8C16", 16),
//									new BigInteger("604399942292681484344954722575621779974285614280181861075722134673877277"),
//									new BigInteger("675317841546748718168801678160111506702021297638836949018311255830651653"));
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
