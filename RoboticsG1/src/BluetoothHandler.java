import java.io.DataInputStream;
import java.io.DataOutputStream;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class BluetoothHandler{
	
	private boolean connectionAvailable = false;
	private DataReceiver dataReceiver;
	private DataSender dataSender;
	
	public BluetoothHandler() throws Exception{
		
		System.out.println("Waiting for BT");
		BTConnection connection = Bluetooth.waitForConnection();
		
		if (connection == null) {
			System.out.println("No BT connection");
			throw new Exception();
		}
		
		System.out.println("Connected to BT!");
		connectionAvailable = true;
		
		DataInputStream inputStream = connection.openDataInputStream();
		DataOutputStream outputStream = connection.openDataOutputStream();
		System.out.println("Opened Streams");
		
		this.dataReceiver = new DataReceiver(inputStream);
		this.dataSender = new DataSender(outputStream);
		this.dataReceiver.start();
	}
	
	public void sendMessage(String message){
		dataSender.sendMessage(message);
	}
	
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
	
	private class DataSender {
		
		private DataOutputStream outputStream;
		
		public DataSender(DataOutputStream outputStream){
			this.outputStream = outputStream;
		}
		
		private void sendMessage(String toSend){
			try{
				outputStream.writeBytes(toSend+"\n");
				outputStream.flush();
			}catch(Exception e){
				System.out.println("OutputStream died");
				connectionAvailable = false;
			}
		}
	}
	
	public boolean getStatus() {
		return connectionAvailable;
	}

}
