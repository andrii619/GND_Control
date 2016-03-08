package gnd_control.model;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;

public class UDPConnection implements Connection, Runnable, Serializable {
	private static final long serialVersionUID = 1L;
	private String connectionName;
	private int port;
	private String address;
	
	private int localPort;
	
	private DatagramSocket udpSocket;
	private InetAddress Inetaddress;
	private DatagramPacket packet;
	private byte[] buf;
	
	private Parser parser;
	
	public UDPConnection(String name, String address, int port)
	{
		this.connectionName=name;
		this.port = port;
		this.address = address;
		

		
		buf = new byte[263];
		this.packet = new DatagramPacket(buf,buf.length);
		
		parser=new Parser();
		
		new Thread(this).start();
		//this.start();
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			this.udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.Inetaddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		udpSocket.close();
	}

	@Override
	public void sendMAV(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
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

	@Override
	public void addObserver(ConnectionObserver c) {
		// TODO Auto-generated method stub
		
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
