import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {

    Rectangle(int x0, int y0, int width, int height, Color color) {
        super(x0, y0, width, height, color);
    }

    @Override
    public String toString() {
        return 4 + " " + x0 + " " + y0 + " " + width + " " + height + " " +
                color.getRGB() + "\n";
    }

    @Override
    void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x0, y0, width, height);
    }
}