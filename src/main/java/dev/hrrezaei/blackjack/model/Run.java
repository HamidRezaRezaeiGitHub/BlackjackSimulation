package dev.hrrezaei.blackjack.model;

public class Run {

    private final Game game;
    private long bet;
    private GameResult gameResult;

    public Run(Game game) {
        this.game = game;
    }

    public long execute(long bet) {
        this.bet = bet;
        this.gameResult = game.play();
        return bet * this.gameResult.getMultiplier();
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public long getBet() {
        return bet;
    }

    public Game getGame() {
        return game;
    }

}
