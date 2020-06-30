package com.meeting.book.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.meeting.book.constants.MeetingRoomConstant;
import com.meeting.book.model.MeetingRoom;
import com.meeting.book.model.ScheduledMeetings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

@Repository
public class MeetingRoomDaoImpl implements MeetingRoomDao {

	@Autowired
	MongoClient mongoClient;

	@Override
	public List<String> getAvailableRooms(String building, String floor, int seats) throws Exception {
		List<String> roomList = new ArrayList<String>();
		MongoCollection<Document> collection = getCollection(MeetingRoomConstant.MEETING_ROOMS);
		if (null != building) {
			if (floor == null) {
				Bson bson = Filters.and(Filters.eq(MeetingRoomConstant.SEATS, seats),
						Filters.eq(MeetingRoomConstant.ISAVAILABLE,true)); 
				Iterable<MeetingRoom> documents = collection.find(bson, MeetingRoom.class);
				roomList = getAvailableRooms(documents, building, true);

			} else {
				String location = building + "-" + floor;
				Bson bson = Filters.and(Filters.eq(MeetingRoomConstant.LOCATION, location),
						Filters.eq(MeetingRoomConstant.SEATS, seats),
						Filters.eq(MeetingRoomConstant.ISAVAILABLE, true));
				Iterable<MeetingRoom> documents = collection.find(bson, MeetingRoom.class);
				roomList = getAvailableRooms(documents, building, false);
			}

		} else
			throw new Exception("Invalid inputs! Try again..");
		if (roomList.isEmpty()) {
			roomList.add("We are sorry,No available rooms for the given criteria");
		}
		return roomList;
	}

	private List<String> getAvailableRooms(Iterable<MeetingRoom> documents, String building, boolean isFloorNull) {
		List<String> roomList = new ArrayList<String>();
		for (MeetingRoom room : documents) {
			if (isFloorNull) {
				if (room.getLocation().contains(building)) {
					roomList.add(room.toString());
				}
			} else {
				roomList.add(room.toString());
			}

		}
		return roomList;
	}

	private MongoCollection<Document> getCollection(String collectionName) {
		MongoDatabase database = mongoClient.getDatabase(MeetingRoomConstant.MEETINGS);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}

	@Override
	public String scheduleMeeting(String meetingRoomNo) throws Exception {
		String msg = "Enter the available meeting room no.";
		if (null != meetingRoomNo) {
			MongoCollection<Document> collection = getCollection(MeetingRoomConstant.MEETING_ROOMS);
			Iterable<MeetingRoom> rooms = collection.find(Filters.eq(MeetingRoomConstant.ISAVAILABLE, true),
					MeetingRoom.class);
			for (MeetingRoom room : rooms) {
				if (room.getMeetingRoomNo().equals(meetingRoomNo)) {
					String refId = bookMeeting(room);
					actionOnMeetingRoom(meetingRoomNo,false);
					msg = "Successfully booked meeting room with reference Id :" + refId;
				}
			}
		} else
			throw new Exception("Meeting Room No. cannot be null");

		return msg;
	}

	private void actionOnMeetingRoom(String meetingRoomNo, boolean availability) {
		MongoCollection<Document> collection = getCollection(MeetingRoomConstant.MEETING_ROOMS);
		Bson filter = Filters.eq(MeetingRoomConstant.MEETING_ROOM_NO, meetingRoomNo);
		Bson update = Updates.set(MeetingRoomConstant.ISAVAILABLE, availability);
		collection.updateOne(filter, update);
	}

	private String bookMeeting(MeetingRoom room) {
		String[] locationArr = room.getLocation().split("-");
		Document document = new Document();
		document.append(MeetingRoomConstant.MEETING_ROOM_NO, room.getMeetingRoomNo());
		document.append(MeetingRoomConstant.BUILDING, Array.get(locationArr, 0));
		document.append(MeetingRoomConstant.FLOOR, Integer.parseInt((String) Array.get(locationArr, 1)));
		document.append(MeetingRoomConstant.DURATION, "1hour"); // by default its 1hour
		InsertOneResult insertResult = getCollection(MeetingRoomConstant.SCHEDULED_MEETINGS).insertOne(document);
		return insertResult.getInsertedId().toString();
	}

	@Override
	public String cancelMeeting(String refId) throws Exception {
		String msg = "Error cancelling the scheduled meeting";
		MongoCollection<Document> collection = getCollection(MeetingRoomConstant.SCHEDULED_MEETINGS);
		Bson deleteFilter = Filters.eq("_id", new ObjectId(refId));
		Iterable<ScheduledMeetings> meetings = collection.find(deleteFilter, ScheduledMeetings.class);
		for (ScheduledMeetings meeting : meetings) {
			if (meeting.getMeetingRoomNo() != null) {
				actionOnMeetingRoom(meeting.getMeetingRoomNo(), true);
			}
			collection.deleteOne(deleteFilter);
			msg = "Meeting cancelled successfully for the reference id :" + refId;
		}

		return msg;
	}

}
