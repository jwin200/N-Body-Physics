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
	public static final int XSCALE = 200;
	public static final int YSCALE = 200;
	
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
			draw();
			step();
			
			// Debugging
			//System.out.println(bodies.get(0));
			//System.out.println(bodies.get(1));
		}
	}
	
	/** Draws each object in the bodies List. */
	public void draw() {
		StdDraw.clear();
		for(Body body : bodies) {
			body.draw();
		}
		StdDraw.show();
	}
	
	/** Steps each object in the bodies List. */
	public void step() {
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
		Universe universe = new Universe();
		 universe.add(new Body(100, new Vector(0, .01), new Point(50, 100)));
		 universe.add(new Body(100, new Vector(0, -.01), new Point(150, 100)));
		 universe.add(new Body(1000, new Vector(-.01, 0), new Point(100, 50)));
		 universe.add(new Body(100000, new Vector(0, 0), new Point(100, 100)));
		 
		 universe.run();
	}
}
