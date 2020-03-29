import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class CreatingPanel extends JPanel {

    private ArrayList<Shape> shapeList = new ArrayList<>();
    private Random rand = new Random();
    private FileOutputStream fos;
    private int originalHeight;
    private int originalWidth;
    private Dimension size;

    CreatingPanel(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        new Thread(() -> {
            try {
                fos = new FileOutputStream("Shapes.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                create();
                repaint();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void create() {
        int width = 10 + (int) (Math.random() * (originalWidth / 3));
        int height = 10 + (int) (Math.random() * (originalHeight / 3));
        int x0 = (int) (Math.random() * (originalWidth - width));
        int y0 = (int) (Math.random() * (originalHeight - height));
        Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        switch ((int) (Math.random() * 3)) {
            case 0:
                shapeList.add(new Rectangle(x0, y0, width, height, color));
                break;
            case 1:
                int[] xPoints = new int[3];
                int[] yPoints = new int[3];
                for (int i = 0; i < 3; i++) {
                    xPoints[i] = rand.nextInt(originalWidth);
                    yPoints[i] = rand.nextInt(originalHeight);
                }
                shapeList.add(new Triangle(xPoints, yPoints, color));
                break;
            case 2:
                shapeList.add(new Oval(x0, y0, width, height, color));
                break;
        }
        try {
            fos.write(shapeList.get(shapeList.size() - 1).toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        size = this.getBounds().getSize();
        Graphics2D g2 = (Graphics2D) g;

        try {
            for (Shape shape : shapeList) {
                scale(shape).paint(g2);
            }
        } catch (ConcurrentModificationException e) {
            System.out.print("");
        }
    }

    private Shape scale(Shape shape) {
        int x = shape.x0;
        int y = shape.y0;
        int w = shape.width;
        int h = shape.height;

        if (getWidth() != originalWidth || getHeight() != originalHeight) {
            x = (x * size.width) / originalWidth;
            y = (y * size.height) / originalHeight;
            w = (size.width * w) / originalWidth;
            h = (size.height * h) / originalHeight;
        }
        if (shape.getClass() == Triangle.class)
            return scaleTriangle(shape);
        if (shape.getClass() == Oval.class)
            return new Oval(x, y, w, h, shape.color);
        else return new Rectangle(x, y, w, h, shape.color);
    }

    private Triangle scaleTriangle(Shape triangle) {
        int[] x = Arrays.copyOf(triangle.xPoints, 3);
        int[] y = Arrays.copyOf(triangle.yPoints, 3);

        for (int i = 0; i < 3; i++) {
            x[i] = (size.width * x[i]) / originalWidth;
            y[i] = (size.height * y[i]) / originalHeight;
        }
        return new Triangle(x, y, triangle.color);
    }
}