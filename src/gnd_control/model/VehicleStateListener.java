package gnd_control.model;

public interface VehicleStateListener {
	public void arm_disarm();
	
	public void locationChange();
	
	public void batteryLevelChange();
	
	public void connected();

}
