package cz.muni.fi.pv168;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class) //Spring se zúčastní unit testů
@ContextConfiguration(classes = {MySpringTestConfig.class}) //konfigurace je ve třídě MySpringTestConfig
@Transactional //každý test poběží ve vlastní transakci, která bude na konci rollbackována

public class RoomManagerImplTest {

	private RoomManagerImpl roomManager;
    private DataSource dataSource;

	
	@Before
	public void setUp() throws SQLException {
		BasicDataSource bds = new BasicDataSource();
        bds.setUrl("jdbc:derby:memory:RoomManagerTest;create=true");
        this.dataSource = bds;

        HotelUtils.executeSQLScript(dataSource, RoomManager.class.getResource("createDatabase.sql"));
		roomManager = new RoomManagerImpl(dataSource);
	}

	@After
	public void tearDown() throws SQLException {
		HotelUtils.executeSQLScript(dataSource, RoomManager.class.getResource("deleteDatabase.sql"));
	}

	@Test
	public void testCreateRoomWithNull() throws Exception {
		try {
			roomManager.createRoom(null);
			fail("InvalidArgumentException not thrown");
		}catch (IllegalArgumentException ex){		
			//exception caught, test passed
		}
	}

	@Test
	public void testCreateRoom() throws SQLException {
		Room room = new Room();
		room.setFloor(1);
		room.setNumber(2);
		room.setCapacity(2);
		roomManager.createRoom(room);

		Long graveId = room.getId();
        assertNotNull(graveId);
	}
	
	@Test
	public void testUpdateRoom() throws SQLException {
		Room room = new Room();
		room.setFloor(2);
		room.setNumber(23);
		room.setCapacity(2);
        roomManager.createRoom(room);
		long roomId = room.getId();
        
        room.setFloor(3);
        roomManager.updateRoom(room);
        
        room.setNumber(43);
        roomManager.updateRoom(room);
        
        room.setCapacity(1);
        roomManager.updateRoom(room);
        
        assertThat(roomManager.findRoomById(roomId).getFloor(), is(room.getFloor()));
		assertThat(roomManager.findRoomById(roomId).getNumber(),is(room.getNumber()));
		assertThat(roomManager.findRoomById(roomId).getCapacity(),is(room.getCapacity()));
	}

	@Test
	public void testFindRoomById() throws Exception {
		Room room = new Room();
		room.setFloor(2);
		room.setNumber(33);
		room.setCapacity(1);
		roomManager.createRoom(room);
		long roomId = room.getId();
		assertThat(roomManager.findRoomById(roomId).getFloor(),is(room.getFloor()));
		assertThat(roomManager.findRoomById(roomId).getNumber(),is(room.getNumber()));
		assertThat(roomManager.findRoomById(roomId).getCapacity(),is(room.getCapacity()));
	}
	
	@Test
	public void testDeleteRoom() throws Exception {
		Room room = new Room();
		room.setFloor(3);
		room.setNumber(22);
		room.setCapacity(1);
		roomManager.createRoom(room);
		roomManager.deleteRoom(room);
		assertThat(roomManager.findAllRooms(), not(hasItem(room)));
	}
}
