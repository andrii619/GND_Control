package gnd_control.model;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.enums.MAV_AUTOPILOT;
import com.MAVLink.enums.MAV_TYPE;

public class Copter implements Vehicle {
	
	private int vehicleId;
	private boolean active;
	private float roll;
	private float pitch;
	private float yaw;
	private float heading;
	private float groundSpeed;
	private float airSpeed;
	private float climbRate;
	private float altitude;
	
	MAV_AUTOPILOT firmwareType;
	MAV_TYPE vehicleType;
	
	private GPosition home_location;
	private GPosition current_location;
	
	private Battery battery;
	private Attitude attitude;
	
	private boolean armed;
	
	private List<Connection> connections;
	private List<VehicleStateListener> listeners;
	
	public Copter(Connection c)
	{
		this.home_location = new GPosition(40.521899, -74.459634);
		connections  = new ArrayList<Connection>();
	}
	public Copter(GPosition home)
	{
		this.home_location = home;
	}

	public void addConnection(Connection c)
	{
		this.connections.add(c);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GeoPosition getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHomeLocation(GPosition p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_flight_mode() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void takeoff(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get_battery_level() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean is_armable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addVehicleStateListener() {
		// TODO Auto-generated method stub
	}

	@Override
	public void set_armed(boolean armed) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLongtitude(float longtitude) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleMAVLink_Message(MAVLinkMessage m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLatitude(double latitude) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLongtitude() {
		// TODO Auto-generated method stub
		
	}
	
	public void takeoff(float altitude)
	{
		
	}

	@Override
	public void set_velocity(double x_velocity, double y_velocity, double z_velocity, int duration) {
		// TODO Auto-generated method stub
		
	}

}
