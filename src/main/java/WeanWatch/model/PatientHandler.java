package WeanWatch.model;

public class PatientHandler {
    // Store an instance of the object, that can be shared
    private static PatientHandler instance = null;

    // Store the patients
    private Patient[] patients = null;

    // Set the constructor private
    private PatientHandler() {};    

    // Create the method to get the shared instance
    public static PatientHandler getInstance() {
        // Create a instance if none has been created
        if (PatientHandler.instance == null) {
            // Create an instance and store it
            PatientHandler.instance = new PatientHandler();
        }
        // Return the stored instance
        return PatientHandler.instance;
    }

    // Return a single patient
    public synchronized Patient getPatient(String cpr) {
        // Search through the list of patients, and return the first patient that matches the CPR or null
        for (Patient patient : this.patients) {
            if (cpr.equals(patient.getCPR())) {
                return patient;
            }
        }
        // Default to null
        return null;
    }

    // Return the patients
    public synchronized Patient[] getPatients() {
        // If the patients have not yet been downloaded from the PDMSConn, get them
        if (this.patients == null) {
            // Try getting the patients
            this.patients = PDMSConn.getInstance().getPatients();
        }
        // Return the patients
        return this.patients;
    }
    
}
