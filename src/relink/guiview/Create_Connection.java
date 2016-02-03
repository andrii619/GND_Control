package relink.guiview;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	
	////////////////////////////////////
	///////
	private final static String SERIAL_PANEL = "serial";
	private final static String WHOI_PANEL = "whoi";
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
		
		
		this.contentPane.add(connectionTypePanel, BorderLayout.NORTH);
		this.contentPane.add(serialPanel, BorderLayout.CENTER);
		this.contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		
		
		
		this.pack();
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
