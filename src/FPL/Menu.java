package FPL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

public class Menu extends JMenuBar {
    JMenu menu,submenu;
    JMenuItem menuItemAuthors;

    public Menu(JLabel statusBar, boolean isAdmin, MainPanel mainPanel) {
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        this.add(menu);
        menuItemAuthors = new JMenuItem("Search", KeyEvent.VK_T);
        menuItemAuthors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItemAuthors);
        submenu = new JMenu("Add");
        submenu.setMnemonic(KeyEvent.VK_S);
        menuItemAuthors = new JMenuItem("Add a player");
        menuItemAuthors.setEnabled(isAdmin);
        menuItemAuthors.addActionListener(e -> {
            try {
                statusBar.setText("Aktualne wydarzenia: otwarto menu dodawania pilakrza");
                new AddingPlayerPanel(statusBar,mainPanel);
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });
        submenu.add(menuItemAuthors);
        menuItemAuthors = new JMenuItem("Add a stadium");
        menuItemAuthors.setEnabled(isAdmin);
        menuItemAuthors.addActionListener(e -> {
            try {
                statusBar.setText("Aktualne wydarzenia: otwarto menu dodawania stadionu");
                new AddingStadiumsPanel(statusBar,mainPanel);
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });
        submenu.add(menuItemAuthors);
        menuItemAuthors = new JMenuItem("Add a club");
        menuItemAuthors.setEnabled(isAdmin);
        menuItemAuthors.addActionListener(e ->{
            try {
                statusBar.setText("Aktualne wydarzenia: otwarto menu dodawania klubu");
                new AddingClubPanel(statusBar,mainPanel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        submenu.add(menuItemAuthors);
        menu.add(submenu);
        menu.addSeparator();
        menuItemAuthors = new JMenuItem("Exit",KeyEvent.VK_ESCAPE);
        menuItemAuthors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        menuItemAuthors.getAccessibleContext().setAccessibleDescription("Test przycisku");
        menu.add(menuItemAuthors);
        menuItemAuthors.addActionListener(e -> {
            System.exit(0);
        });
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Test");
        this.add(menu);
        menuItemAuthors = new JMenuItem("About", KeyEvent.VK_T);
        menuItemAuthors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
        menuItemAuthors.getAccessibleContext().setAccessibleDescription("Test przycisku");

        menu.add(menuItemAuthors);
        menuItemAuthors.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Authors: Marcin Pepke, Marcin Pozniak");
            statusBar.setText("Aktaulne wydarzenie: wysiwetlono informacje o autorach");
        });
        menuItemAuthors = new JMenuItem("Contact", KeyEvent.VK_T);
        menuItemAuthors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        menuItemAuthors.getAccessibleContext().setAccessibleDescription("Test przycisku");
        menu.add(menuItemAuthors);
        menuItemAuthors.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Marcin Pepke, Marcin Pozniak");
            statusBar.setText("Aktaulne wydarzenie: wyswietlono informacje o autorach");

        });
        this.add(menu);
        menu = new JMenu("Get access to database");
        this.add(menu);
        menuItemAuthors = new JMenuItem("Log in");
        if(!isAdmin) {
            menuItemAuthors.addActionListener(e -> {
                mainPanel.dispose();
                LoginPanel LoginPanel = new LoginPanel();
                LoginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                LoginPanel.pack();
                LoginPanel.setTitle("Login panel");
                LoginPanel.setLocationRelativeTo(null);
                LoginPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4);
                LoginPanel.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - LoginPanel.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - LoginPanel.getSize().height / 2);
                LoginPanel.setVisible(true);
                LoginPanel.setLayout(null);
                statusBar.setText("Aktaulne wydarzenie: zalogowano na admina");
            });
        }
        else {
            menuItemAuthors.setText("Logged");
            menuItemAuthors.setEnabled(false);
        }
        menu.add(menuItemAuthors);




    }
}

