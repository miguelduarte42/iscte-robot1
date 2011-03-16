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
	
	private static double KGYROSPEED = 17; //17; //Default: 1.15
	private static double KGYROANGLE = 25.5; //25.5; //Default: 7.5
	private static double KPOS = 0.07;
	private static double KSTEER = 1;
	private static double KSPEED = 0.6;
	private static double KDRIVE = -0.02;
	private static double KWHEEL = 1; //the value for our wheels
	private static int MAX_POWER = 900; //Max Power of each wheel 
	
	//private long lastTime = System.currentTimeMillis(); 
	
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
		//timeInterval = 	(System.currentTimeMillis()-this.lastTime) / 1000.0;
		//this.lastTime =  System.currentTimeMillis();
		
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
	 */
	public void updateWheelPower(double gyroSpeed, double gyroAngle, double timeInterval) {
		
		if(Math.abs(gyroSpeed) < 1.5)
			gyroSpeed = 0;
		
		double power = (KGYROSPEED * gyroSpeed + KGYROANGLE * gyroAngle) /KWHEEL +
	             		//KPOS * motorPosition +
	             		KDRIVE * 0 + //we don't want to drive... for now
	             		KSPEED * motorSpeed;
		
		SteerControl(power,timeInterval);
		//System.out.println(gyroAngle);
		LCD.drawString("PL:"+powerLeft, 0, 1);
		LCD.drawString("PR:"+powerRight, 0, 2);
		
		m1.setSpeed(Math.abs((int)powerLeft));
		m2.setSpeed(Math.abs((int)powerRight));
		
		//m1.setPower(Math.abs((int)powerLeft));
		//m2.setPower(Math.abs((int)powerRight));
		
		//System.out.println("pL "+powerLeft);
		
		if(powerLeft > 0){
			m1.forward();
		}else if(powerLeft < 0){
			m1.backward();
		}
		if(powerRight > 0){
			m2.forward();
		}else if(powerRight < 0){
			m2.backward();
		}
	}
	
    private void SteerControl(double power, double timeInterval) {
        double powerSteer;

        // Update the target motor difference based on the user steering
        // control value.
        double motorControlSteer = 10;
        motorDiffTarget += motorControlSteer * timeInterval;

        // Determine the proportionate power differential to be used based
        // on the difference between the target motor difference and the
        // actual motor difference.
        powerSteer = KSTEER * (motorDiffTarget - motorDiff);

        // Apply the power steering value with the main power value to
        // get the left and right power values.
        powerLeft = (power + powerSteer) * .995;
        powerRight = (power + powerSteer) * .995;
        if (powerLeft > MAX_POWER){
        	powerLeft = MAX_POWER;}
        else if (powerLeft < -MAX_POWER){
        	powerLeft = -MAX_POWER;}
        if (powerRight > MAX_POWER){
        	powerRight = MAX_POWER;}
        else if (powerRight < -MAX_POWER){
        	powerRight = -MAX_POWER;}
      }
}