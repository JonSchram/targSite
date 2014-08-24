package site.tracking;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LoginManager implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Add user to HashSet of logged in users if desired

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Remove user from HashSet when implemented

	}

}
