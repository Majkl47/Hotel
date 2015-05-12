package cz.muni.fi.pv168.hotel;

import java.util.Date;
import java.util.List;

public interface RegistrationManager {

    List<Registration> getRegistrationForGuest(Guest g);

    void createRegistration(Registration registration);

    List<Room> getAvailableRooms();
    
    void updateRegistration(Registration r);

    void deleteRegistration(long id);

    List<Registration> getAllRegistrations();

    Registration getRegistrationById(long id) throws DatabaseException;

    List<Registration> getRegistrationForRoom(Room room);
    
    List<Registration> getRegistrationsByStartDate(Date date) throws DatabaseException;
    
    List<Registration> getRegistrationsByEndDate(Date date) throws DatabaseException;
    
    List<Registration> getRegistrationsByPrice(double price) throws DatabaseException;
}
