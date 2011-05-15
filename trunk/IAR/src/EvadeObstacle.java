import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class EvadeObstacle implements Behavior{

	private TouchSensor touchSensor;
	private UltrasonicSensor ultrasonicSensor;
	private NXTConnHandler blue;
	private Motor head = new Motor(MotorPort.B);
	private static long STOP_TIME = 200;
	private static long BACKTIME_INC = 500;
	private static long BACKTIME_MIN = 500;
	private static long TURNTIME_INC = 2000;
	private static long TURNTIME_MIN = 1000;
	private static long SENSOR_DISTANCE = 40;

	public EvadeObstacle(TouchSensor s, UltrasonicSensor us, NXTConnHandler blue) {
		this.touchSensor = s;
		this.blue = blue;
		this.ultrasonicSensor = us;
	}

	public void action() {

		double distance = ultrasonicSensor.getDistance();
		if(distance == 4) distance = 1000;
		boolean pressed = touchSensor.isPressed();

		stopSmoothly();
		
		lookAround();

		if(pressed){
			goBackwards();
			stopSmoothly();
		}

		turnRight();
		stopSmoothly();
		lookAround();

	}

	private void turnRight(){

		long startTime = System.currentTimeMillis();
		long rand_turn_time = (long)(TURNTIME_MIN + Math.random() * TURNTIME_INC);

		CommandHandler.getInstance().execute(CommandHandler.RIGHT);

		while(System.currentTimeMillis() - startTime < rand_turn_time){

			if(touchSensor.isPressed())
				break;
			Thread.yield();
		}
	}

	private void stopSmoothly(){
		long startTime = System.currentTimeMillis();

		CommandHandler.getInstance().execute(CommandHandler.STOP);

		while(System.currentTimeMillis() - startTime <= STOP_TIME)
			Thread.yield();
	}

	private void goBackwards(){

		long rand_back_time = (long)(BACKTIME_MIN + Math.random() * BACKTIME_INC);
		long startTime = System.currentTimeMillis();

		CommandHandler.getInstance().execute(CommandHandler.BACKWARD);

		while(System.currentTimeMillis() - startTime <= rand_back_time)
			Thread.yield();
	}

	public void suppress() {

	}

	private void lookAround(){

		int angle = 0;

		int degrees = 5;
		int max = 90;

		angle = 0;

		while(angle < max){
			head.rotate(degrees);
			angle+=degrees;
			leitura(angle);
		}

		head.rotateTo(0);
		angle = 0;

		while(angle > -max){
			head.rotate(-degrees);
			angle-=degrees;
			leitura(angle);
		}

		head.rotateTo(0);
	}


	private void enviaMensagem(int x, int y, int status){
		int[] commands = {x,y,status};
		blue.sendCommands(commands);
	}

	private void leitura(double headAngle){

		Odometer odometer = Odometer.getInstance();
		int[] pings = new int[3];
		int total = 0;

		for(int i = 0; i < 3 ; i++){
			ultrasonicSensor.ping();
			try {Thread.sleep(25);} catch (Exception e) {}
			pings[i] = ultrasonicSensor.getDistance();
			total+=pings[i];
		}

		int average = total/3;

		double x = odometer.x;
		double y = odometer.y;
		double orientation = odometer.orientation;

		//headAngle to radians
		headAngle = (2*Math.PI*headAngle)/360;

		double targetOrientation = orientation - headAngle;

		x+=Math.cos(targetOrientation)*average;
		y+=Math.sin(targetOrientation)*average;
		
		//o y tem que estar negativo, porque a posi��o vertical relativa do motor est� invertida

		if(average < 40)
			enviaMensagem((int)x,(int)y,1);

	}

	public boolean takeControl() {
		
		ultrasonicSensor.ping();
		try {Thread.sleep(25);} catch (Exception e) {}
		
		double distance = ultrasonicSensor.getDistance();
		if(distance == 4) distance = 1000;//SENSOR IS STUPID AND READS RANDOM 4s

		return touchSensor.isPressed() || distance < SENSOR_DISTANCE;
	}

}

