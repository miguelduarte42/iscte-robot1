import lejos.nxt.*;
import lejos.robotics.ColorDetector;

public class Test {
	public static void main (String[] args) {
		//Button.waitForPress();
		Motor m = Motor.A;
		Motor m2 = Motor.B;
		Motor m3 = Motor.C;
		
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		TouchSensor touch1 = new TouchSensor(SensorPort.S2);
		TouchSensor touch2 = new TouchSensor(SensorPort.S3);

		m.setSpeed(360);
		//m2.setSpeed(360);
		m3.setSpeed(360);
		
		boolean turning = false;
		int steps_turning = 0;
		int max_steps = 90;
		boolean left = true;
		
		while(true) {
			
			int distance = sensor.getDistance();
			System.out.println("D="+distance);
			
			boolean t1 = touch1.isPressed();
			boolean t2 = touch2.isPressed();
			
			if(!turning) {
				
				if(distance < 30 || t2 || t1) {
					turning = true;
					left = true;
					steps_turning = 0;
				}
				
				if(turning && t2)
					left = false;
				
				if(!turning) {
					m.forward();
					m3.forward();
				}
				
			} else {//turning
				
				if(left){
					m.backward();
					m3.forward();
				}else {
					m3.backward();
					m.forward();
				}
				
				steps_turning++;
				
			}
			
			if(steps_turning > max_steps) {
				steps_turning = 0;
				turning = false;
			}
		
		}
	}
}