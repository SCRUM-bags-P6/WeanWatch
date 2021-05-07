package WeanWatch.model;

import java.lang.Thread;
import java.util.ArrayList;

public class CaseDetectorThread extends Thread {
    private static CaseDetectorThread instance = null;

    private ArrayList<DetectorTask> queuedTasks = new ArrayList<DetectorTask>();
    private ArrayList<DetectionSubscriber> subscribers = new ArrayList<DetectionSubscriber>();
    private Patient patientOfPriority;

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

    public void run() {

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
