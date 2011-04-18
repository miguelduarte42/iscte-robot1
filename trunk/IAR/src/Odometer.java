
public class Odometer {
	
	public double x = 0;
	public double y = 0;
	public double orientation = 0;
	private double distance_wheels = 18;
	private double distance_left_degree;
	private double distance_right_degree;
	private static double leftWheelDiameter = 4;
	private static double rightWheelDiameter = 4;
	private static double radius_right = rightWheelDiameter/2;
	private static double radius_left = leftWheelDiameter/2;
	
	public double prevLeftTacho = 0;
	public double prevRightTacho = 0;
	
	public double sL = 0;
	public double sR = 0;
	
	private long prevTime = 0;
	
	private static Odometer INSTANCE;
	
	private Odometer() {
		this.distance_left_degree = Math.PI * radius_left *2 /360;
		this.distance_right_degree = Math.PI * radius_right *2 /360;
	}
	
	public static Odometer getInstance(){
		if(INSTANCE == null)
			INSTANCE = new Odometer();
		return INSTANCE;
	}
	
	public void nextTacho(double leftTacho, double rightTacho) {
		
		double timePassed = 0;
		
		if(prevTime != 0){
			
			timePassed = (System.currentTimeMillis() - prevTime)/10000.0;
			
			double speed_left = distance_left_degree * (leftTacho-prevLeftTacho);
			double speed_right = distance_right_degree * (rightTacho-prevRightTacho);
			
			this.orientation = this.orientation + 1/this.distance_wheels * (speed_right-speed_left) *timePassed;
			this.x = this.x + (0.5*(speed_left+speed_right)*Math.cos(orientation)) *timePassed;
			this.y = this.y + (0.5*(speed_left+speed_right)*Math.sin(orientation)) *timePassed;
			
			prevLeftTacho = leftTacho;
			prevRightTacho = rightTacho;
			
		}else
			prevTime = System.currentTimeMillis();
	}

}
