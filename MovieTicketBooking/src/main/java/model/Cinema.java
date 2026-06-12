package model;

import java.util.ArrayList;
import java.util.List;

import model.CinemaStatus;

public class Cinema {
	private int id;
	private String name;
	private String address;
	private CinemaStatus status = CinemaStatus.OPEN;
	private List<Room> rooms;
    
	public Cinema() {}
    
	public Cinema(int id, String name, String address, CinemaStatus status) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.status = status;
		this.rooms = new ArrayList<>();
	}
    
	public Cinema(String name, String address) {
		this.name = name;
		this.address = address;
		this.rooms = new ArrayList<>();
	}

	// Get cinema name and address
	public String getAllInfo() {
		return name + " - " + address;
	}
	
	//Getter and Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public int getId() {
		return id;
	}

	public CinemaStatus getStatus() {
		return status;
	}

	public void setStatus(CinemaStatus status) {
		this.status = status;
	}

	
	
}
