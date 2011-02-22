import lejos.nxt.LCD;
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
	
	private static double KGYROSPEED = 20; // Default: 1.15
	private static double KGYROANGLE = 20; //Default: 7.5
	private static double KSTEER = 0;
	private static double KSPEED = 0.01; //decrease the wheel speed 0.6 <->0.8
	private static double KDRIVE = -0.02;
	
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
		
		double rotateLeft = m1.getTachoCount();
		double rotateRight = m2.getTachoCount();
		
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
	 * @param timeInterval	the amount of time that has passed since the last update
	 */
	public void updateWheelPower(double gyroSpeed, double gyroAngle, double timeInterval) {
		
		if(Math.abs(gyroSpeed) < 1)//this might be deleted
			gyroSpeed = 0;
		
		double power = KGYROSPEED * gyroSpeed +
						KGYROANGLE * gyroAngle +
	             		KSPEED * ((powerLeft+powerRight)/2);
		
		//SteerControl(power,timeInterval);
		//System.out.println(gyroAngle);
		//System.out.println("Power:"+powerLeft);
		powerLeft=powerRight=power;
		
		m1.setSpeed(Math.abs((int)powerLeft));
		m2.setSpeed(Math.abs((int)powerRight));
		
		//System.out.println("pL "+powerLeft);
		
		LCD.drawString(("PW:"+powerLeft+"    ").substring(0,9), 0, 0);
		LCD.drawString(("GS:"+gyroSpeed+"    ").substring(0,9), 0, 1);
		LCD.drawString(("GA:"+gyroAngle+"    ").substring(0,9), 0, 2);
		
		
		if(powerLeft > 0){
			m1.forward();
			m2.forward();
		}else if(powerLeft < 0){
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
	}
	
	public double getSpeed(){
		return powerLeft;
	}

}
