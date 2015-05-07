package cz.muni.fi.pv168.hotel;

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
}
