package project;

import lejos.nxt.Motor;

public class MotorHandler {
	
	private double rotateSum = 0;
	private double motorPosition = 0;
	private double motorSpeed = 0;
	private double mrcDeltaP1 = 0;
	private double mrcDeltaP2 = 0;
	private double mrcDeltaP3 = 0;
	
	private Motor m1;
	private Motor m2;
	
	private static double KGYROSPEED = 1.15; //falling speed has a lower influence than the angle
	private static double KGYROANGLE = 7.5; //the greater the angle, the more speed you get
	private static double KPOS = 0.07;
	private static double KSPEED = 0.1; //decrease the wheel speed
	private static double KDRIVE = -0.02;
	private static double KWHEEL = 0.8; //the value for our wheels
	
	public MotorHandler(Motor m1, Motor m2) {
		this.m1 = m1;
		this.m2 = m2;
		m1.resetTachoCount();
		m2.resetTachoCount();
	}
	
	/**
	 * Reads various values from the wheels and updates the MotorHandler object
	 * 
	 * @param timeInterval		the amount of time in seconds since the last reading
	 */
	public void readValues(double timeInterval) {
		
		double rotateLeft = m1.getTachoCount();
		double rotateRight = m2.getTachoCount();
		
		double rotateSumPrev = rotateSum;
		this.rotateSum = rotateLeft + rotateRight;
		//double motorDiff = mrcLeft - mrcRight;
		
		double rotateDelta = rotateSum - rotateSumPrev;
		this.motorPosition += rotateDelta;
		
		this.motorSpeed = (rotateDelta+mrcDeltaP1+mrcDeltaP2+mrcDeltaP3)/(4*timeInterval);
		
		this.mrcDeltaP3 = mrcDeltaP2;
		this.mrcDeltaP2 = mrcDeltaP1;
		this.mrcDeltaP1 = rotateDelta;
		
	}
	/**
	 * The final wheel power is based on various readings:
	 * 	-falling speed
	 * 	-robot angle
	 * 	-drive direction
	 * 	-motor speed
	 * 
	 * @param gyroSpeed		the current falling speed measured by the GyroSensor
	 * @param gyroAngle		the current angle at which the robot is facing
	 */
	public void updateWheelPower(double gyroSpeed, double gyroAngle) {
		
		double power = (KGYROSPEED * gyroSpeed + KGYROANGLE * gyroAngle) /KWHEEL +
	             		KPOS * motorPosition +
	             		KDRIVE * 0 + //we don't want to drive... for now
	             		KSPEED * motorSpeed; 
		
		m1.setPower((int)power);
		m2.setPower((int)power);		
		
		if(gyroSpeed < 0){
			m1.forward();
			m2.forward();
		}else if(gyroSpeed > 0){
			m1.backward();
			m2.backward();
		}else {
			m1.stop();
			m2.stop();
		}
	}

}
