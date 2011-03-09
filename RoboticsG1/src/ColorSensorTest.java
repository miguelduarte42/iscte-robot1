import java.net.ServerSocket;

import lejos.nxt.ColorLightSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.Colors.Color;
import lejos.util.Delay;


public class ColorSensorTest {
	
	private ColorLightSensor color_sensor;
	
	public void readColors(){
		
		color_sensor = new ColorLightSensor(SensorPort.S1, ColorLightSensor.TYPE_COLORFULL);
		int i = 10000;
		
		color_sensor.setFloodlight(Color.WHITE);
		color_sensor.calibrateHigh();
		Delay.msDelay(10);

		color_sensor.setFloodlight(Color.BLACK);
		color_sensor.calibrateLow();
		
		color_sensor.setFloodlight(Color.RED);
		color_sensor.setFloodlight(Color.BLUE);
		color_sensor.setFloodlight(Color.GREEN);
		color_sensor.setFloodlight(Color.WHITE);
		
		
		while(i != 0){
		
			int lightValue = color_sensor.getLightValue();
			LCD.drawString("Light VAlue: " + lightValue, 0, 0, 0);
			i--;
		}
		
		
	}
	
	public static void main(String [] args){
		
		ColorSensorTest c = new ColorSensorTest();
		c.readColors();
	}

}
