import lejos.robotics.subsumption.Behavior;

public class Explore implements Behavior{
	
	private boolean stop = false;

	public void action() {
		
		stop = false;
		
		CommandHandler.getInstance().execute(CommandHandler.FORWARD);
		
		while(!stop)
			Thread.yield();
	}

	public void suppress() {
		stop = true;
	}

	public boolean takeControl() {
		return true;
	}
}
