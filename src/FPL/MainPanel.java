package FPL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class MainPanel extends JFrame {
    JPanel leftpanel, centerPanel, eastPanel, northPanel, southPanel;
    JButton addButton, removeButton, modifyButton, playerButton, stadiumButton, clubButton, exitButon;
    JLabel adminField;
    JTable table;
    JTextField searchingField;

    static void GridBag(int top, int left, int bottom, int right, int padx, int pady, int gridx, int gridy, GridBagConstraints gridBagConstraints) {

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(top, left, bottom, right);
        gridBagConstraints.ipadx = padx;
        gridBagConstraints.ipady = pady;
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;

    }

    public MainPanel(boolean isAdmin) throws IOException, SQLException {
        centerPanel = new JPanel();
        eastPanel = new JPanel();
        northPanel = new JPanel(new GridBagLayout());
        southPanel = new JPanel();
        setLayout(new BorderLayout());
        /*ImageIcon img =  new ImageIcon("background.jpg");
        JLabel background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        add(background);*/
        leftpanel = new JPanel(new GridBagLayout());
        leftpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftpanel.setPreferredSize(new Dimension(250, 250));
        leftpanel.setMaximumSize(new Dimension(250, 250));
        adminField = new JLabel("Zalogowany jako uzytkownik");
        if (isAdmin)
            adminField.setText("Zalogowany jako admin");

        addButton = new JButton("Dodawnie");
        addButton.setEnabled(isAdmin);
        removeButton = new JButton("Usuwanie");
        modifyButton = new JButton("Modyfikacja");
        exitButon = new JButton("Wyjdz z programu");
        playerButton = new JButton("Pilkarze");
        searchingField = new JTextField();
        stadiumButton = new JButton("Stadiony");
        clubButton = new JButton("Kluby");
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 0, 0, gridBagConstraints);
        leftpanel.add(addButton, gridBagConstraints);

        MainPanel.GridBag(5, 10, 0, 0, 50, 50, 0, 0, gridBagConstraints);
        northPanel.add(adminField);

        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 0, 1, gridBagConstraints);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        leftpanel.add(removeButton, gridBagConstraints);

        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 0, 2, gridBagConstraints);
        leftpanel.add(modifyButton, gridBagConstraints);


        MainPanel.GridBag(50, 0, 0, 0, 50, 25, 0, 0, gridBagConstraints);
        centerPanel.add(playerButton, gridBagConstraints);


        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 1, 0, gridBagConstraints);
        centerPanel.add(stadiumButton, gridBagConstraints);


        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 2, 0, gridBagConstraints);
        centerPanel.add(clubButton, gridBagConstraints);

        MainPanel.GridBag(5, 200, 0, 0, 50, 10, 10, 0, gridBagConstraints);
        northPanel.add(searchingField, gridBagConstraints);


        tableModel tableModel = new tableModel();


        JTable table = new JTable(tableModel);

        tableModel.setTable("Select footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position, " +
                "clubs.Name as Club_Name from clubs, footballers inner join clubs as c on c.ID_Club = footballers.ID_Club", false);

        table.getColumnModel().getColumn(4).setHeaderValue("Club name");

        JScrollPane scrollPane = new JScrollPane(table);

        searchingField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Document doc = e.getDocument();
                try {
                    String query = "select footballers.Name as N, Last_name as Ln, footballers.Market_value as Mv, footballers.Position as P, clubs.Name \n" +
                            "from clubs, footballers \n" +
                            "inner join clubs c on c.ID_Club = footballers.ID_Club \n" +
                            "where footballers.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Last_name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Position LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'" +
                            " or c.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                    tableModel.setTable(query, true);

                } catch (BadLocationException | IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Document doc = e.getDocument();
                try {
                    String query = "select footballers.Name as N, Last_name as Ln, footballers.Market_value as Mv, footballers.Position as P, clubs.Name \n" +
                            "from clubs, footballers \n" +
                            "inner join clubs c on c.ID_Club = footballers.ID_Club \n" +
                            "where footballers.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Last_name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                            "   or footballers.Position LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'" +
                            " or c.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                    tableModel.setTable(query, true);

                } catch (BadLocationException | IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        addButton.addActionListener(e -> {
            JFrame tmpFrame = new JFrame("Adding choice");
            tmpFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
            tmpFrame.setLayout(new BorderLayout());
            tmpFrame.setSize(200, 200);
            tmpFrame.setPreferredSize(new Dimension(200, 200));
            tmpFrame.setMaximumSize(new Dimension(200, 200));
            JPanel tmpPanel = new JPanel(new GridBagLayout());
            tmpPanel.setPreferredSize(new Dimension(100, 100));
            GridBagConstraints gbc = new GridBagConstraints();
            GridBag(10, 0, 0, 0, 0, 0, 0, 0, gbc);
            tmpFrame.setVisible(true);
            JButton player1 = new JButton("Add player");
            JButton club1 = new JButton("Add club");
            JButton stadium1 = new JButton("Add stadium");
            tmpPanel.add(player1, gbc);
            GridBag(10, 0, 0, 0, 0, 0, 0, 1, gbc);
            player1.addActionListener(f -> {
                try {
                    new AddingPlayerPanel();
                    tmpFrame.dispose();
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            });
            club1.addActionListener(f -> {

            });
            stadium1.addActionListener(f -> {
                try {
                    new AddingStadiumsPanel();
                } catch (IOException | SQLException ignored) {
                    ignored.printStackTrace();
                }
            });
            tmpPanel.add(club1, gbc);
            GridBag(10, 0, 0, 0, 0, 0, 0, 2, gbc);
            tmpPanel.add(stadium1, gbc);
            tmpFrame.add(tmpPanel, BorderLayout.CENTER);
            //new AddingPlayerPanel();

        });

        modifyButton.addActionListener(e -> {

        });
        stadiumButton.addActionListener(e -> {
            try {
                tableModel.setTable("Select * from stadiums", false);


            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }

        });
        playerButton.addActionListener(e -> {
            try {
                tableModel.setTable("Select * from footballers", false);
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });

        clubButton.addActionListener(e -> {
            try {

                tableModel.setTable("Select * from clubs", false);
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });
        MainPanel.GridBag(0, 0, 0, 0, 500, 500, 0, 1, gridBagConstraints);
        gridBagConstraints.gridwidth = 4;
        centerPanel.add(scrollPane, gridBagConstraints);

        centerPanel.setPreferredSize(new Dimension(700, 700));
        centerPanel.setMaximumSize(new Dimension(800, 800));
        eastPanel.setPreferredSize(new Dimension(300, 300));
        northPanel.setPreferredSize(new Dimension(1250, 50));
        southPanel.setPreferredSize(new Dimension(1250, 50));
        getContentPane().add(leftpanel, BorderLayout.WEST);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        getContentPane().add(eastPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);


    }


}
