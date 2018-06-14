/**
 * A body to represent planets in a universe. Includes
 * draw, getType, and toString methods.
 * @author Jonah Winchell and Nick Schneider
 * @version May 2, 2018
 */
public class Planet extends Body {
	
	public Planet(double mass, Vector vector, Point loc, boolean showVector) {
		super(mass, vector, loc, showVector);
	}
	
	public Planet(double mass, Vector vector, Point loc, boolean showVector, boolean trace) {
		super(mass, vector, loc, showVector, trace);
	}
	
	public Planet() { super(); }
	
	public void draw(Vector force) {
		if(super.show()) {
			// Drawing the force on this body
			StdDraw.setPenColor(StdDraw.RED);
			double newX = super.loc().x() + (force.xVel() * 100000);
			double newY = super.loc().y() + (force.yVel() * 100000);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
			// Drawing the body's current vector
			StdDraw.setPenColor(StdDraw.BLUE);
			newX = super.loc().x() + (super.vector().xVel() * 100);
			newY = super.loc().y() + (super.vector().yVel() * 100);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
		}
		if(super.trace()) {
			// Drawing the body's trail
			StdDraw.setPenColor(StdDraw.GREEN);
			for(Point p : super.tracedPoints()) {
				StdDraw.point(p.x(), p.y());
			}
		}
		// Drawing the body
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(super.loc().x(), super.loc().y(), super.radius());
	}
	
	public String getType() { return "Planet"; }
	
	public String toString() {
		return "Planet: " + super.toString();
	}
}
