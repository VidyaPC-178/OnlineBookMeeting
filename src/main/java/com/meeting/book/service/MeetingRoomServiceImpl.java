package com.meeting.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.book.dao.MeetingRoomDao;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

	@Autowired
	MeetingRoomDao roomDao;
	
	@Override
	public List<String> getAvailableRooms(String building,String floor,int seats) throws Exception {
		return roomDao.getAvailableRooms(building,floor,seats);
	}

	@Override
	public String scheduleMeeting(String roomNo) throws Exception {
		return roomDao.scheduleMeeting(roomNo);

	}

	@Override
	public String cancelMeeting(String refId) throws Exception {
		return roomDao.cancelMeeting(refId);
	}

}
