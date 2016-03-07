package gnd_control.model;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.MAVLink.MAVLinkPacket;

public class WHOIConnection implements Connection,Runnable,SerialPortEventListener {
	private String connectionName;
	
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

	@Override
	public int compareTo(Connection b) {
		// TODO Auto-generated method stub
		return this.connectionName.compareTo(b.getConnectionName());
	}
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if(!(o instanceof Connection))
		{
			return false;
		}
		Connection temp = (Connection)o;
		if(temp.getConnectionName().compareTo(this.connectionName)==0)
		{
			return true;
		}
		else
			return false;
	}

}
