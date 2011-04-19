import lejos.robotics.subsumption.Arbitrator;


public class BehaviorRunner extends Thread{
	
	private Arbitrator arb;
	
	public BehaviorRunner(Arbitrator arb) {
		this.arb = arb;
	}
	
	public void run() {
		arb.start();
	}

}
