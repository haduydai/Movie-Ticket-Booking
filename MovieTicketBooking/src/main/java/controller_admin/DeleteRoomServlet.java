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

import dao.RoomDAO;

@WebServlet("/admin/room/delete")
public class DeleteRoomServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(DeleteRoomServlet.class.getName());
	
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
            request.getSession().setAttribute("roomMessage", "ID không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/rooms");
            return;
        }
        HttpSession session = request.getSession();
        try {
            int res = new RoomDAO().deleteRoomById(id);
            session.setAttribute("roomMessage", (res > 0) ? "Xoá phòng thành công!" : "Xoá phòng thất bại!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting room", e);
            session.setAttribute("roomMessage", "Lỗi máy chủ khi xoá phòng. Vui lòng thử lại sau.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.sendRedirect(request.getContextPath() + "/admin/rooms");
	}

}
