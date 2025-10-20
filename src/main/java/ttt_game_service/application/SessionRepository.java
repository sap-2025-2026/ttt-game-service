package ttt_game_service.application;

import java.util.HashMap;
import java.util.logging.Logger;
import ddd.Aggregate;
import ddd.Repository;
import ttt_game_service.domain.Account;
import ttt_game_service.domain.UserId;

/**
 * 
 * User accounts.
 * 
 */
public class SessionRepository implements Repository {
	static Logger logger = Logger.getLogger("[SessionRepo]");

	private HashMap<UserId, UserSession> userSessions;
	
	public SessionRepository() {
		userSessions = new HashMap<>();
	}
	
	public void addSession(UserSession us) {
		userSessions.put(us.getUserId(), us);
	}
	
}
