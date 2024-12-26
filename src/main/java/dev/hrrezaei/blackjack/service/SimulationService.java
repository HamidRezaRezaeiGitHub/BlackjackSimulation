package dev.hrrezaei.blackjack.service;

import dev.hrrezaei.blackjack.configuration.SimulationConfig;
import dev.hrrezaei.blackjack.model.Simulation;
import dev.hrrezaei.blackjack.model.SimulationResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static dev.hrrezaei.blackjack.util.Currency.format;

@Service
public class SimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationService.class);

    private final int repetition;
    private final Supplier<Simulation> simulationSupplier;

    public SimulationService(SimulationConfig simulationConfig, Supplier<Simulation> simulationSupplier) {
        this.repetition = simulationConfig.getRepetition();
        this.simulationSupplier = simulationSupplier;

        runSimulations();
    }

    private void runSimulations() {
        long accumulativeTotalBalance = 0;
        for (int i = 0; i < repetition; i++) {
            Simulation simulation = simulationSupplier.get();

            SimulationResults simulationResults = simulation.run();
            accumulativeTotalBalance += simulationResults.balance().getTotal();
        }
        LOGGER.info("Average Total Balance of {} simulations: {}", repetition, format(accumulativeTotalBalance / repetition));
    }
}
