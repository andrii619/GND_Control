package gnd_control.model;

import java.io.Serializable;

/**
 * <b>GPosition</b> class encodes the geographical position of the vehicle.
 *
 */
public class GPosition implements Serializable {
private double latitude;
private double longtitude;
private double altitude;
private static final long serialVersionUID = 1L;

/**
 * GPosition constructor.
 * @param latitude The latitude of the position object.
 * @param longtitude Longtitude of the position object.
 */
public	GPosition(double latitude, double longtitude, double altitude)
{
	this.latitude=latitude;
	this.longtitude=longtitude;
	this.altitude=altitude;
}

/**
 * Sets the latitude of the position object.
 * @param latitude the latitude to be set.
 */
public void setLatitude(double latitude)
{
	this.latitude=latitude;
}

/**
 * Sets the longitude of the position object.
 * @param longtitude
 */
public void setLongtitude(double longtitude)
{
	this.longtitude = longtitude;
}
/**
 * Get the latitude of the position object.
 * @return latitude
 */
public double getLatitude()
{
	return this.latitude;
}
/**
 * Get the longitude of the position object.
 * @return longitude
 */
public double getLongtitude()
{
	return this.longtitude;
}

}
