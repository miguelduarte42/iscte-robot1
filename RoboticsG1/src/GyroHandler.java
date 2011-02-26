import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.GyroSensor;

/**
 * The GyroHandler class is responsible for everything related to the
 * Gyroscope Sensor. Besides reading the values from the sensor itself,
 * it 
 */
public class GyroHandler {
	
	private GyroSensor gyroSensor;
	private double offset;
	private double angle;
	private double speed;
	
	/**
	 * Initializes and calibrates the GyroSensor
	 * 
	 * @param port				The physical port for the GyroSensor on the robot
	 * @param autoCalibration	Indicates whether to automatically calibrate the sensor
	 */
	public GyroHandler(SensorPort port, boolean autoCalibration) throws Exception{
		gyroSensor = new GyroSensor(port);
		this.offset = 0;
		this.angle = 0;
		this.speed = 0;
		
		if(autoCalibration)
			calibrate();
		
	}
	
	/**
	 * Reads the falling speed, updates the offset and calculates the current angle.
	 * The angle's calculation is prone to errors since it's derived from integral of 
	 * the gyroscope's speed readings. This means that there will be an increasing error
	 * over time. To compensate, the angle is slightly modified to approach 0 degrees,
	 * which is the desired value when the robot is upright.
	 * 
	 * @param timeInterval		how much time has passed since the last reading
	 */
	public void readValues(double timeInterval){
		
		double gyroCurrentValue = gyroSensor.readValue();
		
		offset = offset*0.9995 + gyroCurrentValue*0.0005;
		speed = gyroCurrentValue - offset;
		angle = (angle + timeInterval*speed)*0.99 + angle*0.01;

	}
	/**
	 * Calibrates the offset value of the Gyroscope. Every Gyroscope Sensor
	 * has errors that might result in wrong readings, so the amount of
	 * correction needed has to be measured. The robot must be completely
	 * still during the calibration process.
	 */
	public void calibrate() throws Exception{
		
		//warn that the calibration is starting
		Sound.twoBeeps();
		
		gyroSensor.setOffset(0);
		
		LCD.clearDisplay();
		LCD.drawString("Calibration...",0,0);
		
		double cumulator = 0;
		double loops = 200.0; //double (not int) because of the division
		
		for(int i = 0 ; i < loops ; i++) {
			
			cumulator+=gyroSensor.readValue();
			
			Thread.sleep(10);
		}
		
		this.offset = cumulator / loops;
		
		LCD.drawString("Calibrated at: " + offset,0,1);
		
		//warn that the calibration ended
		Sound.twoBeeps();
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}

}
