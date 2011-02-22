

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.GyroSensor;

public class GyroHandler {
	
	private GyroSensor gyroSensor;
	private double offset;
	private double angle;
	private double speed;
	
	/**
	 * Initializes and calibrates the GyroSensor
	 * 
	 * @param port		The physical port for the GyroSensor on the robot
	 */
	public GyroHandler(SensorPort port) {
		gyroSensor = new GyroSensor(port);
		this.offset = 0;
		this.angle = 0;
		this.speed = 0;
		
		calibrate();
	}
	
	/**
	 * Reads the falling speed, updates the offset and calculates the current angle
	 * 
	 * @param timeInterval		how much time has passed since the last reading
	 */
	public void readValues(double timeInterval){
		
		double gyroCurrentValue = gyroSensor.readValue();
		
		offset = offset*0.9995 + gyroCurrentValue*0.0005;
		speed = gyroCurrentValue - offset;
		//angle = (angle + timeInterval*speed)*0.99 - (angle*0.01);
		angle = (angle + timeInterval*speed)*0.995;
		//System.out.println("interval:"+timeInterval);
		//System.out.println("Angle:"+angle);
		//System.out.println("Speed:"+speed);
	}
	
	/**
	 * Calibrates the offset value of the GyroSensor. Every GyroSensor
	 * has errors that might result in wrong readings, so the amount of
	 * correction needed has to be measured. 
	 */
	public void calibrate(){
		
		Sound.twoBeeps(); //warn that the calibration is starting
		
		gyroSensor.setOffset(0);
		
		LCD.clearDisplay();
		System.out.println("Calibration...");
		
		double cumulator = 0;
		double loops = 200.0; //double (not int) because of the division
		
		for(int i = 0 ; i < loops ; i++) {
			cumulator+=gyroSensor.readValue();
			
			try{
				Thread.sleep(10);
			}catch(Exception e){
				
			}
			
		}
		
		double average = cumulator / loops;
		
		LCD.clearDisplay();
		System.out.println("Calibrated at: " + average);
		
		this.offset = average;
		
		Sound.twoBeeps(); //warn that the calibration ended
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}

}
