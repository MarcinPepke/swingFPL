package FPL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AddingClubPanel extends JFrame {
    JTextField name, year_of_foundation;
    JFormattedTextField market_value;
    JLabel nameLabel, surnameLabel, positionLabel, market_valueLabel;
    JButton submitButton;
    JPanel panel;

    public AddingClubPanel(JLabel statusBar, MainPanel mainPanel) throws IOException {

        JDialog jDialog = new JDialog(mainPanel,true);
        setVisible(false);
        setSize(new Dimension(500, 400));
        setLayout(new BorderLayout());
        panel = new JPanel(new GridBagLayout());
        setResizable(false);
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height/ 2);
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

        nameLabel = new JLabel("Enter club name: ");
        surnameLabel = new JLabel("Enter year of foundation: ");
        market_valueLabel = new JLabel("Enter value of club ");

        name = new JTextField();
        year_of_foundation = new JTextField();
        SQLcommands sql = new SQLcommands();
        market_value = new JFormattedTextField();
        submitButton = new JButton("Enter data");
        final int[] x = {0};
        final int[] y = {0};
        BufferedImage playerImg = ImageIO.read(new File("background.jpg"));
        JLabel imgLabel = new JLabel(new ImageIcon(playerImg));
        imgLabel.setPreferredSize(new Dimension(100, 100));
        imgLabel.setMinimumSize(new Dimension(100, 100));

        submitButton.addActionListener(e -> {
            try {

                if (!name.getText().isEmpty() && !year_of_foundation.getText().isEmpty() && !market_value.getText().isEmpty()) {
                    try {
                        x[0] = Integer.parseInt(market_value.getText());
                        y[0] = Integer.parseInt(year_of_foundation.getText());
                        if (x[0] > 0 && y[0] > 0) {
                            AddingPlayer add = new AddingPlayer();
                            add.AddClub(name.getText(),Integer.parseInt(year_of_foundation.getText()),Integer.parseInt(market_value.getText()));
                            statusBar.setText("Aktualne wydarzenia: dodano klub " + name.getText());

                            this.dispose();
                            mainPanel.enable();

                        }
                    } catch (NumberFormatException f) {
                        JOptionPane.showMessageDialog(this, "Entered NaN");
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
        panel.add(year_of_foundation, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 0, 0, 0, 3, gridBagConstraints);
        panel.add(market_valueLabel, gridBagConstraints);
        MainPanel.GridBag(10, 50, 0, 0, 100, 15, 2, 3, gridBagConstraints);
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
}
