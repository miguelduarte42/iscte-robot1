import lejos.nxt.ColorLightSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.robotics.Colors.Color;


public class ColorSensorTest {
	
	private ColorLightSensor color_sensor;
	
	public void readColors(){
		
		color_sensor = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORRED);
		color_sensor.calibrateHigh();
		color_sensor.setFloodlight(Color.RED);
		
		int lightValue; 
		
		while(true){
			lightValue = color_sensor.readValue();
			
			LCD.drawString("Light Value: " + lightValue, 0, 2);
			//LCD.drawString("Red:   " + color_sensor.getRedComponent(), 0, 3);
			//LCD.drawString("Green: " + color_sensor.getGreenComponent(), 0, 4);
			//LCD.drawString("Blue:  " + color_sensor.getBlueComponent(), 0, 5);
		}
		
		
	}
	
	public static void main(String [] args){
		System.out.print("Start Program");
		ColorSensorTest c = new ColorSensorTest();
		c.readColors();
	}

}
