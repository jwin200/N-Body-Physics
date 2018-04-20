/**
 * An object representative of celestial bodies
 * to exist in a universe. Stores data for mass,
 * velocity, size, and location.
 * 
 * @author Jonah Winchell & Nick Schneider
 * @version April 19, 2018
 */
public abstract class Body {

	// Fields
	public static final double G = 6.674 * Math.pow(10, -8);
	private double mass, speed, dir, radius;
	private Point loc;
	private Vector vector;
	private boolean showVector;
	
	// Getters / Setters
	public double mass() { return mass; }
	public double speed() { return speed; }
	public double dir() { return dir; }
	public double radius() { return radius; }
	public Vector vector() { return vector; }
	public void setVector(Vector vector) { this.vector = vector; }
	public Point loc() { return loc; }
	public boolean show() { return showVector; }
	
	// Constructors
	public Body(double mass, double speed, double dir, Point loc, boolean show) {
		this.mass = mass;
		this.vector = new Vector(speed, dir);
		this.loc = loc;
		this.radius = Math.sqrt(Math.sqrt(mass) / Math.PI);
		this.showVector = show;
	}
	
	public Body(double mass, Vector vector, Point loc, boolean show) {
		this.mass = mass;
		this.vector = vector;
		this.loc = loc;
		this.radius = Math.sqrt(Math.sqrt(mass) / Math.PI);
		this.showVector = show;
	}
	
	// Methods
	/** Animates the body based on its vector. */
	public void step(Vector net) {
		draw(net);
		vector = vector.add(net);
		loc = new Point(loc.x() + vector.xVel(), loc.y() + vector.yVel());
	}
	
	/** Puts this body into a circular orbit of another given body. */
	public void circOrbit(Body b) {
		// Direction normal to other body
		dir = Math.PI/2 - loc.dirTo(b.loc());
		// Distance between bodies
		double distanceTo = loc.distanceTo(b.loc());
		// Orbital velocity
		speed = Math.sqrt((G * (mass + b.mass())) / distanceTo) + b.speed();
		// Component x and y velocities
		double xVel = Math.cos(dir) * speed;
		double yVel = Math.sin(dir) * speed;
		vector = new Vector(xVel, yVel);
	}
	
	// not yet working
	/** Used if the body of orbit is orbiting another body itself. */
	public void circOrbit(Body b1, Body b2) {
		// Directions normal to other bodies
		dir = Math.PI/2 - loc.dirTo(b1.loc());
		// Distance between bodies
		double distanceToOne = loc.distanceTo(b1.loc());
		double distanceToTwo = loc.distanceTo(b2.loc());
		// Orbital velocity
		double orbVelMinor = Math.sqrt((G * (mass + b1.mass())) / distanceToOne);
		double orbVelMajor = Math.sqrt((G * (mass + b2.mass())) / distanceToTwo);
		speed =  (orbVelMinor + orbVelMajor);
		// Component x and y velocities
		double xVel = Math.cos(dir) * speed;
		double yVel = Math.sin(dir) * speed;
		vector = new Vector(xVel, yVel);
	}
	
	// not yet working
	/** Creates an orbit around a point in space given a total system mass. */
	public void circOrbit(Point p, double m) {
		dir = Math.PI/2 - loc.dirTo(p);
		double distanceTo = loc.distanceTo(p);
		speed = Math.sqrt((G * m) / distanceTo);
		double xVel = Math.cos(dir) * speed;
		double yVel = Math.sin(dir) * speed;
		vector = new Vector(xVel, yVel);
	}
	
	// not yet working
	/** Sets this and one other body into a binary orbiting system. */
	public void binOrbit(Body b) {
		double m = mass + b.mass();
		// Coordinates weighted by bodies' mass to find system's center of mass
		double xCent = ((loc.x() * mass) + (b.loc().x() * b.mass())) / (2*m);
		double yCent = ((loc.y() * mass) + (b.loc().y() * b.mass())) / (2*m);
		Point center = new Point(xCent, yCent);
		
		circOrbit(center, m);
		b.circOrbit(center, m);
	}
	
	/** Draws this body. */
	public abstract void draw(Vector vector);
	
	/** Returns a String representation of this body. */
	public String toString() { return loc + " " + vector; }
}