package WeanWatch.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInterval {
    // Store the start and end time
    private Date newestTime;
    private Date oldestTime;

    // Constructor
    public TimeInterval(Date newestTime, Date oldestTime) {
        this.newestTime = newestTime;
        this.oldestTime = oldestTime;
    }

    // Return the start time
    public Date getNewestTime() {
        return this.newestTime;
    }

    // Return the end time
    public Date getOldestTime() {
        return this.oldestTime;
    }

    // Static method to test if a timeinterval is inside another time interval
    public boolean contains(TimeInterval intervalToCompare) {
        // Check if the newestTime is newer than  before the comparedStartTime
        boolean isNewerThanCompare = this.newestTime.after(intervalToCompare.getNewestTime()) || this.newestTime.equals(intervalToCompare.getNewestTime());
        // Check if the oldestTime is older than the comparedOldestTime
        boolean isOlderThanCompare = this.oldestTime.before(intervalToCompare.getOldestTime()) || this.oldestTime.equals(intervalToCompare.getOldestTime());
        // Return the result
        return isNewerThanCompare && isOlderThanCompare;
    }
	//method to convert time to string
	public String toString(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd hh:mm");  
		String strDate = formatter.format(this.newestTime);  
		
        return new String("Not yet implemented");
	}
}