import lejos.nxt.ADSensorPort;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorConstants;

/**
* This class is designed to work with the HiTechnic Gyrosensor. It has methods to calculate
* the gyro offset, and find the gyro's angle and velocity. It has been modified by the
* original programmers Bent Bisballe Nyeng, Kasper Sohn and Johnny Rieper
* 
* @author Steven Jan Witzand
* @version August 2009
*/
public class GyroscopeSensor
{
   private ADSensorPort port;
   private double angle = 0.0;
   private int lastGetAngleTime = 0;
   private double lastOffset = 0;
   private final double a = 0.999999;// Weight of older offset values.
   
   /**
    * The GyroscopeSensor constructor.
    * 
    * @param port The NXT Sensor port the gyro sensor is connected to.
    */
   public GyroscopeSensor(ADSensorPort port)
   {
      this.port = port;
      
      this.port.setTypeAndMode(SensorConstants.TYPE_CUSTOM, SensorConstants.MODE_RAW);
      calcOffset();
   }

   /**
    * Calculates the offset specific to the HiTechnic gyro sensor. Needs to read the gyro
    * sensor in a stationary position.
    */
   public void calcOffset()
   {
      lastOffset = 0;
      double offsetTotal = 0;
      LCD.drawString("Calibrando Gyro", 0, 2);
      for (int i = 0; i < 50; i++) {
        offsetTotal += (double) port.readRawValue();
         try {
            Thread.sleep(4);
         } catch (InterruptedException e) {
         }
      }
      while (!Button.ENTER.isPressed()) {
         lastOffset = Math.ceil(offsetTotal / 50) + 1;
         LCD.drawString("Calibration OK", 0, 4);
         LCD.drawString("OFFSET: " + lastOffset, 2, 5);
         LCD.drawString("Push Enter!", 1, 6);
      }
      try {
         Thread.sleep(500);
      } catch (InterruptedException e) {
      }
   }

   /**
    * Gets the angle offset of the gyro. Uses a recursive filter to attempt to account for 
    * the gyro drift.
    * 
    * @return A double containing the current gyro offset value.
    */
   private double getAngleOffset()
   {
      double offset = lastOffset * a + (1.0 - a) * (double) port.readRawValue();
      lastOffset = offset;
      return offset;
   }

   /**
    * Get the angle velocity of the gyro sensor.
    * 
    * @return A double containing the angular velocity of the gyro sensor in degrees per 
    *         second
    */
   public double getAngleVelocity()
   {
      double offset = getAngleOffset();
      return (double) port.readRawValue() - offset;
   }

   /**
    * Get the calculated gyro angle (angular velocity integrated over time).
    * 
    * @return The angle in degrees.
    */
   public double getAngle()
   {
      int now = (int) System.currentTimeMillis();
      int delta_t = now - lastGetAngleTime;

      // Make sure we only add to the sum when there has actually
      // been a previous call (delta_t == now if its the first call).
      if (delta_t != now) {
         angle += getAngleVelocity() * ((double) delta_t / 1000.0);
      }
      lastGetAngleTime = now;

      return angle;
   }

   /**
    * Reset the gyro angle
    */
   public void resetGyro()
   {
      angle = 0.0;
      lastGetAngleTime = 0;
   }

}