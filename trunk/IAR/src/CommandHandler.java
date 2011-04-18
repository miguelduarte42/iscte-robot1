
public class CommandHandler {
	
	private final static int
						FORWARD = 2,
						BACKWARD = 8,
						STOP = 5,
						LEFT = 4,
						RIGHT = 6;
	
	private static CommandHandler INSTANCE;
	
	private CommandHandler() {
		
	}
	
	public static CommandHandler getInstance(){
		if(INSTANCE == null)
			INSTANCE = new CommandHandler();
		return INSTANCE;
	}
	
	
	public void execute(int command){
		
		int left = 0, right = 0;
		
		switch(command){
			case FORWARD:
				System.out.println("Forward");
				left = right = 360;
				break;
			case BACKWARD:
				System.out.println("Backward");
				left = 360;
				right = 300;
				break;
			case LEFT:
				System.out.println("Left");
				left = -360;
				right = 360;
				break;
			case RIGHT:
				System.out.println("Right");
				left = 360;
				right = -360;
				break;
			case STOP:
				System.out.println("Stop");
				break;
		}
		
		MotorHandler.getInstance().changeSpeed(left, right);
	}
}
