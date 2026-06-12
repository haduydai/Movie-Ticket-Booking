package utils;

import jakarta.servlet.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.IOException;

public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;

        try {
            //1. Bắt đầu Transaction trước khi xử lý request
            transaction = session.beginTransaction();

            //2.Cho phép request đi tiếp qua các servlet/filter khác
            chain.doFilter(request, response);

            // 3.Commit nếu không xảy ra lỗi và transaction vẫn đang hoạt động
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        } catch (Exception e) {
            //  4. Rollback nếu xảy ra bất kỳ lỗi gì
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Transaction rolled back due to error: " + e.getMessage());
            throw e;
        }
    }
}