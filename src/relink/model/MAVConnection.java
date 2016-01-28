package relink.model;

import java.io.InputStream;
import java.io.OutputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.MAVLink.MAVLinkPacket;

public class MAVConnection implements Connection, SerialPortEventListener, Runnable {

	private String telemetryPort;
	private String WHOIPort;
	private int telemetryRate;
	private int whoiRate;
	
	private CommPortIdentifier telemetry;
	private CommPortIdentifier whoi;
	
	private InputStream input;
	private OutputStream output;
	
	
	public MAVConnection()
	{
		this.telemetryPort = new String();
		this.WHOIPort = new String();
		
		this.telemetryRate = 0;
		this.whoiRate = 0;
	}
	
	
	@Override
	public void sendMAVPacket(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connectTelemetry(String port, int baudRate) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void disconnectTelemetry() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connectWHOI(String port, int baudRate) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void disconnectWHOI() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
