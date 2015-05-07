package cz.muni.fi.pv168.hotel;

import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySpringTestConfig.class})
@Transactional
public class GuestManagerImplTest {

    @Autowired
    private GuestManager guestManager;

    @Test
    public void testGetGuestById() throws Exception {
        Guest g1 = guestManager.getGuestById(1);
        assertThat(g1, is(notNullValue()));
        assertThat(g1.getName(), is(equalTo("Jozef Novak")));
    }

    @Test
    public void testCreateGuest() throws Exception {
        Guest g2 = new Guest(null, "Jan Novak", "Nova 11", (long)12030131, new Date(1, 10, 1994));
        guestManager.createGuest(g2);
        assertThat("guest id", g2.getId(), notNullValue());
        Guest g3 = guestManager.getGuestById(g2.getId());
        assertThat(g3, is(equalTo(g2)));
    }

    @Test
    public void testDeleteGuest() throws Exception {
        Guest guestToDelete = guestManager.getGuestById(1);
        guestManager.deleteGuest(1);
        List<Guest> allGuests = guestManager.getAllGuests();
        assertThat(allGuests, not(hasItem(guestToDelete)));
    }

    @Test
    public void testUpdateGuest() throws Exception {
        Guest c1 = guestManager.getGuestById(1);
        c1.setAddress("Kratka 3");
        c1.setPhone((long)120930321);
        c1.setBirthDate(new Date(1, 3, 1999));
        c1.setName("Peto Kratky");
        guestManager.updateGuest(c1);
        Guest c2 = guestManager.getGuestById(1);
        assertThat(c2, is(equalTo(c1)));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        assertThat(guestManager.getAllGuests(), hasItem(guestManager.getGuestById(1)));
    }
}
