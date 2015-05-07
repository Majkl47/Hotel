package cz.muni.fi.pv168.hotel;

import java.util.List;

public interface RoomManager {

    List<Room> getAllRooms() throws DatabaseException;

    void createRoom(Room room) throws DatabaseException;

    Room getRoomById(Long id) throws DatabaseException;

    void updateRoom(Room room) throws DatabaseException;

    void deleteRoom(Long id) throws DatabaseException;

}
