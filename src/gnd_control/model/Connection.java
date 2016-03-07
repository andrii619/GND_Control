package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

public interface Connection  {
	
	public String getConnectionName();
	
	public void setConnectionName(String name);

	public void connect();
	
	public void disconnect();
	
	public void sendMAV(MAVLinkPacket packet);
	
	public void sendWHOI(MAVLinkPacket packet);
	
	public void sendWHOI(WHOIPacket packet);
	
	public void addObserver(ConnectionObserver c);
	
}
