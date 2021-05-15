package WeanWatch.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.parquet.format.MilliSeconds;

public class TimeInterval {
    // Store the start and end time
    private LocalDateTime newestTime;
    private LocalDateTime oldestTime;

    // Constructor
    public TimeInterval(LocalDateTime newestTime, LocalDateTime oldestTime) {
        this.newestTime = newestTime;
        this.oldestTime = oldestTime;
    }

    // Return the start time
    public LocalDateTime getNewestTime() {
        return this.newestTime;
    }

    // Return the end time
    public LocalDateTime getOldestTime() {
        return this.oldestTime;
    }

    // Static method to test if a timeinterval is inside another time interval
    public boolean contains(TimeInterval intervalToCompare) {
        // Check if the newestTime is newer than  before the comparedStartTime
        boolean isNewerThanCompare = this.newestTime.isAfter(intervalToCompare.getNewestTime()) || this.newestTime.equals(intervalToCompare.getNewestTime());
        // Check if the oldestTime is older than the comparedOldestTime
        boolean isOlderThanCompare = this.oldestTime.isBefore(intervalToCompare.getOldestTime()) || this.oldestTime.equals(intervalToCompare.getOldestTime());
        // Return the result
        //System.out.println(intervalToCompare);
        return isNewerThanCompare && isOlderThanCompare;
    }

	//method to convert time to string
	public String toString(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd hh:mm");  
		String strDate = formatter.format(this.newestTime);  
		
        return new String("Not yet implemented");
	}



    // Method to 
    public long getIntervalTime(){
        LocalDateTime fromDateTime = this.oldestTime;
        LocalDateTime toDateTime = this.newestTime;

        long timeInterval = ChronoUnit.MILLIS.between(fromDateTime, toDateTime);
        return timeInterval;


    }




}
