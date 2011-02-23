
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class SegwayRobot {
	
	private static Motor MOTOR_1 = Motor.A;
	private static Motor MOTOR_2 = Motor.C;
	private static SensorPort GYRO_SENSOR = SensorPort.S3;
	private static double TIME_INTERVAL_DEFAULT = 0.01;
	private static double WAIT_TIME = 0.01;
	
	private GyroHandler gyroHandler;
	private MotorHandler motorHandler;
	private double timeInterval;
	private double timeStart;
	
	
	public SegwayRobot() {
		
		 this.gyroHandler = new GyroHandler(GYRO_SENSOR); //loads and calibrates the GyroSensor
		 this.motorHandler = new MotorHandler(MOTOR_1,MOTOR_2);
		 
		 this.timeInterval = TIME_INTERVAL_DEFAULT;
		 this.timeStart = System.currentTimeMillis();
		 
		 this.execute();
	}
	
	/**
	 * Main controller loop. Reads the GyroSensor, calculates
	 * the wheel speed and prevents the robot from falling.
	 */
	public void execute() {
		
		long loopCount = 0;
		
		boolean standing = true;
		
		while(standing){
			
			LCD.clear();
			
			CalculateInterval(loopCount++);
			
			gyroHandler.readValues(timeInterval);
			motorHandler.readValues(timeInterval);
			
			motorHandler.updateWheelPower(gyroHandler.getSpeed(),gyroHandler.getAngle(), timeInterval);
			
			standing = Math.abs(gyroHandler.getAngle()) > 50;
			
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
		  this.timeInterval = (System.currentTimeMillis() - timeStart)/(1000.0 *loopCount);
	  
	}
	
	public static void main(String[] args) {
		
		SegwayRobot segway = new SegwayRobot();
		segway.execute();
		
	}

}
