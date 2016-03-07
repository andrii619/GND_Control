package gnd_control.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.MAVLink.Messages.MAVLinkMessage;

import gnd_control.model.Backend;
import gnd_control.model.Connection;
import gnd_control.model.GND_Backend;
import gnd_control.model.GND_Profile;
import gnd_control.model.Profile;
import gnd_control.model.Vehicle;

public class GND_Control implements Control, Vehicle_Observer {
	Backend backend;
	Profile currentProfile = new GND_Profile("Sample");
	Vehicle currentVehicle;
	
	public GND_Control()
	{
		backend = new GND_Backend();
		currentVehicle=currentProfile.getVehicle();
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
	public void selectProfile(String Name)
	{
		if(currentProfile != null)
			backend.writeProfile(this.currentProfile);
		this.currentProfile = backend.readProfile(Name);
	}

	@Override
	public void handle_status_msg(MAVLinkMessage m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isVehicleConnected() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isVehicleArmed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void disarmVehicle() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void armVehicle() {
		// TODO Auto-generated method stub
		
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
			return true;
		}
	}
	
	
	
}
