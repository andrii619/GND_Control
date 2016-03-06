package gnd_control.guiview.profile;
import javax.swing.ImageIcon;

import gnd_control.model.GND_Profile;
import gnd_control.model.Profile;


public class Profile_Item {
	//String Title;
	//String Description;
	Profile profile; // a profile data structure
	ImageIcon icon;
	public Profile_Item(String Title){
		profile = new GND_Profile(Title);
		//this.Title = Title;
	}
	public Profile_Item(String Title, String Description){
		profile = new GND_Profile(Title,Description);
		//this.Title = Title;
		//this.Description = Description;
	}
	public Profile_Item(String Title, String Description, ImageIcon icon){
		//this.Title = Title;
		//this.Description = Description;
		profile = new GND_Profile(Title,Description);
		this.icon = icon;
	}
	
	public String getTitle()
	{
		return profile.getName();
	}
	public String getDescription()
	{
		return profile.getDescription();
	}
	
}
