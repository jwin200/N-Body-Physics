import java.util.*;

/**
 * A universe of bodies interacting with each other's
 * gravitational fields. Orbits and other interactions
 * are modeled.
 * 
 * @author Jonah Winchell & Nick Schneider
 * @version April 19, 2018
 */
public class Universe {
	// List of all bodies in the universe
	public static List<Body> bodies;
	// Newton's constant (exponent altered for scaling)
	public static final double G = 6.674 * Math.pow(10, -8);
	// Scales for the program window
	public static final int XSCALE = 1000;
	public static final int YSCALE = 1000;
	
	// Constructor
	public Universe() {
		bodies = new ArrayList<>();
	}
	
	// Methods
	/** Runs the universe. */
	public void run() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, XSCALE);
		StdDraw.setYscale(0, YSCALE);
		while(true) {
			step();
			
			// Debugging
			//System.out.println(bodies.get(0));
			//System.out.println(bodies.get(1));
		}
	}
	
	/** Steps and draws each object in the bodies List. */
	public void step() {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(XSCALE/2, YSCALE/2, XSCALE/2);
		// Cycling through each body
		for(int i = 0; i < bodies.size(); i++) {
			Vector force = new Vector(0, 0);
			// Creating a total force vector for this body
			for(Body body : bodies) {
				if(body != bodies.get(i)) {
					force = force.add(calcVel(bodies.get(i), body));
				}
			}
			// Imparting that force vector onto the body
			bodies.get(i).step(force);
		}
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
	
	/** Adds a body to the Bodies List. */
	public void add(Body body) { bodies.add(body); }
	
	public static void main(String[] args) {
		boolean showForces = true;
		Universe universe = new Universe();
		Body sun = new Star(10000000, new Vector(0, 0), new Point(200, 500), showForces);
		Body sun2 = new Star(10000000, new Vector(0, 0.03), new Point(800, 500), showForces);
		Body earth = new Planet(500000, new Vector(0, 0.02), new Point(500, 150), showForces);
		Body earth2 = new Planet(500000, new Vector(-.02, 0), new Point(900, 950), showForces);
		Body planet = new Planet(100000, new Vector(-.02, 0), new Point(500, 750), showForces);
		earth.circOrbit(sun);
		planet.circOrbit(sun);
		sun.binOrbit(sun2);
		
		universe.add(earth);
		universe.add(planet);
	    universe.add(sun);
	    universe.add(sun2);
	    universe.add(earth2);
	    for(Body body : bodies) { System.out.println(body); }
		 
		 universe.run();
	}
}
