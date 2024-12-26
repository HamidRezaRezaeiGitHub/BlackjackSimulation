package dev.hrrezaei.blackjack.configuration;

import dev.hrrezaei.blackjack.model.BettingStrategy;
import dev.hrrezaei.blackjack.model.CappedMartingale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static dev.hrrezaei.blackjack.configuration.EnvConfig.getProperty;

@Configuration
public class BettingStrategyConfig {
    
    private final long step;
    
    private final BettingStrategy bettingStrategy;
    
    public BettingStrategyConfig(Environment env) {
        this.step = getProperty(env, "strategy.bet.step", Long.class);
        String enabledStrategy = getProperty(env, "strategy.bet.enabled", String.class);
        
        switch (enabledStrategy.toLowerCase()) {
            case "flat":
                int bet = getProperty(env, "strategy.bet.flat.bet", Integer.class);
                bettingStrategy = null; // ToDo: Implement Flat betting strategy
                break;
            case "martingale":
                int initialBet = getProperty(env, "strategy.bet.martingale.initialBet", Integer.class);
                long cap = getProperty(env, "strategy.bet.martingale.cap", Long.class);
                bettingStrategy = new CappedMartingale(step, initialBet, cap);
                break;
            default:
                throw new IllegalArgumentException("No valid betting strategy found!");
        }
    }
    
    public long getStep() {
        return step;
    }
    
    @Bean
    public BettingStrategy bettingStrategy() {
        return bettingStrategy;
    }
    
}
