package dev.hrrezaei.blackjack.configuration;

import dev.hrrezaei.blackjack.model.Balance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static dev.hrrezaei.blackjack.configuration.EnvConfig.getProperty;

@Configuration
public class BalanceConfig {

    private final Balance balance;

    public BalanceConfig(Environment env) {
        long initialBalance = getProperty(env, "balance.initial", Long.class, 100_000L);
        this.balance = new Balance(initialBalance);
    }

    @Bean
    public Balance balance() {
        return balance;
    }

}
