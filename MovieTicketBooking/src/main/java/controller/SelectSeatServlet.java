package controller;

import dao.ShowTimeDAO;
import dao.ShowTimeSeatDAO;
import model.ShowTime;
import model.ShowTimeSeat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/select-seat" )
public class SelectSeatServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int showtimeId = Integer.parseInt(request.getParameter("showtimeId"));
			

			new dao.TicketDAO().cleanupExpiredTickets();
			
			ShowTimeDAO stDao = new ShowTimeDAO();
			ShowTimeSeatDAO stsDao = new ShowTimeSeatDAO();

			ShowTime showTime = stDao.getShowTimeById(showtimeId);
			

			List<ShowTimeSeat> seatList = stsDao.getShowTimeSeatsByShowTimeId(showtimeId);

			java.util.Map<String, String> seatTypeMap = new java.util.HashMap<>();
			try (java.sql.Connection conn = dao.JDBCConnection.getConnection();
				 java.sql.PreparedStatement ps = conn.prepareStatement("SELECT row_label, column_number, seat_type FROM seats WHERE room_id = ?")) {
				ps.setInt(1, showTime.getRoom().getId());
				try (java.sql.ResultSet rs2 = ps.executeQuery()) {
					while (rs2.next()) {
						String key = rs2.getString("row_label").trim() + rs2.getInt("column_number");
						seatTypeMap.put(key, rs2.getString("seat_type"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			double basePrice = showTime.getPricePerTicket().doubleValue();
			for(ShowTimeSeat seat : seatList){
				String seatName = seat.getSeatName().trim();
				String type = seatTypeMap.get(seatName);
				if(type == null) {
					char row = seatName.charAt(0);
					if(row == 'A' || row == 'B') {
						type = "VIP";
					} else {
						type = "REGULAR";
					}
				}
				
				if("VIP".equalsIgnoreCase(type)) {
					seat.setVip(true);
					seat.setPrice(basePrice + 20000); // VIP seat costs base + 20k
				} else if("SWEETBOX".equalsIgnoreCase(type)) {
					seat.setVip(true);
					seat.setPrice(basePrice + 40000); // Sweetbox costs base + 40k
				} else {
					seat.setVip(false);
					seat.setPrice(basePrice); // Regular seat costs base price
				}
			}
			

			List<String> alphabet = new ArrayList<>();
			char c = 'A';
			for(int i = 0; i < showTime.getRoom().getNumberOfRows(); i++) {
				alphabet.add( ((char)(c + i)) + "");
			}
			request.setAttribute("cols", showTime.getRoom().getNumberOfColumns());
			request.setAttribute("alphabet", alphabet);
			request.setAttribute("showTime", showTime);
			request.setAttribute("seatList", seatList);

			request.getRequestDispatcher("/WEB-INF/view/select-seat.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("home");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}
