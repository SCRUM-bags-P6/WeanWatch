package WeanWatch.model;

import java.util.function.Predicate;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Indicator{
    // Store the duration of the indicator
    private Integer duration;
    // Store the indicator as a predicate
    private Predicate<Dataset<Row>> predicate;

    // Constructor
    protected Indicator(Integer duaration, Predicate<Dataset<Row>> predicate) {
        this.duration = duration;
        this.predicate = predicate;
    }

    // Getters
    public Integer getDuration() {
        return this.duration;
    }

    public Predicate<Dataset<Row>> getPredicate() {
        return this.predicate;
    }
}
