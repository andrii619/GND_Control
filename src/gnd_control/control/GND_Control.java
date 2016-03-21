package gnd_control.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_rc_channels_override;
import com.MAVLink.enums.MAVLINK_DATA_STREAM_TYPE;
import com.MAVLink.enums.MAV_DATA_STREAM;

import gnd_control.model.Backend;
import gnd_control.model.Connection;
import gnd_control.model.GND_Backend;
import gnd_control.model.GND_Profile;
import gnd_control.model.MyConnectException;
import gnd_control.model.Profile;
import gnd_control.model.Vehicle;
import gnd_control.model.VehicleStateListener;

public class GND_Control implements Control, VehicleStateListener  {
	Backend backend;
	Profile currentProfile;//= new GND_Profile("Sample");
	Vehicle currentVehicle;
	//List<VehicleStateListener> stateListeners;
	
	public GND_Control()
	{
		currentProfile = new GND_Profile("Sample");
		backend = new GND_Backend();
		currentProfile=backend.readProfile("Sample");//selectProfile("Sample");
		if(currentProfile==null)
		{
			currentProfile = new GND_Profile("Sample");
			backend.writeProfile(currentProfile);
		}
			
		currentVehicle=currentProfile.getVehicle();
		//stateListeners=new ArrayList<VehicleStateListener>();
	}
	
	
	public List<Connection> getConnections()
	{
		return null;
	}
	public List<String> listProfiles() throws ClassNotFoundException, IOException
	{
		List<String> profiles = new ArrayList<String>();
		for(Profile p : backend.getProfiles())
		{
			profiles.add(p.getName());
		}
		
		return profiles;
	}
	
	public boolean addProfile(String name, String description)
	{
		if(!profileExists(name))
		{
			GND_Profile profile = new GND_Profile(name, description);
			backend.writeProfile(profile);
			return true;
		}
		else
		return false;
	}
	
	public boolean deleteProfile(String name)
	{
		if(profileExists(name))
		{
			backend.deleteProfile(name);
			return true;
		}
		else 
			return false;
	}
	private boolean profileExists(String name)
	{
		return false;
	}
	public Profile selectProfile(String Name)
	{
		if(currentProfile != null)
			if(this.currentProfile.getName().compareTo(Name)!=0)
				backend.writeProfile(this.currentProfile);
		this.currentProfile = backend.readProfile(Name);
		return currentProfile;
	}


	@Override
	public boolean isVehicleConnected() {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return false;
		else
			return this.currentVehicle.isConnected();
	}


	@Override
	public boolean isVehicleArmed() {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return false;
		else
			return this.currentVehicle.isArmed();
	}


	@Override
	public void disarmVehicle() {
		// TODO Auto-generated method stub
		if(this.currentVehicle!=null)
		{
			this.currentVehicle.set_armed(false);
		}
	}


	@Override
	public void armVehicle() {
		// TODO Auto-generated method stub
		if(this.currentVehicle!=null)
		{
			this.currentVehicle.set_armed(true);
		}
	}


	@Override
	public Vehicle getCurrentVehicle() {
		// TODO Auto-generated method stub
		return this.currentVehicle;
	}
	
	public List<String> getConnectionList()
	{
		if(this.currentVehicle==null)
			return null;
		List<String> connections = new ArrayList<String>();
		for(Connection c: currentVehicle.listConnections())
		{
			connections.add(c.getConnectionName());
		}
		return connections;
		
	}
	public boolean addConnection(Connection c)
	{
		if(c==null)
			return false;
		List<Connection> connections=currentVehicle.listConnections();
		if(connections.contains(c))
			return false;
		else
		{
			currentVehicle.addConnection(c);
			backend.writeProfile(currentProfile);
			return true;
		}
	}


	@Override
	public void deleteConnection(String currentConnection) {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return;
		if(currentConnection == null)
			return;
		if(currentConnection.isEmpty())
			return;
		List<Connection> temp = this.currentVehicle.listConnections();
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getConnectionName().compareTo(currentConnection)==0)
			{
				this.currentVehicle.closeConnection(temp.get(i));
				temp.remove(i);
				return;
			}
		}
		backend.writeProfile(currentProfile);
	}
	public void saveCurrentProfile()
	{
		if(this.currentProfile!=null)
		{
			this.backend.writeProfile(currentProfile);
		}
	}
	
	public void closeCurrentConnections()
	{
		if(this.currentProfile==null)
			return;
		if(this.currentVehicle==null)
			return;
		List<Connection> temp = this.currentVehicle.listConnections();
		if(temp == null)
			return;
		for(int i = 0; i < temp.size(); i++)
		{
			temp.get(i).disconnect();
		}
	}


	@Override
	public void closeConnection(String currentConnection) {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return;
		List<Connection> c =this.currentVehicle.listConnections();
		if(c==null)
			return;
		for(int i=0;i<c.size();i++)
		{
			if(c.get(i)==null)
				continue;
			if(c.get(i).getConnectionName().compareTo(currentConnection)==0)
			{
				c.get(i).disconnect();
				break;
			}
		}
	}


	@Override
	public void connectConnetion(String currentConnection) {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return;
		List<Connection> c = this.currentVehicle.listConnections();
		if(c==null)
			return;
		for(int i=0;i<c.size();i++)
		{
			if(c.get(i)==null)
				continue;
			if(c.get(i).getConnectionName().compareTo(currentConnection)==0)
			{
				try{
				c.get(i).connect();
				} catch(MyConnectException e)
				{
					
				}
				break;
			}
		}
	}


	@Override
	public void addVehicleListener(VehicleStateListener l) {
		// TODO Auto-generated method stub
		if(this.currentVehicle!=null)
		{
			if(l!=null)
				this.currentVehicle.addVehicleStateListener(l);
		}
	}


	@Override
	public Float getVehicleBatteryLevel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Float getVehicleGroundSpeed() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Float getVehicleAltitude() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getVehicleType() {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return -1;
		return this.currentVehicle.getVehicleType();
	}
	public String getVehicleMode()
	{
		String mode=null;
		if(this.currentVehicle==null)
			return null;
		
		return this.currentVehicle.getVehicleMode();
	}

	@Override
	public void changeMode(String selectedItem) {
		// TODO Auto-generated method stub
		if(selectedItem ==null)
			return;
		if(selectedItem.isEmpty())
			return;
		if(this.currentVehicle==null)
			return;
		this.currentVehicle.set_mode(selectedItem);
	}


	@Override
	public void armedChanged(boolean armed) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void flightModeChanged(String mode) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void locationChange() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void batteryLevelChange() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void connectedChanged(boolean connected) {
		// TODO Auto-generated method stub
		if(connected)
		{
			// start sending regular heartbeats
			
			// request parameters
			this.currentVehicle.request_parameters();
			
			// request data stream
			this.currentVehicle.request_datastream(MAV_DATA_STREAM.MAV_DATA_STREAM_ALL,1024);
		}
		else
		{
			
		}
	}


	@Override
	public void disableRCOverride() {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return;
		this.currentVehicle.disableRCOverride();
		
		
	}


	@Override
	public void manualOverride(int pitch, int roll, int yaw, int throttle) {
		// TODO Auto-generated method stub
		if(this.currentVehicle==null)
			return;
		if(!this.currentVehicle.isConnected())
			return;
		this.currentVehicle.sendRCOverride(pitch,roll,yaw,throttle);
	}
	
}
