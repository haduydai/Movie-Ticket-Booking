package utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DbContextListener implements ServletContextListener {
    private java.util.concurrent.ScheduledExecutorService scheduler;

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


        System.out.println("Starting unpaid ticket cleanup scheduler task...");
        scheduler = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                new dao.TicketDAO().cleanupExpiredTickets();
            } catch (Exception e) {
                System.err.println("[Scheduler] Lỗi khi dọn dẹp vé quá hạn chưa thanh toán: " + e.getMessage());
                e.printStackTrace();
            }
        }, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopping: shutting down Hibernate and JDBC drivers...");


        if (scheduler != null) {
            try {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
                System.out.println("Unpaid ticket cleanup scheduler stopped.");
            } catch (Exception e) {
                System.err.println("Error stopping ticket cleanup scheduler: " + e.getMessage());
            }
        }


        try {
            HibernateUtil.shutdown();
            System.out.println("Hibernate SessionFactory destroyed.");
        } catch (Exception e) {
            System.err.println("Error shutting down Hibernate: " + e.getMessage());
        }


        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL abandoned connection cleanup thread stopped.");
        } catch (Exception e) {
            System.err.println("Error stopping MySQL abandoned connection cleanup thread: " + e.getMessage());
        }


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