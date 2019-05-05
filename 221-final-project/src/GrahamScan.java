import javax.swing.*;
        import java.awt.*;
        import java.util.Arrays;
        import java.util.Scanner;
        import java.util.Stack;

/**
 * Graham Scan algorithm to find convex hull.
 * Average running time: O(nlogn). Worst case O(n^2).
 */

public class GrahamScan
{
    private Stack<Point> hull = new Stack<Point>();
    private Stack<Line> lines = new Stack<Line>();
    private CanvasWindow canvas;
    private Color red = Color.red;
    private Color blue = Color.blue;

    private Point[] points;
    private int V;
    private Point[] pts;
    private boolean hasline2 = false;

    public GrahamScan(Point[] pts, CanvasWindow canvas){
        this.canvas = canvas;
        // make a copy of points
        this.V = pts.length;
        points = new Point[V];
        this.pts = pts;
    }

    public Stack<Point> graham(){
        sortPoints();
        return findConvexHull();
    }

    /**
     * Find the convex hull from input points using Gramham Scan.
     * @return points on the convex hull
     */
    private Stack<Point> findConvexHull() {
        // put the first extreme point in the convex hull
        hull.push(points[0]);
        points[0].setFillColor(red);
        pause();
        // get the second extreme point to start with
        int p2 = getP2();
        // find the rest of the points on the convex hull
        for (int i = p2; i < V; i++)
        {
            Point top = hull.pop();
            top.setFillColor(blue);
            // illustrative lines
            Line line1 = drawLine1(top);
            Line line2 = drawLine2(top, i);
            // go through each point to see if they are on the convex hull
            top = verifyPoints(i, top, line2); // last point on hull
            hull.push(top);
            top.setFillColor(red);
            hull.push(points[i]);
            finalizeHull(i, top, line2);
        }
        addLastLine();

        assert isConvex();
        return hull;
    }

    private void finalizeHull(int i, Point top, Line line2) {
        if (hasline2){
            runOnUIThread(() -> canvas.remove(line2));
            pause();
            hasline2 = false;
        }

        if (i == V-1){
            points[i].setFillColor(red);
            Line line3 = drawLine(top, points[i]);
            lines.add(line3);
            runOnUIThread(() -> canvas.add(line3));
            pause();
        }
    }

    /**
     * Check if each point qualifies to be on the convex hull. Mark it accordingly.
     * @param i
     * @param top
     * @param line2
     * @return
     */
    private Point verifyPoints(int i, Point top, Line line2) {
        while (Point.ccw(hull.peek(), top, points[i]) <= 0)
        {
            top = hull.pop();
            top.setFillColor(blue);
            if (hasline2){
                runOnUIThread(() -> canvas.remove(line2));
                hasline2 = false;
            }
            runOnUIThread(() -> canvas.remove(lines.pop()));
        }
        return top;
    }

    private void addLastLine() {
        Line lastLine = drawLine(points[0], hull.peek());
        runOnUIThread(() -> canvas.add(lastLine));
        pause();
    }

    private Line drawLine2(Point top, int i) {
        Line line2 = drawLine(top, points[i]);
        runOnUIThread(() -> canvas.add(line2));
        pause();
        hasline2 = true;
        return line2;
    }

    private Line drawLine1(Point top) {
        Line line1 = drawLine(hull.peek(), top);
        runOnUIThread(() -> canvas.add(line1));
        pause();
        lines.add(line1);
        return line1;
    }

    private int getP2() {
        int p1;
        for (p1 = 1; p1 < V; p1++)
            if (!points[0].equals(points[p1]))
                break;

        int p2;
        for (p2 = p1 + 1; p2 < V; p2++)
            if (Point.ccw(points[0], points[p1], points[p2]) != 0)
                break;
        hull.push(points[p2 - 1]);
        points[p2 - 1].setFillColor(red);
        pause();
        return p2;
    }

    private void sortPoints() {
        for (int i = 0; i < V; i++)
            points[i] = pts[i];
        // Sort points based on coordinates
        // points[0] is the extreme point with the lowest y coordinates
        Arrays.sort(points);
        // Sort points in increasing order of the angle they and points[0] make with the x-axis
        Arrays.sort(points, 1, V, points[0].POLAR_ORDER);
    }


    public Iterable<Point> hull()
    {
        Stack<Point> s = new Stack<>();
        graham();
        for (Point p : hull)
            s.push(p);
        return s;
    }

    private boolean isConvex()
    {
        int V = hull.size();
        if (V <= 2)
            return true;

        Point[] points = new Point[V];
        int n = 0;
        for (Point p : hull())
            points[n++] = p;

        for (int i = 0; i < V; i++) {
            if (Point.ccw(points[i], points[(i + 1) % V], points[(i + 2) % V]) <= 0)
                return false;
        }
        return true;
    }

    private void pause() {
        runOnUIThread(() -> canvas.pause(500));
    }

    public Line drawLine(Point a, Point b){
        Line line = new Line(a.getX(),a.getY(),b.getX(),b.getY());
        return line;
    }

    private void runOnUIThread(Runnable runnable) {
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
