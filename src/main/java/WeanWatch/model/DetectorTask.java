package WeanWatch.model;

import java.util.ArrayList;
import java.util.Date;

public class DetectorTask {
    private Patient patient;
    private TimeInterval scannedInterval;

    protected DetectorTask(Patient patient) {
        this.patient = patient;
    }
    
    protected ArrayList<Case> getCasesToScan() {
        return CaseHandler.getInstance().getCases();
    }

    protected void updateInterval(Date newestTime, Date oldestTime) {
        this.scannedInterval = new TimeInterval(newestTime, oldestTime);
    }

    protected Date getNewestTime() {
        return this.scannedInterval.getNewestTime();
    }

    protected Date getOldestTime() {
        return this.scannedInterval.getOldestTime();
    }

    protected Patient getPatient() {
        return this.patient;
    }
}
