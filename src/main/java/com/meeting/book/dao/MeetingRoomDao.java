package com.meeting.book.dao;

import java.util.List;

public interface MeetingRoomDao {

	public List<String> getAvailableRooms(String building,String floor,int seats) throws Exception;
	public String scheduleMeeting(String meetingRoomNo) throws Exception;
	public String cancelMeeting(String refId) throws Exception;


}
