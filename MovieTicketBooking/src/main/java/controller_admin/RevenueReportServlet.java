package controller_admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.RevenueDAO;

@WebServlet("/admin/revenue")
public class RevenueReportServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(RevenueReportServlet.class.getName());
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String DEFAULT_DAYS_BACK = "30";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "summary";
		}

		try {
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			LocalDate startDate;
			LocalDate endDate = LocalDate.now();

			if (startDateStr == null || startDateStr.isBlank()) {
				startDate = endDate.minusDays(Integer.parseInt(DEFAULT_DAYS_BACK));
			} else {
				try {
					startDate = LocalDate.parse(startDateStr, dateFormatter);
				} catch (DateTimeParseException e) {
					logger.log(Level.WARNING, "Invalid start date format", e);
					startDate = endDate.minusDays(Integer.parseInt(DEFAULT_DAYS_BACK));
				}
			}

			if (endDateStr != null && !endDateStr.isBlank()) {
				try {
					endDate = LocalDate.parse(endDateStr, dateFormatter);
				} catch (DateTimeParseException e) {
					logger.log(Level.WARNING, "Invalid end date format", e);
				}
			}

			RevenueDAO revenueDAO = new RevenueDAO();

			request.setAttribute("startDate", startDate.format(dateFormatter));
			request.setAttribute("endDate", endDate.format(dateFormatter));

			switch (action) {
				case "daily":
					List<Map<String, Object>> dailyRevenue = revenueDAO.getDailyRevenue(startDate, endDate);
					request.setAttribute("report", dailyRevenue);
					request.setAttribute("reportTitle", "Doanh thu theo ngày");
					request.setAttribute("reportType", "daily");
					break;

				case "movie":
					List<Map<String, Object>> movieRevenue = revenueDAO.getRevenueByMovie(startDate, endDate);
					request.setAttribute("report", movieRevenue);
					request.setAttribute("reportTitle", "Doanh thu theo phim");
					request.setAttribute("reportType", "movie");
					break;

				case "cinema":
					List<Map<String, Object>> cinemaRevenue = revenueDAO.getRevenueBycinema(startDate, endDate);
					request.setAttribute("report", cinemaRevenue);
					request.setAttribute("reportTitle", "Doanh thu theo rạp");
					request.setAttribute("reportType", "cinema");
					break;

				case "payment":
					List<Map<String, Object>> paymentRevenue = revenueDAO.getRevenueByPaymentMethod(startDate, endDate);
					request.setAttribute("report", paymentRevenue);
					request.setAttribute("reportTitle", "Doanh thu theo phương thức thanh toán");
					request.setAttribute("reportType", "payment");
					break;

				case "summary":
				default:
					Map<String, Object> summary = revenueDAO.getTotalRevenue(startDate, endDate);
					List<Map<String, Object>> dailyData = revenueDAO.getDailyRevenue(startDate, endDate);
					List<Map<String, Object>> movieData = revenueDAO.getRevenueByMovie(startDate, endDate);
					List<Map<String, Object>> cinemaData = revenueDAO.getRevenueBycinema(startDate, endDate);
					List<Map<String, Object>> paymentData = revenueDAO.getRevenueByPaymentMethod(startDate, endDate);

					request.setAttribute("summary", summary);
					request.setAttribute("dailyReport", dailyData);
					request.setAttribute("movieReport", movieData);
					request.setAttribute("cinemaReport", cinemaData);
					request.setAttribute("paymentReport", paymentData);
					request.setAttribute("reportTitle", "Tổng quan doanh thu");
					request.setAttribute("reportType", "summary");
					break;
			}

			request.setAttribute("pageView", "/WEB-INF/admin/admin-revenue.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/admin-layout.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in RevenueReportServlet", e);
			request.getSession().setAttribute("error", "Lỗi khi tải báo cáo doanh thu");
			response.sendRedirect(request.getContextPath() + "/admin/dashboard");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("exportCsv".equals(action)) {
			try {
				String startDateStr = request.getParameter("startDate");
				String endDateStr = request.getParameter("endDate");
				String reportType = request.getParameter("reportType");

				LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
				LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);

				RevenueDAO revenueDAO = new RevenueDAO();
				StringBuilder csv = new StringBuilder();

				response.setContentType("text/csv");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"revenue_" + reportType + "_" + startDate + ".csv\"");

				switch (reportType) {
					case "daily":
						csv.append("Ngày,Phim,Rạp,Số vé,Doanh thu\n");
						List<Map<String, Object>> dailyData = revenueDAO.getDailyRevenue(startDate, endDate);
						for (Map<String, Object> row : dailyData) {
							csv.append(row.get("date")).append(",").append(row.get("movieName")).append(",")
									.append(row.get("cinemaName")).append(",").append(row.get("ticketCount"))
									.append(",").append(row.get("revenue")).append("\n");
						}
						break;

					case "movie":
						csv.append("Phim,Thời lượng,Số vé,Doanh thu\n");
						List<Map<String, Object>> movieData = revenueDAO.getRevenueByMovie(startDate, endDate);
						for (Map<String, Object> row : movieData) {
							csv.append(row.get("movieName")).append(",").append(row.get("duration")).append(",")
									.append(row.get("ticketCount")).append(",").append(row.get("revenue")).append("\n");
						}
						break;

					case "cinema":
						csv.append("Rạp,Địa chỉ,Số vé,Doanh thu\n");
						List<Map<String, Object>> cinemaData = revenueDAO.getRevenueBycinema(startDate, endDate);
						for (Map<String, Object> row : cinemaData) {
							csv.append(row.get("cinemaName")).append(",").append(row.get("address")).append(",")
									.append(row.get("ticketCount")).append(",").append(row.get("revenue")).append("\n");
						}
						break;

					case "payment":
						csv.append("Phương thức thanh toán,Số vé,Doanh thu\n");
						List<Map<String, Object>> paymentData = revenueDAO.getRevenueByPaymentMethod(startDate, endDate);
						for (Map<String, Object> row : paymentData) {
							csv.append(row.get("paymentMethod")).append(",").append(row.get("ticketCount")).append(",")
									.append(row.get("revenue")).append("\n");
						}
						break;
				}

				response.getWriter().write(csv.toString());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error exporting revenue data", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error exporting data");
			}
		} else {
			doGet(request, response);
		}
	}
}

