package FPL;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class MainPanel extends JFrame {

    JPanel leftpanel, centerPanel, eastPanel, northPanel, southPanel;
    JLabel statusBar;
    JButton addButton, removeButton, modifyButton, playerButton, stadiumButton, clubButton, exitButon;
    JLabel adminField, serachingLabel;
    final JPopupMenu popupMenu = new JPopupMenu();
    int whichTable = 1;
    ArrayList<Integer> rows = new ArrayList<>();
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
        String status = "Aktualne wyderzenie: ";
        statusBar = new JLabel("Aktualne wydarzenie: ");
        centerPanel = new JPanel();
        eastPanel = new JPanel();
        northPanel = new JPanel(new GridBagLayout());
        southPanel = new JPanel();
        tableModel tableModel = new tableModel();
        setLayout(new BorderLayout());
        leftpanel = new JPanel(new GridBagLayout());
        leftpanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        leftpanel.setPreferredSize(new Dimension(240, 200));
        leftpanel.setMaximumSize(new Dimension(240, 200));

        adminField = new JLabel("Zalogowany jako uzytkownik");
        if (isAdmin)
            adminField.setText("Zalogowany jako admin");

        addButton = new JButton("Dodawnie");
        addButton.setEnabled(isAdmin);
        removeButton = new JButton("Usuwanie");
        modifyButton = new JButton("Modyfikacja");
        exitButon = new JButton("Wyjdz z programu");
        playerButton = new JButton("Pilkarze");
        searchingField = new JTextField(10);
        serachingLabel = new JLabel("Wyszukaj piłkarza");
        stadiumButton = new JButton("Stadiony");
        clubButton = new JButton("Kluby");

        JMenuItem jMenuItem = new JMenuItem("Szczegolowe informacje");
        JMenuItem delete = new JMenuItem("Usun");
        popupMenu.add(jMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(delete);

        JPanel playerPanel = new JPanel();

        jMenuItem.addActionListener(e -> {

            JFrame detailsFrame = new JFrame("Szczegółowe informacje");
            detailsFrame.setVisible(true);
            detailsFrame.setLayout(new BorderLayout());
            JPanel tablePanel = new JPanel(new GridBagLayout());
            tablePanel.setPreferredSize(new Dimension(200, 300));
            tablePanel.setMaximumSize(new Dimension(300, 300));
            detailsFrame.setSize(1200, 600);
            detailsFrame.add(playerPanel);
            detailsFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);

        });




        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();


        MainPanel.GridBag(50, 0, 0, 0, 50, 25, 0, 0, gridBagConstraints);
        centerPanel.add(playerButton, gridBagConstraints);


        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 1, 0, gridBagConstraints);
        centerPanel.add(stadiumButton, gridBagConstraints);


        MainPanel.GridBag(50, 50, 0, 0, 50, 25, 2, 0, gridBagConstraints);
        centerPanel.add(clubButton, gridBagConstraints);

        MainPanel.GridBag(5, 10, 0, 0, 50, 50, 3, 0, gridBagConstraints);

        eastPanel.add(searchingField,gridBagConstraints);

        MainPanel.GridBag(5, 10, 0, 0, 10, 10, 1, 0, gridBagConstraints);
        eastPanel.add(serachingLabel,gridBagConstraints);
        JTable table = new JTable(tableModel);

        tableModel.setTable("select footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position\n" +
                "                from clubs c, footballers where c.ID_Club = footballers.ID_Club", false);

        //table.getColumnModel().getColumn(4).setHeaderValue("Club name");

        JScrollPane scrollPane = new JScrollPane(table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playerPanel.removeAll();
                if (SwingUtilities.isRightMouseButton(e)) {

                    ArrayList<String> names = new ArrayList<>();
                    ArrayList<Integer> skills = new ArrayList<>();
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    int row = table.rowAtPoint(e.getPoint());
                    table.getSelectionModel().addSelectionInterval(row, row);
                    GridBagConstraints c = new GridBagConstraints();
                    for (int i = 0; i <= table.getColumnModel().getColumnCount(); i++) {
                        if (table.isRowSelected(i)) {
                            rows.add(i);
                        }
                    }

                    int gridx = 1;
                    playerPanel.setLayout(new GridBagLayout());
                    if (rows.size() > 1 && whichTable == 1) {
                        jMenuItem.setText("Porownaj zawodnikow");
                        for (Integer integer : rows) {
                            names.add(table.getValueAt(integer, 0) + " " + table.getValueAt(integer, 1));
                            for (int i = 0; i <= 5; i++) {
                                skills.add(Integer.parseInt(tmp(i, integer + 1).getText()));
                            }
                        }
                        statusBar.setText(status + " otwarto szczegolowe informacje pilkarzy " + names.toString().substring(1,names.toString().length()-1));
                        for (int j = 0; j < rows.size(); j++) {
                            names.add(table.getValueAt(rows.get(j), 0) + " " + table.getValueAt(rows.get(j), 1));
                            for (int i = 0; i <= 5; i++) {
                                GridBag(10, 10, 0, 0, 20, 20, gridx, i + 1, c);
                                playerPanel.add(tmp(i, rows.get(j) + 1), c);
                            }
                            GridBag(10, 10, 0, 0, 20, 20, gridx, 0, c);
                            playerPanel.add(new JLabel(names.get(j)), c);
                            gridx++;
                        }
                    } else
                        jMenuItem.setText("Szczegolowe statystyki");
                    rows.clear();
                }
            }
        });

        searchingField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Document doc = e.getDocument();
                try {
                    if (whichTable == 1) {
                        String query = "select footballers.Name as N, footballers.Last_name as Ln, footballers.Market_value as Mv, footballers.Position as P \n" +
                                "from  footballers \n" +
                                "where  \n" +
                                "   footballers.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Last_name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Position LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'";
                        tableModel.setTable(query, true);
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                    } else if (whichTable == 2) {
                        String query = "select stadiums.Name as N, stadiums.Year_of_construction as Ln, stadiums.Capacity as Mv \n" +
                                "from stadiums \n" +
                                "" +
                                "where stadiums.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or stadiums.Year_of_construction LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or stadiums.Capacity LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                        tableModel.setTable(query, true);
                    } else if (whichTable == 3) {
                        String query = "select clubs.Name as N, clubs.Year_of_foundation as Ln, clubs.Market_value as Mv \n" +
                                "from clubs \n" +
                                "where \n" +
                                "   clubs.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or clubs.Year_of_foundation LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or clubs.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                        tableModel.setTable(query, true);
                    }

                } catch (BadLocationException | IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Document doc = e.getDocument();
                try {
                    if (whichTable == 1) {
                        String query = "select footballers.Name as N, Last_name as Ln, footballers.Market_value as Mv, footballers.Position as P\n" +
                                "from footballers \n" +
                                "where\n" +
                                "  footballers.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Last_name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or footballers.Position LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'";
                        tableModel.setTable(query, true);
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                    } else if (whichTable == 2) {
                        String query = "select stadiums.Name as N, stadiums.Year_of_construction as Ln, stadiums.Capacity as Mv \n" +
                                "from stadiums \n" +
                                "" +
                                "where stadiums.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or stadiums.Year_of_construction LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or stadiums.Capacity LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                        tableModel.setTable(query, true);
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                    } else if (whichTable == 3) {
                        String query = "select clubs.Name as N, clubs.Year_of_foundation as Ln, clubs.Market_value as Mv \n" +
                                "from clubs \n" +
                                "where \n" +
                                "  clubs.Name LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or clubs.Year_of_foundation LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n" +
                                "   or clubs.Market_value LIKE '%" + e.getDocument().getText(0, doc.getLength()) + "%'\n";
                        tableModel.setTable(query, true);
                        statusBar.setText(status + "wyszukano po nazwie: " + e.getDocument().getText(0,doc.getLength()));
                    }
                } catch (BadLocationException | IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        delete.addActionListener(e -> {
            try {
                String name = (String) table.getValueAt(table.getSelectedRow(),0);
                System.out.println("name --> " + name);
                new SQLcommands().DeleteSQL(whichTable, name);
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(new JFrame(),"Nie mozna usunac elementu tego elementu, sprobuj usunac inny","Error message",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });




        stadiumButton.addActionListener(e -> {
            try {
                tableModel.setTable("select name, year_of_construction, capacity from stadiums;", false);
                whichTable = 2;
                statusBar.setText(status + " wyszukano stadiony");

            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }

        });
        playerButton.addActionListener(e -> {
            try {
                whichTable = 1;
                tableModel.setTable("select distinct footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position " +
                        " from clubs, footballers where clubs.ID_Club = footballers.ID_Club", false);
                statusBar.setText(status + " wyszukano pilkarzy");
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });

        clubButton.addActionListener(e -> {
            try {
                whichTable = 3;
                tableModel.setTable("select name, year_of_foundation, market_value from clubs;", false);
                statusBar.setText(status + " wyszukano kluby");
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        });
        MainPanel.GridBag(0, 0, 0, 0, 500, 500, 0, 1, gridBagConstraints);
        gridBagConstraints.gridwidth = 4;
        centerPanel.add(scrollPane, gridBagConstraints);

        centerPanel.setPreferredSize(new Dimension(700, 700));
        centerPanel.setMaximumSize(new Dimension(800, 800));
        eastPanel.setPreferredSize(new Dimension(150, 150));
        eastPanel.setMaximumSize(new Dimension(150,150));
        northPanel.setPreferredSize(new Dimension(1250, 50));
        southPanel.setPreferredSize(new Dimension(1250, 50));
        //getContentPane().add(leftpanel, BorderLayout.WEST);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        getContentPane().add(eastPanel, BorderLayout.EAST);
        getContentPane().add(new Menu(statusBar,isAdmin,this),BorderLayout.PAGE_START);
        getContentPane().add(statusBar,BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.GRAY);


    }

    JLabel tmp(int start, int playerID) {
        SQLcommands sql = new SQLcommands();
        JLabel player1Labels = new JLabel();
        String[] array;
        String[] attributes = {"Technique", "Speed", "Strength", "Dribble", "Finish", "Set_piece"};
        try {
            array = sql.selectTable("Select Technique,Speed,Strength,Dribble,Finish,Set_piece from attributes where ID_player = " + playerID);
            for (int i = start; i <= start; i++) {
                player1Labels.setOpaque(true);
                player1Labels.setText(/*attributes[i] + " " + */array[i]);
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
        return player1Labels;
    }


}
