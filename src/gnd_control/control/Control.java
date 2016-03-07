package gnd_control.control;

import gnd_control.model.Vehicle;

public interface Control {

	boolean isVehicleConnected();

	boolean isVehicleArmed();

	void disarmVehicle();

	void armVehicle();

	Vehicle getCurrentVehicle();

}
