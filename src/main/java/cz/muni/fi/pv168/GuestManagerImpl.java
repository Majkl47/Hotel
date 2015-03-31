package cz.muni.fi.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;


import java.sql.*;
import java.util.ArrayList;


import java.util.List;

public class GuestManagerImpl implements GuestManager {

	final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
		
    private final DataSource dataSource;

    public GuestManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
   
    //@Override
	public void createGuest(Guest guest) throws DatabaseException {

        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }
        if (guest.getId() != 0) {
            throw new IllegalArgumentException("guest id is already set");
        }
        
        if (guest.getName() == null) {
            throw new IllegalArgumentException("guest name is not set");
        }
        
        if (guest.getPhone() <= 0) {
            throw new IllegalArgumentException("guest phone is a negative number");
        }
        
        if (guest.getBirthDate() == null) {
            throw new IllegalArgumentException("guest Birthday is not set");
        }

        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO GUEST (name,adress,phone,birthDay) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            	  st.setString(1,guest.getName());
                  st.setString(2,guest.getAdress());
                  st.setLong(3,guest.getPhone());
                  st.setDate(4, convertFromJAVADateToSQLDate(guest.getBirthDate()));
                  
                  int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new DatabaseException("Wrong number of insert guests " + guest);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                guest.setId(getKey(keyRS, guest));
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Error when retrieving all guests", ex);
        }
    }
 
	
	 private Long getKey(ResultSet keyRS, Guest guest) throws DatabaseException, SQLException {
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

	public void updateGuest(Guest guest)  throws DatabaseException {
		// TODO Auto-generated method stub
	    if(guest==null) throw new IllegalArgumentException("guest pointer is null");
        if(guest.getId() == 0) throw new IllegalArgumentException("guest with null ID cannot be updated");
        if(guest.getName()==null) throw new IllegalArgumentException("guest with null NAME cannot be updated");
        if(guest.getAdress()==null) throw new IllegalArgumentException("guest with null ADRESS cannot be updated");
        if(guest.getPhone()<=0) throw new IllegalArgumentException("guest PHONE is not positive number");

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE guest SET name=?,adress=?,phone=?,bithDate=? WHERE id=?")) {
              
                st.setString(1,guest.getName());
                st.setString(2,guest.getAdress());
                st.setLong(3,guest.getPhone());               
                st.setDate(4, convertFromJAVADateToSQLDate(guest.getBirthDate()));
                
                if(st.executeUpdate()!=1) {
                    throw new IllegalArgumentException("Failed to execute query - updateGuest - "+guest);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new DatabaseException("Failed to update guest -", ex);
        }

	}

		   public void deleteGuest(Guest guest) throws DatabaseException {
		        try (Connection conn = dataSource.getConnection()) {
		            try(PreparedStatement st = conn.prepareStatement("DELETE FROM guest WHERE id=?")) {
		                st.setLong(1,guest.getId());
		                if(st.executeUpdate()!=1) {
		                    throw new DatabaseException("Failed to delete guest with ID = "+guest.getId());
		                }
		            }
		        } catch (SQLException ex) {
		            log.error("db connection problem", ex);
		            throw new DatabaseException("Failed to execute query - deleteGuest", ex);
		        }
		   }
	

	public Guest getGuestById(long id) throws DatabaseException {
		   try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,adress,phone,birthDate FROM guest WHERE id = ?")) {
	                st.setLong(1, id);
	                ResultSet rs = st.executeQuery();
	                if (rs.next()) {
	                    Guest guest = resultSetToGuest(rs);
	                    if (rs.next()) {
	                        throw new DatabaseException(" Failed: More entities with the same id found");
	                    }
	                    return guest;
	                } else {
	                    return null;
	                }
	            }
	        } catch (SQLException ex) {
	            log.error("db connection problem", ex);
	            throw new DatabaseException("Error when retrieving all guests", ex);
	        }
	}

	
	  private Guest resultSetToGuest(ResultSet rs) throws SQLException {
		  Guest guest = new Guest();
		  guest.setId(rs.getLong("id"));
		  guest.setName(rs.getString("name"));
		  guest.setAdress(rs.getString("adress"));
		  guest.setPhone(rs.getLong("phone"));
		  guest.setBirthDate(rs.getDate("birthDate"));
		  return guest;
	    }
	  
	public List<Guest> findAllGuests() {
		   log.debug("finding all guests");
	        try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,adress,phone,birthDate FROM guest")) {
	                ResultSet rs = st.executeQuery();
	                List<Guest> result = new ArrayList<>();
	                while (rs.next()) {
	                    result.add(resultSetToGuest(rs));
	                }
	                return result;
	            }
	        } catch (SQLException ex) {
	            log.error("db connection problem", ex);
	            throw new DatabaseException("Error when retrieving all guests", ex);
	        }
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
