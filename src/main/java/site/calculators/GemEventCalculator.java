package site.calculators;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DAO.GemEventDAO;

/**
 * Servlet implementation class GemEventCalculator
 */
@WebServlet("/GemEventCalculator")
public class GemEventCalculator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GemEventCalculator() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	GemEventReward eventRewards = new GemEventDAO().getActiveRewards();
	if (eventRewards != null) {

	} else {
	    // null reward object, so database connection couldn't be made
	    request.setAttribute("message", "Could not connect to the database");
	    request.getRequestDispatcher("/WEB-INF/statusPages/messagePage.jsp")
		    .forward(request, response);
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
