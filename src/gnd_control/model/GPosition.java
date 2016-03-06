package gnd_control.model;

public class GPosition {
private double latitude;
private double longtitude;

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
