package ttt_game_service.application;

import ttt_game_service.domain.InvalidJoinException;
import ttt_game_service.domain.TTTSymbol;
import ttt_game_service.domain.UserId;


public class UserSession {

	private UserId userId;
	private GameService gameService;
	
	public UserSession(UserId userId, GameService gameService) {
		this.userId = userId;
		this.gameService = gameService;
	}
		
	public void createNewGame(String gameId) throws GameAlreadyPresentException {
		gameService.createNewGame(gameId);		
	}

	public PlayerSession joinGame(String gameId, TTTSymbol symbol) throws InvalidJoinException {
		return gameService.joinGame(userId, gameId, symbol);
	}
	public UserId getUserId() {
		return userId;
	}

}
