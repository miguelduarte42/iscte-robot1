
public class CommandHandler {
	
	private final static int
						FORWARD = 2,
						BACKWARDS = 8,
						STOP = 5,
						LEFT = 4,
						RIGHT = 6;
	
	
	public void execute(int command){
		
		switch(command){
			case FORWARD:
				System.out.println("Forward");
				break;
			case BACKWARDS:
				System.out.println("Backwards");
				break;
			case LEFT:
				System.out.println("Left");
				break;
			case RIGHT:
				System.out.println("Right");
				break;
			case STOP:
				System.out.println("Stop");
				break;
		}
	}

}
