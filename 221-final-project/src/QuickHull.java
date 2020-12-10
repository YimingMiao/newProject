import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.Arrays;

/**
 * Quick Hull algorithm to find convex hull.
 * Average running time: O(nlogn). Worst case O(n^2).
 */

public class QuickHull {

    private ArrayList<Point> convexPoints = new ArrayList<>();
    private CanvasWindow canvas;
    private Color red = Color.red;

    public QuickHull(CanvasWindow canvasWindow){
        this.canvas = canvasWindow;
    }

    public Line drawLine(Point a, Point b){
        Line line = new Line(a.getX(),a.getY(),b.getX(),b.getY());
        return line;
    }

    /**
     * This function finds the upper-right starting points.
     * @param original
     * @return
     */
    public Point findMax(ArrayList<Point> original){
        double max = original.get(0).getX();
        int index = 0;
        for(int i = 0; i <= original.size() - 1; i++){
            if(original.get(i).getX() > max) {
                max = original.get(i).getX();
                index = i;
            }
            else if(original.get(i).getX() == max){
                if(original.get(i).getY() > original.get(index).getY()) {
                    index = i;
                }
            }
        }
        return original.get(index);
    }

    /**
     * This function finds the lower-bottom starting points.
     * @param original
     * @return
     */
    public Point findMin(ArrayList<Point> original){
        double min = original.get(0).getX();
        int index = 0;
        for(int i = 0; i <= original.size() - 1; i++){
            if(original.get(i).getX() < min) {
                min = original.get(i).getX();
                index = i;
            }
            else if(original.get(i).getX() == min){
                if(original.get(i).getY() < original.get(index).getY()) {
                    index = i;
                }
            }
        }
        return original.get(index);
    }

    public boolean upStair(Point a, Point b, Point test){
        double k,d;
        if((Math.abs(a.getX() - test.getX()) < 0.00000001 && Math.abs(a.getY() - test.getY()) < 0.00000001)
                || (Math.abs(b.getX() - test.getX()) < 0.00000001 && Math.abs(b.getY() - test.getY()) < 0.00000001))
            return false;
        if(Math.abs(b.getX() - a.getX()) < 0.00000001)
            return false;
        k = (b.getY() - a.getY()) / (b.getX() - a.getX());
        d = a.getY() - k * a.getX();
        double ret = k * test.getX() - test.getY() + d;
        if (ret < -0.00000001)
            return true;
        else
            return false;
    }

    public boolean downStair(Point a, Point b, Point test){
        double k,d;
        if((Math.abs(a.getX() - test.getX()) < 0.00000001 && Math.abs(a.getY() - test.getY()) < 0.00000001)
                || (Math.abs(b.getX() - test.getX()) < 0.00000001 && Math.abs(b.getY() - test.getY()) < 0.00000001))
            return false;
        if(Math.abs(b.getX() - a.getX()) < 0.00000001)
            return false;
        k = (b.getY() - a.getY()) / (b.getX() - a.getX());
        d = a.getY() - k * a.getX();
        double ret = k * test.getX() - test.getY() + d;
        if (ret > 0.00000001)
            return true;
        else
            return false;
    }

    public ArrayList<Point> findUpStairs(ArrayList<Point> points, Point a, Point b){
        ArrayList<Point> up = new ArrayList<>();
        for(Point point : points){
            if(upStair(a, b, point)) up.add(point);
        }
        return up;
    }


    public ArrayList<Point> findDownStairs(ArrayList<Point> points, Point a, Point b){
        ArrayList<Point> down = new ArrayList<>();
        for(Point point : points){
            if(downStair(a, b, point)) down.add(point);
        }
        return down;
    }

    /**
     * Calculates the slope between two input points
     * @param a
     * @param b
     * @return
     */
    public double slope(Point a, Point b){
        return (b.getY() - a.getY()) / (b.getX() - a.getX());
    }

    /**
     * Calculates the intercept between two input points
     * @param a
     * @param b
     * @return
     */
    public double intercept(Point a, Point b){
        return a.getY() - slope(a,b) * a.getX();
    }

    /**
     * Calculates the distance between the test point and the line draw between points a and b
     * @param test
     * @param a
     * @param b
     * @return
     */
    public double distance(Point test, Point a, Point b){
        double k = slope(a,b);
        double d = intercept(a,b);
        return Math.abs((k * test.getX() - test.getY() + d) / Math.sqrt(k * k + 1));
    }

    /**
     * Finds the intersection point between two perpendicular lines, one of which goes through point test.
     * @param k
     * @param d
     * @param test
     * @return
     */
    public Point perpIntersectionPoint(double k, double d, Point test){
        double k1 = -1/k;
        double d1 = test.getY() - k1 * test.getX();
        double x = (d-d1) / (k1-k);
        double y = k * x + d;
        Point inter = new Point(x-5,y-5,5);
        return inter;
    }

    /**
     * finds the point farthest from the line drawn between points a and b
     * @param points
     * @param a
     * @param b
     * @return
     */
    public Point findMaxDistance(ArrayList<Point> points, Point a, Point b){
        double max = 0;
        double currDis;
        double slope = slope(a,b);
        double intercept = intercept(a,b);
        Point maxP = points.get(0);
        Point currPerpInt = perpIntersectionPoint(slope,intercept,maxP);
        Line perp = drawLine(maxP,currPerpInt);;
        canvas.add(perp);
        runOnUIThread(() -> canvas.pause(500));
        for(Point point : points){
            currDis = distance(point, a, b);
            if(currDis >= max){
                max = currDis;
                maxP = point;
                canvas.remove(perp);
                currPerpInt = perpIntersectionPoint(slope,intercept,point);
                perp = drawLine(point, currPerpInt);
                canvas.add(perp);
                pause();
            }
        }
        maxP.setFillColor(red);

        canvas.remove(perp);

        return maxP;
    }

    private void pause() {
        runOnUIThread(() -> canvas.pause(500));
    }

    public void findUpHull(ArrayList<Point> points, Point a, Point b){
        ArrayList<Point> upPoints = findUpStairs(points, a, b);
        if (upPoints.size() != 0){
            Point c = findMaxDistance(upPoints, a, b);
            convexPoints.add(c);
            Line line1 = drawLine(a,c);
            Line line2 = drawLine(c,b);
            runOnUIThread(() -> canvas.add(line1));
            runOnUIThread(() -> canvas.add(line2));
            runOnUIThread(() -> canvas.pause(500));
            findUpHull(upPoints, a, c);
            if(findUpStairs(upPoints, a, c).size()!=0)
                runOnUIThread(() -> canvas.remove(line1));
            findUpHull(upPoints, c, b);
            if(findUpStairs(upPoints, c, b).size()!=0)
                runOnUIThread(() -> canvas.remove(line2));
        }

    }

    public void findDownHull(ArrayList<Point> points, Point a, Point b){
        ArrayList<Point> downPoints = findDownStairs(points, a, b);

        if (downPoints.size() != 0){
            Point c = findMaxDistance(downPoints, a, b);
            convexPoints.add(c);
            Line line1 = drawLine(a,c);
            Line line2 = drawLine(c,b);

            runOnUIThread(() -> canvas.add(line1));
            runOnUIThread(() -> canvas.add(line2));
            runOnUIThread(() -> canvas.pause(500));
            findDownHull(downPoints, a, c);
            if(findDownStairs(downPoints, a, c).size()!=0)
                runOnUIThread(() -> canvas.remove(line1));
            findDownHull(downPoints, c, b);
            if(findDownStairs(downPoints, c, b).size()!=0)
                runOnUIThread(() -> canvas.remove(line2));
        }
    }

    public ArrayList<Point> quickHull(ArrayList<Point> points){
        Point a = findMin(points);
        Point b = findMax(points);
        a.setFillColor(red);
        b.setFillColor(red);
        convexPoints.add(a);
        convexPoints.add(b);
        Line line = drawLine(a,b);
        runOnUIThread(() -> canvas.add(line));

        findUpHull(points, a, b);
        findDownHull(points, a, b);

        runOnUIThread(() -> canvas.remove(line));
        return convexPoints;
    }

    private void runOnUIThread(Runnable runnable) {
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        QuickHull quickHull = new QuickHull(new CanvasWindow("canvas", 100, 100));
//        Point a = new Point(1-5, 3-5, 5);
//        Point b = new Point(2-5, 5-5, 5);
//        System.out.println(quickHull.slope(a,b));
//
//        a = new Point(2.5-5, 4-5, 5);
//        b = new Point(-1.3-5, 9-5, 5);
//        System.out.println(quickHull.slope(a,b));
//
//        a = new Point(-96.724-5, 1-5, 5);
//        b = new Point(0-5, 0-5, 5);
//        System.out.println(quickHull.slope(a,b));
//
//        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(new Point(0-5,0-5,5), new Point(10-5, 0-5, 5), new Point(10-5,10-5,5), new Point(-10-5,0-5,5), new Point(0-5, -10-5, 5), new Point(-10-5, 10-5, 5)));
//        System.out.println(quickHull.findMax(points));
//
//        points = new ArrayList<Point>(Arrays.asList(new Point(1-5,1-5,5), new Point(1-5,1-5,5),new Point(1-5,1-5,5)));
//        System.out.println(quickHull.findMax(points));
//
//        points = new ArrayList<Point>(Arrays.asList(new Point(0-5,0-5,5), new Point(1.1-5, 1.1-5, 5), new Point(1.1-5, 1.2-5, 5), new Point(1.2-5, 1.1-5,5)));
//        System.out.println(quickHull.findMax(points));
//    }
}
