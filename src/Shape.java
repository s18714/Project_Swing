import java.awt.Color;
import java.awt.Graphics;

abstract class Shape {

    int x0;
    int y0;
    int width;
    int height;
    Color color;
    int[] xPoints;
    int[] yPoints;

    Shape(int x0, int y0, int width, int height, Color color) {
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    Shape(int[] xPoints, int[] yPoints, Color color) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.color = color;
    }

    void paint(Graphics g) {
    }
}