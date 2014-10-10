package site.calculators;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.EventType;
import site.DAO.EventDAO;

/**
 * Servlet implementation class RewardCalculator
 */
@WebServlet("/RewardCalculator")
public class RewardCalculator extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8707951424925740357L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RewardCalculator() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String eventType = request.getParameter("event");
	EventType forwardEvent = null;
	if (eventType != null) {
	    if ("SoulCrystal".equalsIgnoreCase(eventType)) {
		forwardEvent = EventType.SoulCrystal;

	    } else if ("MountWhip".equalsIgnoreCase(eventType)) {
		forwardEvent = EventType.MountWhip;

	    } else if ("Mahra".equalsIgnoreCase(eventType)) {
		forwardEvent = EventType.Mahra;

	    } else if ("Sepulcrum".equalsIgnoreCase(eventType)) {
		forwardEvent = EventType.Sepulcrum;
	    }
	}

	Reward r = new EventDAO().getFullRewards(forwardEvent);
	if (!r.isEmpty()) {
	    request.setAttribute("EventName", forwardEvent.getNiceName());
	    request.setAttribute("RewardData", r);
	    request.getRequestDispatcher(
		    "/WEB-INF/calculators/reimburseEvent.jsp").forward(request,
		    response);
	} else {
	    request.setAttribute("message", "Invalid event");
	    request.getRequestDispatcher("/WEB-INF/statusPages/messagePage.jsp")
		    .forward(request, response);
	    ;
	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
    }

}
