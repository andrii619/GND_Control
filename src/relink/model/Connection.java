package relink.model;

import com.MAVLink.MAVLinkPacket;

public interface Connection {

	public void connectTelemetry(String port, int baudRate);
	
	public void disconnectTelemetry();
	
	public void connectWHOI(String port, int baudRate);
	
	public void disconnectWHOI();
	
	public void sendMAVPacket(MAVLinkPacket p);
	
}
