import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class ObstacleAvoider implements Behavior {
	
	private UltrasonicSensor us;
	
	public ObstacleAvoider(UltrasonicSensor u){
		this.us = u;
	}

	@Override
	public void action() {
		
		CommandHandler.getInstance().execute(CommandHandler.RIGHT);
		try{Thread.sleep(100);}catch(Exception e) {}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

	public boolean takeControl() {
		return (us.getDistance() < 30);
	}

}
