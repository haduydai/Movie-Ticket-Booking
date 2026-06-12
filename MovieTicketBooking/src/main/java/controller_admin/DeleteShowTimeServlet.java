package controller_admin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.ShowTimeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/showtime/delete")
public class DeleteShowTimeServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(DeleteShowTimeServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("showtimeMessage", "ID không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/showtimes");
            return;
        }
        HttpSession session = request.getSession();
        try {
            boolean res = new ShowTimeDAO().deleteShowTimeById(id);
            session.setAttribute("showtimeMessage", res ? "Xoá suất chiếu thành công!" : "Xoá suất chiếu thất bại!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting showtime", e);
            session.setAttribute("showtimeMessage", "Lỗi máy chủ khi xoá suất chiếu. Vui lòng thử lại sau.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.sendRedirect(request.getContextPath() + "/admin/showtimes");
	}

}

