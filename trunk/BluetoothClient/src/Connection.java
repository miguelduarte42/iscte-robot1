import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import lejos.pc.comm.NXTCommBluecove;  
import lejos.pc.comm.NXTInfo; 

public class Connection { 

	public static void main(String[] args){ 
		new GUI(new Connection());
	}

	DataOutputStream output;
	DataInputStream input;
	DataReceiver receiver;
	boolean connected = false;
	
	public void send(int n){
		if(connected)
			try{
				output.writeInt(n);
				output.flush();
			}catch(Exception e){e.printStackTrace();}
	}

	public void connect(){
		NXTInfo nxtCtrl = new NXTInfo(); 
		nxtCtrl.deviceAddress = "00:16:53:0e:4d:69"; 

		NXTCommBluecove com = new NXTCommBluecove(); 

		try{
			com.open(nxtCtrl); 
			System.out.println("Connection established!");

			output = new DataOutputStream(com.getOutputStream());
			input = new DataInputStream(com.getInputStream());

			receiver = new DataReceiver(input);
			receiver.start();
			connected = true;
		} 
		catch(Exception e){
			System.err.println("Could not establish connection.");
			e.printStackTrace();
		} 
	}

	public class DataReceiver extends Thread {

		private DataInputStream inputStream;

		public DataReceiver(DataInputStream inputStream) {
			this.inputStream = inputStream;
		}

		public void run() {
			try{
				String received = "";
				while(received!=null){
					received = inputStream.readLine();
					System.out.println(received);
				}
			}catch(Exception e){
				System.out.println("InputStream died");
			}
		}
	}
}