package project;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class SegwayRobot {
	
	private static Motor MOTOR_1 = Motor.A;
	private static Motor MOTOR_2 = Motor.B;
	private static SensorPort GYRO_SENSOR = SensorPort.S3;
	private static double TIME_INTERVAL_DEFAULT = 0.01;
	private static double WAIT_TIME = 0.08;
	
	private GyroHandler gyroHandler;
	private MotorHandler motorHandler;
	private double timeInterval;
	private double timeStart;
	
	
	public SegwayRobot() {
		
		 gyroHandler = new GyroHandler(GYRO_SENSOR); //loads and calibrates the GyroSensor
		 motorHandler = new MotorHandler(MOTOR_1,MOTOR_2);
		 
		 this.timeInterval = TIME_INTERVAL_DEFAULT;
		 timeStart = System.currentTimeMillis();
		 
		 execute();
	}
	
	/**
	 * Main controller loop. Reads the GyroSensor, calculates
	 * the wheel speed and prevents the robot from falling.
	 */
	public void execute() {
		
		long loopCount = 0;
		
		while(true){
			
			CalculateInterval(loopCount++);
			LCD.clear();
			
			gyroHandler.readValues(timeInterval);
			motorHandler.readValues(timeInterval);
			
			motorHandler.updateWheelPower(gyroHandler.getSpeed(),gyroHandler.getAngle());
			
			try{
				Thread.sleep((int)(WAIT_TIME*1000));
			}catch(Exception e){
				
			}
		}
	}
	
	/**
	 * Finds the average time it takes for the main loop to calculate.
	 * This value is useful to know how much time has passed since the last readings.
	 * 
	 * @param loopCount		the current loop number on the main program
	 * 
	 */
	private void CalculateInterval(long loopCount) {
		
	  if (loopCount != 0)
		  this.timeInterval = (System.currentTimeMillis() - timeStart)/1000 *loopCount;
	  
	}
	
	public static void main(String[] args) {
		
		SegwayRobot segway = new SegwayRobot();
		segway.execute();
		
	}

}
