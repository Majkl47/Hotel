package cz.muni.fi.pv168.hotel;

import java.util.List;

public interface RoomManager {

	void createRoom(Room room);

	void updateRoom(Room room);

	void deleteRoom(Room room);

	Room findRoomById(long id);

	List<Room> findAllRooms();

}