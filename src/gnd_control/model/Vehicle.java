package gnd_control.model;

import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.MAVLink.Messages.MAVLinkMessage;

public interface Vehicle {
	public static final int heartbeat_rate_msec = 1000;
	
	public void initialize();
	
	public GeoPosition getLocation();
	public void setHomeLocation(GPosition p);
	
	public void addConnection(Connection c);
	public void setLatitude(double latitude);
	public void setLongtitude();
	public void set_velocity(double x_velocity, double y_velocity, double z_velocity, int duration);
	public void set_mode();
	public void set_armed(boolean armed);
	public void takeoff(int height);
	public double get_battery_level();
	
	public boolean is_armable();
	
	public void addVehicleStateListener(VehicleStateListener c);
	public void setLongtitude(float longtitude);
	public void handleMAVLink_Message(MAVLinkMessage m);
	
	public void get_position();
	
	public List<Connection> listConnections();
	public void closeConnection(Connection c);
}
