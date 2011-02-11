package tests;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroSensor;


public class Kinematics {
	
	public static void main(String[] args) {
		
		Motor m1 = Motor.A;
		Motor m2 = Motor.C;
		DifferentialDriveKinematics k = new DifferentialDriveKinematics(0.9, 0.9, 3);
		GyroSensor g = new GyroSensor(SensorPort.S1);
		
		int left = 360;
		int right = 180;
		
		m1.setSpeed(left);
		m2.setSpeed(right);
		m1.forward();
		m2.forward();
		
		boolean stop = false;
		
		int steps = 0;
		
		while(!stop){
			
			k.nextStep(left, right);
			
			String xs = ""+k.getX();
			String ys = ""+k.getY();
			xs = xs.substring(0,5);
			ys = ys.substring(0,5);
			
			stop = Math.abs(k.getX())<0.2 && Math.abs(k.getY())<0.2 && steps > 10;
			LCD.clearDisplay();
			System.out.println("X:" + xs +" Y:"+ys);
			System.out.println("Gyro:"+g.readValue());
 	
			
			try{
				Thread.sleep(100);
			}catch(Exception e){
				
			}
			
			steps++;
			
		}
		m1.stop();
		m2.stop();
	}

}
