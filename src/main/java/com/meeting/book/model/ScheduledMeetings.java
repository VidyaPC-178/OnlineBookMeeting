package com.meeting.book.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="scheduled_meetings")
public class ScheduledMeetings {
	
	String meetingRoomNo;
	String building;
	int floor;
	String duration;
	public String getMeetingRoomNo() {
		return meetingRoomNo;
	}
	public void setMeetingRoomNo(String meetingRoomNo) {
		this.meetingRoomNo = meetingRoomNo;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "ScheduledMeetings [meetingRoomNo=" + meetingRoomNo + ", building=" + building + ", floor=" + floor + ", duration="
				+ duration + "]";
	}
}
