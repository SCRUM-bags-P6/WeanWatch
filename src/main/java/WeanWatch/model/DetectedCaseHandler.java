package WeanWatch.model;

import java.util.ArrayList;
import java.util.List;

public class DetectedCaseHandler {
    // Store the detected cases
    private List<DetectedCase> detectedCases;

    public DetectedCaseHandler() {
        // Prepare the detected case array
        this.detectedCases = new ArrayList<DetectedCase>();
    }

    // Return the detected cases between a time interval
    public DetectedCase[] getDetectedCases(String caseName, TimeInterval between) {
        // Prepare a temperary array for each of the detectedCases that match the criteria
        List<DetectedCase matchingCases = new ArrayList<DetectedCase();
        // Loop through each detected case
        for (DetectedCase case : this.detectedCases) {
            
        }

        // Return the matching cases
        return detectedCases;
    }

}
