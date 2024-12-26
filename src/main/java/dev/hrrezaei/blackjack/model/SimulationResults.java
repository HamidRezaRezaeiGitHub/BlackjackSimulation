package dev.hrrezaei.blackjack.model;

import java.util.Map;

public record SimulationResults(Balance balance, Map<GameResult, Double> percentages) {
}
