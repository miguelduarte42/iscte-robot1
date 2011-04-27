import lejos.nxt.Motor;
import lejos.nxt.MotorPort;

/**
 * @author Group1 - IAR
 *
 *  This class controls the motors.
 *  It receives the left and right speed and passes it to the motors.
 *  It is also used to get the tacho reading from the motors
 *  
 * @see CommandHandler
 */
public class MotorHandler {
	
	private static final MotorPort PORT_MOTOR_LEFT = MotorPort.A;
	private static final MotorPort PORT_MOTOR_RIGHT = MotorPort.C;
	private Motor motorLeft = new Motor(PORT_MOTOR_LEFT);;
	private Motor motorRight = new Motor(PORT_MOTOR_RIGHT);
	private int speedLeft = 0;
	private int speedRight = 0;
	private static MotorHandler INSTANCE;
	
	
	private MotorHandler(){
		
	}
	
	public static MotorHandler getInstance(){
		if(INSTANCE == null)
			INSTANCE = new MotorHandler();
		return INSTANCE;
	}
	
	public void changeSpeed(int left, int right) {
		
		speedLeft = left;
		speedRight = right;
		
		applySpeedToWheels();
	}
	
	private void applySpeedToWheels() {
		applySpeedToSingleWheel(motorLeft,speedLeft);
		applySpeedToSingleWheel(motorRight,speedRight);
	}
	
	private void applySpeedToSingleWheel(Motor m, int s){
		// TODO: Change to gentle stop between going forward and backward.
		m.setSpeed(s);
		
		if(s > 0) m.forward();
		else if(s < 0) m.backward();
		else m.stop();
	}
	
	public int getRightTacho(){
		return motorRight.getTachoCount();
	}
	
	public int getLeftTacho(){
		return motorLeft.getTachoCount();
	}
}
