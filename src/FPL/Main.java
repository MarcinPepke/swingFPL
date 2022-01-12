package FPL;


import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, SQLException {

       /*File file = new File("test2.wav");
       AudioInputStream audio = AudioSystem.getAudioInputStream(file);
       Clip clip = AudioSystem.getClip();
       clip.open(audio);
       clip.start();*/
        new LoginPanel().createMainPanel(false);
        /*LoginPanel LoginPanel = new LoginPanel();
        LoginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginPanel.pack();
        LoginPanel.setTitle("Login panel");
        LoginPanel.setLocationRelativeTo(null);
        LoginPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        LoginPanel.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - LoginPanel.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - LoginPanel.getSize().height / 2);
        LoginPanel.setVisible(true);
        LoginPanel.setLayout(null);*/

    }
}
