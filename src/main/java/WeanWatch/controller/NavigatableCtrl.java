package WeanWatch.controller;

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
    }

    // Implement the update method from the updatable
    public abstract void update(Patient context);

}
