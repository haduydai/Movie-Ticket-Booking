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

            transaction = session.beginTransaction();


            chain.doFilter(request, response);


            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Transaction rolled back due to error: " + e.getMessage());
            throw e;
        }
    }
}