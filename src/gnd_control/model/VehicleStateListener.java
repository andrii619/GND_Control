package gnd_control.model;

public interface VehicleStateListener {
	public void armedChanged(boolean armed);
	
	public void flightModeChanged(String mode);
	
	public void locationChange();
	
	public void batteryLevelChange();
	
	public void connectedChanged(boolean connected);

}
