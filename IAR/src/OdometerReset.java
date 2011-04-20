import lejos.nxt.Button;


public class OdometerReset extends Thread{

	public OdometerReset() {
		start();
	}

	public void run(){
		while(true){

			Button.waitForPress();

			Odometer.getInstance().orientation = 0;
			Odometer.getInstance().x = 0;
			Odometer.getInstance().y = 0;

		}
	}

}
