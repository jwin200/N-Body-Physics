import java.awt.Color;

public class Body {

	//fields
	int mass;
	double speed;
	double dir;
	Color color;
	Point loc;
	Vector body;
	double radius;
	
	
	public Body(int mass, double speed, double dir, Color color, Point loc) {
		this.mass = mass;
		body = new Vector(speed, dir);
		this.color = color;
		this.loc = loc;
	}
	
	public void step(Vector net) {
		body = body.add(net);
		loc = new Point(loc.x() + body.xVel(), loc.y() + body.yVel());
	}
	
	public void draw() {
		//StdDraw.setPenColor(StdDraw.color);
		double radius = Math.sqrt(Math.sqrt(mass)/Math.PI);
		StdDraw.filledCircle(loc.x(), loc.y(), radius);
		}	
}
