package project;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.GyroSensor;

public class GyroHandler {
	
	private GyroSensor gyroSensor;
	private double offset;
	private double angle;
	private double speed;
	
	public GyroHandler(SensorPort port) {
		gyroSensor = new GyroSensor(port);
		this.offset = 0;
		this.angle = 0;
		this.speed = 0;
		
		calibrate();
	}
	
	public void readValues(double timeInterval){
		
		double gyroCurrentValue = gyroSensor.readValue();
		
		offset = offset*0.9995 + gyroCurrentValue*0.0005;
		speed = gyroCurrentValue - offset;
		angle = angle + timeInterval*speed;
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
		double loops = 500.0; //double (not int) because of the division
		
		for(int i = 0 ; i < loops ; i++)
			cumulator+=gyroSensor.readValue();
		
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
