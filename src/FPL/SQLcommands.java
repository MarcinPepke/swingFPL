package FPL;

import java.io.IOException;
import java.sql.*;


public class SQLcommands {
    DatabaseConnector connector;
    String[] array;
    static Statement stmt = null;
    ResultSet rs;
    ResultSetMetaData rsmd;

    public String[] select(String query) throws IOException, SQLException {
        connector = new DatabaseConnector();
        stmt = connector.getConnection().prepareStatement(query);
        Statement stm2 = connector.getConnection().prepareStatement(query);
        rs = stmt.executeQuery(query);
        ResultSet tmp = stm2.executeQuery(query);
        rsmd = rs.getMetaData();
        int k = 0;
        int count = 0;
        while(tmp.next()){
            count++;
        }
        array = new String[count];
        while(rs.next()){
                array[k] = rs.getString("Name");
                k++;

        }


        return array;
    }
    public void DeleteSQL(int whichTable, String name) throws IOException, SQLException {
        connector = new DatabaseConnector();
        PreparedStatement preparedStatement;
        String query;

        switch(whichTable){
            case 1:
                 query = "DELETE FROM footballers WHERE ID_player = " + selectID("Select ID_player from footballers where Name = '" + name + "'");
                preparedStatement = connector.getConnection().prepareStatement(query);
                preparedStatement.execute();
                 break;
            case 2:
                query = "DELETE  FROM clubs WHERE ID_club = " + selectID("Select ID_club from clubs where Name ='" +  name + "'");
                preparedStatement = connector.getConnection().prepareStatement(query);
                preparedStatement.execute();
                break;
            case 3:
                query = "DELETE FROM stadiums where ID_stadium = " + selectID("Select ID_stadium from stadiums where Name = '" + name + "'");
                preparedStatement = connector.getConnection().prepareStatement(query);
                preparedStatement.execute();
                break;


        }

    }
    public int selectID(String query) throws IOException, SQLException {
        int ID = 0;
        connector = new DatabaseConnector();
        stmt = connector.getConnection().prepareStatement(query);
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();
        if(rs.next()) {
            ID = rs.getInt(1);
            System.out.println("id --->" + ID);
        }
        return ID;
    }
    public String[] selectTable(String query) throws IOException, SQLException {
        connector = new DatabaseConnector();
        stmt = connector.getConnection().prepareStatement(query);
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();
        array = new String[rsmd.getColumnCount()];
        while(rs.next()){
            for (int i = 1; i <=rsmd.getColumnCount() ; i++) {
                if(!rsmd.getColumnName(i).contains("ID"))
                    array[i-1] = rs.getString(i);
            }
        }

        return array;
    }


}
