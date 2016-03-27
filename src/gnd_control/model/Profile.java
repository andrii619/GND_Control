package gnd_control.model;

/**
 * <b>Profile</b> interface describes a user profile object.
 */
public interface Profile extends Comparable<Profile> {

	/**
	 * Returns the name of this profile.
	 * @return Name of the profile
	 */
	String getName();

	/**
	 * Returns the description of this profile.
	 * @return Description of the profile
	 */
	String getDescription();

	/**
	 * Returns the vehicle of this profile.
	 * @return The vehicle of the profile
	 */
	Vehicle getVehicle();
	
	/**
	 * Sets the icon to be used for this profile.
	 * @param file The path to the profile icon.
	 */
	void setFileName(String file);

}
