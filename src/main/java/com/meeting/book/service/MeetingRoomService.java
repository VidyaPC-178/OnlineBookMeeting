package com.meeting.book.service;

import java.util.List;

public interface MeetingRoomService {

	public List<String> getAvailableRooms(String building,String floor,int seats) throws Exception;
	public String scheduleMeeting(String roomNo) throws Exception;
	public String cancelMeeting(String refId) throws Exception;
}
