import java.util.*;

public class Universe {
	public static List<Body> bodies;
	public static final double G = 6.674 * Math.pow(10, -7);
	
	// Constructor
	public Universe() {
		bodies = new ArrayList<>();
	}
	
	// Methods
	/** Runs the universe. */
	public void run() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 100);
		StdDraw.setYscale(0, 100);
		while(true) {
			draw();
			step();
			System.out.println(bodies.get(1).vector());
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
			List<Vector> forces = new ArrayList<>();
			Vector force = new Vector(0, 0);
			// Creating a list of individual forces acting upon this body
			for(Body body : bodies) {
				if(body != bodies.get(i)) {
					double gravity = -calcForce(bodies.get(i).mass(), body.mass(), body.loc().distanceTo(bodies.get(i).loc())) / bodies.get(i).mass();
					double direction = body.loc().dirTo(bodies.get(i).loc());
					forces.add(new Vector(gravity, direction));
				}
			}
			// Creating a total vector force for this body
			for(Vector vector : forces) { force = force.add(vector); }
			bodies.get(i).step(force);
		}
	}
	
	/** Calculating the force of gravity between two objects. */
	public double calcForce(double mass1, double mass2, double dist) {
		double num = G * mass1 * mass2;
		double denom = Math.pow(dist, 2);
		return num / denom;
	}
	
	public void add(Body body) { bodies.add(body); }
	
	public static void main(String[] args) {
		Universe universe = new Universe();
		 universe.add(new Body(100, 0.01, Math.PI, StdDraw.BLACK, new Point(55, 80)));
		 universe.add(new Body(10000, 0.000000001, 0, StdDraw.MAGENTA, new Point(50, 50)));
		 universe.run();
	}
}
