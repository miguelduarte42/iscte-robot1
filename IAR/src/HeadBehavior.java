import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.robotics.subsumption.Behavior;


public class HeadBehavior implements Behavior{

	@Override
	public void action() {
		
		Motor m = new Motor(MotorPort.B);
		
		int angle = 0;
		
		while(true){
			
			angle = 0;
			
			while(angle <= 90){
				m.rotate(30);
				angle+=30;
			}
			
			while(angle >= -90){
				m.rotate(-30);
				angle-=30;
			}
			
			m.rotateTo(0);
		}
		
	}

	@Override
	public void suppress() {
		
	}

	@Override
	public boolean takeControl() {
		return true;
	}

}
