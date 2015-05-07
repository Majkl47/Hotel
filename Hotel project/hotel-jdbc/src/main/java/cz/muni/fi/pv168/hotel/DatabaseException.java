package cz.muni.fi.pv168.hotel;

public class DatabaseException extends Exception {

    public DatabaseException(String msg) {
	super(msg);
    }
	
    public DatabaseException(Throwable cause) {
	super(cause);
    }
        
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
