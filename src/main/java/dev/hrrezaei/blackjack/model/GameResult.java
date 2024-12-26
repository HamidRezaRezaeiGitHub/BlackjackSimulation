package dev.hrrezaei.blackjack.model;

public enum GameResult {

    W("Win", 0.44, 1),
    L("Loss", 0.47, -1),
    D("Draw", 0.09, 0),

    WW("Win Win", 0.1764, 2),
    LL("Loss Loss", 0.2401, -2),
    DD("Draw Draw", 0.0081, 0),
    LW("Loss Win", 0.4116, 0),
    LD("Loss Draw", 0.0882, -1),
    WD("Win Draw", 0.0756, 1);

    private final String description;
    private final double probability;
    private final long multiplier;

    GameResult(String description, double probability, long multiplier) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1: " + probability);
        }
        this.description = description;
        this.probability = probability;
        this.multiplier = multiplier;
    }

    public String getDescription() {
        return description;
    }

    public double getProbability() {
        return probability;
    }

    public long getMultiplier() {
        return multiplier;
    }

}