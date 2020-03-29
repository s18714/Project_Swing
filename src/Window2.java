import javax.swing.JFrame;
import java.awt.Dimension;

class Window2 extends JFrame {

    Window2() {
        super("Painting");
        setVisible(true);
        setSize(800, 600);
        setLocation(820, 0);
        setMinimumSize(new Dimension(100, 75));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new PaintingPanel(getWidth(), getHeight()));
    }
}