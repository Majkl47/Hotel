package cz.muni.fi.pv168.hotel;

import java.util.List;

public interface GuestManager {

    void deleteGuest(long id);

    void updateGuest(Guest guest);

    void createGuest(Guest guest);

    List<Guest> getAllGuests();

    Guest getGuestById(long id) throws DatabaseException;

}
