package relink.model;

import java.io.IOException;

public class Naviator {
	
	private boolean active;
	
	public int vID;
	public boolean uw;
	
	private double altitude;
	private Position location;
	private Position homeLocation;
	private double gSpeed;
	private double aSpeed;	
	
	public Naviator() {
	}
	
	public void goUW(boolean u) {
		this.uw = u;
	}
	
	public double getAltitude(){ return altitude; }
	public void updateAltitude(double a) {
		this.altitude = a;
	}
	
	public Position getLocation(){ return location; }
	public void updateLocation(Position loc) {
		this.location = loc;
	}
	
	public Position getHome(){ return homeLocation; }
	public void setHome(Position home) {
		this.homeLocation = home;
	}
	
	public double getGroundSpeed(){ return gSpeed; }
	public void updateGroundSpeed(double s) {
		this.gSpeed = s;
	}
	
	public double getAirSpeed(){ return aSpeed; }
	public void updateAirSpeed(double s) {
		this.aSpeed = s;
	}
}
