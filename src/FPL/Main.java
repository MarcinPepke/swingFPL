package FPL;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        LoginPanel LoginPanel = new LoginPanel();
        LoginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginPanel.pack();
        LoginPanel.setTitle("Login panel");
        LoginPanel.setLocationRelativeTo(null);
        LoginPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        LoginPanel.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - LoginPanel.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - LoginPanel.getSize().height / 2);
        LoginPanel.setVisible(true);
        LoginPanel.setLayout(null);

    }
}
