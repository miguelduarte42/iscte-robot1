import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;

public class TrackOdometry implements Behavior {

	private TouchSensor touch;
	private BluetoothHandler blue;
	private Odometer odometer;
	private MotorHandler motor;
	private int prevX;
	private int prevY;
	
	public TrackOdometry(BluetoothHandler bt, TouchSensor ts, MotorHandler mh) {
		this.touch = ts;
		this.blue = bt;
		this.motor = mh;
		odometer = Odometer.getInstance();
		prevX = 0;
		prevY = 0;
	}
	
	public void action() {		
		int x = (int)odometer.x;
		int y = (int)odometer.y;
		
		blue.sendCommand(x);
		blue.sendCommand(y);
		
		if(touch.isPressed())
			blue.sendCommand(1);
		else
			blue.sendCommand(0);
	}

	public void suppress() {}

	public boolean takeControl() {
		
		odometer.nextTacho(motor.getLeftTacho(), motor.getRightTacho());
		
		int x = (int)odometer.x;
		int y = (int)odometer.y;
		
		if(x != prevX || y != prevY)
			return true;
		
		return false;
	}

}
