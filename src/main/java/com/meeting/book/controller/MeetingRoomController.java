package com.meeting.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.book.service.MeetingRoomService;

@RestController
public class MeetingRoomController {
	
	@Autowired
	MeetingRoomService roomService;
	
	@RequestMapping(value="/listRooms",method=RequestMethod.GET)
	public List<String> getRoomsByBuildingAndFloor(@RequestParam String building,@RequestParam(required=false) String floor,@RequestParam int seats) throws Exception{
		return roomService.getAvailableRooms(building,floor,seats);
	}
	
	@RequestMapping(value="/makeBooking",method=RequestMethod.GET)
	public String scheduleMeeting(@RequestParam String roomNo) throws Exception{
		return roomService.scheduleMeeting(roomNo);
	}
	
	@RequestMapping(value="/cancelBooking",method=RequestMethod.GET)
	public String cancelMeeting(@RequestParam String refId) throws Exception{
		return roomService.cancelMeeting(refId);
	}
	
}
