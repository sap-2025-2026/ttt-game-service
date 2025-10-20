package ttt_game_service.application;

import java.util.logging.Level;
import java.util.logging.Logger;
import ttt_game_service.domain.Account;
import ttt_game_service.domain.Game;
import ttt_game_service.domain.InvalidJoinException;
import ttt_game_service.domain.TTTSymbol;
import ttt_game_service.domain.UserId;

public class GameService {
	static Logger logger = Logger.getLogger("[Game Service]");

	private AccountRepository accountRepository;
    private GameRepository gameRepository;    
    private SessionRepository sessionRepository;
        
    public GameService() {
    	accountRepository = new AccountRepository();
    	sessionRepository = new SessionRepository();
    	gameRepository = new GameRepository();
    	
    }

    /**
     * 
     * Register a new user.
     * 
     * @param userName
     * @param password
     * @return
     * @throws AccountAlreadyPresentException
     */
	public Account registerUser(String userName, String password) throws AccountAlreadyPresentException {
		logger.log(Level.INFO, "Register User: " + userName + " " + password);		
		if (accountRepository.isPresent(userName)) {
			throw new AccountAlreadyPresentException();
		}
		var account = new Account(userName, password);
		accountRepository.addAccount(account);
		return account;
	}

	/**
	 * 
	 * Login an existing user.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws LoginFailedException
	 */
	public UserSession login(String userName, String password) throws LoginFailedException {
		logger.log(Level.INFO, "Login: " + userName + " " + password);
		if (!accountRepository.isValid(userName, password)) {
			throw new LoginFailedException();
		}		
		var id = new UserId(userName);
		var us = new UserSession(id, this);
		sessionRepository.addSession(us);
		return us;
	}
	
	/* 
	 * 
	 * Create a game -- called by a UserSession  
	 * 
	 */
	void createNewGame(String gameId) throws GameAlreadyPresentException {
		logger.log(Level.INFO, "create New Game " + gameId);
		var game = new Game(gameId);
		if (gameRepository.isPresent(gameId)) {
			throw new GameAlreadyPresentException();
		}
		gameRepository.addGame(game);
	}
	
	/*
	 * 
	 * Join a game -- called by a UserSession (logged in user), creates a new PlayerSession
	 * 
	 */
	PlayerSession joinGame(UserId userId, String gameId, TTTSymbol symbol) throws InvalidJoinException  {
		logger.log(Level.INFO, "JoinGame - user: " + userId + " game: " + gameId + " symbol " + symbol);
		var game = gameRepository.getGame(gameId);
		game.joinGame(userId, symbol);	
		var ps = new PlayerSession(userId, game, symbol);
		game.addGameObserver(ps);
		return ps;
	}	
}
