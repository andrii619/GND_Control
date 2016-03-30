package gnd_control.guiview;

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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import gnd_control.model.Connection;
import gnd_control.model.SerialConnection;
import gnd_control.model.TCPConnection;
import gnd_control.model.UDPConnection;
import gnd_control.model.WHOIConnection;
import jssc.SerialPort;
import jssc.SerialPortList;

/**
 * The GUI that lets the user to create new connections.
 *
 */
public class Create_Connection extends JFrame {
	
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
	private JLabel errorLabel;
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
	private JTextField udpHostField;
	private JTextField udpPortField;
	/////////////////////////////////////////
	private JPanel logPanel;
	private JFileChooser logChooser;
	///////////////////////////////////
	private JPanel whoiPanel;
	private SpringLayout whoiSpring;
	private JLabel whoiPortLabel;
	private JLabel whoiRateLabel;
	private JLabel whoiParityLabel;
	private JLabel whoidataBitsLabel;
	private JLabel whoiStopBitsLabel;
	private JComboBox<String> whoiPortBox;
	private JComboBox<Integer> whoiBaudRateBox;
	private JComboBox<String> whoiParityBox;
	private JComboBox<Integer> whoiDataBitsBox;
	private JComboBox<Integer> whoiStopBitsBox;
	//////////////////////
	private BoardConnect boardConnect;
	private ButtonListener buttonListener;
	
	private final static String SERIAL_PANEL = "Serial";
	private final static String WHOI_PANEL = "WHOI";
	private final static String TCP_PANEL = "TCP";
	private final static String UDP_PANEL = "UDP";
	private final static String LOG_PANEL = "Log";
	private final static String[] choises = {"Serial","WHOI","TCP","UDP","Log"};
	//private final static String[] parityChoise = {};
	private final static Integer[] rateList = {2400,4800,9600,19200,38400,57600,115200,230400,460800,500000};
	private final static String[] parityList = {"None", "Even", "Odd"};
	private final static Integer[] stopList = {1,2};
	private final static Integer[] dataList= {5,6,7,8};
	
	private GND_Control_GUI_HUB hub;
	
	public Create_Connection(GND_Control_GUI_HUB hub,BoardConnect b)
	{
		super("Create Connection");
		this.hub=hub;
		this.setPreferredSize(new Dimension(600,500));
		this.setMinimumSize(new Dimension(600,500));
		this.contentPane = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.contentPane.setLayout(new BorderLayout());
		this.setContentPane(contentPane);
		///////////////////////////////////////////////////
		errorLabel=new JLabel();
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
		//connectionBox.addActionListener(this);
		connectionBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				cardlayout.show(cardPanel, (String)arg0.getItem());
			}
			
		});
		this.connectionTypePanel.add(nameLabel);
		connectionTypePanel.add(errorLabel);
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
		
		
		boardConnect = b;
		buttonListener = new ButtonListener();
		this.addButton.addActionListener(buttonListener);
		this.cancelButton.addActionListener(buttonListener);
		
		
		this.pack();
	}

	
	
	private void makeWHOIPanel() {
		// TODO Auto-generated method stub
		whoiSpring = new SpringLayout();
		this.whoiPanel = new JPanel(whoiSpring);
		
		whoiPanel.setPreferredSize(new Dimension(300,300));
		whoiPanel.setBorder(BorderFactory.createBevelBorder(HEIGHT));
		whoiPortLabel = new JLabel("Serial Port:");;
		whoiRateLabel = new JLabel("Baud Rate:");
		whoiParityLabel = new JLabel("Parity: ");
		whoidataBitsLabel = new JLabel("Data Bits: ");
		whoiStopBitsLabel = new JLabel("Stop Bits: ");
		whoiPortBox = new JComboBox<String>();
		whoiBaudRateBox = new JComboBox<Integer>(rateList);;
		whoiParityBox= new JComboBox<String>(parityList);;
		whoiDataBitsBox=new JComboBox<Integer>(dataList);;
		whoiStopBitsBox=new JComboBox<Integer>(stopList);;
		
		whoiPortBox.setPreferredSize(new Dimension(80,25));
		whoiParityBox.setPreferredSize(new Dimension(80,25));
		whoiStopBitsBox.setPreferredSize(new Dimension(80,25));
		whoiBaudRateBox.setPreferredSize(new Dimension(80,25));
		whoiDataBitsBox.setPreferredSize(new Dimension(80,25));
		
		whoiPanel.add(whoiPortLabel);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiPortLabel, 50, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiPortLabel, 50, SpringLayout.NORTH, whoiPanel);
		
		whoiPanel.add(whoiPortBox);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiPortBox, 20, SpringLayout.EAST, whoiPortLabel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiPortBox, 50, SpringLayout.NORTH, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.EAST, whoiPortBox, -50, SpringLayout.EAST, whoiPanel);
		
		whoiPanel.add(whoiRateLabel);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiRateLabel, 50, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiRateLabel, 50, SpringLayout.SOUTH, whoiPortLabel);
		
		
		whoiPanel.add(whoiBaudRateBox);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiBaudRateBox, 20, SpringLayout.EAST, whoiRateLabel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiBaudRateBox, 50, SpringLayout.SOUTH, whoiPortLabel);
		whoiSpring.putConstraint(SpringLayout.EAST, whoiBaudRateBox, -50, SpringLayout.EAST, whoiPanel);
		
		whoiPanel.add(whoiParityLabel);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiParityLabel, 50, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiParityLabel, 50, SpringLayout.SOUTH, whoiRateLabel);
		
		whoiPanel.add(whoiParityBox);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiParityBox, 147, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiParityBox, 50, SpringLayout.SOUTH, whoiRateLabel);
		whoiSpring.putConstraint(SpringLayout.EAST, whoiParityBox, -50, SpringLayout.EAST, whoiPanel);
		//
		whoiPanel.add(whoidataBitsLabel);
		whoiSpring.putConstraint(SpringLayout.WEST, whoidataBitsLabel, 50, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoidataBitsLabel, 50, SpringLayout.SOUTH, whoiParityLabel);
		
		whoiPanel.add(whoiDataBitsBox);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiDataBitsBox, 147, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiDataBitsBox, 50, SpringLayout.SOUTH, whoiParityLabel);
		whoiSpring.putConstraint(SpringLayout.EAST, whoiDataBitsBox, -50, SpringLayout.EAST, whoiPanel);
		
		
		whoiPanel.add(whoiStopBitsLabel);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiStopBitsLabel, 50, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiStopBitsLabel, 50, SpringLayout.SOUTH, whoidataBitsLabel);
		
		whoiPanel.add(whoiStopBitsBox);
		whoiSpring.putConstraint(SpringLayout.WEST, whoiStopBitsBox, 147, SpringLayout.WEST, whoiPanel);
		whoiSpring.putConstraint(SpringLayout.NORTH, whoiStopBitsBox, 50, SpringLayout.SOUTH, whoidataBitsLabel);
		whoiSpring.putConstraint(SpringLayout.EAST, whoiStopBitsBox, -50, SpringLayout.EAST, whoiPanel);
	}



	private void makeLogPanel() {
		// TODO Auto-generated method stub
		this.logPanel = new JPanel();
		logChooser= new JFileChooser();
		logPanel.add(logChooser);
	}



	private void makeUDPPanel() {
		// TODO Auto-generated method stub
		SpringLayout us=new SpringLayout();
		this.udpPanel = new JPanel(us);
		
		JLabel udpHostLabel = new JLabel("Host:");
		JLabel udpPortLabel = new JLabel("Port:");
		
		udpHostField = new JTextField();
		udpPortField = new JTextField();
		
		udpPanel.add(udpHostLabel);
		us.putConstraint(SpringLayout.WEST, udpHostLabel, 50, SpringLayout.WEST, udpPanel);
		us.putConstraint(SpringLayout.NORTH, udpHostLabel, 50, SpringLayout.NORTH, udpPanel);
		
		udpPanel.add(udpPortLabel);
		us.putConstraint(SpringLayout.WEST, udpPortLabel, 50, SpringLayout.WEST, udpPanel);
		us.putConstraint(SpringLayout.NORTH, udpPortLabel, 50, SpringLayout.SOUTH, udpHostLabel);
		
		udpPanel.add(udpHostField);
		us.putConstraint(SpringLayout.WEST, udpHostField, 140, SpringLayout.WEST, udpPanel);
		us.putConstraint(SpringLayout.NORTH, udpHostField, 50, SpringLayout.NORTH, udpPanel);
		us.putConstraint(SpringLayout.EAST, udpHostField, -50, SpringLayout.EAST, udpPanel);
		
		udpPanel.add(udpPortField);
		us.putConstraint(SpringLayout.WEST, udpPortField, 140, SpringLayout.WEST, udpPanel);
		us.putConstraint(SpringLayout.NORTH, udpPortField, 50, SpringLayout.SOUTH, udpHostLabel);
		us.putConstraint(SpringLayout.EAST, udpPortField, -50, SpringLayout.EAST, udpPanel);
		
		
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
		String[] temp=SerialPortList.getPortNames();
		if(temp!=null)
		{
			for(int i=0;i<temp.length;i++)
			portBox.addItem(temp[i]);
		}
		
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



	//public static void main(String[] args) {
		// TODO Auto-generated method stub
	//		Create_Connection m= new Create_Connection(null);
			//m.pack();
	//		m.setVisible(true);
	//}

	public class ButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == cancelButton)
		{
			Create_Connection.this.setVisible(false);
			boardConnect.setVisible(true);
		}
		else if(e.getSource() == addButton)
		{
			String connectionName=nameField.getText();
			if(connectionName.isEmpty())
			{
				errorLabel.setText("Enter Connection Name!");
				return;
			}
			// create the connection
			Connection c = null;
			if(((String)connectionBox.getSelectedItem()).compareTo("TCP")==0)
			{
				String hostAddress = hostAddressField.getText();
				int listeningPort=0;
				try{
					listeningPort = Integer.parseInt(listeningPortField.getText());
				} catch (Exception ex)
				{
					errorLabel.setText("not a number");
					return;
				}
				if(hostAddress.isEmpty())
				{
					errorLabel.setText("Enter the hostname");
					return;
				}
				if(listeningPort<=1024 || listeningPort>65536)
				{
					errorLabel.setText("Invelid port range");
					return;
				}
				c =new TCPConnection(connectionName, hostAddress,listeningPort);
			}
			else if(((String)connectionBox.getSelectedItem()).compareTo("UDP")==0)
			{
				String hostAddress = udpHostField.getText();
				int listeningPort=0;
				try{
					listeningPort = Integer.parseInt(udpPortField.getText());
				} catch (Exception ex)
				{
					errorLabel.setText("not a number");
					return;
				}
				if(hostAddress.isEmpty())
				{
					errorLabel.setText("Enter the hostname");
					return;
				}
				if(listeningPort<=1024 || listeningPort>65536)
				{
					errorLabel.setText("Invelid port range");
					return;
				}
				c = new UDPConnection(connectionName,listeningPort);
			}
			else if(((String)connectionBox.getSelectedItem()).compareTo("Serial")==0)
			{
				// get port name 
				String portName = (String)portBox.getSelectedItem();
				if(portName == null)
				{
					errorLabel.setText("Invalid port");
					return;
				}
				if(portName.isEmpty())
				{
					errorLabel.setText("Invalid port");
					return;
				}
				// get baud rate
				int baudRate=-1;
				baudRate= (Integer)baudRateBox.getSelectedItem();
				if(baudRate<=0)
				{
					errorLabel.setText("Invelid baud rate");
					return;
				}
				// get parity
				String parity = (String)parityBox.getSelectedItem();
				int parityBits=-1;
				if(parity==null || parity.isEmpty())
				{
					errorLabel.setText("Invelid parity");
					return;
				}
				if(parity.compareToIgnoreCase("Even")==0)
				{
					parityBits=SerialPort.PARITY_EVEN;
				}
				else if(parity.compareTo("Odd")==0)
				{
					parityBits=SerialPort.PARITY_ODD;
				}
				else if(parity.compareToIgnoreCase("None")==0)
				{
					parityBits=SerialPort.PARITY_NONE;
				}
				else
				{
					errorLabel.setText("Invalid parity");
					return;
				}
				
				// get data bits
				int dataBits = -1;
				dataBits=(Integer)dataBitsBox.getSelectedItem();
				if(dataBits<=0 ||dataBits>8)
				{
					errorLabel.setText("Invelid data bits");
					return;
				}
				int stopBits = -1;
				stopBits = (Integer)stopBitsBox.getSelectedItem();
				if(stopBits<=0 ||stopBits>3)
				{
					errorLabel.setText("Invelid stop bits");
					return;
				}
				//get stop bits
				
				c= new SerialConnection(connectionName,portName,baudRate,parityBits,dataBits,stopBits);
			}
			else if(((String)connectionBox.getSelectedItem()).compareTo("WHOI")==0)
			{
				// get port name 
				String portName = (String)portBox.getSelectedItem();
				if(portName == null)
				{
					errorLabel.setText("Invalid port");
					return;
				}
				if(portName.isEmpty())
				{
					errorLabel.setText("Invalid port");
					return;
				}
				// get baud rate
				int baudRate=-1;
				baudRate= (Integer)baudRateBox.getSelectedItem();
				if(baudRate<=0)
				{
					errorLabel.setText("Invelid baud rate");
					return;
				}
				// get parity
				String parity = (String)parityBox.getSelectedItem();
				int parityBits=-1;
				if(parity==null || parity.isEmpty())
				{
					errorLabel.setText("Invelid parity");
					return;
				}
				if(parity.compareToIgnoreCase("Even")==0)
				{
					parityBits=SerialPort.PARITY_EVEN;
				}
				else if(parity.compareTo("Odd")==0)
				{
					parityBits=SerialPort.PARITY_ODD;
				}
				else if(parity.compareToIgnoreCase("None")==0)
				{
					parityBits=SerialPort.PARITY_NONE;
				}
				else
				{
					errorLabel.setText("Invalid parity");
					return;
				}
				
				// get data bits
				int dataBits = -1;
				dataBits=(Integer)dataBitsBox.getSelectedItem();
				if(dataBits<=0 ||dataBits>8)
				{
					errorLabel.setText("Invelid data bits");
					return;
				}
				int stopBits = -1;
				stopBits = (Integer)stopBitsBox.getSelectedItem();
				if(stopBits<=0 ||stopBits>3)
				{
					errorLabel.setText("Invelid stop bits");
					return;
				}
				//get stop bits
				
				c= new WHOIConnection(connectionName,portName,baudRate,parityBits,dataBits,stopBits);
			}
			else if(((String)connectionBox.getSelectedItem()).compareTo("Log")==0)
			{
				
			}
			if(hub.control.addConnection(c))
			{
				Create_Connection.this.setVisible(false);
				boardConnect.addConnection(c);
				boardConnect.setVisible(true);
			}
			else
			{
				errorLabel.setText("Could not add connection");
			}
			// hostAddressField;
			//	private JTextField listeningPortField;
			//	S
		}
		
	}
	}

}
