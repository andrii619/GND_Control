package gnd_control.guiview.profile;
import javax.swing.ImageIcon;


public class Profile_Item {
	String Title;
	String Description;
	ImageIcon icon;
	public Profile_Item(String Title){
		this.Title = Title;
	}
	public Profile_Item(String Title, String Description){
		this.Title = Title;
		this.Description = Description;
	}
	public Profile_Item(String Title, String Description, ImageIcon icon){
		this.Title = Title;
		this.Description = Description;
		this.icon = icon;
	}
}
