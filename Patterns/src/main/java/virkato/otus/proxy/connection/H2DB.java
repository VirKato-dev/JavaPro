package virkato.otus.proxy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class H2DB {
    private static final String url = "jdbc:h2:mem:test";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url);

                Statement stmt = connection.createStatement();
                connection.setAutoCommit(true);
                stmt.execute("CREATE TABLE if not exists users (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), birthday VARCHAR(20))");
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
