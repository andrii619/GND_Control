package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

/**
 * <b>Connection</b> interface defines all the methods that have to be implemented for a connection to the drone.
 *
 */
public interface Connection extends Comparable<Connection>  {
	/**
	 * Get the name of this connection.
	 * @return Connection name.
	 */
	public String getConnectionName();
	
	/**
	 * Sets the name of this connection
	 * @param name the name of this connection
	 */
	public void setConnectionName(String name);

	/**
	 * Connects to the drone using this connection.
	 * @throws MyConnectException
	 */
	public void connect() throws MyConnectException;
	
	/**
	 * Disconnects form the drone, closes any open file descriptors.
	 */
	public void disconnect();
	
	/**
	 * Send a MAVLink packet using this connection
	 * @param packet MAVLink packet to be sent.
	 */
	public void sendMAV(MAVLinkPacket packet);
	
	/**
	 * Add an observer to this connection. The observer will be notified of all arriving packets.
	 * @param c observer to be added.
	 */
	public void addObserver(ConnectionObserver c);
	
	/**
	 * Compares the names of two connections.
	 */
	public int compareTo(Connection b);
	
	/**
	 * Compares two connections based on their name.
	 * @param o Another connection object.
	 * @return True of the connections have same name.
	 */
	public boolean equals(Object o);
	
}
