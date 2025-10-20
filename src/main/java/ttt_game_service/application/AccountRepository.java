package ttt_game_service.application;

import java.util.HashMap;
import java.util.logging.Logger;
import ddd.Aggregate;
import ddd.Repository;
import ttt_game_service.domain.Account;

/**
 * 
 * User accounts.
 * 
 */
public class AccountRepository implements Repository {
	static Logger logger = Logger.getLogger("[AccountRepo]");

	private HashMap<String, Account> userAccounts;
	
	public AccountRepository() {
		userAccounts = new HashMap<>();
	}
	
	public void addAccount(Account account) {
		userAccounts.put(account.getId(), account);
	}
	
	
	public boolean isPresent(String userName) {
		return userAccounts.containsKey(userName);
	}
	
	/**
	 * 
	 * Authenticate
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean isValid(String userName, String password) {
		return (userAccounts.containsKey(userName) && userAccounts.get(userName).getPassword().equals(password));
	}
	
}
