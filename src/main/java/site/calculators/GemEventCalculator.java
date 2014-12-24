package site.calculators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	    if (eventRewards.isEmpty()) {
		// no active event
		request.setAttribute("message",
			"There is no active gem event. Cannot calculate rewards.");
		request.getRequestDispatcher(
			"/WEB-INF/statusPages/messagePage.jsp").forward(
			request, response);
	    } else {
		Map<String, int[]> table = generateRewardTable(eventRewards);
		ArrayList<Integer> levels = new ArrayList<Integer>(
			eventRewards.getLevels());
		Collections.sort(levels);
		request.setAttribute("rewards", eventRewards);
		request.setAttribute("table", table);
		request.setAttribute("levels", levels);
		request.getRequestDispatcher(
			"/WEB-INF/calculators/gemEvent.jsp").forward(request,
			response);
		;
	    }
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

    /**
     * Converts GemEventReward object to a Map sorted by item name, as opposed
     * to its original structure of sorting by levels. This is to allow better
     * display on the web page.
     * 
     * @param rewards
     * @return
     */
    private Map<String, int[]> generateRewardTable(GemEventReward rewards) {
	ArrayList<Integer> levels = new ArrayList<Integer>(rewards.getLevels());
	Collections.sort(levels);

	Map<String, int[]> table = new HashMap<String, int[]>();
	boolean hasBonusGem = false;
	Iterator<Integer> levelIter = levels.iterator();
	// scan rewards for bonus gems so the column can be eliminated if
	// there are none
	while (!hasBonusGem && levelIter.hasNext()) {
	    if (rewards.getBonusGems(levelIter.next()) > 0) {
		hasBonusGem = true;
	    }
	}
	if (hasBonusGem) {
	    String bonusGemKey = "Bonus Gem";
	    // there is at least one bonus gem, so make a table column
	    table.put(bonusGemKey, new int[levels.size()]);
	    Iterator<Integer> levelIterator = levels.iterator();
	    int i = 0;
	    while (levelIterator.hasNext()) {
		table.get(bonusGemKey)[i++] = rewards
			.getBonusGems(levelIterator.next());
	    }
	}

	for (int level : levels) {
	    Map<String, Integer> levelRewards = rewards.getRewards(level);
	    for (String item : levelRewards.keySet()) {
		// iterate over item names and for new items, add new entry
		if (!table.containsKey(item)) {
		    // item hasn't been found yet, so add an entry in the map
		    table.put(item, new int[levels.size()]);
		}
		table.get(item)[levels.indexOf(level)] = levelRewards.get(item);
	    }
	}
	return table;
    }
}
