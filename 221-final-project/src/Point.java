import java.awt.*;
import java.util.Comparator;

public class Point extends Ellipse implements Comparable<Point> {

    final public static double RADIUS = 5;
    final public static Color COLOR = new Color(0, 0, 255);
    public static final Comparator<Point> X_ORDER = new Point.XOrder();
    public static final Comparator<Point> Y_ORDER = new Point.YOrder();
    public static final Comparator<Point> R_ORDER = new Point.ROrder();
    public final Comparator<Point> POLAR_ORDER = new Point.PolarOrder();
    public final Comparator<Point> ATAN2_ORDER = new Point.Atan2Order();
    public final Comparator<Point> DISTANCE_TO_ORDER = new Point.DistanceToOrder();


    /**
     * Constructor to create the ellipse object and initialize its instance variables.
     * The default creates an ellipse at position x,y with a bounding rectangle of width and height.
     * The ellipse is drawn with a 1 pixel black stroke outline by default.
     *
     * @param x      position
     * @param y      position
     * @param radius  of the bounding rectangle
     */
    public Point(double x, double y, double radius) {
        super(x, y, 2*radius, 2*radius);
        this.setFilled(true);
        this.setFillColor(COLOR);
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
    }

    @Override
    public double getX() {
        return super.getX() + RADIUS;
    }

    @Override
    public double getY() {
        return super.getY() + RADIUS;
    }

    public double r(){
        return RADIUS;
    }

    public double theta()
    {
        return Math.atan2(this.getY(), this.getX());
    }

    private double angleTo(Point that)
    {
        double dx = that.getX() - this.getX();
        double dy = that.getY() - this.getY();
        return Math.atan2(dy, dx);
    }

    public static int ccw(Point a, Point b, Point c)
    {
        double area2 = (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
        if (area2 < 0)
            return -1;
        else if (area2 > 0)
            return +1;
        else
            return 0;
    }

    public static double area2(Point a, Point b, Point c)
    {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
    }

    public double distanceTo(Point that)
    {
        double dx = this.getX() - that.getX();
        double dy = this.getY() - that.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceSquaredTo(Point that)
    {
        double dx = this.getX() - that.getX();
        double dy = this.getY() - that.getY();
        return dx * dx + dy * dy;
    }

    public int compareTo(Point that)
    {
        if (this.getY() < that.getY())
            return -1;
        if (this.getY() > that.getY())
            return +1;
        if (this.getX() < that.getX())
            return -1;
        if (this.getX() > that.getX())
            return +1;
        return 0;
    }

    private static class XOrder implements Comparator<Point>
    {
        public int compare(Point p, Point q)
        {
            if (p.getX() < q.getX())
                return -1;
            if (p.getX() > q.getX())
                return +1;
            return 0;
        }
    }

    private static class YOrder implements Comparator<Point>
    {
        public int compare(Point p, Point q)
        {
            if (p.getY() < q.getY())
                return -1;
            if (p.getY() > q.getY())
                return +1;
            return 0;
        }
    }

    private static class ROrder implements Comparator<Point>
    {
        public int compare(Point p, Point q)
        {
            double delta = (p.getX() * p.getX() + p.getY() * p.getY()) - (q.getX() * q.getX() + q.getY() * q.getY());
            if (delta < 0)
                return -1;
            if (delta > 0)
                return +1;
            return 0;
        }
    }

    private class Atan2Order implements Comparator<Point>
    {
        public int compare(Point q1, Point q2)
        {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            if (angle1 < angle2)
                return -1;
            else if (angle1 > angle2)
                return +1;
            else
                return 0;
        }
    }

    private class PolarOrder implements Comparator<Point>
    {
        public int compare(Point q1, Point q2)
        {
            double dx1 = q1.getX() - getX();
            double dy1 = q1.getY() - getY();
            double dx2 = q2.getX() - getX();
            double dy2 = q2.getY() - getY();

            if (dy1 >= 0 && dy2 < 0)
                return -1; // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0)
                return +1; // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0)
            { // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0)
                    return -1;
                else if (dx2 >= 0 && dx1 < 0)
                    return +1;
                else
                    return 0;
            } else
                return -ccw(Point.this, q1, q2); // both above or below
        }
    }

    private class DistanceToOrder implements Comparator<Point>
    {
        public int compare(Point p, Point q)
        {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            if (dist1 < dist2)
                return -1;
            else if (dist1 > dist2)
                return +1;
            else
                return 0;
        }
    }

    public boolean equals(Object other)
    {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        Point that = (Point) other;
        return this.getX() == that.getX() && this.getY() == that.getY();
    }

    public String toString()
    {
        return "(" + getX() + ", " + getX() + ")";
    }

    public int hashCode()
    {
        int hashX = ((Double) getX()).hashCode();
        int hashY = ((Double) getY()).hashCode();
        return 31 * hashX + hashY;
    }
}
