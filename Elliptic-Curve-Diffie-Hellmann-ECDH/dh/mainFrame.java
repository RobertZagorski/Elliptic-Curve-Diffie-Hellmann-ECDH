package dh;
import dh.Window; 

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * Klasa startowa projektu ECDH - protoko�u wymiany kluczy wed�ug
 * Diffiego i Hellmana na krzywych eliptycznych nad cia�em binarnym GF(2^m)
 * @author Vitali Karpinski
 * @author Robert Zag�rski
 *
 */
public class mainFrame extends JFrame {

	/**Domy�lny numer ID klasy*/
	private static final long serialVersionUID = 1L;
	/**Obiekt okna programu typu {@link Window}*/
	private JPanel Window;
	
	/**
	 * Konstruktor klasy {@link mainFrame}
	 * @param name nazwa okna programu  
	 */
	public mainFrame(String name)	{
		super(name);
		//Tworzenie nowego okna i dodanie funkcjonalno�ci zamykania
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
		
		/*Tworzenie i dodawanie panelu g��wwnego menu przy pomocy klasy {@link Menu}*/
		Window = new Window(this);
		add(Window);
		this.pack();
		this.setFocusable(true);
	}

	/**
	 * Punkt startowy programu
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
				new mainFrame("ECDH").setVisible(true);
            }
         });
	}
}
