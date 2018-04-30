import java.util.List;
import java.util.ArrayList;

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
	public static double G = 6.674 * Math.pow(10, -8);
	// Scales for the program window
	public static int xSCALE = 4000;
	public static int ySCALE = 4000;
	
	// Constructor
	public Universe() {
		bodies = new ArrayList<>();
	}
	
	// Methods
	/** Runs the universe. */
	public void run() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, xSCALE);
		StdDraw.setYscale(0, ySCALE);
		while(true) {
			step();
		}
	}
	
	/** Steps and draws each object in the bodies List. */
	public void step() {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(xSCALE/2, ySCALE/2, xSCALE/2);
		// Cycling through each body
		for(int i = 0; i < bodies.size(); i++) {
			Vector force = new Vector(0, 0);
			// Creating a total force vector for this body
			for(Body body : bodies) {
				if(body != bodies.get(i)) {
					force = force.add(calcVel(bodies.get(i), body));
					//bodies.get(i).
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
	
	public static void init() {
		boolean showForces = false;
		Point center = new Point(xSCALE/2, ySCALE/2);
		Universe universe = new Universe();
		
		Body sun = new Star(1989000000, new Vector(0, 0), center, showForces);
		Body venus = new Planet(4867, new Vector(0, 0), new Point(2210, 2000), showForces);
		Body earth = new Planet(5972000, new Vector(0, 0), new Point(2000, 1000), showForces);
		Body moon = new Star(73000, new Vector(0, 0), new Point(2000, 1030), showForces);
		Body jupiter = new Planet(1898000, new Vector(0, 0), new Point(2000, 3500), showForces);
		Body mars = new Planet(639, new Vector(0, 0), new Point(2000, 2450), showForces);
		Body saturn = new Planet(568300, new Vector(0, 0), new Point(2000, 3900), showForces);
		//venus.circOrbit(sun);
		earth.circOrbit(sun);
		moon.circOrbit(earth);
		jupiter.circOrbit(sun);
		mars.circOrbit(sun);
		saturn.circOrbit(sun);
		universe.add(sun);
		//universe.add(venus);
		universe.add(earth);
		universe.add(moon);
		//universe.add(mars);
	    //universe.add(jupiter);
	    //universe.add(saturn);
	    for(Body body : bodies) { System.out.println(body); }
		universe.run();
	}
	
	public static void main(String[] args) {
		init();
	}
}
