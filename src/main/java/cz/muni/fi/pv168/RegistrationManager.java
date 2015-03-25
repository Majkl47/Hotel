package cz.muni.fi.pv168;

import java.util.List;

public interface RegistrationManager {

	void createRegistration(Registration registration);

	void updateRegistration(Registration registration);

	void deleteRegistration(Registration registration);

	List<Registration> findAllRegistrations();

	Registration getRegistrationById(long id);

	List<Registration> findRegistrationForGuest(Guest guest);

	List<Registration> findRegistrationForRoom(Room room);

}