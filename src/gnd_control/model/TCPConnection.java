package gnd_control.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class TCPConnection implements Connection, Runnable {
	Socket socket = null;
	InputStream inputStream = null;
	OutputStream outputStream = null;
	Queue<MAVLinkPacket> queue;
	Parser parser=null;
	MAVLinkStats stats;
	List<ConnectionObserver> listeners;
	private boolean connected;
	
	public TCPConnection(String hostname, int port)
	{
		socket = null;
		try {
			socket = new Socket(hostname, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.setReuseAddress(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue = new ArrayBlockingQueue<MAVLinkPacket>(20);
		parser= new Parser();
		stats = new MAVLinkStats();
		listeners = new ArrayList<ConnectionObserver>();
		
		new Thread(this).start();
	}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMAV(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		if(packet!=null)
		{
			queue.add(packet);
		}
		
	}

	@Override
	public void sendWHOI(MAVLinkPacket packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendWHOI(WHOIPacket packet) {
		// TODO Auto-generated method stub
		
	}
	
	public void run()
	{
		MAVLinkPacket packet = null;
		
	    while (true)
	    {
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

}
