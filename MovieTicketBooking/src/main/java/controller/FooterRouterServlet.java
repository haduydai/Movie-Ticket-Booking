package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = { "/about", "/contact", "/news", "/careers", "/privacy"})
public class FooterRouterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();

		switch (path) {
		case "/about":
			request.getRequestDispatcher("/WEB-INF/view/about.jsp").forward(request, response);
			break;
		case "/contact":
			request.getRequestDispatcher("/WEB-INF/view/contact.jsp").forward(request, response);
			break;
		case "/news":
			request.getRequestDispatcher("/WEB-INF/view/news.jsp").forward(request, response);
			break;
		case "/careers":
			request.getRequestDispatcher("/WEB-INF/view/careers.jsp").forward(request, response);
			break;
		case "/privacy":
			request.getRequestDispatcher("/WEB-INF/view/privacy.jsp").forward(request, response);
			break;
		default:
			response.sendRedirect("home");
			break;
		}
	}
}