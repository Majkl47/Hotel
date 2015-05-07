package cz.muni.fi.pv168.hotel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySpringTestConfig.class})
@Transactional
public class RegistrationManagerImplTest {

    @Autowired
    private GuestManager guestManager;
    @Autowired
    private RoomManager roomManager;
    @Autowired
    private RegistrationManager registrationManager;

    @Test
    public void testCreateLease() throws Exception {
        Room room = roomManager.getRoomById(1l);
        Guest guest = guestManager.getGuestById(1L);
        Registration registration = new Registration(null, room, guest, new Date(22, 2, 2013), new Date(25, 2, 2013), 1293D);
        registrationManager.createRegistration(registration);
        Assert.assertThat(registration.getId(), notNullValue());
    }

    @Test
    public void testGetLeasesForCustomer() throws Exception {
        List<Registration> registrationList = registrationManager.getRegistrationForGuest(guestManager.getGuestById(1L));
        assertThat("number of all registration", registrationList.size(), is(1));
    }

    @Test
    public void testAvailableBooks() throws Exception {
        List<Room> availableBooks = registrationManager.getAvailableRooms();
        assertThat(availableBooks.get(0).getId(),equalTo(1L));
    }
}
