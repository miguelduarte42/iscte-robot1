import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Explorer {
	
	public Explorer() {
		
		CommandHandler commandHandler = CommandHandler.getInstance();
		BluetoothHandler bluetoothHandler = new BluetoothHandler();
		
		MotorHandler motorHandler = MotorHandler.getInstance();
		
		//Map map = Map.getInstance();
		
		//UltrasonicSensor ultraSonic = new UltrasonicSensor(SensorPort.S3);
		TouchSensor touchSensor = new TouchSensor(SensorPort.S1);
		System.out.println("Ready");
		Button.waitForPress();
		commandHandler.execute(CommandHandler.FORWARD);
		
		Behavior b1 = new Explore();
	    Behavior b2 = new EvadeObstacle(touchSensor);
	    Behavior b3 = new TrackOdometry(bluetoothHandler, touchSensor, motorHandler);
	    Behavior [] bArray = {b1, b2, b3};
	    new BehaviorRunner(new Arbitrator(bArray)).start();
	    
	    //Behavior [] bArray2 = {b3};
	    //new BehaviorRunner(new Arbitrator(bArray2)).start();
	}
	
	public static void main(String[] args) {
		new Explorer();
	}

}
