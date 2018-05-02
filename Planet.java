/**
 * An body to represent planets in a universe. Includes
 * draw, getType, and toString methods.
 * @author Jonah Winchell and Nick Schneider
 * @version May 2, 2018
 */
public class Planet extends Body {
	
	public Planet(double mass, Vector vector, Point loc, boolean showVector) {
		super(mass, vector, loc, showVector);
	}
	
	public Planet() { super(); }
	
	public void draw(Vector force) {
		if(super.show()) {
			// Drawing the force on this body
			StdDraw.setPenColor(StdDraw.RED);
			double newX = super.loc().x() + (force.xVel() * 1000000);
			double newY = super.loc().y() + (force.yVel() * 1000000);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
			// Drawing the body's current vector
			StdDraw.setPenColor(StdDraw.BLUE);
			newX = super.loc().x() + (super.vector().xVel() * 1000);
			newY = super.loc().y() + (super.vector().yVel() * 1000);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
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
