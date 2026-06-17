package controller;

import dao.IMovieDAO;
import dao.MovieDAO;
import model.Movie;
import model.MovieStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showing")
public class MovieShowingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        IMovieDAO dao = new MovieDAO();
        List<Movie> fullListShowing = dao.getMoviesWithStatus(MovieStatus.NOW_SHOWING);
        
        request.setAttribute("fullListShowing", fullListShowing);
        request.getRequestDispatcher("/WEB-INF/view/movieShowing.jsp").forward(request, response);
    }
}