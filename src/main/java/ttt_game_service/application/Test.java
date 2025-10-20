package ttt_game_service.application;

import ttt_game_service.domain.*;

public class Test {

	public static void main(String[] args) throws Exception {

		GameService gs = new GameService();
		
		gs.registerUser("bob", "123secret");

		gs.registerUser("alice", "456secret");
		
		UserSession bobUserSession = gs.login("bob", "123secret");
		UserSession aliceUserSession = gs.login("alice", "456secret");
		
		bobUserSession.createNewGame("a-super-game");

		PlayerSession bobPlayerSession = bobUserSession.joinGame("a-super-game", TTTSymbol.CROSS);
		PlayerSession alicePlayerSession = aliceUserSession.joinGame("a-super-game", TTTSymbol.CIRCLE);
		
		bobPlayerSession.makeMove(0, 0);
		alicePlayerSession.makeMove(1, 1);
		bobPlayerSession.makeMove(0, 1);
		alicePlayerSession.makeMove(2, 2);
		bobPlayerSession.makeMove(0, 2);
		
		
	}

}
