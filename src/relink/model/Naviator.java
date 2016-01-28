package relink.model;

import java.io.IOException;

public class Naviator {
	
	private boolean active;
	
	private int vID;
	
	private double altitude;
	private double[] location;
	private double[] homeLocation;
	private double gSpeed;
	private double aSpeed;	
	
	public Naviator() {
	}
	
	public void updateAltitude(double a) {
		this.altitude = a;
	}
	
	public void updateLocation(double[] loc) {
		if (loc.length != 2) throw new IllegalArgumentException("Coordinates must have two elements");
	}
	
	public void setHome(double[] home) {
		if (home.length != 2) throw new IllegalArgumentException("Coordinates must have two elements");
		this.homeLocation = home;
	}
	
	public void updateGroundSpeed(double s) {
		this.gSpeed = s;
	}
	
	public void updateAirSpeed(double s) {
		this.aSpeed = s;
	}
}
