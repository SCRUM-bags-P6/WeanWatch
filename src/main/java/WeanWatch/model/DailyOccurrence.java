package WeanWatch.model;

import java.security.InvalidParameterException;

public class DailyOccurrence {
    
    private DetectedCase caseToDisplay = null;
    private Integer occurrences = 0;
    private Long cumulativeMilliseconds = 0L;

    public DailyOccurrence(DetectedCase detectedCase) {
        // Store the detected case
        this.caseToDisplay = detectedCase;
        // Set the number of occurrences to one
        this.occurrences = 1;
        // Get the difference in time
        this.cumulativeMilliseconds = DailyOccurrence.getOccurrenceTime(this.caseToDisplay.getCaseInterval());
    }

    public void addOccurrence(DetectedCase detectedCase) throws InvalidParameterException {
        // Validate that the new case is of the same type as the stored case to display
        if (!caseToDisplay.getCase().equals(detectedCase.getCase())) {
            throw new InvalidParameterException("Case reference of input DetectedCase, does not match Case reference of previously added DetectedCase");
        }
        // Increment number of occurrences
        this.occurrences++;
        // Get the time interval of the newly added detected case
        TimeInterval occurrenceTimeInterval = caseToDisplay.getCaseInterval();
        // Add the occurrence time to the cumulative time
        this.cumulativeMilliseconds = DailyOccurrence.getOccurrenceTime(occurrenceTimeInterval);
        // Compare the recency of the caseToDisplay with that of the detectedCase
        if (occurrenceTimeInterval.getOldestTime().before(occurrenceTimeInterval.getOldestTime())) {
            // The newly added DetectedCase occured more recendly then the caseToDisplay, use the new insted
            this.caseToDisplay = detectedCase;
        }
    }

    private static Long getOccurrenceTime(TimeInterval occurrenceTimeInterval) {
        return occurrenceTimeInterval.getNewestTime().getTime() - occurrenceTimeInterval.getOldestTime().getTime();
    }

    public DetectedCase getCaseToDisplay() {
        return this.caseToDisplay;
    }

    public Integer getOccurrences() {
        return this.occurrences;
    }

    public Long getCumulativeMilliseconds() {
        return this.cumulativeMilliseconds;
    }

    public String getCumulativeTime() {
        // Construct a string builder to create the output string
        StringBuilder builder = new StringBuilder();
        // Hmmmm
        Object[][] timesToDisplay = {{3600000L, "h"},{60000L, "m"},{1000L, "s"}};
        // Create a placeholder for the remaining time (minutes, seconds)
        Long remainingTime = this.cumulativeMilliseconds;
		System.out.println("Cumulative Milliseconds" + this.cumulativeMilliseconds);
        for (Object[] timeToDisplay : timesToDisplay) {
            // Calculate number of (hours then minutes then seconds)
            Long hms = (remainingTime / (Long) timeToDisplay[0]);
            // Add the hours to the string builder, if it is not 0
            if (hms != 0) {
                builder.append(hms).append(" ").append(timeToDisplay[1]).append(" "); //hvorfor [1]
				System.out.println("Hours,Minutes,Seconds" + hms);
            }
            // Store the remaining time after division
            remainingTime = remainingTime % (Long) timeToDisplay[0];
        }
        // Convert the hours, minuts and seconds to a string
        return builder.toString();
    }
    
}