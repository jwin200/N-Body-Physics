/** 
 * An object to represent a point with x and y coordinates.
 * Also calculates distance to, and angle to other Point objects.
 * 
 * @author Jonah Winchell
 * @version April 18, 2018
 */
public class Point {
  // Fields
  private double x, y;
  
  // Getters / Setters
  public double x() { return x; }
  public void setX(double newX) { x = newX; }
  
  public double y() { return y; }
  public void setY(double newY) { y = newY; }
  
  // Constructors
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Point() {
    this(Math.random(), Math.random());
  }
  
  // Methods
  /** Converts point's data to a string. */
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
  
  /** Creates a random new point within a given range. */
  public Point random(double min, double max) {
    double range = max - min;
    double x = Math.random()*range + min;
    double y = Math.random()*range + min;
    return new Point(x, y);
  }
  
  /** Calculates the angle between this point and another. */
  public double dirTo(Point p) {
    double deltaX = p.x() - this.x;
    double deltaY = p.y() - this.y;
    double dir = Math.atan(deltaY / deltaX);
    return dir;
  }
  
  /** Calculates the distance from one point to another. */
  public double distanceTo(Point p) {
    return Math.sqrt(Math.pow((this.x - p.x), 2) + Math.pow((this.y - p.y), 2));
  }
  
  /** Returns the average of two points. */
  public Point average(Point p1, Point p2) {
	  double x = (p1.x() + p2.x()) / 2;
	  double y = (p1.y() + p2.y()) / 2;
	  return new Point(x, y);
  }
}