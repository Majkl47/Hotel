package cz.muni.fi.pv168.web;

import cz.muni.fi.pv168.hotel.Room;
import cz.muni.fi.pv168.hotel.DatabaseException;
import cz.muni.fi.pv168.hotel.RoomManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(HotelServlet.URL_MAPPING + "/*")
public class HotelServlet extends HttpServlet {

    private static final String LIST_JSP = "/list.jsp";
    public static final String URL_MAPPING = "/hotel";

    private final static Logger log = LoggerFactory.getLogger(HotelServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showRoomsList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();
        switch (action) {
            case "/add":
                String floor = request.getParameter("floor");
                String number = request.getParameter("number");
                String capacity = request.getParameter("capacity");
                if (floor == null || floor.length() == 0 || number == null || number.length() == 0 || capacity == null || capacity.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všetky hodnoty!");
                    showRoomsList(request, response);
                    return;
                }
                try {
                    Integer.parseInt(floor);
                    Integer.parseInt(number);
                    Integer.parseInt(capacity);
                } catch (NumberFormatException e){
                    request.setAttribute("chyba", "Zadana hodnota nie je cislo!");
                    showRoomsList(request, response);
                    return;
                }
                try {
                    Room room = new Room(null, Integer.parseInt(floor), Integer.parseInt(number), Integer.parseInt(capacity));
                    getRoomManager().createRoom(room);
                    log.debug("created {}",room);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (DatabaseException e) {
                    log.error("Cannot add room", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getRoomManager().deleteRoom(id);
                    log.debug("deleted room {}",id);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (DatabaseException e) {
                    log.error("Cannot delete room", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                //TODO
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    private RoomManager getRoomManager() {
        return (RoomManager) getServletContext().getAttribute("roomManager");
    }

    private void showRoomsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("rooms", getRoomManager().getAllRooms());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (DatabaseException e) {
            log.error("Cannot show rooms", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
