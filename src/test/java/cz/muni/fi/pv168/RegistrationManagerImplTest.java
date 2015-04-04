package cz.muni.fi.pv168;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistrationManagerImplTest {
	
	
	private RegistrationManagerImpl registrationManager;
	private GuestManagerImpl guestManager;
	private RoomManagerImpl roomManager;
    private DataSource dataSource;

    private Guest g1, g2, g3, guestWithNullId, guestNotInDB;
    private Room r1, r2, r3, roomWithNullId, roomNotInDB;
    private Registration reg1, reg2, reg3, regWithNullId, regNotInDB, regDuplicateId;
    
	@Before
	public void setUp() throws SQLException {
		BasicDataSource bds = new BasicDataSource();
        bds.setUrl("jdbc:derby:memory:RegistrationManagerTest;create=true");
        this.dataSource = bds;

        HotelUtils.executeSQLScript(dataSource, RegistrationManager.class.getResource("createDatabase.sql"));
        registrationManager = new RegistrationManagerImpl(dataSource);
		guestManager = new GuestManagerImpl(dataSource);
		roomManager = new RoomManagerImpl(dataSource);
		prepareTestData();
	}
	
    @SuppressWarnings("deprecation")
	private void prepareTestData() {

        g1 = new Guest();
        g1.setName("Guest1"); g1.setAddress("Address1"); g1.setPhone(0000001); g1.setBirthDate(new Date(2015, 01, 01));
        g2 = new Guest();
        g2.setName("Guest2"); g2.setAddress("Address2"); g2.setPhone(0000002); g2.setBirthDate(new Date(2015, 01, 02));
        g3 = new Guest();
        g3.setName("Guest3"); g3.setAddress("Adsdress3"); g3.setPhone(0000003); g3.setBirthDate(new Date(2015, 01, 03));
        
        guestManager.createGuest(g1);
        guestManager.createGuest(g2);
        guestManager.createGuest(g3);
        
        guestWithNullId = new Guest();
        guestWithNullId.setName("GuestNoID"); guestWithNullId.setAddress("AdressNoID"); 
        guestWithNullId.setPhone(0000004); guestWithNullId.setBirthDate(new Date(2015, 01, 04));
        guestNotInDB = new Guest();
        guestNotInDB.setName("GuestNotInDB"); guestNotInDB.setAddress("AdressNotInDB"); guestNotInDB.setPhone(0000005);
        guestNotInDB.setBirthDate(new Date(2015, 01, 05)); guestNotInDB.setId(g3.getId() + 100);
        
        r1 = new Room();
        r1.setFloor(1); r1.setNumber(1); r1.setCapacity(1);
        r2 = new Room();
        r2.setFloor(2); r2.setNumber(2); r2.setCapacity(2);
        r3 = new Room();
        r3.setFloor(3); r3.setNumber(3); r3.setCapacity(3);

        roomManager.createRoom(r1);
        roomManager.createRoom(r2);
        roomManager.createRoom(r3);
        
        roomWithNullId = new Room();
        roomWithNullId.setFloor(4); roomWithNullId.setNumber(4); roomWithNullId.setCapacity(4);
        roomNotInDB = new Room();
        roomNotInDB.setFloor(5);
        roomNotInDB.setNumber(5);
        roomNotInDB.setCapacity(5); 
        roomNotInDB.setId(r3.getId() + 100);
        
        reg1 = new Registration();
        reg1.setStartDate(new Date(2015, 02, 01)); reg1.setEndDate(new Date(2015, 03, 01)); reg1.setPrice(1000d);
        reg1.setGuest(g1); reg1.setRoom(r1);
        reg2 = new Registration();
        reg2.setStartDate(new Date(2015, 02, 02)); reg2.setEndDate(new Date(2015, 03, 02)); reg2.setPrice(2000d);
        reg2.setGuest(g2); reg2.setRoom(r1);
        reg3 = new Registration();
        reg3.setStartDate(new Date(2015, 02, 03)); reg3.setEndDate(new Date(2015, 03, 03)); reg3.setPrice(3000d);
        reg3.setGuest(g1); reg3.setRoom(r3);
        regDuplicateId = new Registration();
        regDuplicateId.setStartDate(new Date(2015, 02, 06)); regDuplicateId.setEndDate(new Date(2015, 03, 06)); regDuplicateId.setPrice(6000d);
        regDuplicateId.setGuest(g1); regDuplicateId.setRoom(r1); 
        
        registrationManager.createRegistration(reg1);
        registrationManager.createRegistration(reg2);
        registrationManager.createRegistration(reg3);
        registrationManager.createRegistration(regDuplicateId);

        regWithNullId = new Registration();
        regWithNullId.setStartDate(new Date(2015, 02, 04)); regWithNullId.setEndDate(new Date(2015, 03, 04)); regWithNullId.setPrice(4000d);
        regWithNullId.setGuest(g1); regWithNullId.setRoom(r1);
        regNotInDB = new Registration();
        regNotInDB.setStartDate(new Date(2015, 02, 05)); regNotInDB.setEndDate(new Date(2015, 03, 05)); regNotInDB.setPrice(5000d);
        regNotInDB.setGuest(g1); regNotInDB.setRoom(r1); regNotInDB.setId(regWithNullId.getId() + 100);
    }
    
	@After
	public void tearDown() throws SQLException {
        HotelUtils.executeSQLScript(dataSource, RegistrationManager.class.getResource("deleteDatabase.sql"));
	}

	@Test
	public void testCreateRegistrationNull() throws Exception {
		try {
			registrationManager.createRegistration(null);
			fail("NullPointerException not thrown");
		}catch (IllegalArgumentException ex){		
			//exception
		}
	}
	
	@Test
	public void testUpdateRegistrationWithNull() throws Exception {
		try {
			registrationManager.updateRegistration(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (IllegalArgumentException ex){	
			//Test ended as expected
		}
	}
	
	@Test
	public void testDeleteRegistrationWithNull() throws Exception {
		try {
			registrationManager.deleteRegistration(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (IllegalArgumentException ex){
			//Test ended as expected
		}	
	}

	@Test
	public void testFindRegistrationById() throws Exception {
		assertEquals(registrationManager.findRegistrationById(reg1.getId()), reg1);
		assertEquals(registrationManager.findRegistrationById(reg3.getId()), reg3);
		assertEquals(registrationManager.findRegistrationById(reg2.getId()), reg2);
	}
	
	@Test
	public void testFindRegistrationWithDuplicateId() throws Exception {
		try {
			regDuplicateId.setId(registrationManager.findRegistrationById(reg1.getId()).getId());
			registrationManager.updateRegistration(regDuplicateId);
			registrationManager.findRegistrationById(regDuplicateId.getId());
			fail("DatabaseException not thrown after search: findRegistrationById(), value: duplicit id");
		} catch(DatabaseException ex) {
			//Test ended as expected
		}
	}
	
	@Test
	public void testFindRegistrationForGuest() throws Exception {
		assertTrue(registrationManager.findRegistrationForGuest(g1).contains(reg1));
		assertTrue(registrationManager.findRegistrationForGuest(g1).contains(reg3));
		assertNull(registrationManager.findRegistrationForGuest(guestNotInDB));
	}
	
	@Test
	public void testFindRegistrationForRoom() throws Exception {
		assertTrue(registrationManager.findRegistrationForRoom(r1).contains(reg1));
		assertTrue(registrationManager.findRegistrationForRoom(r1).contains(reg2));
		assertTrue(registrationManager.findRegistrationForRoom(r3).contains(reg3));
		assertNull(registrationManager.findRegistrationForRoom(roomNotInDB));
	}
	
	@Test
	public void testFindAllRegistrations() throws Exception {
		assertTrue(registrationManager.findAllRegistrations().contains(reg1));
		assertTrue(registrationManager.findAllRegistrations().contains(reg2));
		assertTrue(registrationManager.findAllRegistrations().contains(reg3));
		assertTrue(registrationManager.findAllRegistrations().contains(regDuplicateId));
		assertEquals(registrationManager.findAllRegistrations().size(), 4);
	}
}
