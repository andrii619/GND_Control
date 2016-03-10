package gnd_control.control;

import java.util.List;

import gnd_control.model.Connection;
import gnd_control.model.Profile;
import gnd_control.model.Vehicle;
import gnd_control.model.VehicleStateListener;

public interface Control {

	boolean isVehicleConnected();

	boolean isVehicleArmed();

	void disarmVehicle();

	void armVehicle();

	Vehicle getCurrentVehicle();
	
	List<String> getConnectionList();

	boolean addConnection(Connection c);

	void deleteConnection(String currentConnection);
	
	void saveCurrentProfile();
	void closeCurrentConnections();
	
	Profile selectProfile(String name);

	void closeConnection(String currentConnection);

	void connectConnetion(String currentConnection);

	void addVehicleListener(VehicleStateListener l);

	Float getVehicleBatteryLevel();

	Float getVehicleGroundSpeed();

	Float getVehicleAltitude();

}
