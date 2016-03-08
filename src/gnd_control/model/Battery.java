package gnd_control.model;

import java.io.Serializable;

public class Battery implements Serializable {
	private static final long serialVersionUID = 1L;
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
