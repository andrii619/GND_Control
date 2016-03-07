package gnd_control.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import com.MAVLink.MAVLinkPacket;

public class SerialConnection implements Connection,Runnable,  SerialPortEventListener {

	private String connectionName;
	
	private String port;
	private int rate;
	
	private CommPortIdentifier telemetryID;
	
	private SerialPort telemetrySerial;
	
	private InputStream telemetryInput;
	private OutputStream telemetryOutput;
	
	public SerialConnection(String name, String port, int rate )
	{
		this.connectionName = name;
		this.port = port;
		
		this.rate = rate;
	}
	
	
	@Override
	public void sendMAV(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		byte[] b = p.encodePacket();
		
		try {
			this.telemetryOutput.write(b);
			this.telemetryOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// send the byte buffer over serial connection 
	}


	public void connect() {
		// TODO Auto-generated method stub
		try {
			this.telemetryID = CommPortIdentifier.getPortIdentifier(port);
			this.telemetrySerial = (SerialPort) this.telemetryID.open("RE_LINK", 2000);
			this.telemetrySerial.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			this.telemetrySerial.addEventListener(this);
			this.telemetrySerial.setDTR(false);
			this.telemetrySerial.setRTS(false);
			this.telemetrySerial.notifyOnDataAvailable(true);
			this.telemetryInput=this.telemetrySerial.getInputStream();
			this.telemetryOutput=this.telemetrySerial.getOutputStream();
			
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public void disconnectTelemetry() {
		// TODO Auto-generated method stub
		try {
			this.telemetryInput.close();
			this.telemetryOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.telemetrySerial.close();
	}


/**	
	public void connectWHOI(String port, int baudRate) {
		// TODO Auto-generated method stub
		try {
			this.whoiID = CommPortIdentifier.getPortIdentifier(port);
			this.whoiSerial = (SerialPort) whoiID.open("RE_LINK", 2000);
			this.whoiSerial.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			this.whoiSerial.setDTR(false);
			this.whoiSerial.setRTS(false);
			this.whoiSerial.addEventListener(this);
			this.whoiSerial.notifyOnDataAvailable(true);
			this.whoiInput=this.whoiSerial.getInputStream();
			this.whoiOutput=this.whoiSerial.getOutputStream();
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

*/
	/**
	
	public void disconnectWHOI() {
		// TODO Auto-generated method stub
		try {
			this.whoiInput.close();
			this.whoiOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.whoiSerial.close();
	}
*/

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addObserver(ConnectionObserver c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getConnectionName() {
		// TODO Auto-generated method stub
		return this.connectionName;
	}


	@Override
	public void setConnectionName(String name) {
		// TODO Auto-generated method stub
		if(name!= null)
			if(!name.isEmpty())
				this.connectionName=name;
	}


	@Override
	public void run() {
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
