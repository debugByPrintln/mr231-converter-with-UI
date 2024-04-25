package org.example.searadar.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSource {
    private final String connectionName = "jdbc:postgresql://localhost:5432/searadar";
    private final String user = "postgres";
    private final String password = "admin";

    private static final ConnectionSource instance = new ConnectionSource();

    public static ConnectionSource instance() {
        return instance;
    }

    public Connection createConnection() throws SQLException {

        return DriverManager.getConnection(connectionName, user, password);
    }

    public ConnectionSource(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
