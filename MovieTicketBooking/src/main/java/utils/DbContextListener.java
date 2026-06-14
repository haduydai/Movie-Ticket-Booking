package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DbContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application starting: initializing Hibernate SessionFactory...");
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate SessionFactory initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize Hibernate SessionFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopping: shutting down Hibernate and JDBC drivers...");

        // 1. Đóng Hibernate SessionFactory để giải phóng bộ nhớ
        try {
            HibernateUtil.shutdown();
            System.out.println("Hibernate SessionFactory destroyed.");
        } catch (Exception e) {
            System.err.println("Error shutting down Hibernate: " + e.getMessage());
        }

        // 2. Dừng thread dọn dẹp kết nối của MySQL để tránh rò rỉ bộ nhớ
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL abandoned connection cleanup thread stopped.");
        } catch (Exception e) {
            System.err.println("Error stopping MySQL abandoned connection cleanup thread: " + e.getMessage());
        }

        // 3. Hủy đăng ký JDBC drivers của ứng dụng khỏi Tomcat
        ClassLoader webappClassLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() != webappClassLoader) {
                continue;
            }
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Deregistered JDBC driver: " + driver);
            } catch (SQLException e) {
                System.err.println("Error deregistering JDBC driver " + driver + ": " + e.getMessage());
            }
        }
    }
}