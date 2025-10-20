package ttt_game_service.application;

import java.util.logging.Logger;
import ttt_game_service.domain.Game;
import ttt_game_service.domain.GameEnded;
import ttt_game_service.domain.GameEvent;
import ttt_game_service.domain.GameObserver;
import ttt_game_service.domain.GameStarted;
import ttt_game_service.domain.InvalidMoveException;
import ttt_game_service.domain.NewMove;
import ttt_game_service.domain.TTTSymbol;
import ttt_game_service.domain.UserId;

public class PlayerSession implements GameObserver {

	static Logger logger = Logger.getLogger("[Player Session]");
	private UserId userId;
	private Game game;
	private TTTSymbol symbol;
	
	public PlayerSession(UserId userId, Game game, TTTSymbol symbol) {
		this.userId = userId;
		this.game = game;
		this.symbol = symbol;
	}
	
	public void makeMove(int x, int y) throws InvalidMoveException {
		game.makeAmove(userId, x, y);
	}
	
	public TTTSymbol getSymbol() {
		return symbol;
	}
	
	public String getId() {
		return "player-session-" + userId.id() +"-" + game.getId();
	}

	public void notifyGameEvent(GameEvent ev) {
		if (ev instanceof GameStarted) {
			log("game started");
		} else if (ev instanceof GameEnded) {
			var e = (GameEnded) ev;
			if (e.winner().isEmpty()) {
				log("game ended - Tie."); 							
			} else {
				if (e.winner().get().equals(userId.id())) {
					log("game ended - You won."); 											
				} else {
					log("game ended - You lose."); 											
				}
			}
			
		} else if (ev instanceof NewMove) {
			var e = (NewMove) ev;
			log("new move: " + e.symbol() + " in (" + e.x() + ", " + e.y() + ")");
		}
	}
		
	private void log(String msg) {
		System.out.println("[ player " + userId.id() + " in game " + game.getId() + " ] " + msg);
	}
}
