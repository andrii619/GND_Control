package gnd_control.model;

import jssc.*;

//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
//import java.util.TooManyListenersException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

//import javax.comm.CommPortIdentifier;
//import javax.comm.NoSuchPortException;
//import javax.comm.PortInUseException;
//import javax.comm.SerialPort;
//import javax.comm.SerialPortEvent;
//import javax.comm.SerialPortEventListener;
//import javax.comm.UnsupportedCommOperationException;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkStats;

/**
 * <b>SerialConnection</b> class implements the Connection interface. Is used to send MAVLink packets through a serial interface.
 * @see Connection
 */
public class SerialConnection implements Connection,Runnable {

	private String connectionName;
	
	private String port;
	private int rate;
	private int parity;
	private int data;
	private int stop;
	
	private SerialPort serialPort;
	
	private Queue<MAVLinkPacket> queue;
	private Parser parser;
	private MAVLinkStats stats;
	private List<ConnectionObserver> listeners;
	private boolean connected;
	
//	private CommPortIdentifier telemetryID;
	
	//private SerialPort telemetrySerial;
	
	//private InputStream telemetryInput;
	//private OutputStream telemetryOutput;
	
	public SerialConnection(String name, String port, int rate )
	{
		this.connectionName = name;
		this.port = port;
		
		this.rate = rate;
	}
	
	
	public SerialConnection(String connectionName, String portName, int baudRate, int parityBits, int dataBits, int stopBits) {
		// TODO Auto-generated constructor stub
		this.connectionName=connectionName;
		this.port=portName;
		this.rate=baudRate;
		this.parity=parityBits;
		this.data=dataBits;
		this.stop=stopBits;
		this.queue=new ArrayBlockingQueue<MAVLinkPacket>(20);
		this.parser=new Parser();
		this.stats=new MAVLinkStats();
		this.listeners=new ArrayList<ConnectionObserver>();
	}


	@Override
	public void sendMAV(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		//byte[] b = p.encodePacket();
		if(this.queue!=null)
		{
			if(p!=null)
				this.queue.offer(p);
		}
		//try {
		//	this.telemetryOutput.write(b);
		//	this.telemetryOutput.flush();
	//	}// catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		// send the byte buffer over serial connection 
	}

/**
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

*/
	/**
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
*/

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
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			this.serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void addObserver(ConnectionObserver c) {
		// TODO Auto-generated method stub
		if(c!=null)
			listeners.add(c);
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
		MAVLinkPacket packet = null;
		while(true)
		{
			if(this.serialPort==null)
				continue;
			if(!this.serialPort.isOpened())
				continue;
			if(!this.queue.isEmpty())
			{
				byte arr[] = this.queue.remove().encodePacket();
				try {
					this.serialPort.writeBytes(arr);
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				byte arr[] = serialPort.readBytes();
				for(int i=0; i<arr.length;i++)
				{
					packet=parser.mavlink_parse_char(Integer.parseInt(String.format("%02X", arr[i]),16));
					if(packet!=null)
					{
						//System.out.println("not null");
						this.notifyAllObservers(packet);
					}
				}
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private void notifyAllObservers(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).handleMAVPacket(packet);
		}
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


	@Override
	public void connect() throws MyConnectException {
		// TODO Auto-generated method stub
		this.serialPort = new SerialPort(this.port);
		
		try {
			this.serialPort.openPort();
			
			this.serialPort.setParams(this.rate, this.data, this.stop, this.parity);
			  serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                      SerialPort.FLOWCONTROL_RTSCTS_OUT);
			  //this.serialPort.addEventListener(this);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connected=true;
		new Thread(this).start();
	}
	public boolean isConnected()
	{
		return this.connected;
	}
}
