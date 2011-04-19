import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

class DetectWall implements Behavior
{

  public DetectWall()
  {
    touch = new TouchSensor(SensorPort.S1);
    sonar = new UltrasonicSensor(SensorPort.S3);
  }

  public boolean takeControl()
  {
    sonar.ping();
    Sound.pause(20);
    return touch.isPressed() || sonar.getDistance() < 15;
  }

  public void suppress()
  {
    //Since  this is highest priority behavior, suppress will never be called.
  }

  public void action()
  {
    Motor.A.rotate(-180, true);// start Motor.A rotating backward
    Motor.C.rotate(-360);  // rotate C farther to make the turn
  }
  private TouchSensor touch;
  private UltrasonicSensor sonar;
}