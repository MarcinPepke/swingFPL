package FPL;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Vector;

class tableModel extends AbstractTableModel {
    int columnLength;
    Vector<String[]> vector;
    Vector<String> vector1;
    DatabaseConnector connector;
    Statement stmt = null;

    @Override
    public int getRowCount() {
        return vector.size();
    }

    @Override
    public int getColumnCount() {
        return columnLength;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return vector1.elementAt(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (vector.elementAt(rowIndex))[columnIndex];
    }


    public void setTable(String query, boolean bool) throws IOException, SQLException {
        vector = new Vector<>();
        vector1 = new Vector<>();
        connector = new DatabaseConnector();

        try {
            stmt = connector.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if (!rsmd.getColumnName(i).contains("ID"))
                    vector1.addElement(rsmd.getColumnName(i));
            }
            columnLength = vector1.size();


            while (rs.next()) {
                String[] tmp = new String[rsmd.getColumnCount()];
                if (!Objects.equals(query, "Select footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position, " +
                        "clubs.Name as Club_Name from clubs, footballers inner join clubs as c on c.ID_Club = footballers.ID_Club") && !bool) {
                    for (int i = 2; i <= rsmd.getColumnCount(); i++) {
                        tmp[i - 2] = rs.getString(i);
                    }
                } else if (Objects.equals(query, "Select footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position, " +
                        "clubs.Name as Club_Name from clubs, footballers inner join clubs as c on c.ID_Club = footballers.ID_Club")) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        tmp[i - 1] = rs.getString(i);
                    }
                } else if (!Objects.equals(query, "Select footballers.Name, footballers.Last_name, footballers.Market_value, footballers.Position, " +
                        "clubs.Name as Club_Name from clubs, footballers inner join clubs as c on c.ID_Club = footballers.ID_Club") && bool) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        tmp[i - 1] = rs.getString(i);
                    }
                }
                vector.addElement(tmp);
            }
            if (!bool)
                fireTableChanged(null);
            else
                fireTableDataChanged();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

