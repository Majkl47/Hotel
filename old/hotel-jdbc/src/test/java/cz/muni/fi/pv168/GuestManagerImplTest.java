/*package cz.muni.fi.pv168;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pv168.SpringConfigTest;
import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.GuestManagerImpl;
import cz.muni.fi.pv168.hotel.HotelUtils;

@RunWith(SpringJUnit4ClassRunner.class) //Spring se zúčastní unit testů
@ContextConfiguration(classes = {SpringConfigTest.class}) //konfigurace je ve třídě MySpringTestConfig
@Transactional //každý test poběží ve vlastní transakci, která bude na konci rollbackována

public class GuestManagerImplTest {
	@Autowired
	GuestManagerImpl guestManager;
	private DataSource dataSource;

	@Before
	public void setUp() throws SQLException {
		BasicDataSource bds = new BasicDataSource();
		bds.setUrl("jdbc:derby:memory:GuestManagerTest;create=true");
        this.dataSource = bds;

        HotelUtils.executeSQLScript(dataSource, GuestManager.class.getResource("createDatabase.sql"));
		guestManager = new GuestManagerImpl(dataSource);
	} 


	@After
	public void tearDown() throws SQLException {
        HotelUtils.executeSQLScript(dataSource, GuestManager.class.getResource("deleteDatabase.sql"));

	}


	@Test
	public void testCreateGuestWithNull() throws Exception {
		try {
			guestManager.createGuest(null);
			fail("InvalidArgumentException not thrown");
		}catch (IllegalArgumentException ex){		
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateGuest() {
		Guest guest = new Guest();
		guest.setName("Vadim");
		guest.setPhone(46374);
		guest.setAddress("Praha");
		guest.setBirthDate(new Date(4, 6, 1994));

		guestManager.createGuest(guest);

		Long guestId = guest.getId();
		assertNotNull(guestId);
	}

	@Test
	public void testUpdateGuestWithNull() throws Exception {
		try {
			guestManager.updateGuest(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (IllegalArgumentException ex){		
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


	@SuppressWarnings("deprecation")
	@Test
	public void testDeleteGuest() {

		Guest g01 = new Guest();
		g01.setName("Eliot");
		g01.setPhone(1827495);
		g01.setAddress("Moscow");
		g01.setBirthDate(new Date(9, 2, 1997));

		Guest g02 = new Guest();
		g02.setName("Monika");
		g02.setPhone(1827495);
		g02.setAddress("NN");
		g02.setBirthDate(new Date(9, 2, 1993));

		guestManager.createGuest(g01);
		guestManager.createGuest(g02);

		assertNotNull(guestManager.getGuestById(g01.getId()));
		assertNotNull(guestManager.getGuestById(g02.getId()));

		guestManager.deleteGuest(g01);

		assertNull(guestManager.getGuestById(g01.getId()));
		assertNotNull(guestManager.getGuestById(g02.getId()));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testfindAllGuests() throws Exception {
		Guest guest1 = new Guest();
		guest1.setName("Ros");
		guest1.setPhone(182795);
		guest1.setAddress("Piter");
		guest1.setBirthDate(new Date(9, 2, 1992));
		guestManager.createGuest(guest1);

		Guest guest2 = new Guest();
		guest2.setName("Terk");
		guest2.setPhone(182795);
		guest2.setAddress("Piter");
		guest2.setBirthDate(new Date(9, 2, 1996));
		guestManager.createGuest(guest2);

		assertEquals(2,guestManager.findAllGuests().size());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetGuestById() throws Exception {
		Guest guest = new Guest();
		guest.setName("Jane");
		guest.setPhone(1827295);
		guest.setAddress("Kazan");
		guest.setBirthDate(new Date(9, 4, 1992));

		guestManager.createGuest(guest);

		long guestId = guest.getId();
		assertEquals(guest, guestManager.getGuestById(guestId));
	}

	/*@Test
	public void testUpdateGuest() throws SQLException {
		Guest guest = new Guest();
		guest.setName("Anna");
		guest.setPhone(234423);
		guest.setAdress("Samara");
		guest.setBirthDate("1990-3-4");

		guestManager.createGuest(guest);
		long guestId = guest.getId();

		guest.setName("Petr");
		guestManager.updateGuest(guest);

		guest.setPhone(43423);
		guestManager.updateGuest(guest);

		guest.setAdress("Olomouc");
		guestManager.updateGuest(guest);

		guest.setBirthDate("1993-4-5");
		guestManager.updateGuest(guest);

        assertThat(guestManager.getGuestById(guestId).getName(), is(guest.getName()));
		assertThat(guestManager.getGuestById(guestId).getPhone(),is(guest.getPhone()));
		assertThat(guestManager.getGuestById(guestId).getAdress(),is(guest.getAdress()));
		assertThat(guestManager.getGuestById(guestId).getBirthDate(),is(guest.getBirthDate()));

	}

}*/
