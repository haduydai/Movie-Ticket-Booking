package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JDBCConnection {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/movie_ticket_booking";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER);
        config.setPassword(PASS);
        config.setDriverClassName(DRIVER_NAME);
        
        config.setMaximumPoolSize(15);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
    private JDBCConnection() { }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
