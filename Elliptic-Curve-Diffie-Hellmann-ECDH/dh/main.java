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
//		ECPunkt punkt = new ECPunkt(new BigInteger("1"),new BigInteger("1"),new BigInteger("6"),new BigInteger("19"));
//		BigInteger k = new BigInteger("3423423423");
//		punkt = punkt.wielokrotnoscPunktu(punkt,k);
	}

}
