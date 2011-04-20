import lejos.robotics.subsumption.Behavior;


public class SquareBehavior implements Behavior{

	@Override
	public void action() {

		double rotation = Math.PI/2;
		long sleepTime = 5000;

		while(true){
			CommandHandler.getInstance().execute(CommandHandler.FORWARD);
			try {Thread.sleep(sleepTime);} catch (InterruptedException e) {}
			
			double currOrientation = Odometer.getInstance().orientation;
			CommandHandler.getInstance().execute(CommandHandler.LEFT);
			while(Odometer.getInstance().orientation <= currOrientation + rotation){
				//try {Thread.sleep(10);} catch (InterruptedException e) {}
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
