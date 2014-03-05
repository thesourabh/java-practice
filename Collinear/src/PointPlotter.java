/*************************************************************************
 *  Compilation:  javac PointPlotter.java
 *  Execution:    java PointPlotter input.txt
 *  Dependencies: Point.java, In.java, StdDraw.java
 *
 *  Takes the name of a file as a command-line argument.
 *  Reads in an integer N followed by N pairs of points (x, y)
 *  with coordinates between 0 and 32,767, and plots them using
 *  standard drawing.
 *
 *************************************************************************/

public class PointPlotter {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        String filename = "C:/Users/Sourabh/Programming/Java/Collinear/src/collinear/input56.txt";
        In in = new In(filename);
        int N = in.readInt();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.show(0);
        // read in the input
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
        }

        Point p = new Point(50,50);
        Point q = new Point(100,100);
    	p.drawTo(q);
        // display to screen all at once
        StdDraw.show(0);
    }
}