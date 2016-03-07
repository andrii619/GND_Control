package gnd_control.model;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.MAVLink.MAVLinkPacket;

public class WHOIConnection implements Connection,Runnable,SerialPortEventListener {

	@Override
	public String getConnectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionName(String name) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void addObserver(ConnectionObserver c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
