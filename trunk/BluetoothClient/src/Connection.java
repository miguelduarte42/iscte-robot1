import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import lejos.pc.comm.NXTCommBluecove;  
import lejos.pc.comm.NXTInfo; 

public class Connection { 

   public static void main(String[] args){ 
      new Connection();
   }
   
   public Connection() {
	  NXTInfo nxtCtrl = new NXTInfo(); 
      nxtCtrl.deviceAddress = "00:16:53:0e:4d:69"; 
        
      NXTCommBluecove com = new NXTCommBluecove(); 
        
      try{
         com.open(nxtCtrl); 
         System.out.println("Connection established!");
         
         DataOutputStream output = new DataOutputStream(com.getOutputStream());
         DataInputStream input = new DataInputStream(com.getInputStream());
         
         DataReceiver receiver = new DataReceiver(input);
         receiver.start();
         
         Scanner scanner = new Scanner(System.in);
         
         while(true){
        	 System.out.println("Escreva coisas:");
        	 String toSend = scanner.nextLine();
        	 output.writeBytes(toSend+"\n");
        	 output.flush();
         }
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
				while(true){
					String received = inputStream.readLine();
					System.out.println("Received:"+received);
				}
			}catch(Exception e){
				System.out.println("InputStream died");
			}
		}
	}
}