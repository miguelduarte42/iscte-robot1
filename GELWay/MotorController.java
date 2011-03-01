/* -*- tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- */
import lejos.nxt.*;

/**
 * A class used to handle controlling the motors. Has methods to set the motors speed and
 * get the motors angle and velocity. Based off original programmers of Marvin Bent Bisballe
 * Nyeng, Kasper Sohn and Johnny Rieper
 * 
 * @author Steven Jan Witzand
 * @version August 2009
 */
class MotorController
{

   private Motor leftMotor;
   private Motor rightMotor;
   // Sinusoidal parameters used to smooth motors
   private double sin_x = 0.0;
   private final double sin_speed = 0.1;
   private final double sin_amp = 20.0;

   /**
    * MotorController constructor.
    * 
    * @param leftMotor
    *           The GELways left motor.
    * @param rightMotor
    *           The GELways right motor.
    */
   public MotorController(Motor leftMotor, Motor rightMotor)
   {
      this.leftMotor = leftMotor;
      this.leftMotor.resetTachoCount();

      this.rightMotor = rightMotor;
      this.rightMotor.resetTachoCount();
   }

   /**
    * Method is used to set the power level to the motors required to keep it upright. A
    * dampened sinusoidal curve is applied to the motors to reduce the rotation of the
    * motors over time from moving forwards and backwards constantly.
    * 
    * @param leftPower
    *           A double used to set the power of the left motor. Maximum value depends on
    *           battery level but is approximately 815. A negative value results in motors
    *           reversing.
    * @param rightPower
    *           A double used to set the power of the right motor. Maximum value depends on
    *           battery level but is approximately 815. A negative value results in motors
    *           reversing.
    */
   public void setPower(double leftPower, double rightPower)
   {
      sin_x += sin_speed;
      int pwl = (int) (leftPower + Math.sin(sin_x) * sin_amp);
      int pwr = (int) (rightPower - Math.sin(sin_x) * sin_amp);

      leftMotor.setSpeed(pwl);
      if (pwl < 0) {
         leftMotor.backward();
      } else if (pwl > 0) {
         leftMotor.forward();
      } else {
         leftMotor.stop();
      }

      rightMotor.setSpeed(pwr);
      if (pwr < 0) {
         rightMotor.backward();
      } else if (pwr > 0) {
         rightMotor.forward();
      } else {
         rightMotor.stop();
      }
   }

   /**
    * getAngle returns the average motor angle of the left and right motors
    * 
    * @return A double of the average motor angle of the left and right motors in degrees.
    */
   public double getAngle()
   {
      return ((double) leftMotor.getTachoCount() + 
            (double) rightMotor.getTachoCount()) / 2.0;
   }

   /**
    * getAngle returns the average motor velocity of the left and right motors
    * 
    * @return a double of the average motor velocity of the left and right motors in
    *         degrees.
    */
   public double getAngleVelocity()
   {
      return ((double) leftMotor.getSpeed() + 
            (double) rightMotor.getSpeed()) / 2.0;
   }

   /**
    * reset the motors tacho count
    */
   public void resetMotors()
   {
      leftMotor.resetTachoCount();
      rightMotor.resetTachoCount();
   }

   /**
    * stop both motors from rotating
    */
   public void stop()
   {
      leftMotor.stop();
      rightMotor.stop();
   }
}