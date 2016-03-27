package gnd_control.control;

import java.util.List;

import gnd_control.model.Connection;
import gnd_control.model.Profile;
import gnd_control.model.Vehicle;
import gnd_control.model.VehicleStateListener;

/**
 * <b>Control</b> interface defines all methods for implementing a controller. Handles all application logic.
 *
 */
public interface Control {

	/**
	 * Checks if there is a connection to the vehicle
	 * @return True if the vehicle is connected.
	 */
	boolean isVehicleConnected();

	/**
	 * Checks if the vehicle is armed
	 * @return True if the vehicle is armed.
	 */
	boolean isVehicleArmed();

	/**
	 * Disarms vehicle motors
	 */
	void disarmVehicle();

	/**
	 * Arms vehicle motors
	 */
	void armVehicle();

	/**
	 * Return the instance of the current vehicle
	 * @return Current vehicle
	 */
	Vehicle getCurrentVehicle();
	
	/**
	 * List all connections to the vehicle
	 * @return List of vehicle connections
	 */
	List<String> getConnectionList();

	/**
	 * Add another connection to the vehicle
	 * @param c Connection to be added
	 * @return True if the new Connection was added
	 */
	boolean addConnection(Connection c);

	/**
	 * Delete a connection to the vehicle
	 * @param currentConnection Connection to be removed.
	 */
	void deleteConnection(String currentConnection);
	
	/**
	 * Saves the current profile on disk
	 */
	void saveCurrentProfile();
	/**
	 * Closes all opened connections.
	 */
	void closeCurrentConnections();
	
	/**
	 * Changes the current profile to another profile
	 * @param name Profile to be selected
	 * @return The profile that was selected
	 */
	Profile selectProfile(String name);

	/**
	 * Close the specified connection.
	 * @param currentConnection Connection to be closed
	 */
	void closeConnection(String currentConnection);

	/**
	 * Try to establish a connection
	 * @param currentConnection Connection to be opened
	 */
	void connectConnetion(String currentConnection);

	/**
	 * Add a Vehicle state listener
	 * @param l VehicleStateListener to be added
	 */
	void addVehicleListener(VehicleStateListener l);
	
	/**
	 * Get vehicle battery level
	 * @return battery level
	 */
	Float getVehicleBatteryLevel();

	/**
	 * Get vehicle speed relative to ground
	 * @return ground speed
	 */
	Float getVehicleGroundSpeed();

	/**
	 * Get vehicle altitude
	 * @return altitude
	 */
	Float getVehicleAltitude();

	/**
	 * Get vehicle type
	 * @return type of the current vehicle
	 */
	int getVehicleType();

	/**
	 * Change the mode of the current vehicle
	 * @param selectedItem new mode
	 */
	void changeMode(String selectedItem);

	/**
	 * Get the mode of the current vehicle
	 * @return vehicle mode
	 */
	String getVehicleMode();

	/**
	 * Disable RC override
	 */
	void disableRCOverride();

	/**
	 * Override RC PPM values
	 * @param pitch set pitch
	 * @param roll set roll
	 * @param yaw set yaw
	 * @param throttle set throttle
	 */
	void manualOverride(int pitch, int roll, int yaw, int throttle);

}
