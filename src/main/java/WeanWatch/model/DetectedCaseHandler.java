package WeanWatch.model;

import java.util.ArrayList;
import java.util.function.Predicate;

public class DetectedCaseHandler {
    // Store the detected cases
    private ArrayList<DetectedCase> detectedCases;

    public DetectedCaseHandler() {
        // Prepare the detected case array
        this.detectedCases = new ArrayList<DetectedCase>();
    }

	public ArrayList<DetectedCase> getDetectedCases() {
		// Return the matching cases
        return this.detectedCases;
	}

	public ArrayList<DetectedCase> getDetectedCases(String caseName) {
		// Return the matching cases
        return this.getDetectedCases(detectedCase -> detectedCase.getCase().getName().equals(caseName));
	}

	public ArrayList<DetectedCase> getDetectedCases(TimeInterval between) {
		// Return the matching cases
        return this.getDetectedCases(detectedCase -> between.contains(detectedCase.getCaseInterval()));
	}

	public ArrayList<DetectedCase> getDetectedCases(TimeInterval between, String caseName) {
		// Return the matching cases
        return this.getDetectedCases(detectedCase -> detectedCase.getCase().getName().equals(caseName) && between.contains(detectedCase.getCaseInterval()));
	}

	private ArrayList<DetectedCase> getDetectedCases(Predicate<DetectedCase> predicate) {
		ArrayList<DetectedCase> foundCases = new ArrayList<DetectedCase>();
		for (DetectedCase detectedCase : this.detectedCases) {
			if (predicate.test(detectedCase)) {
				foundCases.add(detectedCase);
			}
		}
		// Return the matching cases
        return foundCases;
	}

	protected void addCase(DetectedCase detectedCase) {
		this.detectedCases.add(detectedCase);
	}

	protected void removeCase(DetectedCase detectedCase) {
		this.detectedCases.remove(detectedCase);
	}
}
