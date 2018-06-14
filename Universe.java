import java.util.List;
import java.util.ArrayList;

/**
 * A universe of bodies interacting with each other's
 * gravitational fields. Orbits and other interactions
 * are modeled.
 * 
 * @author Jonah Winchell and Nick Schneider
 * @version May 16, 2018
 */
public class Universe {
	// List of all bodies in the universe
	public static List<Body> bodies;
	// Newton's constant (exponent altered for scaling)
	public static double G = 6.674 * Math.pow(10, -8);
	// Scales for the program window
	public static int xSCALE, ySCALE;
	
	// Constructor
	public Universe() {
		bodies = new ArrayList<>();
		xSCALE = 4000;
		ySCALE = 4000;
	}
	
	// Methods
	/** Runs the universe. */
	public void run() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, xSCALE);
		StdDraw.setYscale(0, ySCALE);
		long time = 0, newTime = 0;
		double fps = 0;
		while(true) {
			time = System.currentTimeMillis();
			step((int)fps);
			newTime = System.currentTimeMillis();
			fps = 1000.0 / (newTime - time);
		}
	}
	
	/** Steps and draws each object in the bodies List. */
	public void step(int fps) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(xSCALE/2, ySCALE/2, xSCALE/2);
		
		// Cycling through each body
		for(int i = 0; i < bodies.size(); i++) {
			Body[] rocheBodies = new Body[2];
			Vector force = new Vector(0, 0);
			Body b1 = new Star(), b2 = new Star();
			// Creating a total force vector and checking for collisions
			for(Body body : bodies) {
				if(body != bodies.get(i)) {
					force = force.add(calcVel(bodies.get(i), body));
					if(checkCollide(bodies.get(i), body)) {
						b1 = bodies.get(i);
						b2 = body;
						//System.out.println(b1.getType() + " crashed into " + b2.getType());
					}
					// If a Roche Limit has been broken and masses aren't negligible, store the bodies for handling
					if(body.mass() > 1000 && bodies.get(i).rocheEncroach(body)) {
						rocheBodies[0] = bodies.get(i);
						rocheBodies[1] = body;
					}
				}
			}
			
			// Imparting net force vector onto the body
			bodies.get(i).step(force);
			
			// Collision handling
			boolean add = false;
			for(int e = bodies.size()-1; e >= 0; e--) { 
				if(bodies.get(e) == b1 || bodies.get(e) == b2) { 
					bodies.remove(e); 
					add = true;
				}
			}
			if(add) { add(collide(b1, b2)); }
			
			// Roche Limit handling
			if(rocheBodies[0] != null && bodies.size() < 5000) {
				rocheHandle(rocheBodies[0], rocheBodies[1]);
			}
		}
		// Frame rate
		StdDraw.setPenColor(StdDraw.YELLOW);
		if(fps == 0) { StdDraw.textRight(xSCALE-xSCALE/15, ySCALE-ySCALE/15, "<1 fps"); }
		else { StdDraw.textRight(xSCALE-xSCALE/15, ySCALE-ySCALE/15, fps + " fps"); }
		StdDraw.show();
	}
	
	/** Calculating the velocity change imparted by the gravity of two bodies. */
	public Vector calcVel(Body body1, Body body2) {
		double distance = body1.loc().distanceTo(body2.loc());
		double gravity = calcForce(body1.mass(), body2.mass(), distance) / body1.mass();
		if(body1.loc().x() > body2.loc().x()) { gravity = -gravity; } // This line fixed the code
		double direction = body1.loc().dirTo(body2.loc());
		double gravityX = Math.cos(direction) * gravity;
		double gravityY = Math.sin(direction) * gravity;
		return new Vector(gravityX, gravityY);
	}
	
	/** Calculating the force of gravity between two objects. */
	public double calcForce(double mass1, double mass2, double dist) {
		double num = G * mass1 * mass2;
		double denom = Math.pow(dist, 2);
		return num / denom;
	}
	
	/** Two objects have collided if the sum of their radii are greater than the distance between them. */
	public boolean checkCollide(Body b1, Body b2) {
		if(b1.radius() + b2.radius() > b1.loc().distanceTo(b2.loc())) { return true; }
		return false;
	}
	
	/** Combining the two bodies after a collision. */
	public Body collide(Body b1, Body b2) {
		boolean trace;
		if(b1.trace() || b2.trace()) { trace = true; }
		else { trace = false; }
		// Calculating new mass, location, and velocity
		double mass = b1.mass() + b2.mass(); // Mass of system
		// Location of new body weighted by mass
		double x = (b1.loc().x() * b1.mass() + b2.loc().x() * b2.mass()) / (mass);
		double y = (b1.loc().y() * b1.mass() + b2.loc().y() * b2.mass()) / (mass);
		Point p = new Point(x, y);
		// Velocities weighted by mass
		double xVel = (b1.vector().xVel() * b1.mass() + b2.vector().xVel() * b2.mass()) / (mass);
		double yVel = (b1.vector().yVel() * b1.mass() + b2.vector().yVel() * b2.mass()) / (mass);
		Vector vec = new Vector(xVel, yVel);
		// Creating the new body
		if(b1.getType().equals("Star") || b2.getType().equals("Star")) {
			return new Star(mass, vec, p, b1.show(), trace);
		} else { 
			return new Planet(mass, vec, p, b1.show(), trace);
		}
	}
	
	/** If a Roche Limit of a primary body is broken, the satellite is ripped apart into two new
	 * satellites.
	 */
	public void rocheHandle(Body prim, Body sat) {
		// General stats
		double dir = prim.loc().dirTo(sat.loc());
		double mass = sat.mass() / 2;
		Vector vector = sat.vector();
		boolean show = sat.show();
		double d1 = prim.loc().distanceTo(sat.loc()) - (1.1*sat.radius());
		double d2 = prim.loc().distanceTo(sat.loc()) + (1.1*sat.radius());
		// Derived locations of new bodies
		double x1 = prim.loc().x() + (Math.cos(dir) * d1);
		double x2 = prim.loc().x() + (Math.cos(dir) * d2);
		double y1 = prim.loc().y() + (Math.sin(dir) * d1);
		double y2 = prim.loc().y() + (Math.sin(dir) * d2);
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		// Creating new bodies and their orbits
		Body b1 = new Planet(mass, vector, p1, show);
		Body b2 = new Planet(mass, vector, p2, show);
		// Destroying old satellite and adding two new ones
		bodies.remove(sat);
		add(b1, b2);
	}
	
	/** Adds bodies to the universe. */
	public static void add(Body... bodyList) { for(Body body : bodyList) { bodies.add(body); } }
	
	/** Creates and initializes all bodies in the universe. Eventually used for file reading. */
	public static void init() {
		boolean showForces = false;
		Point center = new Point(xSCALE/2, ySCALE/2);
		Universe universe = new Universe();
		/*
		Body sun = new Star(1989000000, new Vector(0, 0), new Point(2000, 2000), showForces);
		Body venus = new Planet(4867, new Vector(0, 0), new Point(2150, 2000), showForces);
		Body earth = new Planet(9972, new Vector(0, 0), new Point(2207, 2000), showForces);
		Body moon = new Star(73, new Vector(0, 0), new Point(2200, 2000), showForces);
		Body jupiter = new Planet(1898000, new Vector(0, 0), new Point(2000, 2200), showForces);
		Body mars = new Planet(639, new Vector(0, 0), new Point(2000, 2150), showForces);
		Body saturn = new Planet(568300, new Vector(0, 0), new Point(2000, 3900), showForces);
		venus.circOrbit(sun);
		earth.circOrbit(sun);
		moon.circOrbit(earth);
		jupiter.circOrbit(sun);
		mars.circOrbit(sun);
		saturn.circOrbit(sun);
		add(sun, earth, mars, jupiter, saturn);
		*/
		Body sun = new Star(1989000000, new Vector(0, 0), new Point(1500, 2000), showForces);
		Body sun2 = new Star(1989000000, new Vector(0, 0), new Point(2500, 2000), showForces);
		Body planet = new Planet(189800, new Vector(0, 0), new Point(1500, 2200), showForces);
		Body planet2 = new Planet(189800, new Vector(0, 0), new Point(2500, 1800), showForces);
		Body planet3 = new Planet(198900000, new Vector(0.4, -0.2), new Point(800, 3900), showForces);
		Body planet4 = new Planet(18900000, new Vector(-0.45, 0), new Point(2000, 800), showForces);
		Body planet5 = new Planet(18900000, new Vector(0.5, -0.05), new Point(2000, 3000), showForces);
		sun2.binOrbit(sun);
		planet.circOrbit(sun);
		planet2.circOrbit(sun2);
		planet5.setTrace(true);
		//planet2.setTrace(true);
		planet4.setTrace(true);
		add(sun, sun2, planet, planet2, planet4, planet5);
		/*
		Body central = new Star(1900000000, new Vector(5, 5), new Point(xSCALE/2, ySCALE/2), showForces);
		System.out.println(central);
		add(central);
		for(int i = 0; i < 2000; i++) {
			Body b = new Planet(Math.random()*1000000+10000, new Vector(0, 0), new Point(xSCALE), showForces);
			//b.circOrbit(central);
			add(b);
		}
		
		Body sun = new Star(1099999999, new Vector(0, 0), new Point(2000, 2000), showForces);
		Body planet = new Planet(90000, new Vector(0.4, 0), new Point(2000, 1850), showForces);
		planet.circOrbit(sun);
		add(sun, planet);
		*/
	    //for(Body body : bodies) {System.out.println(body.getType() + " " + (body.radius()<body.rocheLimit(earth)) + " " + body.rocheLimit(earth)); }
		universe.run();
	}
	
	public static void main(String[] args) {
		init();
	}
}
