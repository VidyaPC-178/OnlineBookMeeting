package com.meeting.book.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="meeting_rooms")
public class MeetingRoom {

    String meetingRoomNo;
    String roomName;
	String location;
	int seats;
	String isAvailable;
	
	public String getMeetingRoomNo() {
		return meetingRoomNo;
	}
	public void setMeetingRoomNo(String meetingRoomNo) {
		this.meetingRoomNo = meetingRoomNo;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String isAvailable() {
		return isAvailable;
	}
	public void setAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Override
	public String toString() {
		return "Available Meeting Rooms [meetingRoomNo=" + meetingRoomNo + ", roomName=" + roomName + ", location=" + location
				+ ", seats=" + seats+"]";
	}
	
	
}
