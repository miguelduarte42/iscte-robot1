import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;


public class EightBehavior implements Behavior{

	@Override
	public void action() {

		double rotation = Math.PI/2;
		long sleepTime = 4000;
		
		int current = 0;
		int max = 4*4;
		int i=0;
		while(true){
			if (Button.ESCAPE.isPressed()) break;
			for (i=0; i<4; i++){
				CommandHandler.getInstance().execute(CommandHandler.FORWARD);
				try {Thread.sleep(sleepTime);} catch (InterruptedException e) {}
			
				double currOrientation = Odometer.getInstance().orientation;
				CommandHandler.getInstance().execute(CommandHandler.LEFT);
				while(Odometer.getInstance().orientation <= currOrientation + rotation){
				//try {Thread.sleep(10);} catch (InterruptedException e) {}
				}
			}
			for (i=0; i<4; i++){
				CommandHandler.getInstance().execute(CommandHandler.FORWARD);
				try {Thread.sleep(sleepTime);} catch (InterruptedException e) {}
			
				double currOrientation = Odometer.getInstance().orientation;
				CommandHandler.getInstance().execute(CommandHandler.RIGHT);
				while(Odometer.getInstance().orientation <= currOrientation + rotation){
				//try {Thread.sleep(10);} catch (InterruptedException e) {}
				}
			}
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean takeControl() {
		return true;
	}

}
