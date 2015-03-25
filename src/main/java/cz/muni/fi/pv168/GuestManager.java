package cz.muni.fi.pv168;

import java.util.List;

public interface GuestManager {
	
	void createGuest(Guest guest);

	void updateGuest(Guest guest);

	void deleteGuest(Guest guest);

	Guest getGuestById(long id);

	List<Guest> findAllGuests();

}