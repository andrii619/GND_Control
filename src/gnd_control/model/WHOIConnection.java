package gnd_control.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkStats;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * <b>WHOIConnection</b> class implements the Connection interface. Is used for sending data through 
 * a WHOI acoustics modem.
 * @see Connection
 */
public class WHOIConnection implements Connection,Runnable, Serializable {

	private String connectionName;
	
	private String port;
	private int rate;
	private int parity;
	private int data;
	private int stop;
	
	private SerialPort serialPort;
	
	private Queue<WHOIPacket> queue;
	private Parser parser;
	private MAVLinkStats stats;
	private List<ConnectionObserver> listeners;
	private boolean connected;
	
//	private CommPortIdentifier telemetryID;
	
	//private SerialPort telemetrySerial;
	
	//private InputStream telemetryInput;
	//private OutputStream telemetryOutput;
	

	
	
	public WHOIConnection(String connectionName, String portName, int baudRate, int parityBits, int dataBits, int stopBits) {
		// TODO Auto-generated constructor stub
		this.connectionName=connectionName;
		this.port=portName;
		this.rate=baudRate;
		this.parity=parityBits;
		this.data=dataBits;
		this.stop=stopBits;
		this.queue=new ArrayBlockingQueue<WHOIPacket>(20);
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
			WHOIPacket pack=new WHOIPacket(p);
			if(p!=null)
			{
				// convert the mavlink packet into a whoi packet
				
				this.queue.offer(pack);
			}
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
			
			//try {
				byte arr[]=null;
				try {
					arr = serialPort.readBytes();
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StringBuilder tmp=new StringBuilder();
				tmp.append(new String(arr));
				System.out.println(tmp.toString());
				//for(int i=0; i<arr.length;i++)
				//{
					//tmp.append(String.format("| %03d |", Integer.parseInt(String.format("%02X", arr[i]), 16)));
				//	packet=parser.mavlink_parse_char(Integer.parseInt(String.format("%02X", arr[i]),16));
				//	if(packet!=null)
				//	{
						//System.out.println("not null");
				//		this.notifyAllObservers(packet);
				//	}
				//}
			//} catch (SerialPortException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
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
		if(this.serialPort.isOpened())
			try {
				this.serialPort.closePort();
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			this.serialPort.openPort();
			//this.serialPort.writeBytes("test".getBytes());
			//System.out.println("Got: "+this.serialPort.readBytes(4).toString());
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
