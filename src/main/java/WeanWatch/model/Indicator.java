package WeanWatch.model;

import java.util.function.Predicate;
import java.io.Serializable;

import org.apache.spark.sql.Row;

public class Indicator implements Serializable {
    // Store the duration (in minutes) of the indicator
    private Integer duration;
    // Store the indicator as a predicate
	private Predicate<Row> predicate;

    // Constructor
    public Indicator(Integer duration, Predicate<Row> predicate) {
        this.duration = duration;
        // Cast the perdicate to a serialisable and store it
        this.predicate = (Predicate<Row> & Serializable) predicate;
    }

    // Getters
    public Integer getDuration() {
        return this.duration;
    }

    public Predicate<Row> getPredicate() {
        return this.predicate;
    }
}
