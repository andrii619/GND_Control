package gnd_control.model;

import java.io.IOException;
import java.util.List;

public interface Backend {

	List<Profile> getProfiles() throws IOException, ClassNotFoundException;

	void writeProfile(Profile currentProfile);

	void deleteProfile(String name);

	Profile readProfile(String name);

}
