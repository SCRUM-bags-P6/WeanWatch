package WeanWatch.model;

import javafx.scene.layout.Pane;

public abstract class MetaphoricFactory {
    
    public abstract Pane create(DetectedCase detectedCase);

}
