package gnd_control.model;

import java.io.Serializable;

public class GND_Profile implements Profile, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private Vehicle vehicle;
	
	public GND_Profile(String Name)
	{
		this.Name = Name;
	}
	public GND_Profile(String Name, String description)
	{
		this.Name = Name;
	}
	public boolean createCopter()
	{
		vehicle = new Copter();
		return true;
	}
	
	public Vehicle get_Vehicle()
	{
		return vehicle;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
