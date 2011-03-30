import java.io.*;
import lejos.nxt.comm.*;
/**
 * This thread constantly listens to Bluetooth commands sent to the GELway
 * 
 * @author Steven Jan Witzand
 * @version August 2009
 */
public class BluetoothReader extends Thread
{
   DataInputStream istream;
   DataOutputStream ostream;
   private int dir;
   private boolean newDir = false;
   /**
    * BluetoothReader thread which constantly runs waiting for new commands
    */
   public void run()
   {
	  BTConnection conn = Bluetooth.waitForConnection();
	  conn.setIOMode(0); // Used when a pc connection is made
	  istream = conn.openDataInputStream();
	  ostream = conn.openDataOutputStream();
	  
	  new BluetoothSender(ostream).start();
	  
      dir = 0;
      while (true) {
    	  try {
    		  newDir = false;
    		  dir = istream.readInt();
    		  newDir = true;
    		  try {Thread.sleep(100);} catch (InterruptedException e) {}
    		  if (dir == -1) break;
    	  } catch (IOException e) {}
      }
   }
   /**
    * Returns the current commands send over Bluetooth
    * @return the current command sent over Bluetooth
    */
   public int getDir() { return dir;}
   /**
    * Used to check if a new commands has been sent.
    * 
    * @return is a current commands has been sent over Bluetooth
    */
   public boolean getNewDir(){return newDir;}

   /**
    * Used to send a commands to the Bluetooth device the robot is connected with.
    * 
    * @param command command to be sent to connected Bluetooth device
    */
   public void sendCommand(int command)
   {
      try {
         ostream.writeInt(command);
         ostream.flush();
      } catch (IOException e) {}
   }
}

class BluetoothSender extends Thread{
	
	DataOutputStream ostream;
	
	public BluetoothSender(DataOutputStream ostream) {
		this.ostream = ostream;
	}
	
	public void run(){
		try{
			while(true){
				
				Odometer od = Odometer.instance;
				
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
				}
				Thread.sleep(20);
			}
			
		}catch(Exception e){}
	}
}