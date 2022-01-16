package FPL;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private Connection connection;

    DatabaseConnector() throws IOException {
        try {
            FileOutputStream out = new FileOutputStream("appProperties");
            Properties applicationProps = new Properties();
            applicationProps.setProperty("db.url", "jdbc:mysql://localhost:3306/footballmanager?&serverTimezone=UTC");
            applicationProps.setProperty("db.user", "password");
            applicationProps.store(out, "");
            String dburl = applicationProps.getProperty("db.url");

            this.connection = DriverManager.getConnection(dburl,"root","password");
        } catch (SQLException ex) {
            System.out.println("Connection Failed: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
