import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape {

    Triangle(int[] xPoints, int[] yPoints, Color color) {
        super(xPoints, yPoints, color);
    }

    @Override
    public String toString() {
        return 3 + " " + xPoints[0] + " " + xPoints[1] + " " + xPoints[2] + " " +
                yPoints[0] + " " + yPoints[1] + " " + yPoints[2] + " " + color.getRGB() + "\n";
    }

    @Override
    void paint(Graphics g) {
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}