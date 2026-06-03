package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import model.MovieStatus;

// tìm kiếm phim theo thể loại
@WebServlet("/filter-movie")
public class FilterMovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        //  Khởi tạo DAO
        IMovieDAO dao = new MovieDAO();

        // tạo danh sách phim
        List<Movie> movies;

        // Phân loại phim dựa trên thể loại (type)
        if (type != null && !type.trim().isEmpty()) {
            movies = dao.getAllMovies()
        }
        else{
            movies = dao.getMoviesByType();

        }

        request.setAttribute("filteredMovie", top5Upcoming);

        request.getRequestDispatcher("/WEB-INF/view/filter-movie.jsp").forward(request, response);
    }

}