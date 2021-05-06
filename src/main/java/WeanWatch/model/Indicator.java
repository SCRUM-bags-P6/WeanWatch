package WeanWatch.model;

import java.util.function.Predicate;

public class Indicator {
    // Store the duration of the indicator
    private int duration;
    // Store the indicator as a predicate
    private Predicate predicate;

    // Constructor
    public Indicator(int duaration, Predicate predicate) {
        this.duration = duration;
        this.predicate = predicate;
    }

    // Getters
    public int getDuration() {
        return this.duration;
    }

    public Predicate getPredicate() {
        return this.predicate;
    }
}
