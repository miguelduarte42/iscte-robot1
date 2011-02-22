import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.*;


/**
 * UpdateDisplay. Updates the display with important information
 *
 */
public class UpdateDisplay implements Behavior {

	@Override
	public boolean takeControl() {
		return true;
	}
	
	@Override
	public void action() {
		final int COL1=8, COL2=12;
		LCD.clearDisplay();
		LCD.drawString("         MIN MAX", 0, 0);
		LCD.drawString("zspan:", 0, 3);
		//LCD.drawInt(zeroRangeMin,4,COL1,3);
		//LCD.drawInt(zeroRangeMax,4,COL2,3);
		//LCD.drawString("bias:" + offset + "   ", 0, 2);
	     
		while (!Button.ESCAPE.isPressed()) {
			//if (Button.RIGHT.isPressed()) resetExtents();
		    //if (Button.ENTER.isPressed()) setZeroHeading();
		    if (Button.LEFT.isPressed()) {
	        	LCD.drawString("zspan:", 0, 3);
	            LCD.drawString("---- ----", COL1,3);
	            //setZero();
				//LCD.drawInt(zeroRangeMin,4,COL1,3);
				//LCD.drawInt(zeroRangeMax,4,COL2,3);
				//LCD.drawString("bias:" + offset + "   ", 0, 2);
		    }
		    //LCD.drawString("nrml:" + readValue() + "   ", 0, 1);
		    //LCD.drawInt(minVal,4,COL1,1);
		    //LCD.drawInt(maxVal,4,COL2,1);
		    //LCD.drawString("raw:" + readRawValue() + "   ", 0, 4);
		    //LCD.drawString("tdelta:" + timeDelta + "  ", 0, 5);
		    //LCD.drawInt(maxTimeDeltaVal,4,COL2,5);
		     
		    //LCD.drawString("hdng:" + readHeading() + "          ",0,6);
		    Thread.yield();
		    //doWait(80);
		}
		LCD.drawString("Exiting ...    ", 0, 7);
		//doWait(2000);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

	

}
