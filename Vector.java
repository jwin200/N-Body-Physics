/**
 * An object to represent a scalar speed and
 * a direction measured in radians. Also calculates
 * separate x and y velocities.
 * 
 * @author Jonah Winchell
 * @version April 18, 2018
 */
public class Vector {
	// Fields
	private double speed, dir;
	private double xVel, yVel;
	
	// Getters / Setters
	public double getSpeed() { return speed; }
	public void setSpeed(double speed) { 
		this.speed = speed; 
		this.xVel = Math.cos(dir) * speed;
		this.yVel = Math.sin(dir) * speed;
	}
	
	public double getDir() { return dir; }
	public void setDir(double dir) { 
		this.dir = dir;
		this.xVel = Math.cos(dir) * speed;
		this.yVel = Math.sin(dir) * speed;
	}
	
	public double xVel() { return xVel; }
	public double yVel() { return yVel; }
	
	// Constructors
	public Vector(double speed, double dir) {
		this.speed = speed;
		this.dir = dir;
		this.xVel = Math.cos(dir) * speed;
		this.yVel = Math.sin(dir) * speed;
	}
	
	public Vector() {
		xVel = Math.random();
		yVel = Math.random();
		dir = Math.random() * 2 * Math.PI;
		speed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	
	// Methods
	/** Performs vector addition between this vector and another given vector. */
	public Vector add(Vector vector) {
		double newXVel = this.xVel + vector.xVel();
		double newYVel = this.yVel + vector.yVel();
		double newSpeed = Math.sqrt(Math.pow(newXVel, 2) + Math.pow(newYVel, 2));
		double newDir = Math.asin(newYVel / newSpeed);
		
		return new Vector(newSpeed, newDir);
	}
}
