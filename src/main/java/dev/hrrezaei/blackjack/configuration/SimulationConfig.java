package dev.hrrezaei.blackjack.configuration;

import dev.hrrezaei.blackjack.model.Balance;
import dev.hrrezaei.blackjack.model.BettingStrategy;
import dev.hrrezaei.blackjack.model.Game;
import dev.hrrezaei.blackjack.model.Simulation;
import dev.hrrezaei.blackjack.report.ReportGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Path;
import java.util.function.Supplier;

import static dev.hrrezaei.blackjack.configuration.EnvConfig.getProperty;

@Configuration
public class SimulationConfig {

    private final Path reportDirectoryPath;
    private final ReportGenerator reportGenerator;
    private final BettingStrategy bettingStrategy;
    private final Game game;
    private final Balance balance;
    private final int rounds;
    private final int repetition;

    public SimulationConfig(ReportConfig reportConfig,
                            ReportGenerator reportGenerator,
                            BettingStrategy bettingStrategy,
                            Game game,
                            Balance balance,
                            Environment env) {
        this.reportDirectoryPath = reportConfig.getReportDirectoryPath();
        this.reportGenerator = reportGenerator;
        this.bettingStrategy = bettingStrategy;
        this.game = game;
        this.balance = balance;

        this.rounds = getProperty(env, "simulation.rounds", Integer.class, 10000);
        this.repetition = getProperty(env, "simulation.repetitions", Integer.class, 1000);
    }

    @Bean
    public Supplier<Simulation> simulationSupplier() {
        return () -> new Simulation.Builder()
                .game(game)
                .balance(balance.reset())
                .bettingStrategy(bettingStrategy)
                .rounds(rounds)
                .reportGenerator(reportGenerator)
                .reportDirectory(reportDirectoryPath)
                .build();
    }

    public int getRepetition() {
        return repetition;
    }

}
