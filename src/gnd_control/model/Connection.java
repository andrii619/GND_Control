package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

public interface Connection extends Comparable<Connection>  {
	
	public String getConnectionName();
	
	public void setConnectionName(String name);

	public void connect();
	
	public void disconnect();
	
	public void sendMAV(MAVLinkPacket packet);
	
	public void addObserver(ConnectionObserver c);
	
	public int compareTo(Connection b);
	
	public boolean equals(Object o);
	
}
