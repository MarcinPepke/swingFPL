package FPL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;

public class LoginPanel extends JFrame {
    //boolean isConnectedToDatabase = false;
    boolean isAdmin = false;
    boolean isLogged = false;
    JPanel panel;
    JLabel loginLabel;
    JTextField loginField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton button;
    DatabaseConnector connector;

    public LoginPanel() {
        panel = new JPanel();


        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        loginLabel = new JLabel("Username: ");
        loginField = new JTextField(50);

        passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(100);

        panel.setLayout(new GridBagLayout());
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        menu.add(loginLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        menu.add(passwordLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.ipadx = 200;

        menu.add(loginField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.001;
        gridBagConstraints.weightx = 0.001;
        gridBagConstraints.ipadx = 200;
        menu.add(passwordField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        button = new JButton("Sign In");
        //button.setEnabled(isConnectedToDatabase);
        menu.add(button, gridBagConstraints);

        panel.add(menu, gridBagConstraints);
        this.add(panel);
        getRootPane().setDefaultButton(button);
        button.addActionListener(e -> {
            try {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                connector = new DatabaseConnector();
                Statement stmt = connector.getConnection().createStatement();

                try {
                    String query = "SELECT * FROM users";
                    ResultSet rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()) {

                        for (int i = 2; i < rsmd.getColumnCount(); i++) {
                            if (Objects.equals(login.toLowerCase(Locale.ROOT), rs.getString(i).toLowerCase(Locale.ROOT)) && Objects.equals(password, rs.getString(i + 1))) {
                                if (Objects.equals(rs.getString(4), "1")) {
                                    createMainPanel(true);
                                }
                                else {
                                    createMainPanel(isAdmin);
                                }
                            }
                        }
                    }
                    if (!isLogged)
                        JOptionPane.showMessageDialog(null, "Incorrect login or password");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });


    }
    void createMainPanel(boolean isAdmin) throws IOException, SQLException {
        MainPanel mainPanel = new MainPanel(isAdmin);
        isLogged = true;
        JOptionPane.showMessageDialog(null, "Logged in ");
        mainPanel.setTitle("Player Management Platform");
        mainPanel.setVisible(true);
        mainPanel.pack();
        mainPanel.setLocationRelativeTo(null);
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        //mainPanel.setLocation(0, 0);
        //mainPanel.setLayout(null);
        this.setVisible(false);
    }

}

