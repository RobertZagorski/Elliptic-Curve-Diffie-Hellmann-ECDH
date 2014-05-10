package dh;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Window extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	main mainframe;
	GroupLayout layout;
	JLabel RuleNameLabel;
	JLabel FilterLabel;
	JLabel FilterIdentifierLabel;
	JLabel PrecedenceLabel;
	JLabel QCILabel;
	JLabel maxRequestedBandwidthDLLabel;
	JLabel maxRequestedBandwidthULLabel;
	JLabel guaranteedBitrateDLLabel;
	JLabel guaranteedBitrateULLabel;
	JLabel bearerIdentifierLabel;
	JLabel priorityLabel;
	JLabel pre_emption_capabilityLabel;
	JLabel pre_emption_vulnerabilityLabel;
	
	JPanel RuleNamePanel;
	JPanel FilterPanel;
	JPanel FilterIdentifierPanel;
	JPanel PrecedencePanel;
	JPanel QCIPanel;
	JPanel maxRequestedBandwidthDLPanel;
	JPanel maxRequestedBandwidthULPanel;
	JPanel guaranteedBitrateDLPanel;
	JPanel guaranteedBitrateULPanel;
	JPanel bearerIdentifierPanel;
	JPanel priorityPanel;
	JPanel pre_emption_capabilityPanel;
	JPanel pre_emption_vulnerabilityPanel;
	JPanel ProvisionedRulesPanel;
	
	JTextField RuleName;
	JTextField Filter;
	JTextField FilterIdentifier;
	JTextField Precedence;
	JComboBox<String> QCI;
	JTextField maxRequestedBandwidthDL;
	JTextField maxRequestedBandwidthUL;
	JTextField guaranteedBitrateDL;
	JTextField guaranteedBitrateUL;
	JTextField bearerIdentifier;
	JComboBox<String> priority;
	JComboBox<String> pre_emption_capability;
	JComboBox<String> pre_emption_vulnerability;
	JComboBox<String> ProvisionedRules;
	
	String QCIPattern;
	String priorityPattern;
	String pre_emption_capabilityPattern;
	String pre_emption_vulnerabilityPattern;
	String ProvisionedRulesPattern;
	ArrayList<String> Rules;
	
	JButton DodajButton;
	JButton UsunButton;
	JButton ZakonczButton;
	
	public Window(main main)	{
		layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.mainframe = main;
		this.requestFocusInWindow();
		setDoubleBuffered(true);
		CreateLayout();
		mainframe.setTitle("ECDH");
	}
	
	private void CreateLayout() {
		RuleNameLabel = createRuleNameLabel();
		FilterLabel = createFilterLabel();
		FilterIdentifierLabel = createFilterIdentifierLabel();
		PrecedenceLabel = createPrecedenceLabel();
		QCILabel = createQCILabel();
		maxRequestedBandwidthDLLabel = createmaxRequestedBandwidthDLLabel();
		maxRequestedBandwidthULLabel = createmaxRequestedBandwidthULLabel();
		guaranteedBitrateDLLabel = createguaranteedBitrateDLLabel();
		guaranteedBitrateULLabel = createguaranteedBitrateULLabel();
		bearerIdentifierLabel = createbearerIdentifierLabel();
		priorityLabel = createpriorityLabel();
		pre_emption_capabilityLabel = createpre_emption_capabilityLabel();
		pre_emption_vulnerabilityLabel = createpre_emption_vulnerabilityLabel();
		
		RuleNamePanel = createRuleNamePanel() ;
		FilterPanel = createFilterPanel();
		FilterIdentifierPanel = createFilterIdentifierPanel();
		PrecedencePanel = createPrecedencePanel();
		QCIPanel = createQCIPanel();
		maxRequestedBandwidthDLPanel = createmaxRequestedBandwidthDLPanel();
		maxRequestedBandwidthULPanel = createmaxRequestedBandwidthULPanel();
		guaranteedBitrateDLPanel = createguaranteedBitrateDLPanel();
		guaranteedBitrateULPanel = createguaranteedBitrateULPanel();
		bearerIdentifierPanel = createbearerIdentifierPanel();
		priorityPanel = createpriorityPanel();
		pre_emption_capabilityPanel = createpre_emption_capabilityPanel();
		pre_emption_vulnerabilityPanel = createpre_emption_vulnerabilityPanel();
		ProvisionedRulesPanel = createProvisionedRulesPanel();
		
		DodajButton = createDodajButton();
		UsunButton = createUsunButton();
		ZakonczButton = createZakonczButton();
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			     	.addComponent(RuleNameLabel)
			     	.addComponent(FilterLabel)
			     	.addComponent(FilterIdentifierLabel)
			     	.addComponent(PrecedenceLabel)
			     	.addComponent(QCILabel)
			     	.addComponent(maxRequestedBandwidthDLLabel)
			     	.addComponent(maxRequestedBandwidthULLabel)
			     	.addComponent(guaranteedBitrateDLLabel)
			     	.addComponent(guaranteedBitrateULLabel)
			     	.addComponent(bearerIdentifierLabel)
			     	.addComponent(priorityLabel)
			     	.addComponent(pre_emption_capabilityLabel)
			     	.addComponent(pre_emption_vulnerabilityLabel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			        .addComponent(RuleNamePanel)
			        .addComponent(FilterPanel)
			        .addComponent(FilterIdentifierPanel)
			        .addComponent(PrecedencePanel)
			        .addComponent(QCIPanel)
			        .addComponent(maxRequestedBandwidthDLPanel)
			        .addComponent(maxRequestedBandwidthULPanel)
			        .addComponent(guaranteedBitrateDLPanel)
			        .addComponent(guaranteedBitrateULPanel)
			        .addComponent(bearerIdentifierPanel)
			        .addComponent(priorityPanel)
			        .addComponent(pre_emption_capabilityPanel)
			        .addComponent(pre_emption_vulnerabilityPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			        .addComponent(ProvisionedRulesPanel)
			    	.addComponent(DodajButton)
			        .addComponent(UsunButton)
			        .addComponent(ZakonczButton))
			);
		layout.linkSize(SwingConstants.HORIZONTAL, RuleNameLabel, 
												   FilterLabel,
												   FilterIdentifierLabel,
												   PrecedenceLabel,
												   QCILabel,
												   maxRequestedBandwidthDLLabel,
												   maxRequestedBandwidthULLabel,
												   guaranteedBitrateDLLabel,
												   guaranteedBitrateULLabel,
												   bearerIdentifierLabel,
												   priorityLabel,
												   pre_emption_capabilityLabel,
												   pre_emption_vulnerabilityLabel);
		layout.linkSize(SwingConstants.HORIZONTAL, RuleNamePanel,
												   FilterPanel,
												   FilterIdentifierPanel,
												   PrecedencePanel,
												   QCIPanel,
												   maxRequestedBandwidthDLPanel,
												   maxRequestedBandwidthULPanel,
												   guaranteedBitrateDLPanel,
												   guaranteedBitrateULPanel,
												   bearerIdentifierPanel,
												   priorityPanel,
												   pre_emption_capabilityPanel,
												   pre_emption_vulnerabilityPanel);
		layout.linkSize(SwingConstants.HORIZONTAL, ProvisionedRulesPanel,
												   DodajButton,
												   UsunButton,
												   ZakonczButton);
		layout.setVerticalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			        .addComponent(RuleNameLabel)
			        .addComponent(RuleNamePanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(FilterLabel)
			        .addComponent(FilterPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(FilterIdentifierLabel)
			        .addComponent(FilterIdentifierPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(PrecedenceLabel)
			        .addComponent(PrecedencePanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(QCILabel)
			        .addComponent(QCIPanel)
			        .addComponent(ProvisionedRulesPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(maxRequestedBandwidthDLLabel)
			        .addComponent(maxRequestedBandwidthDLPanel)
			        .addComponent(DodajButton))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(maxRequestedBandwidthULLabel)
			        .addComponent(maxRequestedBandwidthULPanel)
			        .addComponent(UsunButton))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(guaranteedBitrateDLLabel)
			        .addComponent(guaranteedBitrateDLPanel)
			        .addComponent(ZakonczButton))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(guaranteedBitrateULLabel)
			        .addComponent(guaranteedBitrateULPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(bearerIdentifierLabel)
			        .addComponent(bearerIdentifierPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(priorityLabel)
			        .addComponent(priorityPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(pre_emption_capabilityLabel)
			        .addComponent(pre_emption_capabilityPanel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(pre_emption_vulnerabilityLabel)
			        .addComponent(pre_emption_vulnerabilityPanel))
			);
		
		
	}
	
	private JLabel createRuleNameLabel()
	{
		JLabel label = new JLabel("Rule Name");
		return label;
		
	}
	
	private JPanel createRuleNamePanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		RuleName = new JTextField();
		RuleName.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(RuleName);
		return textFieldPanel;
	}
	
	private JLabel createFilterLabel()
	{
		JLabel label = new JLabel("Filter");
		return label;
	}
	
	private JPanel createFilterPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		Filter = new JTextField();
		Filter.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(Filter);
		return textFieldPanel;
	}
	
	private JLabel createFilterIdentifierLabel()
	{
		JLabel label = new JLabel("Filter Identifier");
		return label;
	}
	
	private JPanel createFilterIdentifierPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		FilterIdentifier = new JTextField();
		FilterIdentifier.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(FilterIdentifier);
		return textFieldPanel;
	}
	
	private JLabel createPrecedenceLabel()
	{
		JLabel label = new JLabel("Precedence");
		return label;
	}
	
	private JPanel createPrecedencePanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		Precedence = new JTextField();
		Precedence.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(Precedence);
		return textFieldPanel;
	}
	
	private JLabel createQCILabel()
	{
		JLabel label = new JLabel("QoS Class Identifier");
		return label;
	}
	
	private JPanel createQCIPanel()
	{
		QCIPanel = new JPanel();
		QCIPanel.setLayout(new FlowLayout());
		String[] pattern = {"1","2","3","4","5","6","7","8","9"};
		QCIPattern = pattern[0];
		QCI = new JComboBox<String>(pattern);
		QCI.setEditable(false);
		QCI.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				QCIPattern = (String)QCI.getSelectedItem();
			}
		});
		QCIPanel.add(QCI);
		QCIPattern = pattern[0];
		return QCIPanel;
	}
	
	private JLabel createmaxRequestedBandwidthDLLabel()
	{
		JLabel label = new JLabel("Max Requested Bandwidth Downlink");
		return label;
	}
	
	private JPanel createmaxRequestedBandwidthDLPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		maxRequestedBandwidthDL = new JTextField();
		maxRequestedBandwidthDL.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(maxRequestedBandwidthDL);
		return textFieldPanel;
	}
	
	private JLabel createmaxRequestedBandwidthULLabel()
	{
		JLabel label = new JLabel("Max Requested Bandwidth Uplink");
		return label;
	}
	
	private JPanel createmaxRequestedBandwidthULPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		maxRequestedBandwidthUL = new JTextField();
		maxRequestedBandwidthUL.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(maxRequestedBandwidthUL);
		return textFieldPanel;
	}
	
	private JLabel createguaranteedBitrateDLLabel()
	{
		JLabel label = new JLabel("Guaranteed Bitrate Downlink");
		return label;
	}
	
	private JPanel createguaranteedBitrateDLPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		guaranteedBitrateDL = new JTextField();
		guaranteedBitrateDL.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(guaranteedBitrateDL);
		return textFieldPanel;
	}
	
	private JLabel createguaranteedBitrateULLabel()
	{
		JLabel label = new JLabel("Guaranteed Bitrate Uplink");
		return label;
	}
	
	private JPanel createguaranteedBitrateULPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		guaranteedBitrateUL = new JTextField();
		guaranteedBitrateUL.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(guaranteedBitrateUL);
		return textFieldPanel;
	}
	
	private JLabel createbearerIdentifierLabel()
	{
		JLabel label = new JLabel("Bearer Identifier");
		return label;
	}
	
	private JPanel createbearerIdentifierPanel()
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new FlowLayout());
		bearerIdentifier = new JTextField();
		bearerIdentifier.setPreferredSize(new Dimension(150,20));
		textFieldPanel.add(bearerIdentifier);
		return textFieldPanel;
	}
	
	private JLabel createpriorityLabel()
	{
		JLabel label = new JLabel("Priority");
		return label;
	}
	
	private JPanel createpriorityPanel()
	{
		priorityPanel = new JPanel();
		priorityPanel.setLayout(new FlowLayout());
		String[] pattern = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		priorityPattern = pattern[0];
		priority = new JComboBox<String>(pattern);
		priority.setEditable(false);
		priority.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				priorityPattern = (String)priority.getSelectedItem();
			}
		});
		priorityPanel.add(priority);
		priorityPattern = pattern[0];
		return priorityPanel;
	}
	
	private JLabel createpre_emption_capabilityLabel()
	{
		JLabel label = new JLabel("Pre Emption Capability");
		return label;
	}
	
	private JPanel createpre_emption_capabilityPanel()
	{
		pre_emption_capabilityPanel = new JPanel();
		pre_emption_capabilityPanel.setLayout(new FlowLayout());
		String[] pattern = {"Yes","No"};
		pre_emption_capabilityPattern = pattern[0];
		pre_emption_capability = new JComboBox<String>(pattern);
		pre_emption_capability.setEditable(false);
		pre_emption_capability.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				pre_emption_capabilityPattern = (String)pre_emption_capability.getSelectedItem();
			}
		});
		pre_emption_capabilityPanel.add(pre_emption_capability);
		return pre_emption_capabilityPanel;
	}
	
	private JLabel createpre_emption_vulnerabilityLabel()
	{
		JLabel label = new JLabel("Pre Emption Vulnerability");
		return label;
	}
	
	private JPanel createpre_emption_vulnerabilityPanel()
	{
		pre_emption_vulnerabilityPanel = new JPanel();
		pre_emption_vulnerabilityPanel.setLayout(new FlowLayout());
		String[] pattern = {"Yes","No"};
		pre_emption_vulnerabilityPattern = pattern[0];
		pre_emption_vulnerability = new JComboBox<String>(pattern);
		pre_emption_vulnerability.setEditable(false);
		pre_emption_vulnerability.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				pre_emption_vulnerabilityPattern = (String)pre_emption_vulnerability.getSelectedItem();
			}
		});
		pre_emption_vulnerabilityPanel.add(pre_emption_vulnerability);
		return pre_emption_vulnerabilityPanel;
	}
	
	private JPanel createProvisionedRulesPanel()
	{
		ProvisionedRulesPanel = new JPanel();
		ProvisionedRulesPanel.setLayout(new FlowLayout());
		String[] pattern = {""};
		ProvisionedRulesPattern = pattern[0];
		ProvisionedRules = new JComboBox<String>(pattern);
		ProvisionedRules.setEditable(false);
		ProvisionedRules.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				ProvisionedRulesPattern = (String)ProvisionedRules.getSelectedItem();
			}
		});
		ProvisionedRulesPanel.add(ProvisionedRules);
		ProvisionedRulesPattern = pattern[0];
		return ProvisionedRulesPanel;
	}
	
	private JButton createDodajButton() {
		DodajButton = new JButton("Dodaj regu³ê");
		DodajButton.setActionCommand("Dodaj");
		DodajButton.addActionListener(this);
		return DodajButton;
	}
	
	private JButton createUsunButton() {
		UsunButton = new JButton("Usun Regu³ê");
		UsunButton.setActionCommand("Usuñ");
		UsunButton.addActionListener(this);
		return UsunButton;
	}
	
	private JButton createZakonczButton() {
		ZakonczButton = new JButton("Zakoñcz");
		ZakonczButton.setActionCommand("Zakoñcz");
		ZakonczButton.addActionListener(this);
		return ZakonczButton;
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
