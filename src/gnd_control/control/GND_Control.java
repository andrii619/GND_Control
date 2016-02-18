package gnd_control.control;

import com.MAVLink.Messages.MAVLinkMessage;

public class GND_Control implements Control, Vehicle_Observer {
	
	public GND_Control()
	{
		
	}

	@Override
	public void handle_status_msg(MAVLinkMessage m) {
		// TODO Auto-generated method stub
		
	}

}
