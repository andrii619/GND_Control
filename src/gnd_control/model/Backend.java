package gnd_control.model;

import java.io.IOException;
import java.util.List;

/**
 * <b>Backend</b> interface defines a backend object that is responsible for saving user profiles on disk.
 *
 */
public interface Backend {

	/** 
	 * Get a list of profiles stored on disk.
	 * @return List of current profiles
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	List<Profile> getProfiles() throws IOException, ClassNotFoundException;

	/**
	 * Save a profile on disk
	 * @param currentProfile Profile to be saved.
	 */
	void writeProfile(Profile currentProfile);

	/**
	 * Remove a profile from disk.
	 * @param name Profile to be removed
	 */
	void deleteProfile(String name);

	/**
	 * Read a profile from disk into memory.
	 * @param name Name of the profile to be read.
	 * @return The profile object in memory.
	 */
	Profile readProfile(String name);

}
