
public class Explorer {
	
	public Explorer() {
		
		CommandHandler commandHandler = CommandHandler.getInstance();
		BluetoothHandler bluetoothHandler = new BluetoothHandler();
		MotorHandler motorHandler = MotorHandler.getInstance();
		
		
		while(true){
			
			try {
				
				Odometer.getInstance().nextTacho(motorHandler.getLeftTacho(), motorHandler.getRightTacho());
				
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}
	
	public static void main(String[] args) {
		new Explorer();
	}

}
