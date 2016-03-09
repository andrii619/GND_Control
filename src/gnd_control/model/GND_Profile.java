package gnd_control.model;

import java.io.File;
import java.io.Serializable;


public class GND_Profile implements Profile, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private String Description;
	private Vehicle vehicle;
	private String fileName;
	private static final String defaultPic=new String("src"+File.pathSeparator+"gnd_control"+File.pathSeparator+"guiview"+File.pathSeparator+"copter.png");
	
	public GND_Profile(String Name)
	{
		this.Name = Name;
		vehicle = new Copter();
		fileName=new String(defaultPic);
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
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.Name;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.Description;
	}
	@Override
	public Vehicle getVehicle() {
		// TODO Auto-generated method stub
		return vehicle;
	}
	@Override
	public int compareTo(Profile o) {
		// TODO Auto-generated method stub
		return this.Name.compareTo(o.getName());
	}
	public boolean equals(Object o)
	{
		if (this == o) return true;
        if (!(o instanceof Profile)) return false;

        Profile temp = (Profile) o;
        if (!Name.equals(temp.getName())) return false;
        return true;
	}
	public void setFileName(String file)
	{
		if(file!=null)
			if(!file.isEmpty())
				this.fileName=file;
	}
}
