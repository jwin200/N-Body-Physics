/**
 * An object representative of celestial bodies
 * to exist in a universe.
 * 
 * @author Jonah Winchell and Nick Schneider
 * @version May 2, 2018
 */
public abstract class Body {

	// Fields
	private double mass, speed, dir, radius;
	private Point loc;
	private Vector vector;
	private boolean showVector;
	private Body primaryBody;
	
	// Getters / Setters
	public double mass() { return mass; }
	public double dir() { return dir; }
	public double radius() { return radius; }
	public Point loc() { return loc; }
	public boolean show() { return showVector; }
	
	public double speed() { return speed; }
	public void setSpeed(double speed) { this.speed = speed; }
	
	public Vector vector() { return vector; }
	public void setVector(Vector vector) { this.vector = vector; }
	
	public Body primary() { return primaryBody; }
	public void setPrimary(Body primary) { this.primaryBody = primary; }
	
	// Constructors
	public Body(double mass, Vector vector, Point loc, boolean show) {
		this.mass = mass;
		this.vector = vector;
		this.loc = loc;
		this.radius = Math.sqrt(Math.sqrt(mass) / Math.PI);
		this.showVector = show;
	}
	
	public Body() {
		this.mass = Math.random() * 10000;
		this.vector = new Vector();
		this.loc = new Point();
		this.radius = Math.sqrt(Math.sqrt(mass) / Math.PI);
		this.showVector = false;
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
		primaryBody = b;
		// Direction normal to other body
		dir = Math.PI/2 - loc.dirTo(b.loc());
		// Distance between bodies
		double distanceTo = loc.distanceTo(b.loc());
		// Orbital velocity
		speed = Math.sqrt((Universe.G * (mass + b.mass())) / distanceTo);
		// Component x and y velocities
		double xVel = (Math.cos(dir) * speed) + b.vector().xVel();
		double yVel = (Math.sin(dir) * speed) + b.vector().yVel();
		vector = new Vector(xVel, yVel);
	}
	
	/** 
	 * Setting this body in an orbit with apoapsis at the primary
	 * body's sphere of influence radius. Mathematically guaranteed to 
	 * be a stable orbit when SOI is true. This is the highest stable
	 * orbit possible for these bodies.
	 */
	public void soiOrbit(Body b) {
		primaryBody = b;
		// Direction normal to other body
		dir = Math.PI/2 - loc.dirTo(b.loc());
		// Finding distance, setting new point at SOI radius
		double distanceTo = b.sphereOfInfluence();
		double theta = loc.dirTo(b.loc());
		double x = b.loc().x() + (Math.cos(theta) * distanceTo);
		double y = b.loc().y() + (Math.sin(theta) * distanceTo);
		loc = new Point(x, y);
		// Orbital velocity
		speed = Math.sqrt((Universe.G * (mass + b.mass())) / distanceTo);
		// Component x and y velocities
		double xVel = (Math.cos(dir) * speed) + b.vector().xVel();
		double yVel = (Math.sin(dir) * speed) + b.vector().yVel();
		vector = new Vector(xVel, yVel);
	}
	
	/** Sets this and one other body into a binary orbiting system. */
	public void binOrbit(Body b) {
		// Calculations for this body
		// Finding system's barycenter
		double netMass = mass + b.mass(); // net mass of system
		double a = loc.distanceTo(b.loc()); // distance between bodies
		double dist = a * (b.mass() / netMass); // distance to barycenter
		double theta = loc.dirTo(b.loc()); // angle between bodies
		dir = Math.PI/2 - theta;
		// Calculating orbital period
		double tNum = 4 * Math.pow(Math.PI, 2) * Math.pow(a,  3);
		double tDenom = Universe.G * (mass + b.mass);
		double t = Math.sqrt(tNum / tDenom);
		// Calculating orbital velocity
		speed = (2 * Math.PI * dist) / t;
		double xVel = Math.cos(dir) * speed;
		double yVel = Math.sin(dir) * speed;
		vector = new Vector(xVel, yVel); // setting new vector
		
		// Calculations for other body
		dist = a * (mass / netMass); // distance to barycenter
		theta = b.loc().dirTo(loc); // angle between bodies
		double speed = (2 * Math.PI * dist) / t; // orbital speed
		double dir = -(Math.PI/2 - theta);
		b.setSpeed(speed);
		xVel = Math.cos(dir) * speed;
		yVel = Math.sin(dir) * speed;
		b.setVector(new Vector(xVel, yVel)); // setting new vector
	}
	
	/** Returns the radius of the gravitational sphere of influence of a body. */
	public double sphereOfInfluence() {
		// Semi-major axis between orbiting and primary bodies
		double semiMajorAxis, massRatio;
		if(primaryBody != null) { 
			semiMajorAxis = loc.distanceTo(primaryBody.loc()); 
			massRatio = mass / primaryBody.mass(); 
		} else { 
			semiMajorAxis = 1.0; 
			massRatio = mass; 
		}
		return 0.9431 * semiMajorAxis * Math.pow(massRatio, 2.0/5);
	}
	
	/** Draws this body. */
	public abstract void draw(Vector vector);
	
	/** Returns the type of body. */
	public abstract String getType();
	
	/** Returns a String representation of this body. */
	public String toString() { return loc + " " + vector; }
}