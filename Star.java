
public class Star extends Body {
	
	public Star(double mass, Vector vector, Point loc, boolean showVector) {
		super(mass, vector, loc, showVector);
	}
	
	public void draw(Vector force) {
		if(super.show()) {
			StdDraw.setPenColor(StdDraw.RED);
			double newX = super.loc().x() + (force.xVel() * 1000000);
			double newY = super.loc().y() + (force.yVel() * 1000000);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
			StdDraw.setPenColor(StdDraw.YELLOW);
			newX = super.loc().x() + (super.vector().xVel() * 1000);
			newY = super.loc().y() + (super.vector().yVel() * 1000);
			StdDraw.line(super.loc().x(), super.loc().y(), newX, newY);
		}
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.filledCircle(super.loc().x(), super.loc().y(), super.radius());
	}
	
	public String toString() {
		return "Star: " + super.toString();
	}
}
