package WeanWatch.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DetectorTask {
    private Patient patient;
    private TimeInterval scannedInterval;

    protected DetectorTask(Patient patient) {
        this.patient = patient;
    }
    
    protected ArrayList<Case> getCasesToScan() {
        return CaseHandler.getInstance().getCases();
    }

    protected void updateInterval(LocalDateTime newestTime, LocalDateTime oldestTime) {
        this.scannedInterval = new TimeInterval(newestTime, oldestTime);
    }

    protected LocalDateTime getNewestTime() {
        return this.scannedInterval.getNewestTime();
    }

    protected LocalDateTime getOldestTime() {
        return this.scannedInterval.getOldestTime();
    }

    protected Patient getPatient() {
        return this.patient;
    }
}
