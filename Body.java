import java.awt.Color;

public class Body {

	//fields
	private double mass;
	private double speed;
	private double dir;
	private Color color;
	private Point loc;
	private Vector vector;
	private double radius;
	
	public double mass() { return mass; }
	public double speed() { return speed; }
	public double dir() { return dir; }
	public Vector vector() { return vector; }
	public double radius() { return radius; }
	public Point loc() { return loc; }
	
	
	public Body(int mass, double speed, double dir, Color color, Point loc) {
		this.mass = mass;
		vector = new Vector(speed, dir);
		this.color = color;
		this.loc = loc;
	}
	
	public void step(Vector net) {
		vector = vector.add(net);
		vector.update();
		loc = new Point(loc.x() + vector.xVel(), loc.y() + vector.yVel());
	}
	
	public void draw() {
		StdDraw.setPenColor(color);
		double radius = Math.sqrt(Math.sqrt(mass)/Math.PI);
		StdDraw.filledCircle(loc.x(), loc.y(), radius);
		}	
}
