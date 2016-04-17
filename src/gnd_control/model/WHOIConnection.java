package gnd_control.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkStats;

import gnd_control.model.SerialConnection.SerialPortListener;
import jssc.*;

/**
 * <b>WHOIConnection</b> class implements the Connection interface. Is used for sending data through 
 * a WHOI acoustics modem.
 * echo -e ''\$CCMPC,0,1\\r\\n'' >> /dev/ttyUSB0
 * 
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
	
	private Queue<MAVLinkPacket> queue;
	private Parser parser;
	private MAVLinkStats stats;
	private List<ConnectionObserver> listeners;
	private boolean connected;
	//private boolean test = true;
	
	private SerialPortListener serialListener;
	
	public WHOIConnection(String name, String port, int rate )
	{
		this.connectionName = name;
		this.port = port;
		
		this.rate = rate;
	}
	
	
	public WHOIConnection(String connectionName, String portName, int baudRate, int parityBits, int dataBits, int stopBits) {
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
	}

	public void sendBytes(byte [] arr)
	{
		System.out.println("send bytes");
		System.out.println(Thread.currentThread().getName());
		if(arr!=null)
			if(arr.length!=0)
				try {
					this.serialPort.writeBytes(arr);
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}



	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			serialPort.removeEventListener();
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		System.out.println("Run: "+Thread.currentThread().getName());
		while(true)
		{
			if(this.serialPort==null)
				continue;
			if(!this.serialPort.isOpened())
				continue;
			if(!this.queue.isEmpty())
			{
				System.out.println("Queue is not empty");
				//this.queue.remove().
				WHOIPacket t = new WHOIPacket(this.queue.remove());
				if(t==null)
					return;
				System.out.println("Whoi sends: "+t.toString());
				
				//byte arr[] = this.queue.remove().encodePacket();
				try {//serialPort.write
					//serialPort.writeString("$CCMUC,0,1,0401"+"\r\n");
					//System.out.println("$sdmvsknv");
					//serialPort.writeString("$CCCFQ,SRC\r\n");
					
					this.serialPort.writeString(t.toString());
					//this.serialPort.writeBytes(arr);
					System.out.println("Written");
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			try {
				//byte arr[] = serialPort.readBy
				byte arr[] = serialPort.readBytes();
				if(arr==null)
					continue;
				for(int i=0; i<arr.length;i++)
				{
					System.out.println("Serial got: " +new String(arr));
			//		packet=parser.mavlink_parse_char(Integer.parseInt(String.format("%02X", arr[i]),16));
			//		if(packet!=null)
			//		{
						//System.out.println("not null");
			//			this.notifyAllObservers(packet);
			//		}
				}
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
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
		if(serialPort.isOpened())
			try {
				serialPort.closePort();
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			
			this.serialPort.openPort();
			
			this.serialPort.setParams(this.rate, this.data, this.stop, this.parity);
			//  serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
             //         SerialPort.FLOWCONTROL_RTSCTS_OUT);
			//serialPort.
			  //this.serialPort.addEventListener(this);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.connected=true;
		serialListener = new SerialPortListener();
		try {
			serialPort.addEventListener(serialListener);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			serialPort.writeString("$CCCFG,REV,0\r\n");
			serialPort.writeString("$CCCFG,ASD,0\r\n");                                                        
			serialPort.writeString("$CCCFG,SRC,0\r\n");                                                        
			serialPort.writeString("$CCCFG,RXA,0\r\n");                                                        
			serialPort.writeString("$CCCFG,RXD,1\r\n");                                                        
			serialPort.writeString("$CCCFG,DTO,5\r\n");                                                        
			serialPort.writeString("$CCCFG,AGN,0\r\n");                                                        
			serialPort.writeString("$CCCFG,XST,0\r\n");    
			
			
			serialPort.writeString("$CCCFQ,SRC\r\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serialPort.writeString("$CCMUC,1,0,1FFA\r\n");
			serialPort.writeString("$CCMUC,1,0,0400\r\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serialPort.writeString("$CCMUC,1,0,0090\r\n");
			serialPort.writeString("$CCMUC,1,0,0420\r\n");
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                                        
		 
		
		new Thread(this).start();
	}
	public boolean isConnected()
	{
		return this.connected;
	}
	protected class SerialPortListener implements SerialPortEventListener{
/////
		@Override
		public void serialEvent(SerialPortEvent event) {
			// TODO Auto-generated method stub
			if(event.isRXCHAR() && event.getEventValue() > 0) {
				//test=false;
				MAVLinkPacket packet=null;
				StringBuilder sj=new StringBuilder();
				StringBuilder ch = new StringBuilder();
	            try {
	                //String receivedData = serialPort.readString(event.getEventValue());
	                //System.out.println("Received response: " + receivedData);
	            	byte[] arr=serialPort.readBytes();
	            	if(arr==null)
	            		return;
	            	//System.out.println("Response: "+new String(arr));
	                for(int i=0;i<arr.length;i++)
		            {
	                //	sj.append(String.format("| %03d |", Integer.parseInt(String.format("%02X", arr[i]), 16)));
	                	sj.append(String.format("| %02X |", arr[i]));
	                	packet=parser.mavlink_parse_char(Integer.parseInt(String.format("%02X", arr[i]),16));
	                	ch.append((char)Integer.parseInt(String.format("%02X", arr[i]),16));
	                	if(packet!=null)
	                	{
	                		System.out.println("Serial got mavlink "+packet.toString());
	                		notifyAllObservers(packet);
	                	}
		            }
	               // System.out.println("Serial Got bytes: "+sj.toString());
	                //System.out.println("WHOI");
	                System.out.print(ch.toString());
	                
	            }
	            catch (SerialPortException ex) {
	                System.out.println("Error in receiving string from COM-port: " + ex);
	            }
	            
	        }
		}
		
		////////////////
	}
}
