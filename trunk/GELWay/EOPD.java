import lejos.nxt.SensorPort;
import lejos.nxt.ColorLightSensor;
import lejos.robotics.Colors;
import lejos.robotics.Colors.Color;
import lejos.util.Delay;
/**
 * This is the a wrapper class to replace the EOPD sensor class
 * 
 * @author Nuno Cavalheira Antunes
 * @version March 2011
 */
public class EOPD {
	
	private ColorLightSensor cls;
	
	public EOPD(SensorPort s2) {
		this.cls = new ColorLightSensor(s2, ColorLightSensor.TYPE_COLORFULL);
	}

	public void setModeLong() {
		cls.setFloodlight(Color.WHITE);
		cls.calibrateHigh();
		Delay.msDelay(10);
		cls.setFloodlight(Color.BLACK);
		cls.calibrateLow();
		Delay.msDelay(10);
		cls.setFloodlight(Color.RED);
		cls.setFloodlight(Color.GREEN);
		cls.setFloodlight(Color.BLUE);
		cls.setFloodlight(Color.WHITE);
	}

	public int readRawValue() {
		// TODO Auto-generated method stub
		return cls.getLightValue(); //Normalized value between 0 and 100
	}


}
