package controller_admin;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.UserDAO;


@WebServlet("/admin/user/delete")
public class DeleteAccountServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(DeleteAccountServlet.class.getName());

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
            request.getSession().setAttribute("userMessage", "ID không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }
        HttpSession session = request.getSession();
        try {
            boolean res = new UserDAO().deleteUser(id);
            session.setAttribute("userMessage", (res) ? "Xoá user thành công!" : "Xoá user thất bại!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting user", e);
            session.setAttribute("userMessage", "Lỗi máy chủ khi xoá user. Vui lòng thử lại sau.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
	}

}
