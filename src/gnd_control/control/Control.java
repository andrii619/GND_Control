package gnd_control.control;

public interface Control {

	boolean isVehicleConnected();

	boolean isVehicleArmed();

	void disarmVehicle();

	void armVehicle();

}
