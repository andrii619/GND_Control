package gnd_control.guiview.interractive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gnd_control.control.Control;
import gnd_control.model.GPosition;
import gnd_control.model.VehicleStateListener;

/**
 * The main GUI for controlling the drone using the keyboard or a joystick.
 * @author andrii
 *
 */
public class Interractive_GUI extends JPanel implements VehicleStateListener {
	//private static final String[] arr={"GUIDED","AUTO"};
	private static int roll = 1500;
	private static int yaw = 1500;
	private static int pitch = 1500;
	private static int throttle = 1500;
	private static final int DELTA = 10;
	
	//JComboBox modeBox;
	
	JButton manual_control_enable;
	JButton manual_control_disable;
	JLabel errorLabel;
	
	SpringLayout layout;
	Control control;
	InterractiveListener listener;
	ButtonListener buttonListener;
	
	
	public Interractive_GUI(Control control)
	{
		super();
		this.control = control;
		manual_control_enable = new JButton("Enable manual Controls");
		manual_control_disable = new JButton("Disable Manual Controls");
		if(control.isVehicleConnected())
		{
			
		}
		else
		{
			
		}
		layout = new SpringLayout();
		this.setLayout(layout);
		
		this.add(manual_control_enable);
		layout.putConstraint(SpringLayout.WEST, manual_control_enable, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, manual_control_enable, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, manual_control_enable, -50, SpringLayout.EAST, this);
		
		this.add(manual_control_disable);
		layout.putConstraint(SpringLayout.WEST, manual_control_disable, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, manual_control_disable, 50, SpringLayout.SOUTH, manual_control_enable);
		layout.putConstraint(SpringLayout.EAST, manual_control_disable, -50, SpringLayout.EAST, this);
		
		errorLabel = new JLabel();
		this.add(errorLabel);
		errorLabel.setText("lllll");
		layout.putConstraint(SpringLayout.WEST, errorLabel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 50, SpringLayout.SOUTH, manual_control_disable);
		layout.putConstraint(SpringLayout.EAST, errorLabel, -50, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, errorLabel, 5, SpringLayout.SOUTH, this);
		
		
		
		//control.addVehicleListener(this);
		listener=new InterractiveListener();
		manual_control_enable.addActionListener(listener);
		manual_control_disable.addActionListener(listener);
		this.setFocusable(true);
		this.setEnabled(true);
		
		buttonListener = new ButtonListener();
		manual_control_enable.addKeyListener(buttonListener);
		
		//this.addKeyListener(buttonListener);
	}
	

	@Override
	public void armedChanged(boolean armed) {
		// TODO Auto-generated method stub
		if(control.isVehicleArmed())
		{
			//arm_button.setText("<html><font color = 'green'>Disarmed</font></html>");
		}
		else
		{
			//arm_button.setText("<html><font color = 'red'>Armed</font></html>");
		}
	}


	@Override
	public void locationChange(GPosition position) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void batteryLevelChange(double level) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connectedChanged(boolean connected) {
		// TODO Auto-generated method stub
		if(control.isVehicleConnected())
		{
			
		}
	}
	@Override
	public void flightModeChanged(String mode) {
		// TODO Auto-generated method stub
		
	}
	public class InterractiveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == manual_control_enable)
			{
				if(control.isVehicleConnected())
				{
					//control.ManualControl();
				}
			}
			else if(e.getSource() == manual_control_disable)
			{
				if(control.isVehicleConnected())
				{
					control.disableRCOverride();
				}
			}
		}
		
	}
	public class ButtonListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_UP) // pitch. nose down
			{
				if(pitch>=1100)
					pitch-=DELTA; // nose down
				System.out.println("Current pitch "+pitch);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) // pitch, nose up
			{
				if(pitch<=1900)
					pitch += DELTA;
				System.out.println("Current pitch "+pitch);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);

			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) // roll left
			{
				if(roll>=1100)
					roll-=DELTA;
				System.out.println("Current roll "+roll);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) // roll right
			{
				if(roll<=1900)
					roll+=DELTA;
				System.out.println("Current roll "+roll);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);
			}
			else if(e.getKeyCode() == KeyEvent.VK_A) // yaw left
			{
				if(yaw>=1100)
					yaw-=DELTA;
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);

			}
			else if(e.getKeyCode() == KeyEvent.VK_D) // yaw right
			{
				if(yaw<=1900)
					yaw+=DELTA;
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);

			}
			else if(e.getKeyCode() == KeyEvent.VK_W) // increase throttle
			{
				if(throttle<=1900)
					throttle+=DELTA;
				System.out.println("Current throttle "+throttle);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);

			}
			else if(e.getKeyCode() == KeyEvent.VK_S) // decrease throttle
			{
				if(throttle>=1100)
					throttle-=DELTA;
				System.out.println("Current throttle "+throttle);
				errorLabel.setText("pitch: "+pitch +", roll: "+roll+", yaw: "+yaw+", throttle: "+throttle);
				control.manualOverride(pitch,roll,yaw,throttle);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}




