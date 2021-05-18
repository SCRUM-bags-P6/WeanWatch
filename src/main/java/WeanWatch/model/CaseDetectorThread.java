package WeanWatch.model;

import java.io.Serializable;
import java.lang.Thread;
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
		//Følger Sekvensdiagrammet
		//Loop gennem qeued detectorTasks.		
		for(int i = 0; i<queuedTasks.size(); i++){
			//Få nuværende task
			DetectorTask curTask = this.queuedTasks.get(i);
			Patient curPatient = curTask.getPatient();			
			//For patienten i den i'te DetectorCask køres foreach på hver række af Dataset'et
			if(curPatient.getData() != null){			
				curPatient.getData().foreach((ForeachFunction<Row>) row -> {
					
					//For each kører et for-loop igennem for hver række, der scanner hver række igennem for hver case
					for(int z = 0; z<curTask.getCasesToScan().size(); z++){	
						
						//System.out.println("Number of cases to scan = " + curTask.getCasesToScan().size());					
						TimeInterval detectedTime = curTask.getCasesToScan().get(z).getAlgorithm().evaluate(row);
						
						//long test2 = 100;
						//long test = 
						
						

						if(detectedTime != null){							
							//Hvis en case algoritme returnerer !null, altså TimeInterval, creates en ny case
							DetectedCase newCase = new DetectedCase(curPatient, 
							curTask.getCasesToScan().get(z),
							detectedTime);
							//System.out.println("Tidsintervallet er:" + detectedTime.getIntervalTime());		
							//Denne case addes til den nuværende patient's DetectedCaseHandler
							curPatient.getDetectedCaseHandler().addCase(newCase);

							//NotifySubscribers
							notifySubscribers(curPatient);

							System.out.println("number of detected cases =" + curPatient.getDetectedCaseHandler().getDetectedCases().size());
							}
						}
					});			
		
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
