import lejos.nxt.LCD;


public class DisplayHandler extends Thread{
	
	private static int SLEEP_TIME = 100;
	
	public DisplayHandler() {
		start();
	}
	
	public void run(){
		
		double maxGyro = 0;
		double minGyro = 1024;
		
		try{
			while(true){
				GyroHandler gyro = GyroHandler.instance;
				GyroHandler gyro2 = GyroHandler.instance2;
				MotorHandler motor = MotorHandler.instance;
				
				if(gyro != null && motor != null && gyro2 != null){
					
					/*if(maxGyro < gyro.gyroRead)
						maxGyro = gyro.gyroRead;
					if(minGyro > gyro.gyroRead && gyro.gyroRead!=0)
						minGyro = gyro.gyroRead;*/
					
					//LCD.drawString("Gmin:"+minGyro, 0, 1); Thread.sleep(SLEEP_TIME);
					//LCD.drawString("Gmax:"+maxGyro, 0, 2); Thread.sleep(SLEEP_TIME);
					//LCD.drawString("PL:"+motor.powerLeft, 0, 1); Thread.sleep(SLEEP_TIME);
					//LCD.drawString("PR:"+motor.powerRight, 0, 2); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Gyro  : " + gyro.gyroRead + "  ", 0, 3); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Offset: " + gyro.offset + "  ", 0, 4); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Angle : " + gyro.angle + "  ", 0, 6); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Speed : " + gyro.speed + "  ", 0, 5); Thread.sleep(SLEEP_TIME);
					LCD.drawString("dTime : " + gyro.timeInterval * 1000 + "ms", 0, 7); Thread.sleep(SLEEP_TIME);
					
					LCD.drawString("Gyro2  : " + gyro2.gyroRead + "  ", 0, 3); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Offset2: " + gyro2.offset + "  ", 0, 4); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Angle2 : " + gyro2.angle + "  ", 0, 6); Thread.sleep(SLEEP_TIME);
					LCD.drawString("Speed2 : " + gyro2.speed + "  ", 0, 5); Thread.sleep(SLEEP_TIME);
					LCD.drawString("dTime2 : " + gyro2.timeInterval * 1000 + "ms", 0, 7); Thread.sleep(SLEEP_TIME);
					
				}
			}
		}catch(Exception e){}
	}

}
