package ttt_game_service.domain;

import ddd.DomainEvent;

public record GameStarted (String gameId) implements GameEvent {}
