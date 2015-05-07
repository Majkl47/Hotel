package cz.muni.fi.pv168.web;

import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.SpringConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartListener implements ServletContextListener {

    final static Logger log = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        log.info("webová aplikace inicializována");
        ServletContext servletContext = ev.getServletContext();
        ApplicationContext springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        servletContext.setAttribute("guestManager", springContext.getBean("guestManager", GuestManager.class));
        servletContext.setAttribute("roomManager", springContext.getBean("roomManager", RoomManager.class));
        log.info("vytvořeny manažery");
    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        log.info("aplikace končí");
    }
}
