package gnd_control.model;

import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_heartbeat;

/** 
 * <i>Vehicle</i> interface describes the general vehicle object.
 */
public interface Vehicle {
	public static final int heartbeat_rate_msec = 1000;
	
	public static final String[] COPTER_MODES ={"STABILIZE","ACRO","ALT_HOLD","AUTO","GUIDED","LOITER","RTL",
			"CIRCLE","POSITION","LAND","OF_LOITER","DRIFT","RESERVED_12","SPORT","FLIP","AUTOTUNE","POS_HOLD","BREAK"};
	public static final String[] ROVER_MODES= {"MANUAL","RESERVED_1","LEARNING","STEERING","HOLD","RESERVED_5",
			"RESERVED_6","RESERVED_7","RESERVED_8","RESERVED_9","AUTO","RTL","RESERVED_12","RESERVED_13","RESERVED_14",
			"GUIDED","INITIALIZING"};
	public static final String[] PLANE_MODES={"MANUAL","CIRCLE","STABILIZE","TRAINING","ACRO","FLY_BY_WIRE_A",
			"FLY_BY_WIRE_B","CRUIZE","AUTOTUNE","RESERVED_9"};
	
	/**
	 * Is used to initialize the vehicle when it is first connected.
	 */
	public void initialize();
	
	/**
	 * Returns the geographical position of the drone.
	 * @return The geographical position of the drone or null if the drones position could not be determined.
	 */
	public GPosition getLocation();
	
	/**
	 * Sets the home location of the drone.
	 * @param p The home location.
	 */
	public void setHomeLocation(GPosition p);
	
	/**
	 * Adds another connection that is used to communicate with the drone.
	 * @param c The new connection to be added.
	 */
	public void addConnection(Connection c);
	
	/**
	 * Set the latitude of the vehicle.
	 * @param latitude New Latitude.
	 */
	public void setLatitude(double latitude);
	
	/**
	 * Set the longitude of the vehicle.
	 */
	public void setLongtitude(double longtitude);
	
	/**
	 * Set the velocity of the vehicle.
	 * @param x_velocity the x component of the velocity.
	 * @param y_velocity the y component of the velocity.
	 * @param z_velocity the z component of the velocity.
	 * @param duration the duration of the new velocity.
	 */
	public void set_velocity(double x_velocity, double y_velocity, double z_velocity, int duration);
	
	/**
	 * Change the vehicle mode the the specified mode.
	 * @param mode The new mode of the vehicle.
	 */
	public void set_mode(String mode);
	
	/**
	 * Sets the vehicle motors to armed or disarmed state.
	 * @param armed True if vehicle motors are armed.
	 */
	public void set_armed(boolean armed);
	
	/**
	 * Tells the vehicle to takeoff to specified altitude.
	 * @param height The altitude to takeoff to.
	 */
	public void takeoff(int height);
	
	/**
	 * Get the battery level of the vehicle.
	 * @return The battery level of the vehicle or 0.0 if the vehicle is not connected.
	 */
	public double get_battery_level();
	
	/**
	 * Tells if the vehicle can be armed.
	 * @return True if the vehicle can be armed.
	 */
	public boolean is_armable();
	
	/**
	 * Add a new vehicle state listener to this vehicle. The state listener will be 
	 * notified in any changes of vehicles parameters. 
	 * @param c The VehicleStateListener to be added.
	 * @see VehicleStateListener
	 */
	public void addVehicleStateListener(VehicleStateListener c);
	
	/**
	 * Get the list of the state listeners that are registered with this vehicle.
	 * @return The list of the VehicleStateListener
	 * @see VehicleStateListener
	 */
	public List<VehicleStateListener> getVehicleStateListeners();
	
	
	//public void setLongtitude(float longtitude);
	/**
	 * Process a new MAVLink message that arrived on a connection.
	 * @param m The new message to be processed.
	 */
	public void handleMAVLink_Message(MAVLinkMessage m);
	
	/**
	 * Get the list of current vehicle connections.
	 * @return List of vehicle connections.
	 */
	public List<Connection> listConnections();
	
	/**
	 * Closes the specified connection to the vehicle.
	 * @param c The connection to be closed.
	 */
	public void closeConnection(Connection c);
	
	/**
	 * Process the heartbeat message arriving on a connection.
	 * @param m The heartbeat message to be processed.
	 */
	public void handleHeartbeat(msg_heartbeat m);

	/**
	 * Tells weather the vehicle is in the armed state.
	 * @return True if the vehicle is armed.
	 */
	public boolean isArmed();
	
	/**
	 * Tells weather the vehicle is connected. To be connected at least one connection to the vehicle has to be active.
	 * @return True if the vehicle is connected.
	 */
	public boolean isConnected();

	/**
	 * Returns the type of this vehicle.
	 * @return Vehicle type.
	 */
	public int getVehicleType();

	/**
	 * Get the current vehicle mode.
	 * @return Mode of the vehicle.
	 */
	public String getVehicleMode();
	
	/**
	 * Request the vehicle to send a list of parameters.
	 */
	public void request_parameters();
	
	/**
	 * Requests data to be streamed from the vehicle or a vehicle component.
	 * @param streamNumber The component to stream data. 
	 * @param rate_hz Rate of the data stream.
	 */
	public void request_datastream(int streamNumber, int rate_hz);
	
	/**
	 * Stops the streaming of data from the vehicle.
	 * @param streamID The stream to be stopped.
	 */
	public void stop_datastream(int streamID);

	/**
	 * Disables the RC override commands from ground station.
	 */
	public void disableRCOverride();

	/**
	 * Sends the RC override message to the vehicle
	 * @param pitch The pitch value to be sent
	 * @param roll the roll value to be sent
	 * @param yaw the yaw value to be sent 
	 * @param throttle the throttle value to be sent
	 */
	public void sendRCOverride(int pitch, int roll, int yaw, int throttle);
	
	//public void calibratePressure();
	//public void calibrateLevel();
	
	//public void rebootAutopilot(boolean holdInBootloader);
	
	
	
	
	
}
