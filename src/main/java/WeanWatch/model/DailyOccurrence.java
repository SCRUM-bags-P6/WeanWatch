package WeanWatch.model;

import java.security.InvalidParameterException;

public class DailyOccurrence {
    
    private DetectedEvent eventToDisplay = null;
    private Integer occurrences = 0;
    private Long cumulativeMilliseconds = 0L;

    public DailyOccurrence(DetectedEvent detectedEvent) {
        // Store the detected Event
        this.eventToDisplay = detectedEvent;
        // Set the number of occurrences to one
        this.occurrences = 1;
        // Get the difference in time
        this.cumulativeMilliseconds = DailyOccurrence.getOccurrenceTime(this.eventToDisplay.getEventInterval());
    }

    public void addOccurrence(DetectedEvent detectedEvent) throws InvalidParameterException {
        // Validate that the new Event is of the same type as the stored Event to display
        if (!eventToDisplay.getEvent().equals(detectedEvent.getEvent())) {
            throw new InvalidParameterException("Event reference of input DetectedEvent, does not match Event reference of previously added DetectedEvent");
        }
		System.out.println(occurrences);
		System.out.println(detectedEvent);
        // Increment number of occurrences
        this.occurrences++;
        // Get the time interval of the newly added detected Event
        TimeInterval occurrenceTimeInterval = eventToDisplay.getEventInterval();
        // Add the occurrence time to the cumulative time
        this.cumulativeMilliseconds = DailyOccurrence.getOccurrenceTime(occurrenceTimeInterval);
        // Compare the recency of the EventToDisplay with that of the detectedEvent
        if (occurrenceTimeInterval.getOldestTime().isBefore(occurrenceTimeInterval.getOldestTime())) {
            // The newly added DetectedEvent occured more recendly then the EventToDisplay, use the new insted
            this.eventToDisplay = detectedEvent;
        }
    }
    
    private static Long getOccurrenceTime(TimeInterval occurrenceTimeInterval) {
        //return occurrenceTimeInterval.getNewestTime().getTime() - occurrenceTimeInterval.getOldestTime().getTime();
        //return occurrenceTimeInterval.getNewestTime().getTime() - occurrenceTimeInterval.getOldestTime().getTime();
        //return occurrenceTimeInterval.ChronoUnit.MILLIS.between(getNewestTime, getOldestTime);
        return occurrenceTimeInterval.getIntervalTime();   
    }
    

    public DetectedEvent getEventToDisplay() {
        return this.eventToDisplay;
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
