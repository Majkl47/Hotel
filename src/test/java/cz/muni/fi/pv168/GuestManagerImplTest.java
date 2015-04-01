package cz.muni.fi.pv168;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class GuestManagerImplTest {
	GuestManagerImpl guestManager;
    private DataSource dataSource;

	@Before
	public void setUp() throws SQLException {
		BasicDataSource bds = new BasicDataSource();
        bds.setUrl("jdbc:derby:memory:GuestManagerTest;create=true");
        this.dataSource = bds;

        try (Connection conn = bds.getConnection()) {
            conn.prepareStatement("CREATE TABLE GUEST ("
                    + "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "name VARCHAR(20),"
                    + "adress VARCHAR(20),"
                    + "phone INTEGER NOT NULL,"
                    + "birthDate VARCHAR(10))").executeUpdate();
        }
        guestManager = new GuestManagerImpl(dataSource);
	} 
	

	@After
	 public void tearDown() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            con.prepareStatement("DROP TABLE GUEST").executeUpdate();
        }
    }


	@Test
	public void testCreateGuestWithNull() throws Exception {
		try {
			guestManager.createGuest(null);
			fail("InvalidArgumentException not thrown");
		}catch (IllegalArgumentException ex){		
		}
	}
	
	 @Test
	 public void testCreateGuest() {
		 Guest guest = new Guest();
		 guest.setName("Vadim");
		 guest.setPhone(46374);
		 guest.setAdress("Praha");
		 guest.setBirthDate("1993-4-6");
		 
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

	
	  @Test
	    public void testDeleteGuest() {

		  	Guest g01 = new Guest();
		  	g01.setName("Eliot");
		  	g01.setPhone(1827495);
		  	g01.setAdress("Moscow");
		  	g01.setBirthDate("1997-2-9");
		  	
			Guest g02 = new Guest();
			g02.setName("Monika");
			g02.setPhone(1827495);
			g02.setAdress("NN");
			g02.setBirthDate("1991-2-9");
		  	
			guestManager.createGuest(g01);
			guestManager.createGuest(g02);

	        assertNotNull(guestManager.getGuestById(g01.getId()));
	        assertNotNull(guestManager.getGuestById(g02.getId()));

	        guestManager.deleteGuest(g01);
	        
	        assertNull(guestManager.getGuestById(g01.getId()));
	        assertNotNull(guestManager.getGuestById(g02.getId()));

	    }
	  
	@Test
	public void testfindAllGuests() throws Exception {
		Guest guest1 = new Guest();
		guest1.setName("Ros");
		guest1.setPhone(182795);
		guest1.setAdress("Piter");
		guest1.setBirthDate("1997-2-9");
		guestManager.createGuest(guest1);

		Guest guest2 = new Guest();
		guest2.setName("Terk");
		guest2.setPhone(182795);
		guest2.setAdress("Piter");
		guest2.setBirthDate("1996-2-9");
		guestManager.createGuest(guest2);

		assertEquals(2,guestManager.findAllGuests().size());
	}

	@Test
	public void testGetGuestById() throws Exception {
		Guest guest = new Guest();
		guest.setName("Jane");
		guest.setPhone(1827295);
		guest.setAdress("Kazan");
		guest.setBirthDate("1993-1-4");
		
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

	}*/
	
}


