package WeanWatch.model;

import java.io.IOException;
import java.io.Serializable;
import java.lang.Thread;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class EventDetectorThread implements Serializable{
    private static EventDetectorThread instance = null;

    private ArrayList<DetectorTask> queuedTasks = new ArrayList<DetectorTask>();
    private ArrayList<DetectionSubscriber> subscribers = new ArrayList<DetectionSubscriber>();
    private static Patient patientOfPriority;

	private boolean doRun = true;

	private static Integer apneaEvents = 0;

	private Integer processedRows = 0;

	private static LocalDateTime newestProcessedTime;
	
	private EventDetectorThread() {
		//this.setDaemon(true);
	}

	public static EventDetectorThread getInstance() {
		if (EventDetectorThread.instance == null) {
            // Create an instance and store it
            EventDetectorThread.instance = new EventDetectorThread();
        }
        // Return the stored instance
        return EventDetectorThread.instance;
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
			patientOfPriority = currentTask.getPatient();
			Patient currentPatient = patientOfPriority;
			System.out.println("Running task for patient with CPR: " + currentTask.getPatient().getCPR());	
			// Create a placeholder for the timestamp of the latest processed row
			newestProcessedTime = currentTask.getNewestTime();
			// Process the patient data, and apply the detection algorithm for each data row
			if (currentPatient.getData() != null) {

				Row[] rows = (Row[]) currentPatient.getData().collect();

				for (Row row : rows) {
					// Get the time of the row to process
					LocalDateTime rowTime = LocalDateTime.parse(row.getString(0));
					// Only processe the data if the timestamp is after the newest processed time
					if (newestProcessedTime == null || rowTime.isAfter(newestProcessedTime)) {

						processedRows = processedRows + 1;
						System.out.println("Have processed: " + processedRows + " number of rows");

						// Get each event and run the respective detection algorithm
						for (Event event : currentTask.getEventsToScan()) {
							// Compute the algorithm for the patient data row		
							TimeInterval detectedTime = event.getAlgorithm().evaluate(row);
							// Validate if a Event was found
							if (detectedTime != null) {							
								System.out.println("Found event of type: " + event.getName() + " for patient with CPR: " + currentPatient.getCPR());
								// Store the detected in the patients detected event handler
								patientOfPriority.addEvent(event, detectedTime);
								// Notify subscribers to allow updating views
								this.notifySubscribers(currentPatient);
							}
						}
						// Update the newest processed time
						newestProcessedTime = rowTime;
					}
				}

				// currentPatient.getData().foreach((ForeachFunction<Row>) row -> {
					
				// });
				// Store progress in detector task
				currentTask.updateInterval(newestProcessedTime, currentTask.getOldestTime());
			}
		}
	} 

    public void initialize() {
        for (Patient patient : PatientHandler.getInstance().getPatients()) {
            this.queuedTasks.add(new DetectorTask(patient));
			System.out.println("Created detector task for patient with CPR " + patient.getCPR());
        }
		run();
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
