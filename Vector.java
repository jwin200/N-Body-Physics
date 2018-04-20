/**
 * An object to represent a scalar speed and
 * a direction measured in radians. Also calculates
 * separate x and y velocities.
 * 
 * @author Jonah Winchell & Nick Schneider
 * @version April 19, 2018
 */
public class Vector {
	// Fields
	// Direction in radians, 0 / tau is to the right
	private double speed, dir;
	private double xVel, yVel;
	
	// Getters / Setters
	public double getSpeed() { return speed; }
	public double getDir() { return dir; }
	public double xVel() { return xVel; }
	public double yVel() { return yVel; }
	
	// Constructors
	public Vector(double xVel, double yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
		this.speed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
		this.dir = Math.atan(yVel / xVel);
	}
	
	public Vector() {
		xVel = Math.random();
		yVel = Math.random();
		dir = Math.random();
		speed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	
	// Methods
	/** Performs vector addition between this vector and another given vector. */
	public Vector add(Vector vector) {
		double newXVel = this.xVel + vector.xVel();
		double newYVel = this.yVel + vector.yVel();
		
		return new Vector(newXVel, newYVel);
	}
	
	/** Displays information about a given vector. */
	public String toString() {
		return "{magnitude: " + speed + ", xVel: " + xVel + ", yVel: " + yVel + ", dir: " + dir + "}";
	}
	
	public static void main(String[] args) {
		Vector vec1 = new Vector(.5, .5);
		Vector vec2 = new Vector(.5, .7);
		System.out.println(vec1.add(vec2));
	}
}
