package WeanWatch.controller;

import WeanWatch.model.CaseDetectorThread;
import WeanWatch.model.DetectionSubscriber;
import WeanWatch.model.Patient;

public abstract class NavigatableCtrl implements DetectionSubscriber {
    // Store a reference to the partent
    RootCtrl parentNode;

    // Constructor
    public NavigatableCtrl() {

    }

    // Set the patient
    public void setParent(RootCtrl rootCtrl) {
        this.parentNode = rootCtrl;
        // Force an update
        this.update(this.parentNode.getPatient());
        // Subscribe to the DetectorThread
        CaseDetectorThread.getInstance().subscribe(this);
    }

    // Implement the update method from the updatable
    public abstract void update(Patient context);

    public void unsubscribe() {
        // Unsubscribe this from the DetectorThread
        CaseDetectorThread.getInstance().unSubscribe(this);
    }
}
