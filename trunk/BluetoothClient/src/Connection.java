import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommBluecove;  
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo; 

public class Connection { 
	private boolean BT = true;

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
		NXTConnector conn = new NXTConnector();
		NXTComm com; 

		if (BT){
			try{
				com = new NXTCommBluecove();
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
		else {
			// TODO: for MAC USB use NXTCommFantom
			//com = new NXTCommLibnxt();
			if (!conn.connectTo("usb://"))//nxtCtrl, lejos.pc.comm.NXTComm.PACKET))//))
			{ 
				System.err.println("No NXT find using USB");
				System.exit(1);	}
			else{
				output = new DataOutputStream(conn.getOutputStream());
				input = new DataInputStream(conn.getInputStream());
				receiver = new DataReceiver(input);
				receiver.start();
				connected = true;
			}
		}
	}

	public class DataReceiver extends Thread {

		private DataInputStream inputStream;

		public DataReceiver(DataInputStream inputStream) {
			this.inputStream = inputStream;
		}

		public void run() {
			try{
				
				//RobotWindow rw = new RobotWindow();
				MapWindow mw = new MapWindow();
				double r1 = 0;
				double r2 = 0;
				double r3 = 0;
				long r4 = 0;
				double r5 = 0;
				double r6 = 0;
				double r7 = 0;
				double r8 = 0;
				
				while(true){
					int x = inputStream.readInt();
					int y = inputStream.readInt();
					int status = inputStream.readInt();
					if(status == 0)
						Map.getInstance().markEmpty(x, y);
					else
						Map.getInstance().markOccuppied(x, y);
					System.out.println("x: "+x+" y: "+y+" status: "+status);
					
					/*r1 = inputStream.readDouble();
					r2 = inputStream.readDouble();
					r3 = inputStream.readDouble();
					r4 = inputStream.readLong();
					r5 = inputStream.readDouble();
					r6 = inputStream.readDouble();
					r7 = inputStream.readDouble();
					r8 = inputStream.readDouble();
					
					System.out.println("x: " + r1 + " y: " + r2 + " o: " + r3 +" t: "+r4+" lt: "+r5+" rt: "+r6 +" sl: "+r7+" sr: "+r8);
					rw.step(r1, r2);*/
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("InputStream died");
			}
		}
	}
	
	public void stop(){
		try {
			output.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}