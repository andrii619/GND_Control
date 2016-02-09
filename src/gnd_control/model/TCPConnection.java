package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

public class TCPConnection implements Connection {

	
	public TCPConnection(String hostname, int port)
	{
		
	}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMAV(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWHOI(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWHOI(WHOIPacket packet) {
		// TODO Auto-generated method stub
		
	}

}
