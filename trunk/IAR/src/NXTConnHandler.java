import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.USB;

public class NXTConnHandler extends Thread
{
	private boolean BT = true; 
	private DataInputStream istream;
	private DataOutputStream ostream;
	private boolean beingUsed = false;

	private int input;

	public NXTConnHandler() {
		start();
	}

	public void run() {
		NXTConnection conn;
		
		if (BT)	{System.out.println("Waiting for BT");conn = Bluetooth.waitForConnection();}
		else	{System.out.println("Waiting for USB");conn = USB.waitForConnection();}

		conn.setIOMode(0); // Used when a pc connection is made

		istream = conn.openDataInputStream();
		ostream = conn.openDataOutputStream();

		if (BT)	{System.out.println("BT connected!");}
		else	{System.out.println("USB connected!");}
		
		new NXTCommSender(ostream).start();

		input = 0;
		try{
			while (true){
				input = istream.readInt();
				CommandHandler.getInstance().execute(input);
			}
		}catch(Exception e){}
	}

	public void sendCommand(int command)
	{
		if(ostream != null){
			try {
				
				while(beingUsed);
				
				beingUsed = true;
				
				ostream.writeInt(command);
				ostream.flush();
				
				beingUsed = false;
			} catch (IOException e) {}
		}
	}
	
	public void sendCommands(int[] commands){
		if(ostream != null){
			try {
				
				while(beingUsed);
				
				beingUsed = true;
				
				for(int i = 0 ; i < commands.length; i++){
					ostream.writeInt(commands[i]);
					ostream.flush();
				}
				
				beingUsed = false;
			} catch (IOException e) {}
		}
	}
}

class NXTCommSender extends Thread{

	private DataOutputStream ostream;
	private Map map = Map.getInstance();
	private static int SEND_WAIT = 100;

	public NXTCommSender(DataOutputStream ostream) {
		this.ostream = ostream;
	}

	public void run(){
		/*
		try{
			while(true){

				Odometer od = Odometer.getInstance();

				if(od != null){
					//ostream.write(Map);
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
				Thread.sleep(SEND_WAIT);
			}

		}catch(Exception e){}*/
	}
}