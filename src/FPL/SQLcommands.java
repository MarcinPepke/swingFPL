package FPL;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLcommands {
    DatabaseConnector connector;
    String[] array;
    static Statement stmt = null;
    ResultSet rs;
    ResultSetMetaData rsmd;

    public String[] select(String query) throws IOException, SQLException {
        connector = new DatabaseConnector();
        stmt = connector.getConnection().prepareStatement(query);
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();
        int k = 0;
        array = new String[rsmd.getColumnCount()];
        while(rs.next()){
                array[k] = rs.getString(1);
                k++;
        }

        return array;
    }


}
