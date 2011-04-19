import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


public class EvadeObstacle implements Behavior{
	
	private TouchSensor touchSensor;
	private boolean turning = false;
	private boolean backwards = false;
	private long backTime = 1000;
	private long turnTime = 1500;
	
	public EvadeObstacle(TouchSensor s) {
		this.touchSensor = s;
	}

	public void action() {
		
		long startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.BACKWARD);
		
		while(System.currentTimeMillis() - startTime < backTime){
			try{Thread.sleep(100);}catch(Exception e) {}
		}
		
		startTime = System.currentTimeMillis();
		CommandHandler.getInstance().execute(CommandHandler.RIGHT);
		
		while(System.currentTimeMillis() - startTime < turnTime){
			
			if(touchSensor.isPressed())
				break;
			
			try{Thread.sleep(100);}catch(Exception e) {}
		}
	}

	public void suppress() {
		CommandHandler.getInstance().execute(CommandHandler.STOP);
	}

	public boolean takeControl() {
		Sound.beep();
		return touchSensor.isPressed();
	}

}
