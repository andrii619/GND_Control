package gnd_control.guiview;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class VehicleStatus extends JPanel {
	private JButton armButton;
	private JProgressBar batteryStatusBar;
	private static final int PROGRESS_MAX=100;
	private SpringLayout layout;
	private JLabel groundSpeedLabel=new JLabel("Ground Speed(km/h)");
	private JLabel altitudeLabel = new JLabel("Altitude(m)");
	private JTextField groundSpeedField;
	private JTextField altitudeField;
	
	private JComboBox<String> modeBox;
	private static final String[] MODES = {"Stabilize","Acro","Alt Hold","Auto","Guided","Loiter","RTL","Circle","Position",
			"Land"};
	
	private GND_Control_GUI_HUB hub;
	
	public VehicleStatus(GND_Control_GUI_HUB hub)
	{
		this.hub=hub;
		layout = new SpringLayout();
		armButton = new JButton("<html><font color=blue>Disconnected</font></html>");
		armButton.setEnabled(false);
		batteryStatusBar = new JProgressBar();
		batteryStatusBar.setMaximum(PROGRESS_MAX);
		batteryStatusBar.setForeground(Color.RED);
		batteryStatusBar.setBorder(BorderFactory.createLineBorder(Color.black));
		batteryStatusBar.setOrientation(JProgressBar.HORIZONTAL);
		batteryStatusBar.setStringPainted(true);
		batteryStatusBar.setValue(0);
		batteryStatusBar.setString("Battery Level: "+this.batteryStatusBar.getValue());
		
		
		
		this.setLayout(layout);
		
		this.add(armButton);
		layout.putConstraint(SpringLayout.WEST, armButton, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, armButton, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, armButton, -50, SpringLayout.EAST, this);
		
		modeBox = new JComboBox<String>(MODES);
		modeBox.setEnabled(false);
		this.add(modeBox);
		layout.putConstraint(SpringLayout.WEST, modeBox, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, modeBox, 50, SpringLayout.SOUTH, armButton);
		layout.putConstraint(SpringLayout.EAST, modeBox, -50, SpringLayout.EAST, this);
		
		this.add(batteryStatusBar);
		layout.putConstraint(SpringLayout.WEST, batteryStatusBar, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, batteryStatusBar, 50, SpringLayout.SOUTH, modeBox);
		layout.putConstraint(SpringLayout.EAST, batteryStatusBar, -50, SpringLayout.EAST, this);
		
		this.add(altitudeLabel);
		layout.putConstraint(SpringLayout.WEST, altitudeLabel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, altitudeLabel, 50, SpringLayout.SOUTH, batteryStatusBar);
		
		this.add(groundSpeedLabel);
		layout.putConstraint(SpringLayout.WEST, groundSpeedLabel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, groundSpeedLabel, 50, SpringLayout.SOUTH, altitudeLabel);
		
		this.altitudeField=new JTextField();
		this.altitudeField.setText("0.0");
		this.altitudeField.setEditable(false);
		this.add(altitudeField);
		layout.putConstraint(SpringLayout.WEST, altitudeField, 250, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, altitudeField, 50, SpringLayout.SOUTH, batteryStatusBar);
		layout.putConstraint(SpringLayout.EAST, altitudeField, -50, SpringLayout.EAST, this);
		
		this.groundSpeedField=new JTextField();
		this.groundSpeedField.setText("0.0");
		this.groundSpeedField.setEditable(false);
		this.add(groundSpeedField);
		layout.putConstraint(SpringLayout.WEST, groundSpeedField, 250, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, groundSpeedField, 50, SpringLayout.SOUTH, altitudeLabel);
		layout.putConstraint(SpringLayout.EAST, groundSpeedField, -50, SpringLayout.EAST, this);
		
		
		if(this.hub!=null)
		if(hub.control.isVehicleConnected())
		{
			if(hub.control.isVehicleArmed())
			{
				this.armButton.setEnabled(true);
				this.armButton.setText("<html><font color=red>Armed</font></html>");
				
			}
			else
			{
				this.armButton.setEnabled(true);
				this.armButton.setText("<html><font color=green>Disarmed</font></html>");
			}
			this.batteryStatusBar.setString("Battery Level "+hub.control.getVehicleBatteryLevel());
			this.groundSpeedField.setText(""+hub.control.getVehicleGroundSpeed());
			this.altitudeField.setText(""+hub.control.getVehicleAltitude());
			
		}
		else
		{
			this.armButton.setEnabled(false);
			this.armButton.setText("<html><font color=blue>Disconnected</font></html>");
			this.batteryStatusBar.setValue(0);
			this.groundSpeedField.setText("0.0");
			this.altitudeField.setText("0.0");
		}
		
		
		
		
		this.setBorder(BorderFactory.createTitledBorder("Vehicle Status"));
		this.setPreferredSize(new Dimension(400,400));
		this.setMinimumSize(new Dimension(400,400));
		this.setMaximumSize(new Dimension(400,400));
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			JFrame f = new JFrame();
			f.add(new VehicleStatus(null));
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			
	}

}
