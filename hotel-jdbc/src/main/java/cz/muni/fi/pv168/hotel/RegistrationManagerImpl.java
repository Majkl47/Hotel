package cz.muni.fi.pv168.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cz.muni.fi.pv168.hotel.RegistrationManager;;


public class RegistrationManagerImpl implements RegistrationManager {
	
	//final static Logger log = LoggerFactory.getLogger(RegistrationManagerImpl.class);
	
    final static Logger log = LoggerFactory.getLogger(RegistrationManagerImpl.class);

    private final DataSource dataSource;

    private JdbcTemplate jdbc;
    private RoomManager roomManager;
    private GuestManager guestManager;
    
    /*public RegistrationManagerImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }*/
    
    public RegistrationManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void setGuestManager(GuestManager roomManager) {
        this.guestManager = guestManager;
    }

    
	public void createRegistration(Registration registration) {
		if (registration == null) {
            throw new IllegalArgumentException("registration is null");
        }
        if (registration.getId() != 0) {
            throw new IllegalArgumentException("registration id is already set");
        }
        if (registration.getStartDate() == null) {
            throw new IllegalArgumentException("registration start date is null");
        }
        if (registration.getEndDate() == null) {
            throw new IllegalArgumentException("registration end date is null");
        }
        if (registration.getPrice() == 0) {
            throw new IllegalArgumentException("registration price is null");
        }
        if (registration.getGuest() == null) {
            throw new IllegalArgumentException("registration guest is null");
        }
        if (registration.getRoom() == null) {
            throw new IllegalArgumentException("registration room is null");
        }
        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO REGISTRATION (startDate,endDate,price,guestID,roomID) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            	  
                  st.setString(1, HotelUtils.convertDateToString(registration.getStartDate()));
                  st.setString(2, HotelUtils.convertDateToString(registration.getEndDate()));
                  st.setDouble(3,registration.getPrice());
                  st.setLong(4, registration.getGuest().getId());
                  st.setLong(5, registration.getRoom().getId());

                  int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new DatabaseException("Wrong number of insert registrations " + registration);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                registration.setId(getKey(keyRS, registration));
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Error when retrieving all registrations", ex);
        }
    }
	
	 private Long getKey(ResultSet keyRS, Registration registration) throws DatabaseException, SQLException {
	        if (keyRS.next()) {
	            if (keyRS.getMetaData().getColumnCount() != 1) {
	                throw new DatabaseException("Failed: wrong number of key fields");
	            }
	            Long result = keyRS.getLong(1);
	            if (keyRS.next()) {
	                throw new DatabaseException("Failed: more keys retrieved than expected");
	            }
	            return result;
	        } else {
	            throw new DatabaseException("Failed: no key found");
	        }
	    }

	public void updateRegistration(Registration registration) throws DatabaseException{
		if(registration==null) throw new IllegalArgumentException("registration pointer is null");
        if(registration.getId() == 0) throw new IllegalArgumentException("registration with null ID cannot be updated");

        if(registration.getStartDate() == null) throw new IllegalArgumentException("registration with null start date cannot be updated");
        if(registration.getEndDate() == null) throw new IllegalArgumentException("registration with null end date cannot be updated");

        if(registration.getPrice() == 0) throw new IllegalArgumentException("registration with price = 0 cannot be updated");
       
        if(registration.getGuest() == null) throw new IllegalArgumentException("registration without guest cannot be updated");
        if(registration.getRoom() == null) throw new IllegalArgumentException("registration without room cannot be updated");

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE registration SET startDate=?,endDate=?,price=?,guestID=?,roomID=? WHERE id=?")) {
              
                st.setString(1, HotelUtils.convertDateToString(registration.getStartDate()));
                st.setString(2, HotelUtils.convertDateToString(registration.getEndDate()));
                st.setDouble(3, registration.getPrice());               
                st.setLong(4, registration.getGuest().getId());
                st.setLong(4, registration.getRoom().getId());
                
                if(st.executeUpdate() != 1) {
                    throw new IllegalArgumentException("Failed to execute query - updateRegistration - " + registration);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Failed to update registration -", ex);
        }

	}

	public void deleteRegistration(Registration registration) throws DatabaseException {
		if(registration==null) throw new IllegalArgumentException("registration pointer is null");
	    try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("DELETE from registration WHERE id=?")) {
                st.setLong(1, registration.getId());
                if(st.executeUpdate() != 1) {
                    throw new DatabaseException("Failed to delete registration with ID = " + registration.getId());
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Failed to execute query - deleteRegistration", ex);
        }

	}

	public List<Registration> findAllRegistrations() {
		 log.debug("finding all registration");
	        try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,startDate,endDate,price,guestID,roomID FROM registration")) {
	                ResultSet rs = st.executeQuery();
	                List<Registration> result = new ArrayList<>();
	                while (rs.next()) {
	                    result.add(resultSetToRegistration(rs));
	                }
	                return result;
	            }
	        } catch (SQLException ex) {
	            log.error("db connection problem", ex);
	            throw new DatabaseException("Error when retrieving all registrations", ex);
	        }
	}


	  private Registration resultSetToRegistration(ResultSet rs) throws SQLException {
		  GuestManagerImpl guestManager = new GuestManagerImpl(dataSource);
		  RoomManagerImpl roomManager = new RoomManagerImpl(dataSource);

		  Registration registration = new Registration();
		  registration.setId(rs.getLong("id"));
		  registration.setStartDate(HotelUtils.convertStringToDate(rs.getString("startDate")));
		  registration.setEndDate(HotelUtils.convertStringToDate(rs.getString("endDate")));
		  registration.setPrice(rs.getDouble("price"));
		  registration.setGuest(guestManager.getGuestById(rs.getLong("guestID")));
		  registration.setRoom(roomManager.findRoomById(rs.getLong("roomID")));

		  return registration;
	    }
	  
	public Registration findRegistrationById(long id) throws DatabaseException {
		   try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,startDate,endDate,price,guestID,roomID FROM registration WHERE id = ?")) {
	                st.setLong(1, id);
	                ResultSet rs = st.executeQuery();
	                if (rs.next()) {
	                	Registration registration = resultSetToRegistration(rs);
	                    if (rs.next()) {
	                        throw new DatabaseException(" Failed: More entities with the same id found");
	                    }
	                    return registration;
	                } else {
	                    return null;
	                }
	            }
	        } catch (SQLException ex) {
	            log.error("DB connection problem", ex);
	            throw new DatabaseException("Error when retrieving all guests", ex);
	        }
	}

	public List<Registration> findRegistrationForGuest(Guest guest) throws DatabaseException {
		if (guest == null) throw new NullPointerException("Input guest not initialized in \"findRegistrationForGuest()\"");

		try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,startDate,endDate,price,guestID,roomID FROM registration WHERE guestID = ?")) {
                st.setLong(1, guest.getId());
                ResultSet rs = st.executeQuery();
                List<Registration> result;
                if(!rs.next()) {
                	return null;
                } else {
                	result = new ArrayList<>();
                	result.add(resultSetToRegistration(rs));
                }
                while (rs.next()) {
                    result.add(resultSetToRegistration(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("DB connection problem", ex);
            throw new DatabaseException("Error when retrieving all guests", ex);
        }
	}

	public List<Registration> findRegistrationForRoom(Room room) {
		if (room == null) throw new NullPointerException("Input room not initialized in \"findRegistrationForRoom()\"");

		try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,startDate,endDate,price,guestID,roomID FROM registration WHERE roomID = ?")) {
                st.setLong(1, room.getId());
                ResultSet rs = st.executeQuery();
                List<Registration> result;
                if (!rs.next())
                {
                	return null;
                } else {
                	result = new ArrayList<>();
                	result.add(resultSetToRegistration(rs));
                }
                while (rs.next()) {
                    result.add(resultSetToRegistration(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("DB connection problem", ex);
            throw new DatabaseException("Error when retrieving all guests", ex);
        }
	}
}
