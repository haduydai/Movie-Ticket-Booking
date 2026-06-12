package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

import java.io.IOException;
import java.util.List;

import dao.IMovieDAO;
import dao.MovieDAO;

@WebServlet("/search")
public class SearchMovieServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Tìm kiếm phim theo tên
		String keyword = request.getParameter("keyword");
		IMovieDAO dao = new MovieDAO();
		List<Movie> movies = dao.getMoviesHaveNameLikeKeyword(keyword);
		
		request.setAttribute("searchResults", movies);
		request.getRequestDispatcher("/WEB-INF/view/search-result.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
