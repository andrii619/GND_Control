package gnd_control.model;

/**
 * <i>VehicleStateListener</i> interface defines an interface for all classes who wish to 
 * monitor the status of a vehicle.
 */
public interface VehicleStateListener {
	/**
	 * This method is used to notify the observer when the vehicle changes armed/disarmed status.
	 * @param <i>armed</i> The observer gets notified of the new vehicle armed status. 
	 */
	public void armedChanged(boolean armed);
	
	/**
	 * This method is used to notify the observer when the vehicle changes flight modes.
	 * @param <i>mode</i> The new mode of the vehicle.
	 */
	public void flightModeChanged(String mode);
	
	/**
	 * This method is used to notify the observer of the change in vehicle position.
	 * @param <i>position</i> New geographical position of the drone.
	 */
	public void locationChange(GPosition position);
	
	/**
	 * This method is used to notify the observer of the change in vehicle battery level.
	 * @param <i>level</i> The new battery level of the vehicle.
	 */
	public void batteryLevelChange(double level);
	
	/**
	 * This method is used to notify the observer when the vehicle gets connected/disconnected.
	 * @param <i>connected</i> New vehicle connection status.
	 */
	public void connectedChanged(boolean connected);

}
