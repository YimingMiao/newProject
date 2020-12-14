import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;

public class defender {
    @Test
    public void slopeTest() {
        QuickHull quickHull = new QuickHull(new CanvasWindow("canvas", 100, 100));
        Point a = new Point(1-5, 3-5, 5);
        Point b = new Point(2-5, 5-5, 5);
        Assert.assertEquals(1.0, quickHull.intercept(a,b), 0.000001);
    }
}
