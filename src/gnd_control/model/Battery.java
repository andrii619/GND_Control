package gnd_control.model;

import java.io.Serializable;

/**
 * <b>Battery</b> class stores information about the vehicles battery.
 *
 */
public class Battery implements Serializable {
	private static final long serialVersionUID = 1L;
	private double current;
	private double voltage;
	private double level;
	
	/**
	 * Battery object constructor
	 * @param current battery current
	 * @param voltage battery voltage
	 * @param level battery level
	 */
	public Battery(double current, double voltage, double level)
	{
		this.current = current;
		this.voltage = voltage;
		this.level = level;
	}

}
