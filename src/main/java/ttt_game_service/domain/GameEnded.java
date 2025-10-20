package ttt_game_service.domain;

import java.util.Optional;

public record GameEnded (String gameId, Optional<String> winner) implements GameEvent {}
