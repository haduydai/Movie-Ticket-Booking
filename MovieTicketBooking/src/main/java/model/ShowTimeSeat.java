package model;

import java.time.LocalDateTime;

public class ShowTimeSeat {
	private int id;
	private String seatName;
	private Room room;
	private User bookedBy;
	private ShowTime showTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private boolean vip;
	private double price;
	public ShowTimeSeat() {}
	
	// contructor use for get data from db
	public ShowTimeSeat(int id, String seatName, Room room, User bookedBy, ShowTime showTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.seatName = seatName;
		this.room = room;
		this.bookedBy = bookedBy;
		this.showTime = showTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	// constructor use for user take show time seat (user can be null)
	public ShowTimeSeat(String seatName, Room room, User bookedBy, ShowTime showTime) {
		this.seatName = seatName;
		this.room = room;
		this.bookedBy = bookedBy;
		this.showTime = showTime;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = createdAt;
	}
	
	// constructor use for create show time seat
		public ShowTimeSeat(String seatName, Room room, ShowTime showTime) {
			this.seatName = seatName;
			this.room = room;
			this.bookedBy = null;
			this.showTime = showTime;
			this.createdAt = LocalDateTime.now();
			this.updatedAt = createdAt;
		}
	
	//Getter and Setter
	public int getUserId() {
		return bookedBy.getId();
	}
	
	public int getShowTimeId() {
		return showTime.getId();
	}
	
	//Get seat name
	public String getSeatName() {
		return seatName;
	}

	
	public void setSeat(String seatName) {
		this.seatName = seatName;
	}

	public User getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(User bookedBy) {
		this.bookedBy = bookedBy;
	}

	public ShowTime getShowTime() {
		return showTime;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Room getRoom() {
		return room;
	}
	
	public boolean isVip(){
		return vip;
	}
	public void setVip(boolean vip){
		this.vip = vip;
	}
	public void setPrice(doubl3e price){
		this.price = price;
	}
	
}
