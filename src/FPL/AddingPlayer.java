package FPL;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AddingPlayer {
    DatabaseConnector con;
    Statement stmt, tmp = null;
    int i = 0;
    int j = 0;
    int clubID;
    private Object AddingClubPanel;

    public AddingPlayer(String name1, String surname1, String position1, String club1, int marketValue1) throws IOException, SQLException {

        con = new DatabaseConnector();
        stmt = con.getConnection().createStatement();
        tmp = con.getConnection().createStatement();
        tmp.executeQuery("Select * from clubs");
        ResultSet rs = tmp.getResultSet();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next())
            for (int k = 1; k <= rsmd.getColumnCount(); k++) {
                if (Objects.equals(rs.getString(k), club1)) {
                    clubID = rs.getInt("ID_Club");
                    break;
                }

            }
        this.j = stmt.executeUpdate("INSERT INTO footballers(Name,Last_name,Market_value,Position,ID_Club) VALUES('" + name1 + "', '" + surname1 + "', '" + marketValue1 + "', '" + position1 + "'," + clubID + ")");

    }

    public AddingPlayer() {

    }

    public void AddClub(String clubName, int year_of_foundation, float market_value) throws IOException, SQLException {
        con = new DatabaseConnector();
        stmt = con.getConnection().createStatement();
        tmp = con.getConnection().createStatement();
        tmp.executeQuery("Select * from clubs");
        ResultSet rs = tmp.getResultSet();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            for (int k = 1; k <= rsmd.getColumnCount(); k++) {
                if (Objects.equals(rs.getString("Name"), clubName)) {
                    JOptionPane.showMessageDialog((Component) AddingClubPanel, "Taki klub juz istnieje");
                    break;
                }

            }
        }
        stmt.executeUpdate("INSERT INTO clubs(Name,Year_of_foundation,Market_value,ID_Stadium) VALUES('" + clubName + "','" + year_of_foundation + "','" + market_value + "','" + 1 + "')");
    }
}
