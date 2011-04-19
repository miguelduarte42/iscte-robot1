import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BluetoothHandler extends Thread
{
	private DataInputStream istream;
	private DataOutputStream ostream;

	private int input;

	public BluetoothHandler() {
		start();
	}

	public void run() {

		System.out.println("Waiting for BT");

		BTConnection conn = Bluetooth.waitForConnection();
		conn.setIOMode(0); // Used when a pc connection is made

		istream = conn.openDataInputStream();
		ostream = conn.openDataOutputStream();

		System.out.println("BT connected!");

		new BluetoothSender(ostream).start();

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
				ostream.writeInt(command);
				ostream.flush();
			} catch (IOException e) {}
		}
	}
}

class BluetoothSender extends Thread{

	private DataOutputStream ostream;
	private Map map = Map.getInstance();
	private static int SEND_WAIT = 100;

	public BluetoothSender(DataOutputStream ostream) {
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