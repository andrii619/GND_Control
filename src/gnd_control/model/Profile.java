package gnd_control.model;

public interface Profile extends Comparable<Profile> {

	String getName();

	String getDescription();

	Vehicle getVehicle();

}
