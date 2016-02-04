package relink.guiview;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Create_Connection extends JFrame implements ActionListener {
	
	///////
	private JPanel contentPane; // root pane
	/////////////
	private JPanel connectionTypePanel;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton cancelButton;
	
	private JComboBox<String> connectionBox;
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JTextField nameField;
	//////////////
	//////////////////////////////////
	private JPanel cardPanel;
	private CardLayout cardlayout;
	/////////////////////////////////////////////////////////////////
	private JPanel serialPanel;
	private SpringLayout serialSpring;
	private JLabel serialPortLabel;
	private JLabel baudRateLabel;
	private JLabel parityLabel;
	private JLabel dataBitsLabel;
	private JLabel stopBitsLabel;
	private JComboBox<String> portBox;
	private JComboBox<Integer> baudRateBox;
	private JComboBox<String> parityBox;
	private JComboBox<Integer> dataBitsBox;
	private JComboBox<Integer> stopBitsBox;
	////////////////////////////////////////////////////////////////
	private JPanel tcpPanel;
	private JTextField hostAddressField;
	private JTextField listeningPortField;
	
	////////////////////////////////////
	private JPanel udpPanel;
	/////////////////////////////////////////
	private JPanel logPanel;
	///////////////////////////////////
	private JPanel whoiPanel;
	//////////////////////
	private final static String SERIAL_PANEL = "Serial";
	private final static String WHOI_PANEL = "WHOI";
	private final static String TCP_PANEL = "TCP";
	private final static String UDP_PANEL = "UDP";
	private final static String LOG_PANEL = "Log";
	private final static String[] choises = {"Serial","WHOI","TCP","UDP","Log"};
	private final static String[] parityChoise = {};
	private final static Integer[] rateList = {2400,4800,9600,19200,38400,57600,115200,230400,460800,500000};
	private final static String[] parityList = {"None", "Even", "Odd"};
	private final static Integer[] stopList = {1,2};
	private final static Integer[] dataList= {5,6,7,8};
	
	
	
	public Create_Connection()
	{
		super("Create Connection");
		this.setPreferredSize(new Dimension(600,500));
		this.setMinimumSize(new Dimension(600,500));
		this.contentPane = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.contentPane.setLayout(new BorderLayout());
		this.setContentPane(contentPane);
		///////////////////////////////////////////////////
		connectionTypePanel = new JPanel();
		connectionTypePanel.setPreferredSize(new Dimension(400,80));
		this.connectionTypePanel.setLayout(new FlowLayout());
		this.connectionTypePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		nameLabel = new JLabel("Connection Name:");
		typeLabel = new JLabel("Connection Type:");
		nameField= new JTextField();
		nameField.setPreferredSize(new Dimension(100,20));
		connectionBox = new JComboBox<String>(choises);
		connectionBox.setSelectedIndex(0);
		connectionBox.addActionListener(this);
		connectionBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				cardlayout.show(cardPanel, (String)arg0.getItem());
			}
			
		});
		this.connectionTypePanel.add(nameLabel);
		nameField.setPreferredSize(new Dimension(100,20));
		this.connectionTypePanel.add(nameField);
		JPanel tmp = new JPanel();
		tmp.setPreferredSize(new Dimension(100,20));
		this.connectionTypePanel.add(tmp);
		this.connectionTypePanel.add(typeLabel);
		this.connectionTypePanel.add(connectionBox);
		/////////////////////////////////////////////////////////////////////////////
		this.makeSerialPanel();
		this.makeTCPPanel();
		this.makeUDPPanel();
		this.makeLogPanel();
		this.makeWHOIPanel();
		//////////////////////////////////////////////////////////////////////////////
		
		
		this.buttonPanel = new JPanel(new FlowLayout());
		//buttonPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		//SpringLayout t = new SpringLayout();
		//this.buttonPanel = new JPanel(t);
		addButton = new JButton("Add");
		addButton.setPreferredSize(new Dimension(80,25));
		cancelButton = new JButton("Cancel");
		buttonPanel.add(addButton);
		JPanel t=new JPanel();
		t.setPreferredSize(new Dimension(338,50));
		buttonPanel.add(t);
		buttonPanel.add(cancelButton);
		////t.putConstraint(SpringLayout.WEST, addButton, 50, SpringLayout.WEST, buttonPanel);
		//t.putConstraint(SpringLayout.NORTH, addButton, 20, SpringLayout.NORTH, buttonPanel);
		
		cardlayout = new CardLayout();
		this.cardPanel = new JPanel(cardlayout);
		cardPanel.add(serialPanel,SERIAL_PANEL);
		cardPanel.add(tcpPanel, TCP_PANEL);
		cardPanel.add(udpPanel,UDP_PANEL);
		cardPanel.add(logPanel, LOG_PANEL);
		cardPanel.add(whoiPanel, WHOI_PANEL);
		this.contentPane.add(connectionTypePanel, BorderLayout.NORTH);
		this.contentPane.add(cardPanel,BorderLayout.CENTER);
		//cardlayout.show(cardPanel, (String)TCP_PANEL);
		//this.contentPane.add(serialPanel, BorderLayout.CENTER);
		//this.contentPane.add(tcpPanel,BorderLayout.CENTE
		this.contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		
		
		
		this.pack();
	}

	
	
	private void makeWHOIPanel() {
		// TODO Auto-generated method stub
		this.whoiPanel = new JPanel();
	}



	private void makeLogPanel() {
		// TODO Auto-generated method stub
		this.logPanel = new JPanel();
	}



	private void makeUDPPanel() {
		// TODO Auto-generated method stub
		this.udpPanel = new JPanel();
	}



	private void makeTCPPanel() {
		// TODO Auto-generated method stub
		this.tcpPanel = new JPanel();
		SpringLayout tcpLayout = new SpringLayout();
		tcpPanel.setLayout(tcpLayout);
		
		this.hostAddressField = new JTextField();
		hostAddressField.setPreferredSize(new Dimension(100,25));
		this.listeningPortField= new JTextField();
		listeningPortField.setPreferredSize(new Dimension(100,25));
		
		JLabel hostAddressLabel = new JLabel("Host Address:");
		JLabel listeningPortLabel = new JLabel("Listening Port:");
		
		tcpPanel.add(listeningPortLabel);
		tcpPanel.add(hostAddressLabel);
		tcpPanel.add(hostAddressField);
		tcpPanel.add(listeningPortField);
		
		tcpLayout.putConstraint(SpringLayout.WEST, hostAddressLabel, 50, SpringLayout.WEST, tcpPanel);
		tcpLayout.putConstraint(SpringLayout.NORTH, hostAddressLabel, 50, SpringLayout.NORTH, tcpPanel);
		
		tcpLayout.putConstraint(SpringLayout.WEST, listeningPortLabel, 50, SpringLayout.WEST, tcpPanel);
		tcpLayout.putConstraint(SpringLayout.NORTH, listeningPortLabel, 50, SpringLayout.SOUTH, hostAddressLabel);
		
		tcpLayout.putConstraint(SpringLayout.WEST, hostAddressField, 200, SpringLayout.WEST, tcpPanel);
		tcpLayout.putConstraint(SpringLayout.NORTH, hostAddressField, 50, SpringLayout.NORTH, tcpPanel);
		tcpLayout.putConstraint(SpringLayout.EAST, hostAddressField, -50, SpringLayout.EAST, tcpPanel);
		
		tcpLayout.putConstraint(SpringLayout.WEST, listeningPortField, 200, SpringLayout.WEST, tcpPanel);
		tcpLayout.putConstraint(SpringLayout.NORTH, listeningPortField, 60, SpringLayout.NORTH, hostAddressLabel);
		tcpLayout.putConstraint(SpringLayout.EAST, listeningPortField, -50, SpringLayout.EAST, tcpPanel);
		
	}



	private void makeSerialPanel() {
		// TODO Auto-generated method stub
		serialSpring = new SpringLayout();
		serialPanel = new JPanel(serialSpring);
		serialPanel.setPreferredSize(new Dimension(300,300));
		
		serialPortLabel = new JLabel("Serial Port:");
		baudRateLabel = new JLabel("Baud Rate:");
		parityLabel=new JLabel("Parity:");
		dataBitsLabel=new JLabel("Data Bits:");
		stopBitsLabel=new JLabel("Stop Bits:");
		portBox=new JComboBox<String>();
		//portBox.setPreferredSize(new Dimension(50,50));
		baudRateBox=new JComboBox<Integer>(rateList);
		parityBox = new JComboBox<String>(parityList);
		dataBitsBox=new JComboBox<Integer>(dataList);
		stopBitsBox=new JComboBox<Integer>(stopList);
		portBox.setPreferredSize(new Dimension(80,25));
		parityBox.setPreferredSize(new Dimension(80,25));
		stopBitsBox.setPreferredSize(new Dimension(80,25));
		baudRateBox.setPreferredSize(new Dimension(80,25));
		dataBitsBox.setPreferredSize(new Dimension(80,25));
		//portBox.setMaximumSize(new Dimension(100,20));
		//serialPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		serialPanel.add(serialPortLabel);
		serialSpring.putConstraint(SpringLayout.WEST, serialPortLabel, 50, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, serialPortLabel, 50, SpringLayout.NORTH, serialPanel);
		
		serialPanel.add(portBox);
		serialSpring.putConstraint(SpringLayout.WEST, portBox, 20, SpringLayout.EAST, serialPortLabel);
		serialSpring.putConstraint(SpringLayout.NORTH, portBox, 50, SpringLayout.NORTH, serialPanel);
		serialSpring.putConstraint(SpringLayout.EAST, portBox, -50, SpringLayout.EAST, serialPanel);
		//serialSpring.putConstraint(SpringLayout.EAST, portBox, 100, SpringLayout.WEST, c2);
			
		serialPanel.add(baudRateLabel);
		serialSpring.putConstraint(SpringLayout.WEST, baudRateLabel, 50, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, baudRateLabel, 50, SpringLayout.SOUTH, serialPortLabel);
		
		
		serialPanel.add(baudRateBox);
		serialSpring.putConstraint(SpringLayout.WEST, baudRateBox, 20, SpringLayout.EAST, baudRateLabel);
		serialSpring.putConstraint(SpringLayout.NORTH, baudRateBox, 50, SpringLayout.SOUTH, serialPortLabel);
		serialSpring.putConstraint(SpringLayout.EAST, baudRateBox, -50, SpringLayout.EAST, serialPanel);
		
		serialPanel.add(this.parityLabel);
		serialSpring.putConstraint(SpringLayout.WEST, parityLabel, 50, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, parityLabel, 50, SpringLayout.SOUTH, baudRateLabel);
		
		serialPanel.add(parityBox);
		serialSpring.putConstraint(SpringLayout.WEST, parityBox, 147, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, parityBox, 50, SpringLayout.SOUTH, baudRateLabel);
		serialSpring.putConstraint(SpringLayout.EAST, parityBox, -50, SpringLayout.EAST, serialPanel);
		
		
		serialPanel.add(this.dataBitsLabel);
		serialSpring.putConstraint(SpringLayout.WEST, dataBitsLabel, 50, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, dataBitsLabel, 50, SpringLayout.SOUTH, parityLabel);
		
		serialPanel.add(this.dataBitsBox);
		serialSpring.putConstraint(SpringLayout.WEST, dataBitsBox, 147, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, dataBitsBox, 50, SpringLayout.SOUTH, parityLabel);
		serialSpring.putConstraint(SpringLayout.EAST, dataBitsBox, -50, SpringLayout.EAST, serialPanel);
		
		
		serialPanel.add(stopBitsLabel);
		serialSpring.putConstraint(SpringLayout.WEST, stopBitsLabel, 50, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, stopBitsLabel, 50, SpringLayout.SOUTH, dataBitsLabel);
		
		serialPanel.add(stopBitsBox);
		serialSpring.putConstraint(SpringLayout.WEST, stopBitsBox, 147, SpringLayout.WEST, serialPanel);
		serialSpring.putConstraint(SpringLayout.NORTH, stopBitsBox, 50, SpringLayout.SOUTH, dataBitsLabel);
		serialSpring.putConstraint(SpringLayout.EAST, stopBitsBox, -50, SpringLayout.EAST, serialPanel);
		
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Create_Connection m= new Create_Connection();
			//m.pack();
			m.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
