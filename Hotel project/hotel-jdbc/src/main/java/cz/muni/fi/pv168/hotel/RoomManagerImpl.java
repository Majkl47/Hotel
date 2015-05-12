package cz.muni.fi.pv168.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomManagerImpl implements RoomManager {

    final static Logger log = LoggerFactory.getLogger(RoomManagerImpl.class);
    private final DataSource dataSource;

    public RoomManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Room> getAllRooms() throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("SELECT id,floor,number,capacity FROM rooms")) {
                ResultSet rs = st.executeQuery();
                List<Room> rooms = new ArrayList<>();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int number = rs.getInt("number");
                    int capacity = rs.getInt("capacity");
                    rooms.add(new Room(id, floor, number, capacity));
                }
                log.info("All rooms retrieved");
                return rooms;
            }
        } catch (SQLException e) {
            log.error("cannot select rooms", e);
            throw new DatabaseException("database select failed", e);
        }
    }

    @Override
    public void createRoom(Room room) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("INSERT INTO rooms (floor,number,capacity) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, room.getFloor());
                st.setInt(2, room.getNumber());
                st.setInt(3, room.getCapacity());
                st.executeUpdate();
                ResultSet keys = st.getGeneratedKeys();
                if (keys.next()) {
                    room.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("cannot insert room", e);
            throw new DatabaseException("database insert failed", e);
        }
        log.info("New room created");
    }

    @Override
    public Room getRoomById(Long searchId) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("select * from rooms where id = ?")) {
                st.setLong(1, searchId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int number = rs.getInt("number");
                    int capacity = rs.getInt("capacity");
                    log.info("Room with id: " + searchId + " retrieved");
                    return new Room(id, floor, number, capacity);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error("cannot select rooms", e);
            throw new DatabaseException("database select failed", e);
        }
    }
    
    @Override
    public List<Room> getRoomsByFloor(int sFloor) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("SELECT id,floor,number,capacity FROM rooms where floor = ?")) {
                st.setInt(1, sFloor);
                ResultSet rs = st.executeQuery();
                List<Room> rooms = new ArrayList<>();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int number = rs.getInt("number");
                    int capacity = rs.getInt("capacity");
                    rooms.add(new Room(id, floor, number, capacity));
                }
                log.info("All rooms retrieved");
                return rooms;
            }
        } catch (SQLException e) {
            log.error("cannot select rooms", e);
            throw new DatabaseException("database select failed", e);
        }
    }
    
    @Override
    public List<Room> getRoomByNumber(int sNumber) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("SELECT id,floor,number,capacity FROM rooms where number = ?")) {
                st.setInt(1, sNumber);
                ResultSet rs = st.executeQuery();
                List<Room> rooms = new ArrayList<>();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int number = rs.getInt("number");
                    int capacity = rs.getInt("capacity");
                    rooms.add(new Room(id, floor, number, capacity));
                }
                log.info("All rooms retrieved");
                return rooms;
            }
        } catch (SQLException e) {
            log.error("cannot select rooms", e);
            throw new DatabaseException("database select failed", e);
        }
    }
    
    @Override
    public List<Room> getRoomsByCapacity(int sCapacity) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("SELECT id,floor,number,capacity FROM rooms where capacity = ?")) {
                st.setInt(1, sCapacity);
                ResultSet rs = st.executeQuery();
                List<Room> rooms = new ArrayList<>();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int number = rs.getInt("number");
                    int capacity = rs.getInt("capacity");
                    rooms.add(new Room(id, floor, number, capacity));
                }
                log.info("All rooms retrieved");
                return rooms;
            }
        } catch (SQLException e) {
            log.error("cannot select rooms", e);
            throw new DatabaseException("database select failed", e);
        }
    }

    @Override
    public void updateRoom(Room room) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("UPDATE rooms SET floor=?,number=?,capacity=? WHERE id=?")) {
                st.setInt(1, room.getFloor());
                st.setInt(2, room.getNumber());
                st.setInt(3, room.getCapacity());   
                st.setLong(4, room.getId());
                int n = st.executeUpdate();
                if (n == 0) {
                    throw new DatabaseException("not updated room with id " + room.getId(), null);
                }
                if (n > 1) {
                    throw new DatabaseException("more than 1 room with id " + room.getId(), null);
                }
            }
        } catch (SQLException e) {
            log.error("cannot update rooms", e);
            throw new DatabaseException("database update failed", e);
        }
        log.info("Room with id: " + room.getId() + " updated");
    }

    @Override
    public void deleteRoom(Long id) throws DatabaseException {
        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement("delete from rooms where id=?")) {
                st.setLong(1, id);
                int n = st.executeUpdate();
                if (n == 0) {
                    throw new DatabaseException("not deleted room with id " + id, null);
                }
            }
        } catch (SQLException e) {
            log.error("cannot delete room", e);
            throw new DatabaseException("database delete failed", e);
        }
        log.info("Room with id: " + id + " deleted");
    }
}
