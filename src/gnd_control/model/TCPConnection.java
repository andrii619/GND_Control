package gnd_control.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.Messages.MAVLinkStats;

public class TCPConnection implements Connection, Runnable, Serializable {
	private static final long serialVersionUID = 1L;
	String connectionName;
	Socket socket = null;
	InputStream inputStream = null;
	OutputStream outputStream = null;
	Queue<MAVLinkPacket> queue;
	Parser parser=null;
	MAVLinkStats stats;
	List<ConnectionObserver> listeners;
	private boolean connected;
	
	private String hostname;
	private int port;
	
	public TCPConnection(String name,String hostname, int port)
	{
		this.connectionName=name;
		socket = null;
		this.hostname=hostname;
		this.port=port;
		
		queue = new ArrayBlockingQueue<MAVLinkPacket>(20);
		parser= new Parser();
		stats = new MAVLinkStats();
		listeners = new ArrayList<ConnectionObserver>();
		//try {
		//	connect();
		//} catch (MyConnectException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		//new Thread(this).start();
	}
	
	@Override
	public void connect() throws MyConnectException {
		// TODO Auto-generated method stub
		try {
			socket = new Socket(hostname, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			this.connected=false;
			throw new MyConnectException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected=false;
			return;
		}
		try {
			socket.setReuseAddress(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected=false;
			return;
		}
		try {
			socket.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected=false;
		}
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected=false;
		}
		connected = true;
		new Thread(this).start();
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			if(this.outputStream!=null)
				outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(this.inputStream!=null)
				inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(this.socket!=null)
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connected=false;
	}

	@Override
	public void sendMAV(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		if(packet!=null)
		{
			queue.add(packet);
		}
		
	}
	
	public void run()
	{
		MAVLinkPacket packet = null;
		
		
	    while (true)
	    {
	    	//if(socket==null)
			//{
			//	try {
			//		connect();
			//	} catch (MyConnectException e) {
					// TODO Auto-generated catch block
			//		e.printStackTrace();
			//		continue;
			//	}
			//}
	    	if(socket==null)
	    		continue;
	    	if(!queue.isEmpty())
	    	{
	    		//queue.remove().encodePacket()
	    		byte arr[] = queue.remove().encodePacket();
	    		try {
					outputStream.write(arr,0,arr.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	try {
	    		//System.out.println("Trying to read packet");
	    	//	byte r[] = new byte[280];
	    		
				packet = parser.mavlink_parse_char(inputStream.read());
			}  catch(SocketTimeoutException m)
	    	{
				continue;
	    	}
	    	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(packet != null)
	    	{
	    		//System.out.println(packet.unpack().toString());
	    		//packet = null;
	    		notifyAllObservers(packet);
	    	}
	    }
	}
	public void addObserver(ConnectionObserver c)
	{
		if(c!=null)
			listeners.add(c);
	}

	private void notifyAllObservers(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).handleMAVPacket(p);
		}
	}

	@Override
	public String getConnectionName() {
		// TODO Auto-generated method stub
		return this.connectionName;
	}

	@Override
	public void setConnectionName(String name) {
		// TODO Auto-generated method stub
		if(name!=null)
			if(!name.isEmpty())
				this.connectionName=name;
	}
	
	public int compareTo(Connection b)
	{
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
	public boolean isConnected()
	{
		return this.connected;
	}

}
