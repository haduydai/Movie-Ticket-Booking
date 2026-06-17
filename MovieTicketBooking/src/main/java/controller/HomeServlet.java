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


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MovieDAO dao = new MovieDAO();
        
        List<Movie> moviesShowing = dao.getNMoviesWithStatus(4, MovieStatus.NOW_SHOWING);
        List<Movie> moviesComingSoon = dao.getNMoviesWithStatus(4, MovieStatus.COMING_SOON);

        request.setAttribute("listShowing", moviesShowing);
        request.setAttribute("listUpcoming", moviesComingSoon);
        
        request.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}