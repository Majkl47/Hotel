package cz.muni.fi.pv168.web;

import cz.muni.fi.pv168.Guest;
import cz.muni.fi.pv168.DatabaseException;
import cz.muni.fi.pv168.GuestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Servlet for managing books.
 */
@WebServlet(GuestServlet.URL_MAPPING + "/*")
public class GuestServlet extends HttpServlet {

    private static final String LIST_JSP = "/list.jsp";
    public static final String URL_MAPPING = "/guest";

    private final static Logger log = LoggerFactory.getLogger(GuestServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	showGuestList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //aby fungovala čestina z formuláře
        request.setCharacterEncoding("utf-8");
        //akce podle přípony v URL
        String action = request.getPathInfo();
        switch (action) {
            case "/add":
                //načtení POST parametrů z formuláře
                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String birthDate = request.getParameter("birthDate");
                //kontrola vyplnění hodnot
                if (name == null || name.length() == 0 || address == null || address.length() == 0 || phone == null || phone.length() == 0 || birthDate == null || birthDate.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    showGuestList(request, response);
                    return;
                }
                //zpracování dat - vytvoření záznamu v databázi
                try {
                    Guest guest = new Guest(null, name, address, null, null);
                    getGuestManager().createGuest(guest);
                    log.debug("created {}",guest);
                    //redirect-after-POST je ochrana před vícenásobným odesláním formuláře
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (DatabaseException e) {
                    log.error("Cannot add book", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    Guest g1;
                    g1=getGuestManager().getGuestById(id);
                    getGuestManager().deleteGuest(g1);
                    log.debug("deleted book {}",id);
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (DatabaseException e) {
                    log.error("Cannot delete book", e);
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

    /**
     * Gets BookManager from ServletContext, where it was stored by {@link StartListener}.
     *
     * @return BookManager instance
     */
    private GuestManager getGuestManager() {
        return (GuestManager) getServletContext().getAttribute("guestManager");
    }

    /**
     * Stores the list of books to request attribute "books" and forwards to the JSP to display it.
     */
    private void showGuestList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("guest", getGuestManager().findAllGuests());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (DatabaseException e) {
            log.error("Cannot show books", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
