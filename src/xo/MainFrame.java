package xo;

import javax.swing.*;

public class MainFrame extends JFrame {
    public JTextField jTextField = new JTextField();
    public MainFrame() {
        setTitle("Swing XO");
        setSize(300, 300);
        setLocationRelativeTo(null); //mid screen
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
