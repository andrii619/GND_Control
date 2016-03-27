package gnd_control.model;

import com.MAVLink.MAVLinkPacket;

/**
 * <b>ConnectionObserver</b> interface defines methods for handling data that arrives on a connection.
 */
public interface ConnectionObserver {

	/**
	 * The connection observer will have to process a MAVLink packet that arrives on a connection.
	 * @param p arriving mavlink packet.
	 */
	public void handleMAVPacket(MAVLinkPacket p );
	
	
}
