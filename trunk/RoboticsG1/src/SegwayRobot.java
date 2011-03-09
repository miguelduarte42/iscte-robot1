import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class SegwayRobot {

	private static final Motor MOTOR_1 = Motor.A;
	private static final Motor MOTOR_2 = Motor.C;
	private static final SensorPort GYRO_SENSOR = SensorPort.S3;
	private static final double WAIT_TIME = 0.01;

	private GyroHandler gyroHandler;
	private MotorHandler motorHandler;
	private BluetoothHandler bluetoothHandler;

	private double timeStart;

	public SegwayRobot() {

		try {

			this.gyroHandler = new GyroHandler(GYRO_SENSOR, true);

			this.motorHandler = new MotorHandler(MOTOR_1, MOTOR_2);
			this.bluetoothHandler = new BluetoothHandler();

			createButtonListeners(); //Test function (to remove)

			this.timeStart = System.currentTimeMillis();

			this.execute();

		} catch (Exception e) {
			System.out.println("Init failure");
		}
	}

	/**
	 * Main controller loop:
	 * 		- Reads the GyroSensor
	 * 		- Calculates the wheel speed (prevents the robot from falling)
	 */
	public void execute() {

		long loopCount = 0;
		boolean standing = true;
		double timeInterval = WAIT_TIME;

		while (standing) {

			timeInterval = calculateInterval(loopCount++);

			gyroHandler.readValues(timeInterval);
			motorHandler.readValues(timeInterval);

			motorHandler.updateWheelPower(gyroHandler.getSpeed(),
					gyroHandler.getAngle(), timeInterval);

			standing = Math.abs(gyroHandler.getAngle()) < 50;

			try {
				Thread.sleep((int) (WAIT_TIME * 1000));
			} catch (Exception e) {
				
			}
		}
	}

	/**
	 * Finds the average time it takes for the main loop to process. This value
	 * is useful to know how much time has passed since the last readings,
	 * allowing estimations based on readings from the sensors.
	 * 
	 * Miguel - I think this shoudn't use the average time, we should change it
	 * so that the actual time elapsed is used on the calculation. What's the benefit
	 * of using and average over time? It produces slightly incorrect values and
	 * is more complex to calculate because it has an additional multiplication and
	 * division.
	 * 
	 * @param loopCount
	 *            the current loop number on the main program
	 */
	private double calculateInterval(long loopCount) {
			
		if (loopCount == 0)
			return WAIT_TIME;
		
		//multiplying by 1000 allows conversion to milliseconds
		return (System.currentTimeMillis() - timeStart) / (1000.0 * loopCount);
	}

	/**
	 * Sends a message through the Bluetooth handler to a connected computer
	 * @param message 	the message to send
	 */
	public void sendMessage(String message) {
		bluetoothHandler.sendMessage(message);
	}

	/**
	 * Test function that send information through the Bluetooth connection
	 * when a button is pressed/released. (to remove) 
	 */
	private void createButtonListeners() {
		Button.ENTER.addButtonListener(new ButtonListener() {
			public void buttonPressed(Button b) {
				sendMessage("ENTER pressed");
			}

			public void buttonReleased(Button b) {
				sendMessage("ENTER released");
				try {
					gyroHandler.calibrate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
				}
			}
		});

		Button.ESCAPE.addButtonListener(new ButtonListener() {

			public void buttonPressed(Button arg0) {
				sendMessage("ESC pressed");
			}

			public void buttonReleased(Button arg0) {
				sendMessage("ESC released");
			}
		});
	}
	
	public static void main(String[] args) {
		SegwayRobot segway = new SegwayRobot();
		segway.execute();
	}

}
