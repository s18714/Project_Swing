import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window1::new);

        SwingUtilities.invokeLater(Window2::new);
    }
}