import java.awt.Color;

/**
 * An object representative of celestial bodies
 * to exist in a universe. Stores data for mass,
 * velocity, size, and location.
 * 
 * @author Jonah Winchell & Nick Schneider
 * @version April 19, 2018
 */
public class Body {

	// Fields
	private double mass, speed, dir, radius;
	private Color color;
	private Point loc;
	private Vector vector;
	
	public double mass() { return mass; }
	public double speed() { return speed; }
	public double dir() { return dir; }
	public double radius() { return radius; }
	public Vector vector() { return vector; }
	public Point loc() { return loc; }
	
	// Constructors
	public Body(double mass, double speed, double dir, Color color, Point loc) {
		this.mass = mass;
		vector = new Vector(speed, dir);
		this.color = color;
		this.loc = loc;
	}
	
	public Body(double mass, Vector vector, Point loc) {
		this.mass = mass;
		this.vector = vector;
		color = StdDraw.BLACK;
		this.loc = loc;
	}
	
	// Methods
	/** Animates the body based on its vector. */
	public void step(Vector net) {
		vector = vector.add(net);
		loc = new Point(loc.x() + vector.xVel(), loc.y() + vector.yVel());
	}
	
	/** Draws this body. */
	public void draw() {
		StdDraw.setPenColor(color);
		double radius = Math.sqrt(Math.sqrt(mass)/Math.PI);
		StdDraw.filledCircle(loc.x(), loc.y(), radius);
	}
	
	public String toString() {
		return loc + " " + vector;
	}
}
