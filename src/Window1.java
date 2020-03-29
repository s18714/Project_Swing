import javax.swing.JFrame;
import java.awt.Dimension;

class Window1 extends JFrame {

    Window1() {
        super("Creating");
        setVisible(true);
        setSize(800, 600);
        setMinimumSize(new Dimension(100, 75));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CreatingPanel(getWidth(), getHeight()));
    }
}