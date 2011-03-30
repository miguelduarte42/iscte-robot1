
public class Odometer {
	
	public double x = 0;
	public double y = 0;
	public double orientation = 0;
	private double distance_wheels = 1.8;
	private double distance_left_degree;
	private double distance_right_degree;
	private static double leftWheelDiameter = 0.4;
	private static double rightWheelDiameter = 0.4;
	private static double radius_right = rightWheelDiameter/2;
	private static double radius_left = leftWheelDiameter/2;
	
	public double prevLeftTacho = 0;
	public double prevRightTacho = 0;
	
	public double sL = 0;
	public double sR = 0;
	
	private long prevTime = 0;
	
	public static Odometer instance;
	
	public Odometer() {
		this.distance_left_degree = Math.PI * radius_left *2 /360;
		this.distance_right_degree = Math.PI * radius_right *2 /360;
		instance = this;
	}
	
	public void nextTacho(double leftTacho, double rightTacho) {
		
		double timePassed = 0;
		
		if(prevTime != 0){
			
			timePassed = (System.currentTimeMillis() - prevTime)/1000;
			
			double speed_left = distance_left_degree * (leftTacho-prevLeftTacho);
			double speed_right = distance_right_degree * (rightTacho-prevRightTacho);
			
			this.orientation = this.orientation + 1/this.distance_wheels * (speed_right-speed_left) *timePassed;
			this.x = this.x + (0.5*(speed_left+speed_right)*Math.cos(orientation)) *timePassed;
			this.y = this.y + (0.5*(speed_left+speed_right)*Math.sin(orientation)) *timePassed;
			
			prevLeftTacho = leftTacho;
			prevRightTacho = rightTacho;
			
			/*long currTime = System.currentTimeMillis();
			
			double diffTime = (currTime - prevTime) / 1000.0;
		
			double leftDiff = leftTacho - prevLeftTacho;
			double rightDiff = rightTacho - prevRightTacho;
		
			
			prevLeftTacho = leftTacho;
			prevRightTacho = rightTacho;
			
			double speed_left = distance_left_degree * leftDiff *diffTime;
			double speed_right = distance_right_degree * rightDiff *diffTime;
			
			sL = speed_left;
			sR = speed_right;
			
			this.orientation = this.orientation + 1/this.distance_wheels * (speed_right+speed_left) * diffTime;
			this.x = this.x + (0.5*(speed_left+speed_right)*Math.cos(orientation)) *diffTime;
			this.y = this.y + (0.5*(speed_left+speed_right)*Math.sin(orientation)) *diffTime;
			
			prevTime = currTime;*/
			
		}else
			prevTime = System.currentTimeMillis();
		
		
		/*this.orientation = this.orientation + 1/this.distance_wheels * (leftDiff-rightDiff);
		this.x = this.x + (0.5*(leftDiff+rightDiff)*Math.cos(orientation));
		this.y = this.y + (0.5*(leftDiff+rightDiff)*Math.sin(orientation));*/
	}
	
	public void nextStep(double angularSpeedOfLeftWheelInDegrees, double angularSpeedOfRightWheelInDegrees, double timePassed){		
		
		timePassed /= 1000;
		
		double speed_left = distance_left_degree * angularSpeedOfLeftWheelInDegrees;
		double speed_right = distance_right_degree * angularSpeedOfRightWheelInDegrees;
		
		this.orientation = this.orientation + 1/this.distance_wheels * (speed_right-speed_left) *timePassed;
		this.x = this.x + (0.5*(speed_left+speed_right)*Math.cos(orientation)) *timePassed;
		this.y = this.y + (0.5*(speed_left+speed_right)*Math.sin(orientation)) *timePassed;
		
	}

}
