import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class TrackOdometry implements Behavior {

	private TouchSensor touch;
	private UltrasonicSensor sonar;
	private NXTConnHandler blue;
	private Odometer odometer;
	private MotorHandler motor;
	private int prevX;
	private int prevY;
	private static int SONAR_DISTANCE = 40;
	

	public TrackOdometry(NXTConnHandler bt, TouchSensor ts, UltrasonicSensor us, MotorHandler mh) {

		this.touch = ts;
		this.blue = bt;
		this.motor = mh;
		this.sonar = us;
		odometer = Odometer.getInstance();
		prevX = 0;
		prevY = 0;
	}
	
	public void action() {

		int x = (int)odometer.x;
		int y = (int)odometer.y;
		
		int command = 0;
		/*
		double distance = sonar.getDistance();
		if(distance == 4) distance = 1000;//SENSOR IS STUPID AND READS RANDOM 4s
			
		if(distance < SONAR_DISTANCE){
			
			if(!touch.isPressed()) //dizer que estamos em caminho fixe
				enviaMensagem((int)odometer.x,(int)odometer.y,0);
			
			double orientation = odometer.orientation;
			
			x = (int)(x + Math.cos(orientation) * distance);
			y = (int)(y + Math.sin(orientation) * distance);
			
			//Sound.beep();
			command = 1;
		}*/
			
		if(touch.isPressed()/* && distance >= SONAR_DISTANCE*/){
			command = 2;
			//Map.getInstance().markOccuppied(x, y);
		}
		
		enviaMensagem(x,y,command);
		
		prevX = (int)odometer.x;
		prevY = (int)odometer.y;
	}
	
	private void enviaMensagem(int x, int y, int status){
		int[] commands = {x,y,status};
		blue.sendCommands(commands);
	}

	public void suppress() {
	    //Since  this is highest priority behavior, suppress will never be called.
	}

	public boolean takeControl() {
		
		odometer.nextTacho(motor.getLeftTacho(), motor.getRightTacho());
		
		int x = (int)odometer.x;
		int y = (int)odometer.y;
		
		if(x != prevX || y != prevY)
			return true;
		/*
		double distance = sonar.getDistance();
		if(distance == 4) distance = 1000;//SENSOR IS STUPID AND READS RANDOM 4s
		*/
		if(touch.isPressed()/* || distance < SONAR_DISTANCE*/)
			return true;
		
		return false;
	}

}
