package ttt_game_service.application;

import java.util.HashMap;

import ddd.Aggregate;
import ddd.Repository;
import ttt_game_service.domain.Game;

/**
 * 
 * Games Repository
 * 
 */
public class GameRepository implements Repository {

	private HashMap<String, Game> games;

	public GameRepository() {
		games = new HashMap<>();
	}
	
	public void addGame(Game game) {
		games.put(game.getId(), game);
		
	}
	
	public boolean isPresent(String gameId) {
		return games.containsKey(gameId);
	}
	
	public Game getGame(String gameId) {
		return games.get(gameId);
	}


}
