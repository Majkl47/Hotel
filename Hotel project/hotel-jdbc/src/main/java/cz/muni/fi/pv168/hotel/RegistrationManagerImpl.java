package cz.muni.fi.pv168.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public class RegistrationManagerImpl implements RegistrationManager {

    final static Logger log = LoggerFactory.getLogger(RegistrationManagerImpl.class);

    private final JdbcTemplate jdbc;
    private RoomManager roomManager;
    private GuestManager guestManager;

    public RegistrationManagerImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void setGuestManager(GuestManager guestManager) {
        this.guestManager = guestManager;
    }

    @Override
    public List<Registration> getRegistrationForGuest(final Guest g) {
        log.info("Registration for guest with id: " + g.getId() + " retrieved");
        return jdbc.query("SELECT * FROM registrations WHERE guestId=?", new RowMapper<Registration>() {
            @Override
            public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
                long roomId = rs.getLong("roomId");
                Room room = null;
                try {
                    room = roomManager.getRoomById(roomId);
                } catch (DatabaseException e) {
                    log.error("cannot find room", e);
                }
                return new Registration(rs.getLong("id"), room, g, HotelUtils.convertStringToDate(rs.getString("startDate")), HotelUtils.convertStringToDate(rs.getString("endDate")), rs.getDouble("price"));
            }
        },
        g.getId());
    }

    @Override
    public void createRegistration(Registration registration) {
        SimpleJdbcInsert insertLease = new SimpleJdbcInsert(jdbc).withTableName("registrations").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("roomId", registration.getRoom().getId());
        parameters.put("guestId", registration.getGuest().getId());
        parameters.put("startDate", HotelUtils.convertDateToString(registration.getStartDate()));
        parameters.put("endDate", HotelUtils.convertDateToString(registration.getEndDate()));
        parameters.put("price", registration.getPrice());
        Number id = insertLease.executeAndReturnKey(parameters);
        registration.setId(id.longValue());
        log.info("New registration created");
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Long> ids = jdbc.queryForList("SELECT id FROM rooms WHERE id NOT IN (SELECT roomId FROM registrations WHERE endDate is null)", Long.class);
        List<Room> rooms = new ArrayList<>(ids.size());
        for (Long id : ids) {
            try {
                rooms.add(roomManager.getRoomById(id));
            } catch (DatabaseException e) {
                log.error("room problem",e);
            }
        }
        log.info("All available rooms retrieved");
        return rooms;
    }
    
    @Override
    public void updateRegistration(Registration r) {
        jdbc.update("UPDATE regustrations set roomId=?,guestId=?,startDate=?,endDate=?,price=? where id=?",
                r.getRoom(), r.getGuest(), HotelUtils.convertDateToString(r.getStartDate()), HotelUtils.convertDateToString(r.getEndDate()), r.getPrice());
        log.info("Registration with id: " + r.getId() + " updated");
    }

    @Override
    public void deleteRegistration(long id) {
        jdbc.update("DELETE FROM registrations WHERE id=?", id);
        log.info("Registration with id: " + id + " deleted");
    }

    private final RowMapper<Registration> registrationMapper = new RowMapper<Registration>() {
        @Override
        public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
            long guestId = rs.getLong("guestId");
            Guest guest = null;
            try {
                guest = guestManager.getGuestById(guestId);
            } catch (DatabaseException e) {
                log.error("cannot find guest for registration", e);
            }
            long roomId = rs.getLong("roomId");
            Room room = null;
            try {
                room = roomManager.getRoomById(roomId);
            } catch (DatabaseException e) {
                log.error("cannot find room for registration", e);
            }
            return new Registration(rs.getLong("id"), room, guest, HotelUtils.convertStringToDate(rs.getString("startDate")), HotelUtils.convertStringToDate(rs.getString("endDate")), rs.getDouble("price"));
        }
    };
    
    @Override
    public List<Registration> getAllRegistrations() {
        log.info("All registrations retrieved");
        return jdbc.query("SELECT * FROM registrations", registrationMapper);
    }

    @Override
    public Registration getRegistrationById(long id) throws DatabaseException {
        try {
            log.info("Registration with id: " + id + "retrieved");
            return jdbc.queryForObject("SELECT * FROM registrations WHERE id=?", registrationMapper, id);
        } catch(DataAccessException e) {
            log.error("cannot select guests", e);
            throw new DatabaseException("database select failed", e);
        }
    }

    @Override
    public List<Registration> getRegistrationForRoom(final Room r) {
        log.info("Registration for room with id: " + r.getId() + " retrived");
        return jdbc.query("SELECT * FROM registrations WHERE roomId=?", new RowMapper<Registration>() {
            @Override
            public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
                long guestId = rs.getLong("guestId");
                Guest guest = null;
                try {
                    guest = guestManager.getGuestById(guestId);
                } catch (DatabaseException e) {
                    log.error("cannot find guest", e);
                }
                return new Registration(rs.getLong("id"), r, guest, HotelUtils.convertStringToDate(rs.getString("startDate")), HotelUtils.convertStringToDate(rs.getString("endDate")), rs.getDouble("price"));
            }
        },
        r.getId());
    }
    
    @Override
    public List<Registration> getRegistrationsByStartDate(final Date date) throws DatabaseException {
        log.info("Registration for start date: " + date.toString() + " retrived");
        return jdbc.query("SELECT * FROM registrations WHERE startDate=?", new RowMapper<Registration>() {
            @Override
            public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
                long guestId = rs.getLong("guestId");
                Guest guest = null;
                try {
                    guest = guestManager.getGuestById(guestId);
                } catch (DatabaseException e) {
                    log.error("cannot find guest", e);
                }
                long roomId = rs.getLong("roomId");
                Room room = null;
                try {
                    room = roomManager.getRoomById(roomId);
                } catch (DatabaseException ex) {
                    log.error("cannot find room", ex);
                }
                return new Registration(rs.getLong("id"), room, guest, date, HotelUtils.convertStringToDate(rs.getString("endDate")), rs.getDouble("price"));
            }
        },
                HotelUtils.convertDateToString(date));
    }
    
    @Override
    public List<Registration> getRegistrationsByEndDate(final Date date) throws DatabaseException {
        log.info("Registration for end date: " + date.toString() + " retrived");
        return jdbc.query("SELECT * FROM registrations WHERE endDate=?", new RowMapper<Registration>() {
            @Override
            public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
                long guestId = rs.getLong("guestId");
                Guest guest = null;
                try {
                    guest = guestManager.getGuestById(guestId);
                } catch (DatabaseException e) {
                    log.error("cannot find guest", e);
                }
                long roomId = rs.getLong("roomId");
                Room room = null;
                try {
                    room = roomManager.getRoomById(roomId);
                } catch (DatabaseException ex) {
                    log.error("cannot find room", ex);
                }
                return new Registration(rs.getLong("id"), room, guest, HotelUtils.convertStringToDate(rs.getString("startDate")), date, rs.getDouble("price"));
            }
        },
                HotelUtils.convertDateToString(date));
    }
    
    @Override
    public List<Registration> getRegistrationsByPrice(final double price) throws DatabaseException {
        log.info("Registration for price: " + price + " retrived");
        return jdbc.query("SELECT * FROM registrations WHERE price=?", new RowMapper<Registration>() {
            @Override
            public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
                long guestId = rs.getLong("guestId");
                Guest guest = null;
                try {
                    guest = guestManager.getGuestById(guestId);
                } catch (DatabaseException e) {
                    log.error("cannot find guest", e);
                }
                long roomId = rs.getLong("roomId");
                Room room = null;
                try {
                    room = roomManager.getRoomById(roomId);
                } catch (DatabaseException ex) {
                    log.error("cannot find room", ex);
                }
                return new Registration(rs.getLong("id"), room, guest, HotelUtils.convertStringToDate(rs.getString("startDate")), HotelUtils.convertStringToDate(rs.getString("endDate")), price);
            }
        },
                price);
    }
}
