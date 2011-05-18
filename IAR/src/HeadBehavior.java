import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.subsumption.Behavior;


public class HeadBehavior implements Behavior{
	
	private UltrasonicSensor sonar;
	private NXTConnHandler blue;
	private Odometer odometer;
	private Motor head;
	
	public HeadBehavior(NXTConnHandler bt, Motor head, UltrasonicSensor us) {
		this.blue = bt;
		this.head = head;
		this.sonar = us;
		odometer = Odometer.getInstance();
	}

	@Override
	public void action() {
		
		sonar.ping();
		try {Thread.sleep(25);} catch (Exception e) {}
		System.out.println(sonar.getDistance());
		
	}
	
	private void enviaMensagem(int x, int y, int status){
		blue.sendCommand(x);
		blue.sendCommand(y);
		blue.sendCommand(status);
	}
	
	private void leitura(double headAngle){
		
		int[] pings = new int[3];
		int total = 0;
		
		for(int i = 0; i < 3 ; i++){
			sonar.ping();
			try {Thread.sleep(25);} catch (Exception e) {}
			pings[i] = sonar.getDistance();
			total+=pings[i];
		}
		
		int average = total/3;
		
		double x = odometer.x;
		double y = odometer.y;
		double orientation = odometer.orientation;
		
		//headAngle to radians
		headAngle = (2*Math.PI*headAngle)/360;
		
		double targetOrientation = orientation+headAngle;
		
		x+=Math.cos(targetOrientation)*average;
		y-=Math.sin(targetOrientation)*average;
		
		if(average < 40)
			enviaMensagem((int)x,(int)y,1);
		
	}

	@Override
	public void suppress() {
		
	}

	@Override
	public boolean takeControl() {
		return true;
	}

}
