/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    private class SlopeOrder implements Comparator<Point>
    {
    	public int compare(Point first, Point second)
    	{
    		return (slopeTo(first) <= slopeTo(second)) ? -1 : 1;
    	}
    }
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if(this.y == that.y)
        {	if(this.x == that.x)
        		return Double.NEGATIVE_INFINITY;
        	else
        		return 0.0;
        }
        else if(this.x == that.x)
        	return Double.POSITIVE_INFINITY;
        else
        	return (float)(that.y - this.y) / (that.x - this.x); 
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	if(this.y != that.y)
    		return that.y - this.y;
    	else return that.x - this.x;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
    	StdDraw.setXscale(0, 128);
        StdDraw.setYscale(0, 128);
        StdDraw.show(0);
    	Point x = new Point(0,0);
    	x.draw();
    	Point y = new Point(100,100);
    	y.draw();
    	Point z = new Point(40,80);
    	z.draw();
        StdDraw.show(0);
    	x.drawTo(y);
    	y.drawTo(z);
        StdDraw.show(0);
    }
}