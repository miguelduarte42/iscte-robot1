import lejos.nxt.Motor;

public class MotorHandler {
	
	private double rotateSum = 0;
	private double motorPosition = 0;
	private double motorSpeed = 0;
	private double mrcDeltaP1 = 0;
	private double mrcDeltaP2 = 0;
	private double mrcDeltaP3 = 0;
	private double motorDiff = 0;
	private double motorDiffTarget = 0;
	
	private double powerLeft = 0;
	private double powerRight = 0;
	
	private Motor m1;
	private Motor m2;
	
	private static double KGYROSPEED = 1.15; //falling speed has a lower influence than the angle Default: 1.15
	private static double KGYROANGLE = 7.5; //the greater the angle, the more speed you get Default: 7.5
	private static double KPOS = 0.07;
	private static double KSTEER = 0;
	private static double KSPEED = 0.1; //decrease the wheel speed
	private static double KDRIVE = -0.02;
	private static double KWHEEL = 0.8; //the value for our wheels
	
	public MotorHandler(Motor m1, Motor m2) {
		this.m1 = m1;
		this.m2 = m2;
	}
	
	/**
	 * Reads various values from the wheels and updates the MotorHandler object
	 * 
	 * @param timeInterval		the amount of time in seconds since the last reading
	 */
	public void readValues(double timeInterval) {
		
		double rotateLeft = m1.getRotationSpeed();
		double rotateRight = m2.getRotationSpeed();
		
		double rotateSumPrev = rotateSum;
		this.rotateSum = rotateLeft + rotateRight;
		motorDiff = rotateLeft - rotateRight;
		
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
	public void updateWheelPower(double gyroSpeed, double gyroAngle, double timeInterval) {
		
		if(Math.abs(gyroSpeed) < 1)
			gyroSpeed = 0;
		
		double power = (KGYROSPEED * gyroSpeed + KGYROANGLE * gyroAngle) /KWHEEL +
	             		KPOS * motorPosition +
	             		KDRIVE * 0 + //we don't want to drive... for now
	             		KSPEED * motorSpeed;
		
		SteerControl(power,timeInterval);
		//System.out.println(gyroAngle);
		//System.out.println("Power:"+powerLeft);
		
		m1.setPower(Math.abs((int)powerLeft));
		m2.setPower(Math.abs((int)powerRight));		
		
		if(powerLeft < 0){
			m1.forward();
			m2.forward();
		}else if(powerLeft > 0){
			m1.backward();
			m2.backward();
		}
	}
	
	private void SteerControl(double power, double timeInterval) {
	  double powerSteer;

	  // Update the target motor difference based on the user steering
	  // control value.
	  double motorControlSteer = 0;
	  motorDiffTarget += motorControlSteer * timeInterval;

	  // Determine the proportionate power differential to be used based
	  // on the difference between the target motor difference and the
	  // actual motor difference.
	  powerSteer = KSTEER * (motorDiffTarget - motorDiff);

	  // Apply the power steering value with the main power value to
	  // get the left and right power values.
	  powerLeft = power + powerSteer;
	  powerRight = power - powerSteer;

	  // Limit the power to motor power range -100 to 100
	  if (powerLeft > 100)   powerLeft = 100;
	  if (powerLeft < -100)  powerLeft = -100;

	  // Limit the power to motor power range -100 to 100
	  if (powerRight > 100)  powerRight = 100;
	  if (powerRight < -100) powerRight = -100;
	}

}
