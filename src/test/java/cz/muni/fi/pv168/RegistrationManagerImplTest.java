package cz.muni.fi.pv168;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistrationManagerImplTest {
	RegistrationManagerImpl registrationManager; 

	@Before
	public void setUp() throws Exception {
		registrationManager=new RegistrationManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public void testCreateRegistrationNull() throws Exception {
		try {
			registrationManager.createRegistration(null);
			fail("NullPointerException not thrown");
		}catch (NullPointerException ex){		
			//exception
		}
	}
	
	
	@Test
	public void testUpdateRegistrationWithNull() throws Exception {
		try {
			registrationManager.updateRegistration(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (NullPointerException ex){		
		}
	}

	@Test
	public void testfindAllRegistrations() throws Exception {
		Registration registration1= new Registration();
		Registration registration2= new Registration();
		
		registrationManager.createRegistration(registration1);	
		registrationManager.createRegistration(registration2);
	
		assertEquals(2,registrationManager.findAllRegistrations().size());
	}
	
	
	@Test
	public void testgetRegistrationById() throws Exception {
		Registration registration= new Registration();
		
		registrationManager.createRegistration(registration);	
		
		assertEquals(registration, registrationManager.getRegistrationById((long) 01));
	}
	
	
	@Test
	public void testDeleteRegistrationWithNull() throws Exception {
		try {
			registrationManager.deleteRegistration(null);
			fail("nevyhodil NullPointerException pro prazdny vstup");
		}catch (NullPointerException ex){
			
		}	
	}
	
	/*@Test
	public void testfindRegistrationForGuest() throws Exception {
		Registration registration= new Registration();
		Guest guest = new Guest();
		registrationManager.createRegistration(registration);	
		
		assertEquals(1, registrationManager.findRegistrationForGuest(guest).size());
	}
	
	@Test
	public void testfindRegistrationForRoom() throws Exception {
		Registration registration= new Registration();
		Room room = new Room();

		registrationManager.createRegistration(registration);	
		
		assertEquals(1, registrationManager.findRegistrationForRoom(room).size());
	}*/
	
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
