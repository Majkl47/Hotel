package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationManagerImpl implements RegistrationManager {
	
	final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
	
    private final DataSource dataSource;

    public RegistrationManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
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
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO REGISTRATION (startDate,endDate,price,guest,room) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            	  
                  st.setDate(1, convertFromJAVADateToSQLDate(registration.getStartDate()));
                  st.setDate(2, convertFromJAVADateToSQLDate(registration.getEndDate()));
                  st.setDouble(3,registration.getPrice());
                  st.setObject(4, registration.getGuest());
                  st.setObject(5, registration.getRoom());

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
            try(PreparedStatement st = conn.prepareStatement("UPDATE guest SET name=?,adress=?,phone=?,bithDate=? WHERE id=?")) {
              
                st.setDate(1,convertFromJAVADateToSQLDate(registration.getStartDate()));
                st.setDate(2,convertFromJAVADateToSQLDate(registration.getEndDate()));
                st.setDouble(3,registration.getPrice());               
                st.setObject(4, registration.getGuest());
                st.setObject(4, registration.getRoom());
                
                if(st.executeUpdate()!=1) {
                    throw new IllegalArgumentException("Failed to execute query - updateGuest - "+registration);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Failed to update registration -", ex);
        }

	}

	public void deleteRegistration(Registration registration) throws DatabaseException {
	    try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("DELETE from registration WHERE id=?")) {
                st.setLong(1,registration.getId());
                if(st.executeUpdate()!=1) {
                    throw new DatabaseException("Failed to delete registration with ID = "+registration.getId());
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
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,startDate,endDate,price,guest,room FROM registration")) {
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
		  Registration registration = new Registration();
		  registration.setId(rs.getLong("id"));
		  registration.setStartDate(rs.getDate("startDate"));
		  registration.setEndDate(rs.getDate("endDate"));
		  registration.setPrice(rs.getDouble("price"));
		 // registration.setGuest(rs.getObject("guest"));
		 // registration.setRoom(rs.getObject("room"));

		  return registration;
	    }
	  
	public Registration getRegistrationById(long id) throws DatabaseException {
		   try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,adress,phone,birthDate FROM registration WHERE id = ?")) {
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
	            log.error("db connection problem", ex);
	            throw new DatabaseException("Error when retrieving all guests", ex);
	        }
	}

	public List<Registration> findRegistrationForGuest(Guest guest) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Registration> findRegistrationForRoom(Room room) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 public static java.sql.Date convertFromJAVADateToSQLDate(
	            java.util.Date javaDate) {
	        java.sql.Date sqlDate = null;
	        if (javaDate != null) {
	            sqlDate = new Date(javaDate.getTime());
	        }
	        return sqlDate;
	    }

}
