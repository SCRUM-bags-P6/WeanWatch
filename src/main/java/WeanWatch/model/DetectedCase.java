package WeanWatch.model;

public class DetectedCase {
	// Store the patient for which the case is detected
	private Patient patient;
	// Store a reference to the case
	private Case caseType;
	// Store the time interval in which the case occured
	private TimeInterval caseInterval;

	// Constructor
	public DetectedCase(Patient patient, Case caseType, TimeInterval caseInterval){
		this.patient = patient;
		this.caseType = caseType;
		this.caseInterval = caseInterval;
	}
	
	// Getters
	public Patient getPatient() {
		return this.patient;
	}

	public Case getCase() {
		return this.caseType;
	}

	public TimeInterval getCaseInterval() {
		return this.caseInterval;
	}
}
