package tests;

public class DifferentialDriveKinematics {
	
	private double x;
	private double y;
	private double orientation;
	private double distance_left_degree;
	private double distance_right_degree;
	private double distance_wheels;
	
	public DifferentialDriveKinematics(double leftWheelDiameter, double rightWheelDiameter, double distanceBetweenWheels) {
		this.x = 0;
		this.y = 0;
		this.orientation = 0;
		double radius_left = leftWheelDiameter/2;
		double radius_right = rightWheelDiameter/2;
		this.distance_left_degree = Math.PI * radius_left *2 /360;
		this.distance_right_degree = Math.PI * radius_right *2 /360;
		this.distance_wheels = distanceBetweenWheels;
	}
	
	public void nextStep(double angularSpeedOfLeftWheelInDegrees, double angularSpeedOfRightWheelInDegrees){		
		
		double speed_left = distance_left_degree * angularSpeedOfLeftWheelInDegrees;
		double speed_right = distance_right_degree * angularSpeedOfRightWheelInDegrees;
		
		this.orientation = this.orientation + 1/this.distance_wheels * (speed_right-speed_left) *0.1;
		this.x = this.x + (0.5*(speed_left+speed_right)*Math.cos(orientation)) *0.1;
		this.y = this.y + (0.5*(speed_left+speed_right)*Math.sin(orientation)) *0.1;
		
	}
	
	public double getX(){return x;}
	public double getY(){return y;}

}
