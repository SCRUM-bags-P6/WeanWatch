package WeanWatch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Patient implements Serializable {
    // Parameters
    private Dataset<Row> patientData; // Patient dataset
    private DetectedEventHandler patientEventHandler = null; // Event handler
    
    private ReentrantLock detectedEventHandlerLock = new ReentrantLock();

    // Patient information
    private String cpr;
    private int age;
    private String name;

    // Constructor
    public Patient(String cpr, String name, int age, Dataset<Row> patientData) {
        // Store the input
        this.cpr = cpr;
        this.name = name;
        this.age = age;
        this.patientData = patientData;
        // Instanciate a detected event handler
        this.patientEventHandler = new DetectedEventHandler();
    }

    // Getters
    public synchronized String getCPR() {
        return this.cpr;
    }

    public synchronized int getAge(){
        return this.age;
    }

    public synchronized String getName() {
        return this.name;
    }

    public synchronized Dataset<Row> getData() {
        return this.patientData;
    }
    
	public synchronized List<DetectedEvent> getDetectedEvents() {
        System.out.println("Gettings events, eventHandler is null:" + this.patientEventHandler != null + " and has events: ");
        System.out.println(this.patientEventHandler.getDetectedEvents());
        return this.patientEventHandler.getDetectedEvents();
	}

	public synchronized List<DetectedEvent> getDetectedEvents(String eventName) {
        return this.patientEventHandler.getDetectedEvents(eventName);
	}

	public synchronized List<DetectedEvent> getDetectedEvents(TimeInterval between) {
        return this.patientEventHandler.getDetectedEvents(between);
	}

	public synchronized List<DetectedEvent> getDetectedEvents(TimeInterval between, String eventName) {
        return this.patientEventHandler.getDetectedEvents(between, eventName);
	}

	public synchronized void addEvent(Event eventType, TimeInterval eventInterval) {
		this.patientEventHandler.addEvent(new DetectedEvent(this, eventType, eventInterval));
	}

	public synchronized void removeEvent(DetectedEvent detectedEvent) {
		this.patientEventHandler.removeEvent(detectedEvent);
	}
}
