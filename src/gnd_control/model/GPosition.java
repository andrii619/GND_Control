package gnd_control.model;

import java.io.Serializable;

public class GPosition implements Serializable {
private double latitude;
private double longtitude;
private static final long serialVersionUID = 1L;

public	GPosition(float latitude, float longtitude)
{
	this.latitude=latitude;
	this.longtitude=longtitude;
}
public void setLatitude(float latitude)
{
	this.latitude=latitude;
}

public void setLongtitude(float longtitude)
{
	this.longtitude = longtitude;
}


}
