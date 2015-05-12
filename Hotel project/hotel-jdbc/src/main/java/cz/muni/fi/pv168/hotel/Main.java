package cz.muni.fi.pv168.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws DatabaseException {

        log.info("Main start");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        RoomManager roomManager = ctx.getBean("roomManager", RoomManager.class);
        
        List<Room> allRooms = roomManager.getAllRooms();
        System.out.println("allBooks = " + allRooms);
    }

}
