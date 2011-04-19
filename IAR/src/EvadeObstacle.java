import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class EvadeObstacle implements Behavior{
	
	private TouchSensor touchSensor;
	private boolean turning = false;
	private boolean backwards = false;
	private static long BACKTIME_INC = 1000;
	private static long BACKTIME_MIN = 1000;
	private static long TURNTIME_INC = 2000;
	private static long TURNTIME_MIN = 1000;
	
	public EvadeObstacle(TouchSensor s) {
		this.touchSensor = s;
	}

	public void action() {
		
		long startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.BACKWARD);
		
		long rand_back_time = (long)(BACKTIME_MIN + Math.random() * BACKTIME_INC);
		while(System.currentTimeMillis() - startTime < rand_back_time){
			try{Thread.sleep(100);}catch(Exception e) {}
		}
		
		startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.RIGHT);
		
		long rand_turn_time = (long)(TURNTIME_MIN + Math.random() * TURNTIME_INC);
		while(System.currentTimeMillis() - startTime < rand_turn_time){
			
			if(touchSensor.isPressed())
				break;
			
			try{Thread.sleep(100);}catch(Exception e) {}
		}
	}

	public void suppress() {
		CommandHandler.getInstance().execute(CommandHandler.STOP);
	}

	public boolean takeControl() {
		return touchSensor.isPressed();
	}

}
