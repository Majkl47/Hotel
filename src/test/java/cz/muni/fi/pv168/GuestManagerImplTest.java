package cz.muni.fi.pv168;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GuestManagerImplTest {
	GuestManagerImpl guestManager;

	@Before
	public void setUp() throws Exception {
		guestManager = new GuestManagerImpl();	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateGuestWithNull() throws Exception {
		try {
			guestManager.createGuest(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (NullPointerException ex){		
		}
	}

//	@Test
//	public void testCreatGuestWithId() throws Exception {
//		Guest guest = new Guest();
//		guest.setName("Jack");
//		Guest guest2 = guestManager.registerGuest(guest);
//		assertThat(guest2.getId(), is(not(equalTo(0l))));
//	}
//
//	@Test
//	public void testCreatGuestCanBeRetrieved() throws Exception {
//		Guest guest = new Guest();
//		guest.setName("Jack");
//		Guest guest2 = guestManager.registerGuest(guest);
//		assertThat(guestManager.findAllGuests(), hasItem(guest2));
//	}

	@Test
	public void testUpdateGuestWithNull() throws Exception {
		try {
			guestManager.updateGuest(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (NullPointerException ex){		
		}
	}

	@Test
	public void testDeleteGuestWithNull() throws Exception {
		try {
			guestManager.deleteGuest(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (NullPointerException ex){
			
		}	
	}

	@Test
	public void testDeleteGuest() throws Exception {
		Guest guest = new Guest();
		guest.setName("Jack");
		guestManager.createGuest(guest);
		guestManager.deleteGuest(guest);
		assertThat(guestManager.findAllGuests(), not(hasItem(guest)));
	}

	@Test
	public void testfindAllGuests() throws Exception {
		Guest guest1 = new Guest();
		guest1.setName("Nick");
		guestManager.createGuest(guest1);

		Guest guest2 = new Guest();
		guest2.setName("Jack");
		guestManager.createGuest(guest2);

		assertEquals(2,guestManager.findAllGuests().size());
	}

	@Test
	public void testGetGuestById() throws Exception {
		Guest guest = new Guest();
		guest.setName("Jack");
		assertEquals(guest, guestManager.getGuestById((long) 01));
	}
}
