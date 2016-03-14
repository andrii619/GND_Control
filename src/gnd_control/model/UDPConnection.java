package gnd_control.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;

public class UDPConnection implements Connection, Runnable, Serializable {
	private static final long serialVersionUID = 1L;
	private String connectionName;
	
	private int localPort;
	private int targetPort;
	//private String address;
	
	//private int localPort;
	
	private DatagramSocket udpSocket;
	private InetAddress targetAddress;
	private DatagramPacket packet;
	private byte[] buf;
	
	private Parser parser;
	private Queue<MAVLinkPacket> queue;
	private List<ConnectionObserver> listeners;
	private boolean connected;
	
	public UDPConnection(String name, int port)
	{
		this.connectionName=name;
		this.localPort = port;
		//this.address = address;
		connected=false;
		
		buf = new byte[263];
		this.packet = new DatagramPacket(buf,buf.length);
		
		parser=new Parser();
		queue=new ArrayBlockingQueue<MAVLinkPacket>(20);
		listeners=new ArrayList<ConnectionObserver>();
		
		
		//this.start();
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			this.udpSocket = new DatagramSocket(this.localPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//try {
		//	this.Inetaddress = InetAddress.getByName(address);
		//} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		try {
			udpSocket.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			udpSocket.setReuseAddress(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connected=true;
		new Thread(this).start();
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		udpSocket.close();
	}

	@Override
	public void sendMAV(MAVLinkPacket packet) {
		
		if(packet!=null)
			this.queue.add(packet);
	}
		/**
		packet.generateCRC();
		this.buf=packet.encodePacket();
		
		this.packet = new DatagramPacket(buf,buf.length);
		this.packet.setData(buf);
		try {
			this.udpSocket.send(this.packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void send(byte []b , int length)
	{
		try {
			udpSocket.send(new DatagramPacket(b,length));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MAVLinkPacket recieve()
	{
		MAVLinkPacket p=null;
		try {
			udpSocket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return p;
		}
		byte[] data=packet.getData();
		int l = packet.getLength();
		for(int i=0;i<l;i++)
		{
			p=parser.mavlink_parse_char(data[i]);
		}
		
		return p;
	}
	*/
	@Override
	public void addObserver(ConnectionObserver c) {
		if(c!=null)
			listeners.add(c);
	}
	private void notifyAllObservers(MAVLinkPacket p) {
		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).handleMAVPacket(p);
		}
	}
	
	public void setConnectionName(String name)
	{
		if(name!=null)
			if(!name.isEmpty())
				this.connectionName=name;
	}
	public String getConnectionName()
	{
		return this.connectionName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		MAVLinkPacket p=null;
		while(true)
		{
			if(this.udpSocket==null)
				continue;
			if(!queue.isEmpty())
			{
				byte[] arr=queue.remove().encodePacket(); 
				try {
					//System.out.println("Sending packet");
					if(this.targetAddress!=null && targetPort!=0)
						udpSocket.send(new DatagramPacket(arr,arr.length,this.targetAddress,this.targetPort));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				packet=new DatagramPacket(this.buf,buf.length);
				udpSocket.receive(packet);
				//System.out.println("Got pack");
				//System.out.println("Got: "+new String(packet.getData()));
				this.targetAddress=packet.getAddress();
				this.targetPort=packet.getPort();
				byte[] arr=packet.getData();
				
				////////////////////////
				StringBuilder sb=new StringBuilder();
				StringBuilder sj=new StringBuilder();
				///////////////
				int nextValue=0;
				for(int i=packet.getOffset();i<packet.getLength();i++)
				{
					//sb.append(String.format("| %02X |", arr[i]));
					//sj.append(String.format("| %03d |", Integer.parseInt(String.format("%02X", arr[i]), 16)));
					//System.out.println("Got byte: "+Character.toString((char)arr[i]));
					p=parser.mavlink_parse_char(Integer.parseInt(String.format("%02X", arr[i]),16));
					if(p!=null)
					{
						System.out.println("not null");
						this.notifyAllObservers(p);
					}
				}
				//System.out.println("Got bytes: "+sb.toString());
				//System.out.println("Got bytes: "+sj.toString());
				ByteBuffer wrapper=ByteBuffer.wrap(arr);
				//CharBuffer c=wrapper.asCharBuffer();
				///char[] chara=c.array();
				for(int i=0;i<wrapper.remaining();i++)
				{
					//sj.append(String.format("| %03d |",wrapper.getInt()));
					p=parser.mavlink_parse_char(wrapper.getInt());
					if(p!=null)
					{
						System.out.println("not null");
						this.notifyAllObservers(p);
					}
				}
				//System.out.println("Got bytes: "+sj.toString());
			} catch(SocketTimeoutException m)
			{
				continue;
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private int stringToInt(String m)
	{
		int i=-1;
		if(m==null)
			return i;
		if(m.length()!=2)
			return i;
		for(int j=0;i<2;j++)
		{
			char t=m.charAt(j);
		}
		
		
		return i;
		
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
