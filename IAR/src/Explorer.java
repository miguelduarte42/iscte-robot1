import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Explorer {
	
	public Explorer() {
		
		new OdometerReset();
		
		CommandHandler commandHandler = CommandHandler.getInstance();
		NXTConnHandler bluetoothHandler = new NXTConnHandler();
		
		MotorHandler motorHandler = MotorHandler.getInstance();
		
		//Map map = Map.getInstance();
		
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S3);
		ultrasonicSensor.continuous();
		ultrasonicSensor.setContinuousInterval((byte) 10);
		ultrasonicSensor.setCalibrationData(new byte[] {0,1,10});
		
		TouchSensor touchSensor = new TouchSensor(SensorPort.S1);
		System.out.println("Ready");
		Button.waitForPress();
		//commandHandler.execute(CommandHandler.FORWARD);
		
		Behavior b1 = new Explore();
	    Behavior b2 = new EvadeObstacle(touchSensor,ultrasonicSensor,bluetoothHandler);
	    Behavior b3 = new TrackOdometry(bluetoothHandler, touchSensor, ultrasonicSensor, motorHandler);
	    Behavior b4 = new SquareBehavior();
	    Behavior b5 = new CircleBehavior();
	    Behavior b6 = new HeadBehavior(bluetoothHandler, new Motor(MotorPort.B), ultrasonicSensor);
	    
	    boolean autonomous = true;
	    boolean square = false;
	    boolean circle = false;
	    boolean head = false;
	    
	    if(autonomous){
	    	Behavior [] bArray = {b1,b2};
		    new BehaviorRunner(new Arbitrator(bArray)).start();
	    }
	    
	    if(square){
	    	Behavior [] bArray = {b4};
	    	new BehaviorRunner(new Arbitrator(bArray)).start();
	    }
	    
	    if(circle){
	    	Behavior [] bArray = {b5};
	    	new BehaviorRunner(new Arbitrator(bArray)).start();
	    }
	    
	    if(head){
	    	Behavior [] bArray = {b6};
	    	new BehaviorRunner(new Arbitrator(bArray)).start();
	    }
	    
	    Behavior [] bArray2 = {b3};
	    new BehaviorRunner(new Arbitrator(bArray2)).start();
	    
	}
	
	public static void main(String[] args) {
		new Explorer();
	}

}