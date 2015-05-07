package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RoomManagerImpl implements RoomManager {

    private final DataSource dataSource;
    
    public RoomManagerImpl(DataSource dataSource) {
    	this.dataSource = dataSource;
    }
    
    public void createRoom(Room room) {
    	if (room == null) {
    		throw new IllegalArgumentException("Room is null");
    	}
    	if (room.getId() != null) {
    		throw new IllegalArgumentException("Room id is already set");
    	}
    	if (room.getCapacity() <= 0) {
    		throw new IllegalArgumentException("Room capacity is negative number");
    	}
    	if (room.getFloor() <= 0) {
    		throw new IllegalArgumentException("Room floor is negative number");
    	}
    	if (room.getNumber() <= 0) {
    		throw new IllegalArgumentException("Room number is negative number");
    	}
    	
    	try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO ROOM (floor,number,capacity) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, room.getFloor());
                st.setInt(2, room.getNumber());
                st.setInt(3, room.getCapacity());
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new DatabaseException("Wrong number of inserted rooms" + room);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                room.setId(getKey(keyRS, room));
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error when retrieving all rooms", ex);
        }
	}
    
    private Long getKey(ResultSet keyRS, Room room) throws DatabaseException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new DatabaseException("Failed to retrieve generated key - wrong number of key fields");
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new DatabaseException("Failed to retrieve generated key - more keys retrieved than expected");
            }
            return result;
        } else {
            throw new DatabaseException("Failed to retrieve generated key - no key generated");
        }
    }

	public void updateRoom(Room room) throws DatabaseException {
		if(room == null) throw new IllegalArgumentException("Room pointer is null");
		if(room.getId() == null) throw new IllegalArgumentException("Room with null id cannot be updated");
		if(room.getFloor() < 0) throw new IllegalArgumentException("Room floor is negative number");
		if(room.getNumber() < 0) throw new IllegalArgumentException("Room number is negative number");
		if(room.getCapacity() <= 0) throw new IllegalArgumentException("Room capacity is negative number");

		try (Connection conn = dataSource.getConnection()) {
			try(PreparedStatement st = conn.prepareStatement("UPDATE room SET floor=?,number=?,capacity=? WHERE id=?")) {
				st.setInt(1,room.getFloor());
				st.setInt(2,room.getNumber());
				st.setInt(3,room.getCapacity());
				st.setLong(4,room.getId());
				if(st.executeUpdate()!=1) {
					throw new IllegalArgumentException("Failed to execute query - updateRoom");
				}
			}
		} catch (SQLException ex) {
			throw new DatabaseException("Failed to update room", ex);
		}
	}
	
	public void deleteRoom(Room room) {
		 try (Connection conn = dataSource.getConnection()) {
	            try(PreparedStatement st = conn.prepareStatement("DELETE FROM room WHERE id=?")) {
	                st.setLong(1,room.getId());
	                if(st.executeUpdate()!=1) {
	                    throw new DatabaseException("Failed to delete room");
	                }
	            }
	        } catch (SQLException ex) {
	            throw new DatabaseException("Failed to execute query - deleteRoom", ex);
	        }
	}

	public Room findRoomById(long id) throws DatabaseException {
		try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,floor,number,capacity FROM room WHERE id = ?")) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Room room = resultSetToRoom(rs);
                    if (rs.next()) {
                        throw new DatabaseException("Duplicit id found");
                    }
                    return room;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to retrieve room", ex);
        }
	}

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setFloor(rs.getInt("floor"));
        room.setNumber(rs.getInt("number"));
        room.setCapacity(rs.getInt("capacity"));
        return room;
    }
    
	public List<Room> findAllRooms() {
		try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,floor,number,capacity FROM room")) {
                ResultSet rs = st.executeQuery();
                List<Room> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToRoom(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to execute query - findAllRooms", ex);
        }
	}

}
