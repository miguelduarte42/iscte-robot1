
public class CommandHandler {
	
	private final static int
						FORWARD = 2,
						BACKWARD = 8,
						STOP = 5,
						LEFT = 4,
						RIGHT = 6;
	
	private static CommandHandler INSTANCE;
	
	public CommandHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void execute(int command){
		
		switch(command){
			case FORWARD:
				System.out.println("Forward");
				break;
			case BACKWARD:
				System.out.println("Backward");
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
