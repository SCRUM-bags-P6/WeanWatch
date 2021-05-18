package WeanWatch.model;

import java.io.Serializable;
import java.lang.Thread;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class CaseDetectorThread extends Thread implements Serializable{
    private static CaseDetectorThread instance = null;

    private ArrayList<DetectorTask> queuedTasks = new ArrayList<DetectorTask>();
    private ArrayList<DetectionSubscriber> subscribers = new ArrayList<DetectionSubscriber>();
    private Patient patientOfPriority;

	private boolean doRun = true;

	private static LocalDateTime newestProcessedTime;
	
	private CaseDetectorThread() {
	}

	public static CaseDetectorThread getInstance() {
		if (CaseDetectorThread.instance == null) {
            // Create an instance and store it
            CaseDetectorThread.instance = new CaseDetectorThread();
        }
        // Return the stored instance
        return CaseDetectorThread.instance;
	}

	/**
	 * TODO:
	 * Missing: Handle interruption (using priotitize patient)
	 * Missing: Store progress on interrupt.
	 * Missing: Start from prioritized patient
	 */
    public void run() {
		// Loop through each detector task
		for (int task = 0; task < queuedTasks.size(); task++) {
			// Get the current task and patient
			DetectorTask currentTask = this.queuedTasks.get(task);
			Patient currentPatient = currentTask.getPatient();	
			// Create a placeholder for the timestamp of the latest processed row
			CaseDetectorThread.newestProcessedTime = currentTask.getNewestTime();
			// Process the patient data, and apply the detection algorithm for each data row
			if (currentPatient.getData() != null) {
				currentPatient.getData().foreach((ForeachFunction<Row>) row -> {
					// Get the time of the row to process
					LocalDateTime rowTime = LocalDateTime.parse(row.getString(0));
					// Only processe the data if the timestamp is after the newest processed time
					if (newestProcessedTime == null || rowTime.isAfter(newestProcessedTime)) {
						// Get each event and run the respective detection algorithm
						for (Case event : currentTask.getCasesToScan()) {
							// Compute the algorithm for the patient data row				
							TimeInterval detectedTime = event.getAlgorithm().evaluate(row);
							// Validate if a case was found
							if (detectedTime != null) {							
								// Create a new detected case
								DetectedCase detectedEvent = new DetectedCase(currentPatient, event, detectedTime);
								// Store the detected in the patients detected event handler
								currentPatient.getDetectedCaseHandler().addCase(detectedEvent);
								// Notify subscribers to allow updating views
								this.notifySubscribers(currentPatient);
							}
						}
						// Update the newest processed time
						CaseDetectorThread.newestProcessedTime = rowTime;
					}
				});
				// Store progress in detector task
				currentTask.updateInterval(CaseDetectorThread.newestProcessedTime, currentTask.getOldestTime());
			}
		}
	}

    public void initialize() {
        for (Patient patient : PatientHandler.getInstance().getPatients()) {
            this.queuedTasks.add(new DetectorTask(patient));
        }
    }
    
    public void prioritizePatient(Patient patient) {
        this.patientOfPriority = patient;
        //TODO Interrupt for at starte med at detecte i den prioriterede patients data
    }

    public void subscribe(DetectionSubscriber context) {
        this.subscribers.add(context);
    }

    public void unSubscribe(DetectionSubscriber context) {
        this.subscribers.remove(context);
    }

    private void notifySubscribers(Patient patient) {
        this.subscribers.forEach(subscriber -> {
            subscriber.update(patient);
        });
    }


}
