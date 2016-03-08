package gnd_control.control;

import java.util.List;

import gnd_control.model.Connection;
import gnd_control.model.Vehicle;

public interface Control {

	boolean isVehicleConnected();

	boolean isVehicleArmed();

	void disarmVehicle();

	void armVehicle();

	Vehicle getCurrentVehicle();
	
	List<String> getConnectionList();

	boolean addConnection(Connection c);

	void deleteConnection(String currentConnection);

}
