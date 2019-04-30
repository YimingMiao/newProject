import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import static javafx.application.Platform.exit;

public class Runner extends CanvasWindow implements MouseMotionListener, MouseListener,ActionListener{

    private ArrayList<Point> points;
    private Point[] pts;
    private JButton runButton;
    private JButton quitButton;
    private JButton refreshButton;
    private JButton runButton1;

    /**
     * Constructor to create the main program by creating a canvas window.
     */
    public Runner() {
        super("ConvexHull", 800, 600);
        setupUI();
        points = new ArrayList<>();
    }

    public void setupUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        runButton = new JButton("RunQuickHull");
        runButton.addActionListener(this);
        panel.add(runButton);
        add(panel, BorderLayout.SOUTH);

        runButton1 = new JButton("RunGram-Scan");
        runButton1.addActionListener(this);
        panel.add(runButton1);
        add(panel, BorderLayout.SOUTH);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        panel.add(refreshButton);
        add(panel, BorderLayout.SOUTH);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        panel.add(quitButton);
        add(panel, BorderLayout.SOUTH);

        addMouseListener(this);
        addMouseMotionListener(this);

        revalidate();
    }


    public static void main(String[] args) {
        Runner runner = new Runner();

//        ArrayList<Point> test = new ArrayList<>();
//        Point p1 = new Point(130, 250, Point.RADIUS);
//        Point p2 = new Point(200, 198, Point.RADIUS);
//        Point p3 = new Point(365, 112, Point.RADIUS);
//        Point p4 = new Point(210, 430, Point.RADIUS);
//        Point p5 = new Point(145, 145, Point.RADIUS);
//        Point p6 = new Point(530, 300, Point.RADIUS);
//        Point p7 = new Point(745, 197.99999, Point.RADIUS);
//
//        runner.add(p1);
//        runner.add(p2);
//        runner.add(p3);
//        runner.add(p4);
//        runner.add(p5);
//        runner.add(p6);
//        runner.add(p7);
//        runner.pause(3000);
//        test.add(p1);
//        test.add(p2);
//        test.add(p3);
//        test.add(p4);
//        test.add(p5);
//        test.add(p6);
//        test.add(p7);
//
//        QuickHull myQuickHull = new QuickHull(runner);
//        System.out.println(myQuickHull.quickHull(test));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = new Point(e.getX() - 5, e.getY() - 5, 5);
        this.add(point);
        points.add(point);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton){
            QuickHull q = new QuickHull(this);
            this.removeAll();
            for (Point point : points){
                point.setFillColor(Point.COLOR);
                this.add(point);
            }
            new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        q.quickHull(points);
                    }
                }
            ).start();
        }
        if (e.getSource() == refreshButton){
            points.clear();
            this.removeAll();
        }
        if (e.getSource() == quitButton){
            System.exit(0);
        }
        if (e.getSource() == runButton1){
            pts = new Point[points.size()];
            for(int i = 0; i < points.size(); i++){
                pts[i] = points.get(i);
            }
            GrahamScan g = new GrahamScan(pts, this);
            this.removeAll();
            for (Point point : points){
                point.setFillColor(Point.COLOR);
                this.add(point);
            }
            System.out.println(g.hull());
        }
    }
}
