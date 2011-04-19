import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior{

	public void action() {
		CommandHandler.getInstance().execute(CommandHandler.FORWARD);
	}

	public void suppress() {
		CommandHandler.getInstance().execute(CommandHandler.STOP);
	}

	public boolean takeControl() {
		return true;
	}
}
