import java.util.List;
import java.util.ArrayList;

/**
 * A universe of bodies interacting with each other's
 * gravitational fields. Orbits and other interactions
 * are modeled.
 * 
 * @author Jonah Winchell and Nick Schneider
 * @version April 30, 2018
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
			Body b1 = new Star(), b2 = new Star();
			// Creating a total force vector and checking for collisions
			for(Body body : bodies) {
				if(body != bodies.get(i)) {
					force = force.add(calcVel(bodies.get(i), body));
					if(checkCollide(bodies.get(i), body)) {
						b1 = bodies.get(i);
						b2 = body;
						System.out.println(b1.getType() + " crashed into " + b2.getType());
					}
				}
			}
			// Collision handling
			boolean add = false;
			for(int e = bodies.size()-1; e >= 0; e--) { 
				if(bodies.get(e) == b1 || bodies.get(e) == b2) { 
					bodies.remove(e); 
					add = true;
				}
			}
			if(add) { add(collide(b1, b2)); for(Body body : bodies) { System.out.println(body); } }
			
			// Imparting net force vector onto the body
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
	
	/** Two objects have collided if the sum of their radii are greater than the distance between them. */
	public boolean checkCollide(Body b1, Body b2) {
		if(b1.radius() + b2.radius() > b1.loc().distanceTo(b2.loc())) { return true; }
		return false;
	}
	
	/** Combining the two bodies after a collision. */
	public Body collide(Body b1, Body b2) {
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
			return new Star(mass, vec, p, b1.show());
		} else { 
			return new Planet(mass, vec, p, b1.show());
		}
	}
	
	/** Adds a body to the Bodies List. */
	public void add(Body body) { bodies.add(body); }
	
	public static void init() {
		boolean showForces = false;
		Point center = new Point(xSCALE/2, ySCALE/2);
		Universe universe = new Universe();
		/*
		Body sun = new Star(1989000000, new Vector(0, 0), center, showForces);
		Body venus = new Planet(4867, new Vector(0, .4), new Point(2210, 2000), showForces);
		Body earth = new Planet(59720, new Vector(0, 0), new Point(2000, 1000), showForces);
		Body moon = new Planet(73, new Vector(0, 0), new Point(2000, 1030), showForces);
		Body jupiter = new Planet(1898000, new Vector(0, 0), new Point(2000, 3500), showForces);
		Body mars = new Planet(639, new Vector(0, 0), new Point(2000, 2450), showForces);
		Body saturn = new Planet(568300, new Vector(0, 0), new Point(2000, 3900), showForces);
		venus.circOrbit(sun);
		earth.circOrbit(sun);
		moon.circOrbit(earth, true);
		jupiter.circOrbit(sun);
		mars.circOrbit(sun);
		saturn.circOrbit(sun);
		universe.add(sun);
		universe.add(venus);
		universe.add(earth);
		universe.add(moon);
		universe.add(mars);
	    universe.add(jupiter);
	    universe.add(saturn);
	    */
		Body sun = new Star(1989000000, new Vector(0, 0), center, showForces);
		Body venus = new Planet(486700000, new Vector(0, 0), new Point(1000, 1800), showForces);
		venus.binOrbit(sun);
		universe.add(sun);
		universe.add(venus);
		
	    for(Body body : bodies) { System.out.println(body); }
		universe.run();
	}
	
	public static void main(String[] args) {
		init();
	}
}
