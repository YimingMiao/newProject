import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;

public class TDDTest {
    @Test
    public void pointsNumTest() throws AWTException {
        Runner runner = new Runner();
        Assert.assertEquals(0, Integer.parseInt(runner.pointsnum.getText()));

        Point point = new Point(130,20, 5);
        runner.points.add(point);
        runner.updateText();
        Assert.assertEquals(1, Integer.parseInt(runner.pointsnum.getText()));

        point = new Point(0,30, 5);
        runner.points.add(point);
        runner.updateText();
        Assert.assertEquals(2, Integer.parseInt(runner.pointsnum.getText()));

        runner.points.clear();
        runner.updateText();
        Assert.assertEquals(0, Integer.parseInt(runner.pointsnum.getText()));
    }
}
