package gnd_control.model;

import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_heartbeat;

public interface Vehicle {
	public static final int heartbeat_rate_msec = 1000;
	
	public static final String[] COPTER_MODES ={"STABILIZE","ACRO","ALT_HOLD","AUTO","GUIDED","LOITER","RTL",
			"CIRCLE","POSITION","LAND","OF_LOITER","DRIFT","RESERVED_12","SPORT","FLIP","AUTOTUNE","POS_HOLD","BREAK"};
	public static final String[] ROVER_MODES= {"MANUAL","RESERVED_1","LEARNING","STEERING","HOLD","RESERVED_5",
			"RESERVED_6","RESERVED_7","RESERVED_8","RESERVED_9","AUTO","RTL","RESERVED_12","RESERVED_13","RESERVED_14",
			"GUIDED","INITIALIZING"};
	public static final String[] PLANE_MODES={"MANUAL","CIRCLE","STABILIZE","TRAINING","ACRO","FLY_BY_WIRE_A",
			"FLY_BY_WIRE_B","CRUIZE","AUTOTUNE","RESERVED_9"};
	
	public void initialize();
	
	public GeoPosition getLocation();
	public void setHomeLocation(GPosition p);
	
	public void addConnection(Connection c);
	public void setLatitude(double latitude);
	public void setLongtitude();
	public void set_velocity(double x_velocity, double y_velocity, double z_velocity, int duration);
	public void set_mode(String mode);
	public void set_armed(boolean armed);
	public void takeoff(int height);
	public double get_battery_level();
	
	public boolean is_armable();
	
	public void addVehicleStateListener(VehicleStateListener c);
	public List<VehicleStateListener> getVehicleStateListeners();
	public void setLongtitude(float longtitude);
	public void handleMAVLink_Message(MAVLinkMessage m);
	
	public void get_position();
	
	public List<Connection> listConnections();
	public void closeConnection(Connection c);
	
	public void handleHeartbeat(msg_heartbeat m);

	public boolean isArmed();
	public boolean isConnected();

	public int getVehicleType();

	public String getVehicleMode();
	public void request_parameters();
	
	public void request_datastream(int streamNumber, int rate_hz);
	public void stop_datastream(int streamID);

	public void disableRCOverride();

	public void sendRCOverride(int pitch, int roll, int yaw, int throttle);
	
	//public void calibratePressure();
	//public void calibrateLevel();
	
	//public void rebootAutopilot(boolean holdInBootloader);
	
	
	
	
	
}
