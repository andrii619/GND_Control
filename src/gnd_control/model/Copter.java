package gnd_control.model;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

public class Copter implements Vehicle {
	
	private GeoPosition home_location;
	private GeoPosition current_location;
	
	private Battery battery;
	private Attitude attitude;
	
	private boolean armed;
	
	private List<Connection> connections;
	private List<VehicleStateListener> listeners;
	
	public Copter()
	{
		this.home_location = new GeoPosition(40.521899, -74.459634);
		connections  = new ArrayList<Connection>();
	}
	public Copter(GeoPosition home)
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
	public void setHomeLocation(GeoPosition p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_mode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arm_throttle() {
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

}
