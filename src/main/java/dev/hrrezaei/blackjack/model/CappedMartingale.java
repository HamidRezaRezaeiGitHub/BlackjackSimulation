        package dev.hrrezaei.blackjack.model;

        import java.util.List;

        import static dev.hrrezaei.blackjack.model.GameResult.*;

        public class CappedMartingale implements BettingStrategy {

            public static final int DEFAULT_MULTIPLIER = 2;
            private final long step;
            private final long initialBet;
            private final long cap;

            public CappedMartingale(long step, int initialBet, long cap) {
                this.step = step;
                this.initialBet = initialBet;
                this.cap = cap;

                if (step <= 0 || initialBet <= 0 || cap <= 0 || initialBet > cap || initialBet % step != 0) {
                    throw new IllegalArgumentException("Invalid strategy parameters!");
                }

            }

            @Override
            public long getNextBet(Balance balance, Run lastRun) {
                if (lastRun == null || List.of(W, WW).contains(lastRun.getGameResult())) {
                    balance.resetAccumulativeLoss();
                    return initialBet;
                }

                if (List.of(D, LW, DD).contains(lastRun.getGameResult())) {
                    return lastRun.getBet();
                }

                long allowance = cap - balance.getAccumulativeLoss();
                if (allowance <= 0) {
                    balance.resetAccumulativeLoss();
                    return initialBet;
                }

                long nextBetCandidate = lastRun.getBet() * DEFAULT_MULTIPLIER;
                if (List.of(LD).contains(lastRun.getGameResult())) {
                    nextBetCandidate = (lastRun.getBet() + lastRun.getBet() * DEFAULT_MULTIPLIER) / 2;
                }
                if (List.of(WD).contains(lastRun.getGameResult())) {
                    nextBetCandidate = (lastRun.getBet() + 0) / 2;
                }
                nextBetCandidate = Math.min(nextBetCandidate, allowance);
                nextBetCandidate = nextBetCandidate - nextBetCandidate % step;
                nextBetCandidate = Math.max(nextBetCandidate, initialBet);

                return nextBetCandidate;
            }

        }
