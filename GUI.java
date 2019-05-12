import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

    JPanel panel;
    Vertex[] tour;

    public GUI(Vertex[] tour) {
        this.tour = tour;
        initComponents();
    }

    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        panel = new JPanel();
        getContentPane().add(panel);

        setTitle("Graph");
        panel.setSize(300, 200);

        add(panel);
        setSize(300, 200);
        pack();
    }

    public void paint(Graphics g) {
        double scale = 3;
        super.paint(g); // fixes the immediate problem.

        Graphics2D g2 = (Graphics2D) g;
        if (tour == null)
            return;
        Vertex currentNode;
        Vertex prevNode;

        int last = 0;
        for (int i = 1; i < tour.length; i++) {
            if (tour[i] == null)
                break;
            last = i;
            currentNode = tour[i];
            prevNode = tour[i - 1];
            drawLine(g2, currentNode, prevNode, scale);
        }

        currentNode = tour[0];
        prevNode = tour[last];

        drawLine(g2, currentNode, prevNode, scale);
    }

    private void drawLine(Graphics2D g, Vertex currentNode, Vertex prevNode,
                          double scale) {

        Ellipse2D p1 = new Ellipse2D.Double(
                20 + scale * currentNode.getX() - 7, 20 + scale
                * currentNode.getY() - 7, 14, 14);
        g.drawString(currentNode.getIndex() + "", (float) (20 + scale
                        * currentNode.getX() - 3),
                (float) (20 + scale * currentNode.getY() + 3));

        Line2D lin = new Line2D.Double(20 + scale * currentNode.getX(), 20
                + scale * currentNode.getY(), 20 + scale * prevNode.getX(), 20
                + scale * prevNode.getY());
        g.draw(p1);
        g.draw(lin);
    }

    public void drawTour() {
        repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}