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
	private long lastTime;
	
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
		this.lastTime =  System.currentTimeMillis();
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
		timeInterval = 	(System.currentTimeMillis()-this.lastTime) / 1000.0;
		this.lastTime =  System.currentTimeMillis();

		double gyroRead = 0;
		for (int i = 1; i <= 10; i++){
			double gyroCurrentValue = gyroSensor.readValue();
			gyroRead += gyroCurrentValue;
		}
		gyroRead /= 10.0;
		speed = gyroRead - offset;
		offset = offset * 0.9995 + gyroRead * 0.0005;
		angle = (angle + timeInterval*speed)*0.9995; // + angle*0.01;

		LCD.drawString("Gyro  : " + gyroRead + "  ", 0, 3);
		LCD.drawString("Offset: " + offset + "  ", 0, 4);
		LCD.drawString("Speed : " + speed + "  ", 0, 5);
		LCD.drawString("Angle : " + angle + "  ", 0, 6);
		LCD.drawString("dTime : " + timeInterval * 1000 + "ms", 0, 7);

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
		double loops = 500.0; //double (not int) because of the division
		
		for(int i = 1 ; i <= loops ; i++) {
			
			int gyroCurrentValue = gyroSensor.readValue();
			cumulator+=gyroCurrentValue;
			LCD.drawString("ReadValue: " + gyroCurrentValue, 0, 3);
			LCD.drawString("Cumulator: " + cumulator, 0, 4);
			LCD.drawString("Average: " + (cumulator / i), 0, 5);
			Thread.sleep(10);
		}
		this.angle = 0;
		this.offset = cumulator / loops;
		
		LCD.drawString("OFFSET: " + offset,0,0);
		
		//warn that the calibration ended
		Sound.twoBeeps();
		this.lastTime =  System.currentTimeMillis();
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}

}
