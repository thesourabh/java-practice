import java.util.Arrays;
import java.util.TreeSet;
import java.util.Set;
public class PointSET {
	private Set<Point2D> PS;
	public PointSET() {
		PS = new TreeSet<Point2D>();
	}
	public boolean isEmpty() {
		return PS.isEmpty();
	}
	public int size() {
		return PS.size();
	}
	public void insert(Point2D p) {
		PS.add(p);
	}
	public boolean contains(Point2D p) {
		return PS.contains(p);
	}
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		for (Point2D i : PS) {
			StdDraw.point(i.x(), i.y());
		}
		StdDraw.show();
	}
	public Iterable<Point2D> range(RectHV rect) {
		Set<Point2D> s = new TreeSet<Point2D>();
		for (Point2D i : PS) {
			if (rect.contains(i))
				s.add(i);
		}
		return s;
	}
	public Point2D nearest(Point2D p) {
		TreeSet<Point2D> temp = new TreeSet<Point2D>(p.DISTANCE_TO_ORDER);
		temp.addAll(PS);
		return temp.first();
	}
}