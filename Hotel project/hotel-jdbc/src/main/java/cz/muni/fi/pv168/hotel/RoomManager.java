package cz.muni.fi.pv168.hotel;

import java.util.List;

public interface RoomManager {

    List<Room> getAllRooms() throws DatabaseException;

    void createRoom(Room room) throws DatabaseException;

    Room getRoomById(Long id) throws DatabaseException;
    
    List<Room> getRoomsByFloor(int floor) throws DatabaseException;
    
    List<Room> getRoomByNumber(int number) throws DatabaseException;
    
    List<Room> getRoomsByCapacity(int capacity) throws DatabaseException;

    void updateRoom(Room room) throws DatabaseException;

    void deleteRoom(Long id) throws DatabaseException;

}
