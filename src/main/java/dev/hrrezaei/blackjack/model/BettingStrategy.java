package dev.hrrezaei.blackjack.model;

public interface BettingStrategy {

    long getNextBet(Balance balance, Run lastRun);

}
