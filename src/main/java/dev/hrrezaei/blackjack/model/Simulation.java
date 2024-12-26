package dev.hrrezaei.blackjack.model;

import dev.hrrezaei.blackjack.report.ReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.hrrezaei.blackjack.util.Currency.format;

public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    private final Game game;
    private final Balance balance;
    private final BettingStrategy bettingStrategy;
    private final int rounds;
    private final ReportGenerator reportGenerator;
    private final Path reportPath;

    private Simulation(Game game, Balance balance, BettingStrategy bettingStrategy, int rounds, ReportGenerator reportGenerator, Path reportDirectory) {
        this.game = game;
        this.balance = balance;
        this.bettingStrategy = bettingStrategy;
        this.rounds = rounds;
        this.reportGenerator = reportGenerator;
        this.reportPath = reportDirectory.resolve(getReportFileName());
    }

    private String getReportFileName() {
        return format(balance.getInitial()) + "-" +
               bettingStrategy.getClass().getSimpleName() + "-" +
               rounds + "x-" +
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSS")) +
               ".csv";
    }

    public static class Builder {
        private Game game;
        private Balance balance;
        private BettingStrategy bettingStrategy;
        private int rounds;
        private ReportGenerator reportGenerator;
        private Path reportDirectory;

        public Builder game(Game game) {
            this.game = game;
            return this;
        }

        public Builder balance(Balance balance) {
            this.balance = balance;
            return this;
        }

        public Builder bettingStrategy(BettingStrategy strategy) {
            this.bettingStrategy = strategy;
            return this;
        }

        public Builder rounds(int rounds) {
            this.rounds = rounds;
            return this;
        }

        public Builder reportGenerator(ReportGenerator reportGenerator) {
            this.reportGenerator = reportGenerator;
            return this;
        }

        public Builder reportDirectory(Path reportDirectory) {
            this.reportDirectory = reportDirectory;
            return this;
        }

        public Simulation build() {
            return new Simulation(game, balance, bettingStrategy, rounds, reportGenerator, reportDirectory);
        }
    }

    public SimulationResults run() {
        reportGenerator.addHeaders("Repetition", "Balance", "Bet", "GameResult");
        reportGenerator.addRow(0, balance.getInitial(), "", "");

        Map<GameResult, Integer> counts = new HashMap<>();
        game.getPossibleResults().forEach(result -> counts.put(result, 0));

        Run lastRun = null;
        int i;
        for (i = 1; i <= rounds; i++) {
            long bet = bettingStrategy.getNextBet(balance, lastRun);
            Run run = new Run(game);
            long amount = run.execute(bet);
            balance.update(amount);

            counts.put(run.getGameResult(), counts.get(run.getGameResult()) + 1);
            reportGenerator.addRow(i, balance.getTotal(), bet, run.getGameResult().name());

            if (balance.getTotal() <= 0) {
                break;
            }

            lastRun = run;
        }

        LOGGER.info("Total balance after {} rounds: {}", --i, format(balance.getTotal()));
        Map<GameResult, Double> percentages = new HashMap<>();
        for (Map.Entry<GameResult, Integer> entry : counts.entrySet()) {
            percentages.put(entry.getKey(), (double) entry.getValue() / i * 100);
        }
        String formattedPercentages = percentages.entrySet()
                .stream()
                .map(e -> String.format("%s: %.2f%%", e.getKey(), e.getValue()))
                .collect(Collectors.joining(", "));
        LOGGER.info("Simulation results: {}", formattedPercentages);

        reportGenerator.generateReport(reportPath);
        return new SimulationResults(balance, percentages);
    }

}
