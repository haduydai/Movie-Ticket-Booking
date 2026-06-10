package controller;

import dao.CinemaDAO;
import model.Cinema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/theaters")
public class TheaterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CinemaDAO cinemaDAO = new CinemaDAO();

        // Tìm kiếm rạp theo tên
        String keyword = request.getParameter("keyword");
        List<Cinema> listCinemas;
        if(keyword != null && !keyword.trim().isEmpty()){
            listCinemas = cinemaDAO.searchCinemaByName(keyword);
        }
        else {
            // lấy danh sách tất cả các rạp
            listCinemas = cinemaDAO.getAllCinema();

        }
        // Gửi dữ liệu sang trang JSP
        request.setAttribute("keyword", keyword);
        request.setAttribute("listCinemas", listCinemas);
        
        // Chuyển hướng về giao diện
        request.getRequestDispatcher("/WEB-INF/view/theater.jsp").forward(request, response);
    }
}