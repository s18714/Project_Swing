import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class PaintingPanel extends JPanel {

    private ArrayList<String> allLines = new ArrayList<>();
    private BufferedReader reader = null;
    private int originalWidth;
    private int originalHeight;
    private Dimension currentSize;

    PaintingPanel(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        shapesToList();
    }

    private void shapesToList() {
        new Thread(() -> {
            try {
                reader = new BufferedReader(new FileReader("Shapes.txt"));
                String c;
                while (true) {
                    while ((c = reader.readLine()) != null) {
                        allLines.add(c);
                        repaint();
                        Thread.sleep(300);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        currentSize = this.getBounds().getSize();
        Graphics2D g2 = (Graphics2D) g;

        try {
            for (String tmp : allLines) {
                String[] params = tmp.split(" ");

                switch (params[0]) {
                    case "0":
                        scale(stringToOval(params)).paint(g2);
                        break;
                    case "4":
                        scale(stringToRectangle(params)).paint(g2);
                        break;
                    case "3":
                        scale(stringToTriangle(params)).paint(g2);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.print("");
        }
    }

    private Rectangle stringToRectangle(String[] params) {
        return new Rectangle(
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]),
                Integer.parseInt(params[3]),
                Integer.parseInt(params[4]),
                new Color(Integer.parseInt(params[5]))
        );
    }

    private Oval stringToOval(String[] params) {
        return new Oval(
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]),
                Integer.parseInt(params[3]),
                Integer.parseInt(params[4]),
                new Color(Integer.parseInt(params[5]))
        );
    }

    private Triangle stringToTriangle(String[] params) {
        return new Triangle(
                new int[]{Integer.parseInt(params[1]),
                        Integer.parseInt(params[2]),
                        Integer.parseInt(params[3])},
                new int[]{Integer.parseInt(params[4]),
                        Integer.parseInt(params[5]),
                        Integer.parseInt(params[6])},
                new Color(Integer.parseInt(params[7]))
        );
    }

    private Shape scale(Shape shape) {
        int x = shape.x0;
        int y = shape.y0;
        int w = shape.width;
        int h = shape.height;

        if (getWidth() != originalWidth || getHeight() != originalHeight) {
            x = (x * currentSize.width) / originalWidth;
            y = (y * currentSize.height) / originalHeight;
            w = (currentSize.width * w) / originalWidth;
            h = (currentSize.height * h) / originalHeight;
        }
        if (shape.getClass() == Rectangle.class)
            return new Rectangle(x, y, w, h, shape.color);

        if (shape.getClass() == Oval.class)
            return new Oval(x, y, w, h, shape.color);
        else return scaleTriangle(shape);
    }

    private Shape scaleTriangle(Shape triangle) {
        int[] x = triangle.xPoints;
        int[] y = triangle.yPoints;

        for (int i = 0; i < 3; i++) {
            x[i] = (currentSize.width * x[i]) / originalWidth;
            y[i] = (currentSize.height * y[i]) / originalHeight;
        }
        return new Triangle(x, y, triangle.color);
    }
}