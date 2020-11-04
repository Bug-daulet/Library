package Classes;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DB {
    private static DB instance;

    private DB() {}

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public Connection getConnection() {
        InitialContext initialContext;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup("java:comp/env/jdbc/myOracle");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
