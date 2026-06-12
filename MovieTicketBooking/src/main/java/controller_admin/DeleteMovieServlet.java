package controller_admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.IMovieDAO;
import dao.MovieDAO;

@WebServlet("/admin/movie/delete")
public class DeleteMovieServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(DeleteMovieServlet.class.getName());

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
            request.getSession().setAttribute("movieMessage", "ID không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/movies");
            return;
        }

        HttpSession session = request.getSession();
        try {
            IMovieDAO movieDAO = new MovieDAO();
            int update = movieDAO.deleteMovieById(id);
            if (update > 0) {
                session.setAttribute("movieMessage", "Xoá phim thành công!");
            } else {
                session.setAttribute("movieMessage", "Xoá thất bại!");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting movie", e);
            session.setAttribute("movieMessage", "Lỗi máy chủ khi xoá phim. Vui lòng thử lại sau.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.sendRedirect(request.getContextPath() + "/admin/movies");
	}

}
