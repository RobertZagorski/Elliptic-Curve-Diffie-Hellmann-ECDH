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
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	
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
				int m;
				int k;
				//BigInteger n;
				BigInteger gx;
				BigInteger gy;
				ECPunkt punktG;
				BigInteger a2;
				BigInteger a6;
				BigInteger h;
				try {
					m = new Integer(textField_14.getText());
					k = new Integer(textField.getText());
					//n = new BigInteger(textField.getText(), 16);
					gx = new BigInteger(textField_1.getText(), 16);
					gy = new BigInteger(textField_2.getText(), 16);
					a2 = new BigInteger(textField_13.getText(), 16);
					a6 = new BigInteger(textField_15.getText(), 16);
					///TODO Zmienic wartoœæ h
					h = new BigInteger("2");
					punktG = new ECPunkt(m,k,
										 a2,
										 a6,
										 gx,
										 gy);

					klientA = new Klient(punktG, h);
					klientA.genKluczaPrywatnego(m);
					textField_3.setText(klientA.kluczPrywatny.toString());
					listModel.addElement("Wygenerowano klucz prywatny u¿ytkownika A.");
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Nale¿y podaæ parametry: m, k, Gx, Gy, a2 i a6.", "B³¹d", JOptionPane.ERROR_MESSAGE);
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
		
		/**
		 * Obliczenie klucza publicznego u¿ytkownika A
		 */
		JButton btnOblicz = new JButton("Oblicz");
		btnOblicz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (klientA == null || klientA.G == null || klientA.kluczPrywatny == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y wygenerowaæ klucz prywatny u¿ytkownika A.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klientB.ustawKluczPublicznyB(klientA.oblKluczaPublicznego());
					textField_5.setText(klientA.kluczPubliczny.getPoint().getX().toBigInteger().toString());
					textField_6.setText(klientA.kluczPubliczny.getPoint().getY().toBigInteger().toString());
					listModel.addElement("Obliczono klucz publiczny u¿ytkownika A");
					listModel.addElement("Przekazano klucz publiczny u¿ytkownika A stronie B");
				}
			}
		});
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
		
		/**
		 * Obliczenie klucza tajnego u¿ytkownika A
		 */
		JButton btnOblicz_2 = new JButton("Oblicz");
		btnOblicz_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (klientA == null || klientA.kluczPrywatny == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y wygenerowaæ klucz prywatny u¿ytkownika A.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else if (klientA.kluczPublicznyB == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y obliczyæ klucz publiczny u¿ytkownika B.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klientA.oblKluczaTajnego();
					textField_9.setText(klientA.kluczTajny.getPoint().getX().toBigInteger().toString());
					textField_10.setText(klientA.kluczTajny.getPoint().getY().toBigInteger().toString());
					listModel.addElement("Obliczono klucz tajny u¿ytkownika A");
				}
			}
		});
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
		 * Generacja klucza prywatnego u¿ytkownika B
		 */
		JButton btnGeneruj_1 = new JButton("Generuj");
		btnGeneruj_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int m;
				int k;
				//BigInteger n;
				BigInteger gx;
				BigInteger gy;
				ECPunkt punktG;
				BigInteger a2;
				BigInteger a6;
				BigInteger h;
				try {
					m = new Integer(textField_14.getText());
					k = new Integer(textField.getText());
					//n = new BigInteger(textField.getText(), 16);
					gx = new BigInteger(textField_1.getText(), 16);
					gy = new BigInteger(textField_2.getText(), 16);
					a2 = new BigInteger(textField_13.getText(), 16);
					a6 = new BigInteger(textField_15.getText(), 16);
					///TODO Zmienic wartoœæ h
					h = new BigInteger("2");
					punktG = new ECPunkt(m,k,
										 a2,
										 a6,
										 gx,
										 gy);
					
					klientB = new Klient(punktG, h);
					klientB.genKluczaPrywatnego(m);
					textField_4.setText(klientB.kluczPrywatny.toString());
					
					listModel.addElement("Wygenerowano klucz prywatny u¿ytkownika B.");
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Nale¿y podaæ parametry: m, k, Gx, Gy, a2 i a6.", "B³¹d", JOptionPane.ERROR_MESSAGE);
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
		
		/**
		 * Obbliczenie klucza publicznego u¿ytkownika B
		 */
		JButton btnOblicz_1 = new JButton("Oblicz");
		btnOblicz_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (klientB == null || klientB.G == null || klientB.kluczPrywatny == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y wygenerowaæ klucz prywatny u¿ytkownika B.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klientA.ustawKluczPublicznyB(klientB.oblKluczaPublicznego());
					textField_7.setText(klientB.kluczPubliczny.getPoint().getX().toBigInteger().toString());
					textField_8.setText(klientB.kluczPubliczny.getPoint().getY().toBigInteger().toString());
					listModel.addElement("Obliczono klucz publiczny u¿ytkownika B");
					listModel.addElement("Przekazano klucz publiczny u¿ytkownika B stronie A");
				}
			}
		});
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
		
		/**
		 * Obliczenie klucza tajnego u¿ytkownika B
		 */
		JButton btnNewButton = new JButton("Oblicz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (klientB == null || klientB.kluczPrywatny == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y wygenerowaæ klucz prywatny u¿ytkownika B.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else if (klientB.kluczPublicznyB == null) {
					JOptionPane.showMessageDialog(null, "Nale¿y obliczyæ klucz publiczny u¿ytkownika A.", "B³¹d", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klientB.oblKluczaTajnego();
					textField_11.setText(klientB.kluczTajny.getPoint().getX().toBigInteger().toString());
					textField_12.setText(klientB.kluczTajny.getPoint().getY().toBigInteger().toString());
					listModel.addElement("Obliczono klucz tajny u¿ytkownika B");
				}
			}
		});
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
		
		JLabel lblNewLabel = new JLabel("k: ");
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
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		
		JLabel lblA = new JLabel("a2:");
		lblA.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		JLabel lblNewLabel_1 = new JLabel("m:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		
		JLabel lblA_1 = new JLabel("a6:");
		lblA_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblGx)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
									.addGap(29)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_14, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblA)
										.addComponent(lblGy))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textField_13, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
										.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(lblA_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_15, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(textField_14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGx))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGy)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblA))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblA_1))
					.addContainerGap())
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
		else if ("Usuñ".equals(e.getActionCommand()))
		{

		}
		else if ("Zakoñcz".equals(e.getActionCommand()))
		{
			
		}
		else JOptionPane.showMessageDialog(mainframe, "Wyst¹pi³ b³¹d. Spróbuj ponownie");		
	}
	
}
