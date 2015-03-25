package cz.muni.fi.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;


public class GuestManagerImpl implements GuestManager {

	final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
		
    private final DataSource dataSource;

    public GuestManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
     
    //@Override
	public void createGuest(Guest guest) throws ServiceFailureException {

        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }
        if (guest.getId() != 0) {
            throw new IllegalArgumentException("guest id is already set");
        }
        
        if (guest.getName() != null) {
            throw new IllegalArgumentException("guest name is already set");
        }
        
        if (guest.getAdress() != null) {
            throw new IllegalArgumentException("guest adress is already set");
        }
                
        if (guest.getPhone() != 0) {
            throw new IllegalArgumentException("guest phone is already set");
        }
        
        if (guest.getBirthDate() != null) {
            throw new IllegalArgumentException("guest Birthday is already set");
        }

        
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO GUEST (name,adress,phone,birthDay) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            	  st.setString(1,guest.getName());
                  st.setString(2,guest.getAdress());
                  st.setLong(3,guest.getPhone());
                //  st.setDate(4, guest.getBirthDate());
                  int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert guest " + guest);
                }
                ResultSet keyRS = st.getGeneratedKeys();
                guest.setId(getKey(keyRS, guest));
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all graves", ex);
        }
    }
 
	
	 private Long getKey(ResultSet keyRS, Guest guest) throws ServiceFailureException, SQLException {
	        if (keyRS.next()) {
	            if (keyRS.getMetaData().getColumnCount() != 1) {
	                throw new ServiceFailureException("Internal Error: Generated key"
	                        + "retriving failed when trying to insert guest " + guest
	                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
	            }
	            Long result = keyRS.getLong(1);
	            if (keyRS.next()) {
	                throw new ServiceFailureException("Internal Error: Generated key"
	                        + "retriving failed when trying to insert guest " + guest
	                        + " - more keys found");
	            }
	            return result;
	        } else {
	            throw new ServiceFailureException("Internal Error: Generated key"
	                    + "retriving failed when trying to insert guest " + guest
	                    + " - no key found");
	        }
	    }

	public void updateGuest(Guest guest)  throws ServiceFailureException {
		// TODO Auto-generated method stub
	    if(guest==null) throw new IllegalArgumentException("guest pointer is null");
        if(guest.getId() == 0) throw new IllegalArgumentException("guest with null id cannot be updated");
        if(guest.getName()==null) throw new IllegalArgumentException("guest with null name cannot be updated");
        if(guest.getAdress()==null) throw new IllegalArgumentException("guest with null adress cannot be updated");
        if(guest.getPhone()==0) throw new IllegalArgumentException("guest phone is not positive number");

        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement("UPDATE guest SET name=?,adress=?,phone=?,bithDate=? WHERE id=?")) {
              
                st.setString(1,guest.getName());
                st.setString(2,guest.getAdress());
                st.setLong(3,guest.getPhone());
              //  st.setDate(4,guest.getBirthDate());
                
                if(st.executeUpdate()!=1) {
                    throw new IllegalArgumentException("cannot update guest "+guest);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all guests", ex);
        }

	}

		   public void deleteGuest(Guest guest) throws ServiceFailureException {
		        try (Connection conn = dataSource.getConnection()) {
		            try(PreparedStatement st = conn.prepareStatement("DELETE FROM guest WHERE id=?")) {
		                st.setLong(1,guest.getId());
		                if(st.executeUpdate()!=1) {
		                    throw new ServiceFailureException("did not delete guest with id ="+guest.getId());
		                }
		            }
		        } catch (SQLException ex) {
		            log.error("db connection problem", ex);
		            throw new ServiceFailureException("Error when retrieving all guests", ex);
		        }
		   }
	

	public Guest getGuestById(long id) throws ServiceFailureException {
		   try (Connection conn = dataSource.getConnection()) {
	            try (PreparedStatement st = conn.prepareStatement("SELECT id,name,adress,phone,birthDate FROM guest WHERE id = ?")) {
	                st.setLong(1, id);
	                ResultSet rs = st.executeQuery();
	                if (rs.next()) {
	                    Guest guest = resultSetToGuest(rs);
	                    if (rs.next()) {
	                        throw new ServiceFailureException(
	                                "Internal error: More entities with the same id found "
	                                        + "(source id: " + id + ", found " + guest + " and " + resultSetToGuest(rs));
	                    }
	                    return guest;
	                } else {
	                    return null;
	                }
	            }
	        } catch (SQLException ex) {
	            log.error("db connection problem", ex);
	            throw new ServiceFailureException("Error when retrieving all graves", ex);
	        }
	}

	
	  private Guest resultSetToGuest(ResultSet rs) throws SQLException {
		  Guest guest = new Guest();
		  guest.setId(rs.getLong("id"));
		  guest.setName(rs.getString("name"));
		  guest.setAdress(rs.getString("adress"));
		  guest.setPhone(rs.getLong("phone"));
		//  guest.setBirthDate(rs.getBirthDate("birthDate"));
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
	            throw new ServiceFailureException("Error when retrieving all guests", ex);
	        }
	}

}
