import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class ObstacleAvoider implements Behavior {
	
	private UltrasonicSensor us;
	private Odometer odometer;
	private Map map;
	private int x;
	private int y;
	private double orientation;
	private int obstacle_x;
	private int obstacle_y;
	
	public ObstacleAvoider(UltrasonicSensor u, Odometer o, Map map){
		this.odometer = o;
		this.us = u;
		this.map = map;
		
	}

	@Override
	public void action() {
		
		CommandHandler.getInstance().execute(CommandHandler.RIGHT);
		try{Thread.sleep(100);}catch(Exception e) {}
		x = (int)odometer.x;
		y = (int)odometer.y;
		orientation = odometer.orientation;
		
		obstacle_x = (int)(x + Math.cos(orientation) * us.getDistance());
		obstacle_y = (int)(y + Math.sin(orientation) * us.getDistance());
		
		map.markOccuppied(obstacle_x, obstacle_y);
		
		
		
		
		
		

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

	public boolean takeControl() {
		return (us.getDistance() < 30);
	}

}
