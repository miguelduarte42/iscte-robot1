import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;


public class CircleBehavior implements Behavior{

	@Override
	public void action() {

		double rotation = 2*Math.PI;


		CommandHandler.getInstance().execute(CommandHandler.LEFT);

		while(Odometer.getInstance().orientation < rotation){
			
		}

		CommandHandler.getInstance().execute(CommandHandler.STOP);

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
