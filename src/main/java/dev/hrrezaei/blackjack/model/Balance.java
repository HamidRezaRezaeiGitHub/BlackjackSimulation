package dev.hrrezaei.blackjack.model;

public class Balance {

    private final long initial;
    private long total;
    private long accumulativeLoss;

    public Balance(long initial) {
        this.initial = initial;
        this.total = initial;
        this.accumulativeLoss = 0;
    }

    public Balance reset() {
        this.total = initial;
        this.accumulativeLoss = 0;
        return this;
    }

    public void resetAccumulativeLoss() {
        accumulativeLoss = 0;
    }

    public void update(long amount) {
        total += amount;
        accumulativeLoss -= amount;
    }

    public long getInitial() {
        return initial;
    }

    public long getTotal() {
        return total;
    }

    public long getAccumulativeLoss() {
        return accumulativeLoss;
    }

}
