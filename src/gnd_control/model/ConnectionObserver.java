package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

public interface ConnectionObserver {

	public void handleMAVPacket(MAVLinkPacket p );
	
	
}
