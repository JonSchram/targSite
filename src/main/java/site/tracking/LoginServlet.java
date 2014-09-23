package site.tracking;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -971740384419716416L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// req.setAttribute("test", "stuff");
		// TODO have this servlet store some stuff in the session and the
		// redirect to a landing page
		// resp.sendRedirect(req.getContextPath() + "/login.jsp");
		// req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
