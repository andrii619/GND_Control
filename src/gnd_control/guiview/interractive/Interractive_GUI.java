package gnd_control.guiview.interractive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gnd_control.control.Control;
import gnd_control.model.VehicleStateListener;

public class Interractive_GUI extends JPanel implements VehicleStateListener {
	private static final String[] arr={"GUIDED","AUTO"};
	
	
	JComboBox modeBox;
	
	JButton arm_button;
	
	SpringLayout layout;
	Control control;
	
	
	public Interractive_GUI(Control control)
	{
		super();
		this.control = control;
		arm_button = new JButton("");
		if(control.isVehicleConnected())
		{
			arm_button.setText("<html><font color = 'green'>Disarmed</font></html>");
			if(control.isVehicleArmed())
				arm_button.setText("<html><font color = 'red'>Armed</font></html>");
		}
		else
		{
			arm_button.setText("Disconnected");
			arm_button.setEnabled(false);
		}
		layout = new SpringLayout();
		this.setLayout(layout);
		
		this.add(arm_button);
		
		control.addVehicleListener(this);
		
	}
	

	@Override
	public void armedChanged(boolean armed) {
		// TODO Auto-generated method stub
		if(control.isVehicleArmed())
		{
			arm_button.setText("<html><font color = 'green'>Disarmed</font></html>");
		}
		else
		{
			arm_button.setText("<html><font color = 'red'>Armed</font></html>");
		}
	}


	@Override
	public void locationChange() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void batteryLevelChange() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connected() {
		// TODO Auto-generated method stub
		if(control.isVehicleConnected())
		{
			arm_button.setEnabled(true);
			if(control.isVehicleArmed())
			{
				arm_button.setText("<html><font color = 'red'>Armed</font></html>");
			}
			else
			{
				arm_button.setText("<html><font color = 'green'>Disarmed</font></html>");
			}
		}
		else
		{
			arm_button.setText("Disconnected");
			arm_button.setEnabled(false);
		}
	}
	public class InterractiveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == arm_button)
			{
				if(control.isVehicleConnected())
				{
					if(control.isVehicleArmed())
					{
						control.disarmVehicle();
					}
					else
						control.armVehicle();
				}
			}
		}
		
	}
	@Override
	public void flightModeChanged(String mode) {
		// TODO Auto-generated method stub
		
	}
}
