package dh;

import dh.Klient;
import dh.ECPunkt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.math.BigInteger;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;


public class Window extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	main mainframe;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	
	private Klient klientA;
	private Klient klientB;
	
	public Window(main main)	{
		this.mainframe = main;
		this.requestFocusInWindow();
		setDoubleBuffered(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dane wej\u015Bciowe", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Kroki wykonywanego algorytmu", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(2)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
		);
		
		final DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		scrollPane.setColumnHeaderView(list);
		scrollPane.setViewportView(list);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "U\u017Cytkownik A", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "U\u017Cytkownik B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JLabel lblKluczPrywatny = new JLabel("Klucz prywatny (a):");
		lblKluczPrywatny.setBounds(26, 30, 121, 14);
		lblKluczPrywatny.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField_3 = new JTextField();
		textField_3.setBounds(26, 56, 310, 20);
		textField_3.setColumns(10);
		
		/**
		 * Generacja klucza prywatnego uzytkownika A.
		 */
		JButton btnGeneruj = new JButton("Generuj");
		btnGeneruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BigInteger n;
				BigInteger gx;
				BigInteger gy;
				ECPunkt punktG;
				BigInteger h;
				try {
					n = new BigInteger(textField.getText());
					gx = new BigInteger(textField_1.getText());
					gy = new BigInteger(textField_2.getText());
					punktG = new ECPunkt(gx, gy, BigInteger.ONE);
					
					///TODO Zmienic warto�� h
					h = new BigInteger("2");
					
					klientA = new Klient(punktG, n, h);
					klientA.genKluczaPrywatnego(n.bitLength());
					textField_3.setText(klientA.kluczPrywatny.toString());
					
					listModel.addElement("Wygenerowano klucz prywatny U�ytkownika A.");
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Nale�y poda� parametry: n, Gx, Gy.", "B��d", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnGeneruj.setLocation(259, 27);
		btnGeneruj.setSize(78, 23);
		
		JLabel lblKluczPublicznya = new JLabel("Klucz publiczny (Ax = aGx; Ay = aGy):");
		lblKluczPublicznya.setBounds(26, 96, 225, 16);
		lblKluczPublicznya.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField_5 = new JTextField();
		textField_5.setBounds(26, 123, 310, 20);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(26, 149, 310, 20);
		textField_6.setColumns(10);
		
		JButton btnOblicz = new JButton("Oblicz");
		btnOblicz.setBounds(259, 94, 78, 23);
		panel_2.setLayout(null);
		panel_2.add(textField_3);
		panel_2.add(lblKluczPrywatny);
		panel_2.add(btnGeneruj);
		panel_2.add(lblKluczPublicznya);
		panel_2.add(btnOblicz);
		panel_2.add(textField_5);
		panel_2.add(textField_6);
		
		JLabel lblKluczTajnysx = new JLabel("Klucz tajny (Sx = aBx; Sy = aBy):");
		lblKluczTajnysx.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblKluczTajnysx.setBounds(26, 194, 225, 16);
		panel_2.add(lblKluczTajnysx);
		
		JButton btnOblicz_2 = new JButton("Oblicz");
		btnOblicz_2.setBounds(258, 192, 78, 23);
		panel_2.add(btnOblicz_2);
		
		textField_9 = new JTextField();
		textField_9.setBounds(26, 221, 310, 20);
		panel_2.add(textField_9);
		textField_9.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setBounds(26, 247, 310, 20);
		panel_2.add(textField_10);
		textField_10.setColumns(10);
		
		JLabel lblKluczPrywatnyb = new JLabel("Klucz prywatny (b):");
		lblKluczPrywatnyb.setBounds(26, 30, 128, 16);
		lblKluczPrywatnyb.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField_4 = new JTextField();
		textField_4.setBounds(26, 56, 310, 20);
		textField_4.setColumns(10);
		
		/**
		 * Generacja klucza prywatnego u�ytkownika B
		 */
		JButton btnGeneruj_1 = new JButton("Generuj");
		btnGeneruj_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BigInteger n;
				BigInteger gx;
				BigInteger gy;
				ECPunkt punktG;
				BigInteger h;
				try {
					n = new BigInteger(textField.getText());
					gx = new BigInteger(textField_1.getText());
					gy = new BigInteger(textField_2.getText());
					punktG = new ECPunkt(gx, gy,BigInteger.ONE);
					
					///TODO Zmienic warto�� h
					h = new BigInteger("2");
					
					klientB = new Klient(punktG, n, h);
					klientB.genKluczaPrywatnego(n.bitLength());
					textField_4.setText(klientB.kluczPrywatny.toString());
					
					listModel.addElement("Wygenerowano klucz prywatny U�ytkownika B.");
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Nale�y poda� parametry: n, Gx, Gy.", "B��d", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnGeneruj_1.setBounds(257, 27, 78, 23);
		
		JLabel lblKluczPublicznybx = new JLabel("Klucz publiczny (Bx = bGx; By = bGy):");
		lblKluczPublicznybx.setBounds(26, 96, 219, 16);
		lblKluczPublicznybx.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField_7 = new JTextField();
		textField_7.setBounds(26, 123, 310, 20);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(26, 149, 310, 20);
		textField_8.setColumns(10);
		
		JButton btnOblicz_1 = new JButton("Oblicz");
		btnOblicz_1.setBounds(257, 94, 78, 23);
		panel_3.setLayout(null);
		panel_3.add(textField_8);
		panel_3.add(lblKluczPublicznybx);
		panel_3.add(btnOblicz_1);
		panel_3.add(lblKluczPrywatnyb);
		panel_3.add(btnGeneruj_1);
		panel_3.add(textField_4);
		panel_3.add(textField_7);
		
		JLabel lblKluczTajnysx_1 = new JLabel("Klucz tajny (Sx = bAx; Sy = bAy):");
		lblKluczTajnysx_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblKluczTajnysx_1.setBounds(26, 194, 225, 16);
		panel_3.add(lblKluczTajnysx_1);
		
		JButton btnNewButton = new JButton("Oblicz");
		btnNewButton.setBounds(258, 192, 78, 23);
		panel_3.add(btnNewButton);
		
		textField_11 = new JTextField();
		textField_11.setBounds(26, 221, 310, 20);
		panel_3.add(textField_11);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setBounds(26, 247, 310, 20);
		panel_3.add(textField_12);
		textField_12.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("n: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblGx = new JLabel("Gx: ");
		lblGx.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		JLabel lblGy = new JLabel("Gy: ");
		lblGy.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblGy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblGx)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGx)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGy)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		mainframe.setTitle("ECDH");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Dodaj".equals(e.getActionCommand()))
		{
			
		}
		else if ("Usu�".equals(e.getActionCommand()))
		{

		}
		else if ("Zako�cz".equals(e.getActionCommand()))
		{
			
		}
		else JOptionPane.showMessageDialog(mainframe, "Wyst�pi� b��d. Spr�buj ponownie");		
	}
}