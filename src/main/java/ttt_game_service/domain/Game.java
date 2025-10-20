package ttt_game_service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import ddd.Aggregate;
import ddd.Entity;


/**
 * 
 * A class representing a running game
 * 
 */
public class Game implements Aggregate<String>{
	static Logger logger = Logger.getLogger("[Game]");

	private String id;
	private GameBoard board;

	public enum GameState { WAITING_FOR_PLAYERS, STARTED, FINISHED }
	private GameState state;
	
	private Optional<UserId> playerCross;  /* joined player with cross */
	private Optional<UserId> playerCircle; /* joined player with circle */
	private Optional<UserId> winner;		
	private Optional<UserId> currentTurn;
	
	private List<GameObserver> observers; /* observers of game events */
	
	public Game(String id) {
		this.id = id;
		board = new GameBoard(id+"-board");

		playerCross = Optional.empty();
		playerCircle = Optional.empty();
		currentTurn = Optional.empty();		
		winner = Optional.empty();
		state = GameState.WAITING_FOR_PLAYERS;
		observers = new ArrayList<>();		
	}	
	
	public String getId() {
		return id;
	}
		
	/**
	 * 
	 * A player joins a game
	 * 
	 * @param user
	 * @param symbol
	 * @throws InvalidJoinException
	 */
	public void joinGame(UserId userId, TTTSymbol symbol) throws InvalidJoinException {
		if (!state.equals(GameState.WAITING_FOR_PLAYERS) || 
		    (symbol.equals(TTTSymbol.CROSS) && playerCross.isPresent()) ||
			(symbol.equals(TTTSymbol.CIRCLE) && playerCircle.isPresent())) {
			throw new InvalidJoinException();
		} 
		
		if (symbol.equals(TTTSymbol.CROSS)) {
			playerCross = Optional.of(userId);
		} else {
			playerCircle = Optional.of(userId);
		}

		if (playerCross.isPresent() && playerCircle.isPresent()) {
			state = GameState.STARTED;
			currentTurn = playerCross;
			notifyGameEvent(new GameStarted(id));				
		} 
	}
	
	/**
	 * 
	 * A player makes a move
	 * 
	 * @param UserId
	 * @param symbol
	 * @param x
	 * @param y
	 * @throws InvalidMoveException
	 */
	public void makeAmove(UserId userId, int x, int y) throws InvalidMoveException {
		logger.log(Level.INFO, "new move by " + userId.id() + " in (" + x + ", " + y + ")");
		UserId p = currentTurn.get();
		if (userId.id().equals(p.id())) {
			
			var gridSymbol = userId.id().equals(playerCross.get().id()) ?
						TTTSymbol.CROSS : TTTSymbol.CIRCLE;
			
			board.newMove(gridSymbol, x, y);
			notifyGameEvent(new NewMove(id, gridSymbol.toString(), x, y));				

			/* check state */ 
			
			currentTurn = (currentTurn == playerCross) ? playerCircle : playerCross;
			var optWin = board.checkWinner();
			if (optWin.isPresent()) {
				winner = Optional.of(getPlayerUsingSymbol(optWin.get()));
				state = GameState.FINISHED;
				notifyGameEvent(new GameEnded(id, Optional.of(winner.get().id())));				
			} else if (board.isTie()) {
				state = GameState.FINISHED;
				notifyGameEvent(new GameEnded(id, Optional.empty()));				
			}				
		} else {
			throw new InvalidMoveException();			
		}
	}
	
	/**
	 * 
	 * Adding an observer to notify game events
	 * 
	 * @param observer
	 */
	public void addGameObserver(GameObserver observer) {
		observers.add(observer);
	}
	
	private void notifyGameEvent(GameEvent ev) {
		for (var o: observers) {
			o.notifyGameEvent(ev);				
		}
	}
		
	private UserId getPlayerUsingSymbol(TTTSymbol symbol) {
		if (symbol.equals(TTTSymbol.CROSS)) {
			return playerCross.get();
		} else {
			return playerCircle.get();
		}
	}
}
