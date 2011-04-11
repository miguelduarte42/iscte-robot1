import lejos.nxt.Motor;
import lejos.nxt.MotorPort;

public class MotorHandler {
	
	private static final MotorPort PORT_MOTOR_LEFT = MotorPort.A;
	private static final MotorPort PORT_MOTOR_RIGHT = MotorPort.C;
	private Motor motorLeft = new Motor(PORT_MOTOR_LEFT);;
	private Motor motorRight = new Motor(PORT_MOTOR_RIGHT);
	private int speedLeft = 0;
	private int speedRight = 0;
	
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
		
		m.setSpeed(s);
		
		if(s > 0) m.forward();
		else if(s < 0) m.backward();
		else m.stop();
		
	}
}
