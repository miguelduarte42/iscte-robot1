import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * This class is responsible for all Bluetooth communication
 * between the brick and a computer. It allows the sending and
 * receiving of messages.
 */
public class BluetoothHandler{
	
	/** indicates if the bluetooth communication is active**/
	private boolean connectionAvailable = false;

	private DataReceiver dataReceiver;
	private DataOutputStream outputStream;
	
	public BluetoothHandler() throws Exception{
		
		LCD.clearDisplay();
		
		LCD.drawString("Waiting for BT",0,0);
		BTConnection connection = Bluetooth.waitForConnection();
		
		if (connection == null) {
			LCD.drawString("No BT connection",0,1);
			throw new Exception();
		}
		
		LCD.drawString("Connected to BT!",0,1);
		connectionAvailable = true;
		
		DataInputStream inputStream = connection.openDataInputStream();
		outputStream = connection.openDataOutputStream();
		LCD.drawString("Opened Streams",0,2);
		
		this.dataReceiver = new DataReceiver(inputStream);
		this.dataReceiver.start();
	}
	
	/**
	 * Sends a message through the bluetooth channel
	 * if the connection is active. The \n appended
	 * to the message enables the use of readLine
	 * on the receiving end of the communication.
	 * 
	 * @param message	the message to send
	 */
	public void sendMessage(String message){
		if(connectionAvailable)
			try{
				outputStream.writeBytes(message+"\n");
				outputStream.flush();
			}catch(Exception e){
				System.out.println("OutputStream died");
				connectionAvailable = false;
			}
	}
	
	/**
	 * This thread is responsible for waiting for messages
	 * sent over bluetooth and relaying them to the main
	 * control of the robot.
	 */
	private class DataReceiver extends Thread {
		
		private DataInputStream inputStream;
		
		public DataReceiver(DataInputStream inputStream) {
			this.inputStream = inputStream;
		}
		
		public void run() {
			try{
				while(true){
					String received = inputStream.readLine();
					System.out.println(received);
				}
			}catch(Exception e){
				System.out.println("InputStream died");
				connectionAvailable = false;
			}
		}
	}

	public boolean getStatus() {
		return connectionAvailable;
	}

}
