package gnd_control.model;

public class GPosition {
private double latitude;
private double longtitude;

public	GPosition(double latitude, double longtitude)
{
	this.latitude=latitude;
	this.longtitude=longtitude;
}
public void setLatitude(double latitude)
{
	this.latitude=latitude;
}

public void setLongtitude(double longtitude)
{
	this.longtitude = longtitude;
}


}
