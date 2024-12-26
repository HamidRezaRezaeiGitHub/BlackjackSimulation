package dev.hrrezaei.blackjack.configuration;

import dev.hrrezaei.blackjack.model.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Set;

import static dev.hrrezaei.blackjack.configuration.EnvConfig.getProperty;
import static dev.hrrezaei.blackjack.model.GameResult.*;

@Configuration
public class GameConfig {

    private final Game game;

    public GameConfig(Environment env) {
        boolean isParallel = getProperty(env, "strategy.game.parallel", Boolean.class, false);
        if (isParallel) {
            this.game = new Game(Set.of(LL, WW, DD, LW, LD, WD));
        } else {
            this.game = new Game(Set.of(L, W, D));
        }
    }

    @Bean
    public Game game() {
        return game;
    }

}
