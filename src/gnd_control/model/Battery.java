package gnd_control.model;

public class Battery {
	private double current;
	private double voltage;
	private double level;
	
	public Battery(double current, double voltage, double level)
	{
		this.current = current;
		this.voltage = voltage;
		this.level = level;
	}

}
