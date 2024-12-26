package dev.hrrezaei.blackjack.model;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {

    public static final double TOTAL_PROBABILITY_ACCEPTED_ERROR = 0.0001;

    private final List<GameResult> possibleResults;

    private final Random random = new Random();

    public Game(Set<GameResult> possibleResults) {
        this.possibleResults = possibleResults.stream().toList();
        validateProbabilities();
    }

    public GameResult play() {
        double randomValue = random.nextDouble();

        // Find the GameResult corresponding to the random value
        double cumulativeProbability = 0;
        List<GameResult> possibleResultsList = List.copyOf(possibleResults);
        for (GameResult gameResult : possibleResultsList) {
            cumulativeProbability += gameResult.getProbability();
            if (randomValue < cumulativeProbability) {
                return gameResult;
            }
        }
        return possibleResultsList.getLast();
    }

    private void validateProbabilities() {
        double totalProbability = possibleResults.stream()
                .mapToDouble(GameResult::getProbability)
                .sum();

        if (Math.abs(totalProbability - 1) > TOTAL_PROBABILITY_ACCEPTED_ERROR) {
            throw new IllegalArgumentException("Total probability must be equal to 1");
        }
    }

    public List<GameResult> getPossibleResults() {
        return possibleResults;
    }
}