package gnd_control.control;

import com.MAVLink.Messages.MAVLinkMessage;

public interface Vehicle_Observer {
	public void handle_status_msg(MAVLinkMessage m);

}
