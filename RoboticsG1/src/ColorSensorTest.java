import lejos.nxt.ColorLightSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.robotics.Colors.Color;
import lejos.util.Delay;


public class ColorSensorTest {
	
	private ColorLightSensor color_sensor;
	
	public void readColors(){
		
		color_sensor = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORFULL);
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
		
		int lightValue; 
		
		while(i != 0){
			color_sensor.setFloodlight(Color.RED);
			color_sensor.setFloodlight(Color.BLUE);
			color_sensor.setFloodlight(Color.GREEN);
			color_sensor.setFloodlight(Color.WHITE);
			lightValue = color_sensor.getLightValue();
			LCD.drawString("Waiting for BT",0,0);
			LCD.drawString("Light Value: " + lightValue, 0, 2);
			LCD.drawString("Red:   " + color_sensor.getRedComponent(), 0, 3);
			LCD.drawString("Green: " + color_sensor.getGreenComponent(), 0, 4);
			LCD.drawString("Blue:  " + color_sensor.getBlueComponent(), 0, 5);
			i--;
		}
		
		
	}
	
	public static void main(String [] args){
		System.out.print("Start Program");
		ColorSensorTest c = new ColorSensorTest();
		c.readColors();
	}

}
