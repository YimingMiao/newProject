import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import static javafx.application.Platform.exit;

public class Runner extends CanvasWindow implements MouseMotionListener, MouseListener,ActionListener{

    public ArrayList<Point> points;
    private Point[] pts;
    private JButton runButton;
    private JButton quitButton;
    private JButton refreshButton;
    private JButton runButton1;
//    private JLabel time;
//    private Timer timer;
    public JLabel pointsnum;


    /**
     * Constructor to create the main program by creating a canvas window.
     */
    public Runner() {
        super("ConvexHull", 1440, 1080);
        setupUI();
        points = new ArrayList<>();
    }

    public void setupUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        runButton = new JButton("Run QuickHull");
        runButton.addActionListener(this);
        panel.add(runButton);
        add(panel, BorderLayout.SOUTH);

        runButton1 = new JButton("Run GrahamScan");
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

//        time = new JLabel("0:00");
//        panel.add(time);
//        add(panel, BorderLayout.SOUTH);

        JLabel word = new JLabel("Number of points: ");
        panel.add(word);
        add(panel, BorderLayout.SOUTH);

        pointsnum = new JLabel("0");
        panel.add(pointsnum);
        add(panel, BorderLayout.SOUTH);

        addMouseListener(this);
        addMouseMotionListener(this);

        revalidate();
    }


    public static void main(String[] args) {
        Runner runner = new Runner();
    }

    public void updateText(){
        pointsnum.setText("" + points.size());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = new Point(e.getX() - 5, e.getY() - 5, 5);
        this.add(point);
        points.add(point);
        updateText();

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
            runQuickHull();

        }
        if (e.getSource() == refreshButton){
            points.clear();
            this.removeAll();
            pointsnum.setText("" + points.size());
        }
        if (e.getSource() == quitButton){
            System.exit(0);
        }
        if (e.getSource() == runButton1){
            pts = new Point[points.size()];
            for(int i = 0; i < points.size(); i++){
                pts[i] = points.get(i);
            }
            runGrahamScan();
        }
    }

    private void runGrahamScan() {
        GrahamScan g = new GrahamScan(pts, this);
        this.removeAll();
        for (Point point : points){
            point.setFillColor(Point.COLOR);
            this.add(point);
        }

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        g.hull();
                    }
                }
        ).start();
    }

    private void runQuickHull() {
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
}
