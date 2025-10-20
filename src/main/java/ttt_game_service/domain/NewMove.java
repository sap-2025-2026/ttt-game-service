package ttt_game_service.domain;

public record NewMove (String gameId, String symbol, int x, int y) implements GameEvent {}
