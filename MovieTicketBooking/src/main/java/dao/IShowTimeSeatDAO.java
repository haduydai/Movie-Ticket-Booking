package dao;

import java.util.List;

import model.ShowTimeSeat;
import model.User;

public interface IShowTimeSeatDAO {
	List<ShowTimeSeat> getShowTimeSeatsByShowTimeId(int showTimeId);
	List<ShowTimeSeat> getShowTimeSeatsByShowTimeAndUserAndTicket(int showTimeId, int userId, int ticketId);
	void addShowTimeSeats(List<ShowTimeSeat> list);
	void updateShowTimeSeat(int showTimeSeatId, User user, int ticketId);
	List<String> getBookedSeatNames(int showTimeId);
	void bookSeats(List<Integer> showTimeSeatId, User user, int ticketId);
}
