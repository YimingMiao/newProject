import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class GrahamScan
{
    private Stack<Point> hull = new Stack<Point>();
    private CanvasWindow canvas;
    private Color red = Color.red;

    public GrahamScan(Point[] pts, CanvasWindow canvas)
    {
        this.canvas = canvas;
        // defensive copy
        int N = pts.length;
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++)
            points[i] = pts[i];
        Arrays.sort(points);

        Arrays.sort(points, 1, N, points[0].POLAR_ORDER);

        hull.push(points[0]); // p[0] is first extreme point
//        points[0].setFillColor(red);
        canvas.add(points[0]);
        int k1;
        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1]))
                break;
        if (k1 == N)
            return; // all points equal

        int k2;
        for (k2 = k1 + 1; k2 < N; k2++)
            if (Point.ccw(points[0], points[k1], points[k2]) != 0)
                break;
        hull.push(points[k2 - 1]); // points[k2-1] is second extreme point
        points[0].setFillColor(red);
        canvas.add(points[0]);

        for (int i = k2; i < N; i++)
        {
            Point top = hull.pop();
            while (Point.ccw(hull.peek(), top, points[i]) <= 0)
            {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }

        assert isConvex();
    }

    public Iterable<Point> hull()
    {
        Stack<Point> s = new Stack<>();
        for (Point p : hull) {
            s.push(p);
            p.setFillColor(red);
        }
        return s;
    }

    private boolean isConvex()
    {
        int N = hull.size();
        if (N <= 2)
            return true;

        Point[] points = new Point[N];
        int n = 0;
        for (Point p : hull())
        {
            points[n++] = p;
        }

        for (int i = 0; i < N; i++)
        {
            if (Point
                    .ccw(points[i], points[(i + 1) % N], points[(i + 2) % N]) <= 0)
            {
                return false;
            }
        }
        return true;
    }

    // test client
//    public static void main(String[] args)
//    {
//        System.out.println("Graham Scan Test");
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter the number of points");
//        int N = sc.nextInt();
//
//        Point[] points = new Point[N];
//        System.out.println("Enter the coordinates of each points: <x> <y>");
//        for (int i = 0; i < N; i++)
//        {
//            int x = sc.nextInt();
//            int y = sc.nextInt();
//            points[i] = new Point(x - Point.RADIUS, y - Point.RADIUS, Point.RADIUS);
//        }
//        GrahamScan graham = new GrahamScan(points);
//        System.out.println("The convex hull consists of following points: ");
//        for (Point p : graham.hull())
//            System.out.println(p);
//
//        sc.close();
//    }

}
