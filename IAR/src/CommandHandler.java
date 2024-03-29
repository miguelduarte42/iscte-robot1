
/**
 * @author Group1 - IAR
 *
 * This class receives the commands (FORWARD, BACKWARD, STOP, LEFT, RIGHT)
 *  and based on this sends the correct left and right speeds to the MotorHandler class
 *
 * @see MotorHandler
 */
public class CommandHandler {
	
	public final static int
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
				left = 75;
				right = 250;
				break;
			case RIGHT:
				left = 250;
				right = 75;
				break;
			case STOP:
				break;
		}
		
		MotorHandler.getInstance().changeSpeed(left, right);
	}
}
