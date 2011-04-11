import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BluetoothHandler extends Thread
{
   private DataInputStream istream;
   private DataOutputStream ostream;
   private CommandHandler commandHandler;
   private int input;
   
   public BluetoothHandler(CommandHandler ch) {
	   this.commandHandler = ch;
	   start();
   }

   public void run()
   {
	  BTConnection conn = Bluetooth.waitForConnection();
	  conn.setIOMode(0); // Used when a pc connection is made
	  istream = conn.openDataInputStream();
	  ostream = conn.openDataOutputStream();
	  
	  new BluetoothSender(ostream).start();
	  
      input = 0;
	  try{
		  while (true){
    		  input = istream.readInt();
    		  commandHandler.execute(input);
		  }
	  }catch(Exception e){}
   }
   
   public void sendCommand(int command)
   {
      try {
         ostream.writeInt(command);
         ostream.flush();
      } catch (IOException e) {}
   }
}

class BluetoothSender extends Thread{
	
	private DataOutputStream ostream;
	private static int SEND_WAIT = 100;
	
	public BluetoothSender(DataOutputStream ostream) {
		this.ostream = ostream;
	}
	
	public void run(){
		try{
			while(true){
				
				/*Odometer od = Odometer.instance;
				
				if(od != null){
			         ostream.writeDouble(od.x);
			         ostream.writeDouble(od.y);
			         ostream.writeDouble(od.orientation);
			         ostream.writeLong(System.currentTimeMillis());
			         ostream.writeDouble(od.prevLeftTacho);
			         ostream.writeDouble(od.prevRightTacho);
			         ostream.writeDouble(od.sL);
			         ostream.writeDouble(od.sR);
			         ostream.flush();
				}*/
				Thread.sleep(SEND_WAIT);
			}
			
		}catch(Exception e){}
	}
}