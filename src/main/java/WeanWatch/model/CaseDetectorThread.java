package WeanWatch.model;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.Date;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

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
		//iflg. Sekvensdiagram
		//Loop gennem qeued detectorTasks.	
		for(int i = 0; i<queuedTasks.size(); i++){
			//Få nuværende task
			DetectorTask currentTask = queuedTasks.get(i);
			
			//Looper igennem antallet af tasks der er at scanne for 
			
				currentTask.getPatient().getData().foreach((ForeachFunction<Row>) row -> {
					for(int z = 0; z<currentTask.getCasesToScan().size(); z++){						
						if(currentTask.getCasesToScan().get(z).getAlgorithm().evaluate(row) == null){
							//TODO: hvad sker der når evaluate returnerer null
						}
						else{
							//TODO: Create en case når evaluate returnerer et TimeInterval. Den returnerer et TimeInterval, hvis testen er true.
						}


					}
					//Hvis der returneres noget forskelligt fra Null, skal der creates en DetectedCase, som skal sættes i DetectedCaseHandler
				});
				//Evaluer alle cases igennem, for hver patient i tasken
				
				

			
		//Nested loop: Loop gennem hver case i DetectorTask
			
		//If Case is detected		

			};
		}
		

		
		
		//Lav TimeInterval med newest time, oldest time
		//Lav detectedCase(patient, case, caseinterval)
		//getPatient()
		//getDetectedCaseHandler()		
		//Return DetectedCaseHandler
		//AddCase til detectedCaseHandler
		//NotifySubscribers




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
