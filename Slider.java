/**
 * A slider for controlling values in StdDraw-based
 * programs.  Use the update() method within the animation
 * loop to allow the user to drag the slider, and access
 * the current value with getValue().
 * 
 * Defaults:
 *   xCtr=0.5, yCtr=0.1, radius=0.3, height=0.015
 * 
 * @author A. Jacoby & Jonah Winchell
 */
public class Slider {
  // Fields
  private double val;
  private double min, max;
  private double xCtr, yCtr;
  private double radius, height;
  private boolean toZero;
  
  // Getters / Setters
  public double getVal() { return val; }
  public void setVal(double newVal) { val = newVal; }

  public double getMin() { return min; }
  public void setMin(double newMin) { min = newMin; }

  public double getMax() { return max; }
  public void setMax(double newMax) { max = newMax; }

  public double getXCtr() { return xCtr; }
  public void setXCtr(double newXCtr) { xCtr = newXCtr; }

  public double getYCtr() { return yCtr; }
  public void setYCtr(double newYCtr) { yCtr = newYCtr; }

  public double getRadius() { return radius; }
  public void setRadius(double newRadius) { radius = newRadius; }
  
  public double getHeight() { return height; }
  public void setHeight(double newHeight) { height = newHeight; }
  
  public boolean getToZero() { return toZero; }
  public void setToZero(boolean newToZero) { toZero = newToZero; }
  
  // Constructor
  public Slider(double x, double y, double val, double min, double max) {
    this.val = val;
    this.min = min;
    this.max = max;
    this.xCtr = x;
    this.yCtr = y;
    this.radius = 0.3;
    this.height = 0.015;
    this.toZero = false;
  }
  
  public Slider(double min, double max) {
	  this.min = min;
	  this.max = max;
	  this.val = (min + max) / 2;
	  this.xCtr = 0.5;
	  this.yCtr = 0.1;
	  this.radius = 0.3;
	  this.height = 0.015;
	  this.toZero = false;
  }
  
  // Methods
  /**
   * Updates value and draws slider. Should be called inside animation
   * loop of client.
   */
  public void update() {
    // If mouse not pressed, just draw and return
    if (!StdDraw.mousePressed()) {
      draw();
      if(toZero) {
        val = (min + max) / 2;
      }
      return;
    }
    // If mouse pressed, update val if necessary, then draw
    double x = StdDraw.mouseX();
    double y = StdDraw.mouseY();
    if (Math.abs(y - yCtr) <= height*1.1 &&
        Math.abs(x - xCtr) <= radius*1.1)
    {
      double xMin = xCtr - radius;
      double percent = (x - xMin) / (2*radius);
      val = min + percent * (max - min);
      val = Math.max(val, min);
      val = Math.min(val, max);
    }
    draw();
  }
  
  /** Draws slider to StdDraw. Called automatically after update(). */
  private void draw() {
    StdDraw.setPenColor(StdDraw.BLACK);
    double xMin = xCtr - radius;
    double xMax = xCtr + radius;
    StdDraw.line(xMin, yCtr, xMax, yCtr);
    double percent = (val - min) / (max - min);
    double xDot = xMin + percent * (2*radius);
    StdDraw.filledCircle(xDot, yCtr, height);
    StdDraw.text(xCtr, yCtr-height*2.1, "" + val);
  }

}
