package FPL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AddingPlayerPanel extends JFrame {
    JTextField name, surname, position;
    JButton refresh;
    JComboBox<String> club;
    JFormattedTextField market_value;
    JLabel nameLabel, surnameLabel, positionLabel, market_valueLabel, clubLabel;
    JButton submitButton;
    JPanel panel;


    public AddingPlayerPanel(JLabel statusBar, MainPanel mainPanel) throws IOException, SQLException {

      JDialog jDialog = new JDialog(mainPanel,true);
        setVisible(false);
       /* NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setMaximum(1000);
        numberFormatter.setAllowsInvalid(false);*/
        setLayout(new BorderLayout());
        panel = new JPanel(new GridBagLayout());
        setResizable(false);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        jDialog.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
        JPanel leftpanel = new JPanel();
        leftpanel.setPreferredSize(new Dimension(100, 100));
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(600, 600));
        centerPanel.setMinimumSize(new Dimension(600, 600));
        JPanel nortPanel = new JPanel();
        nortPanel.setPreferredSize(new Dimension(800, 100));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        nameLabel = new JLabel("Enter player name: ");
        surnameLabel = new JLabel("Enter player second name: ");
        market_valueLabel = new JLabel("Enter player market value: ");
        positionLabel = new JLabel("Enter player position: ");
        clubLabel = new JLabel("Enter player club: ");
        refresh = new JButton("Odswiez kluby");

        name = new JTextField();
        surname = new JTextField();
        position = new JTextField();
        market_value = new JFormattedTextField();
        SQLcommands sql = new SQLcommands();

        club = new JComboBox<>(sql.select("select Name from clubs"));
        final DefaultComboBoxModel model = new DefaultComboBoxModel(sql.select("select Name from clubs"));
        club.setModel(model);
        club.addItem("Dodaj klub");
        club.addActionListener(e -> {
            if (Objects.equals(club.getSelectedItem(), "Dodaj klub")) {
                try {
                    new AddingClubPanel(statusBar,mainPanel);
                    //model = new DefaultComboBoxModel(sql.select("select Name from clubs"));
                    club.removeAllItems();
                    club.setModel(model);
                    //club.removeAllItems();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        submitButton = new JButton("Enter data");
        final int[] x = {0};
        BufferedImage playerImg = ImageIO.read(new File("background.jpg"));
        JLabel imgLabel = new JLabel(new ImageIcon(playerImg));
        imgLabel.setPreferredSize(new Dimension(100, 100));
        imgLabel.setMinimumSize(new Dimension(100, 100));

        refresh.addActionListener( e ->{
            club.revalidate();
            club.repaint();
        });
        submitButton.addActionListener(e -> {
            try {

                if (!name.getText().isEmpty() && !surname.getText().isEmpty() && !position.getText().isEmpty() && club.getSelectedIndex() != -1 && !market_value.getText().isEmpty()) {
                    try {
                        x[0] = Integer.parseInt(market_value.getText());
                        if (x[0] > 0) {
                            new AddingPlayer(name.getText(), surname.getText(), position.getText(), Objects.requireNonNull(club.getSelectedItem()).toString(), Integer.parseInt(market_value.getText()));
                            statusBar.setText("Aktualne wydarzenia: dodano pilkarza " + name.getText() + " " + surname.getText());
                            this.dispose();
                            jDialog.dispose();
                        }
                    } catch (NumberFormatException f) {
                        JOptionPane.showMessageDialog(this, "Entered NaN in market value");
                    }
                } else
                    JOptionPane.showMessageDialog(this, "Entered wrong data");
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 0, gridBagConstraints);
        panel.add(imgLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 1, gridBagConstraints);
        panel.add(nameLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 1, gridBagConstraints);
        panel.add(name, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 2, gridBagConstraints);
        panel.add(surnameLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 2, gridBagConstraints);
        panel.add(surname, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 3, gridBagConstraints);
        panel.add(clubLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 3, gridBagConstraints);
        panel.add(club, gridBagConstraints);
        MainPanel.GridBag(10,50,0,0,0,0,1,3,gridBagConstraints);
        panel.add(refresh,gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 4, gridBagConstraints);
        panel.add(positionLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 4, gridBagConstraints);
        panel.add(position, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 5, gridBagConstraints);
        panel.add(market_valueLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 5, gridBagConstraints);
        panel.add(market_value, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 30, 30, 3, 3, gridBagConstraints);
        panel.add(submitButton, gridBagConstraints);

        add(leftpanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(nortPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.WEST);
        pack();
        jDialog.getContentPane().add(panel);
        jDialog.pack();
        jDialog.setVisible(true);
        setLocationRelativeTo(null);



    }
/*    public String[] combo() {
        SQLcommands sql = new SQLcommands();

        return sql.select()
    }*/
}
