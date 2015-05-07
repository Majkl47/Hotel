package cz.muni.fi.pv168.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySpringTestConfig.class})
@Transactional
public class RoomManagerImplTest {

    @Autowired
    private RoomManager roomManager;

    @Test
    public void testGetAllRooms() throws Exception {
        List<Room> allRooms = roomManager.getAllRooms();
        Room room1 = roomManager.getRoomById(1L);
        assertThat(allRooms, hasItem(room1));
        assertThat("number of all rooms", allRooms.size(), is(2));
    }

    @Test
    public void testCreateRoom() throws Exception {
        Room r1 = new Room(null, 3, 337, 2);
        roomManager.createRoom(r1);
        assertThat("room id", r1.getId(), notNullValue());
        Room r2 = roomManager.getRoomById(r1.getId());
        assertThat(r2, equalTo(r1));
    }

    @Test
    public void testGetRoomById() throws Exception {
        Room room = roomManager.getRoomById(1L);
        assertThat(room.getFloor(), is(2));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        Room room = roomManager.getRoomById(1L);
        room.setFloor(2);
        roomManager.updateRoom(room);
        Room r2 = roomManager.getRoomById(1L);
        assertThat(r2, equalTo(room));
    }

    @Test
    public void testDeleteRoom() throws Exception {
        roomManager.deleteRoom(1L);
        Room r2 = roomManager.getRoomById(1L);
        assertThat(r2, is(nullValue()));
    }
}
