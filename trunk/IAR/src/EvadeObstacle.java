import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class EvadeObstacle implements Behavior{

	private TouchSensor touchSensor;
	private UltrasonicSensor ultrasonicSensor;
	private static long BACKTIME_INC = 500;
	private static long BACKTIME_MIN = 500;
	private static long TURNTIME_INC = 2000;
	private static long TURNTIME_MIN = 1000;
	private static long SENSOR_DISTANCE = 40;

	public EvadeObstacle(TouchSensor s, UltrasonicSensor us) {
		this.touchSensor = s;
		this.ultrasonicSensor = us;
	}

	public void action() {

		long startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.BACKWARD);

		long rand_back_time = (long)(BACKTIME_MIN + Math.random() * BACKTIME_INC);

		if(ultrasonicSensor.getDistance() < SENSOR_DISTANCE){
			rand_back_time = 0;
		}
		
		while(System.currentTimeMillis() - startTime <= rand_back_time){
			Thread.yield();
		}

		startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.RIGHT);

		long rand_turn_time = (long)(TURNTIME_MIN + Math.random() * TURNTIME_INC);
		while(System.currentTimeMillis() - startTime < rand_turn_time){

			if(touchSensor.isPressed())
				break;

			Thread.yield();
		}
	}

	public void suppress() {

	}

	public boolean takeControl() {
		
		double distance = ultrasonicSensor.getDistance();
		if(distance == 4) distance = 1000;//SENSOR IS STUPID AND READS RANDOM 4s
		
		return touchSensor.isPressed() || distance < SENSOR_DISTANCE;
	}

}
