package gnd_control.model;

import org.jdesktop.swingx.mapviewer.GeoPosition;

public interface Vehicle {
	public void initialize();
	
	public GeoPosition getLocation();
	public void setHomeLocation(GeoPosition p);
	
	public void addConnection(Connection c);
	public void set_mode();
	public void arm_throttle();
	public void takeoff(int height);
	public double get_battery_level();
	
	public boolean is_armable();
}
