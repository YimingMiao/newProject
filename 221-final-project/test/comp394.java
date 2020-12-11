import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class comp394 {
    @Test
    public void name() {
        Assert.assertTrue(true); // Pass
//        Assert.assertTrue(false);  // Tests failed
    }

    @Test
    public void slopeTest() {
        QuickHull quickHull = new QuickHull(new CanvasWindow("canvas", 100, 100));
        Point a = new Point(1-5, 3-5, 5);
        Point b = new Point(2-5, 5-5, 5);
        Assert.assertTrue(quickHull.slope(a,b) == 2.0);

        a = new Point(2.5-5, 4-5, 5);
        b = new Point(-1.3-5, 9-5, 5);
        Assert.assertTrue(quickHull.slope(a,b) == -1.3157894736842106);

        a = new Point(-96.724-5, 1-5, 5);
        b = new Point(0-5, 0-5, 5);
        Assert.assertTrue(quickHull.slope(a,b) == -0.010338695670154253);
    }

    @Test
    public void getMaxTest() {
        QuickHull quickHull = new QuickHull(new CanvasWindow("canvas", 100, 100));
        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(new Point(0-5,0-5,5), new Point(10-5, 0-5, 5), new Point(10-5,10-5,5), new Point(-10-5,0-5,5), new Point(0-5, -10-5, 5), new Point(-10-5, 10-5, 5)));
        Assert.assertEquals(new Point(10-5,10-5,5), quickHull.findMax(points));

        points = new ArrayList<Point>(Arrays.asList(new Point(1-5,1-5,5), new Point(1-5,1-5,5),new Point(1-5,1-5,5)));
        Assert.assertEquals(new Point(1-5,1-5,5), quickHull.findMax(points));

        points = new ArrayList<Point>(Arrays.asList(new Point(0-5,0-5,5), new Point(1.1-5, 1.1-5, 5), new Point(1.1-5, 1.2-5, 5), new Point(1.2-5, 1.1-5,5)));
        Assert.assertEquals(new Point(1.1-5, 1.2-5,5), quickHull.findMax(points));
    }
}
