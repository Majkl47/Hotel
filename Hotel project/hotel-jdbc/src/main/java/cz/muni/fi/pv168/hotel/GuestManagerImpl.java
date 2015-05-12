package cz.muni.fi.pv168.hotel;

import static cz.muni.fi.pv168.hotel.RoomManagerImpl.log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

public class GuestManagerImpl implements GuestManager {

    final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
    private final JdbcTemplate jdbc;    


    public GuestManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
        
    }

    @Override
    public void deleteGuest(long id) {
        log.info("Deleting guest with id: ", id);
        jdbc.update("DELETE FROM guests WHERE id=?", id);
    }

    @Override
    public void updateGuest(Guest g) {
        jdbc.update("UPDATE guests set name=?,address=?,phone=?,birthDate=? where id=?",
                g.getName(), g.getAddress(), g.getPhone(), g.getBirthDate(), g.getId());
        log.info("Guest with id: " + g.getId() + " updated");
    }

    private final RowMapper<Guest> guestMapper = new RowMapper<Guest>() {
        @Override
        public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Guest(rs.getLong("id"),rs.getString("name"),rs.getString("address"),rs.getLong("phone"),HotelUtils.convertStringToDate(rs.getString("birthDate")));
        }
    };

    @Transactional
    @Override
    public List<Guest> getAllGuests() {
        log.info("All guests retrieved");
        return jdbc.query("SELECT * FROM guests", guestMapper);
    }

    @Override
    public Guest getGuestById(long id) throws DatabaseException {
        try {
            log.info("Guest with id: " + id + " retrieved");
            return jdbc.queryForObject("SELECT * FROM guests WHERE id=?", guestMapper, id);
        } catch(DataAccessException e) {
            log.error("cannot select guests", e);
            throw new DatabaseException("database select failed", e);
        }
    }

    @Override
    public void createGuest(Guest g) {
        SimpleJdbcInsert insertGuest = new SimpleJdbcInsert(jdbc).withTableName("guests").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("name", g.getName());
        parameters.put("address", g.getAddress());
        parameters.put("phone", g.getPhone());
        parameters.put("birthDate", HotelUtils.convertDateToString(g.getBirthDate()));
        Number id = insertGuest.executeAndReturnKey(parameters);
        g.setId(id.longValue());
        log.info("New guest created");
    }
    
    
    @Override
    public List<Guest> getGuestsByName(String name) throws DatabaseException {
        log.info("Guests by name retrieved");
        return jdbc.query("SELECT * FROM guests where name=?", guestMapper, name);
    }
    
    @Override
    public List<Guest> getGuestsByAddress(String address) throws DatabaseException {
        log.info("Guests by address retrieved");
        return jdbc.query("SELECT * FROM guests where address=?", guestMapper, address);
    }
    
    @Override
    public List<Guest> getGuestsByPhone(long phone) throws DatabaseException {
        log.info("Guests by phone retrieved");
        return jdbc.query("SELECT * FROM guests where phone=?", guestMapper, phone);
    }
    
    @Override
    public List<Guest> getGuestsByBirthDate(Date date) throws DatabaseException {
        log.info("Guests by BirthDate retrieved");
        return jdbc.query("SELECT * FROM guests where birthdate=?", guestMapper, HotelUtils.convertDateToString(date));
    }
}
