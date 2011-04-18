
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
				left = right = 360;
				break;
			case BACKWARD:
				left = -360;
				right = -360;
				break;
			case LEFT:
				left = 50;
				right = 360;
				break;
			case RIGHT:
				left = 360;
				right = 50;
				break;
			case STOP:
				break;
		}
		
		MotorHandler.getInstance().changeSpeed(left, right);
	}
}
