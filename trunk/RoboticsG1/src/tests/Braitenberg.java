package tests;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class Braitenberg {
	
	
	public static void main(String[] args) {
		
		Motor m1 = Motor.A;
        Motor m2 = Motor.C;
		LightSensor lightSensor1 = new LightSensor ( SensorPort.S1); 
		LightSensor lightSensor2 = new LightSensor ( SensorPort.S4);
		
		TouchSensor touch = new TouchSensor(SensorPort.S2);
		
	    m1.setSpeed(360);
        m2.setSpeed(360);
        
		//lightSensor1 .setFloodlight(false);
		//lightSensor2 .setFloodlight(false);
        
        boolean marcha_atras = false;
        
		long tempo_inicial = System.currentTimeMillis();
		while (true){
			
			int normalizedValue1 = lightSensor1.readNormalizedValue();
			int normalizedValue2 = lightSensor2.readNormalizedValue();
		     
		     System.out.println("VALUE 1: " + normalizedValue1);
		     System.out.println("VALUE 2: " + normalizedValue2);
		     
		     if(!marcha_atras){
		    	 
				 m1.setSpeed((normalizedValue1));
			     m2.setSpeed((normalizedValue2));
		     
		     
			     m1.forward();
			     m2.forward();
		     }
			if(touch.isPressed() || marcha_atras){
				
				if(touch.isPressed()){
					tempo_inicial = System.currentTimeMillis();
					marcha_atras = true;
				}else{
					marcha_atras = System.currentTimeMillis() - tempo_inicial < 2000;
				}
				
				m1.setSpeed(150);
				m2.setSpeed(360);
				m1.backward();
				m2.backward();
				
			}
			
		}
		
		
	}

}
