package cz.muni.fi.pv168.hotel;

import java.util.Date;
import java.util.List;

public interface GuestManager {

    void deleteGuest(long id);

    void updateGuest(Guest guest);

    void createGuest(Guest guest);

    List<Guest> getAllGuests();

    Guest getGuestById(long id) throws DatabaseException;
    
    List<Guest> getGuestsByName(String name) throws DatabaseException;
    
    List<Guest> getGuestsByAddress(String address) throws DatabaseException;
    
    List<Guest> getGuestsByPhone(long phone) throws DatabaseException;
    
    List<Guest> getGuestsByBirthDate(Date date) throws DatabaseException;
}
