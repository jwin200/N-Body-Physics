import java.awt.Color;

public class Body {

	//fields
	private double mass;
	private double speed;
	private double dir;
	private Color color;
	private Point loc;
	private Vector body;
	private double radius;
	
	public double mass() { return mass; }
	public double speed() { return speed; }
	public double dir() { return dir; }
	public Vector vector() { return body; }
	public double radius() { return radius; }
	public Point loc() { return loc; }
	
	
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
		StdDraw.setPenColor(color);
		//StdDraw.setPenColor(StdDraw.color);
		double radius = Math.sqrt(Math.sqrt(mass)/Math.PI);
		StdDraw.filledCircle(loc.x(), loc.y(), radius);
		}	
}
