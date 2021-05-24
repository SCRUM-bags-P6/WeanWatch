package WeanWatch.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import org.apache.spark.sql.Row;



public class IndicatorAlgorithm implements DetectionAlgorithm {
	
	private LocalDateTime startPointer;
	private LocalDateTime endPointer;

	// Minimum duration of the Event (in minutes)
	private Integer minimumEventDuration = 0;

	private ArrayList<Indicator> characteristics;

	public IndicatorAlgorithm(ArrayList<Indicator> characteristics) {
		// Store the indicators
		this.characteristics = characteristics;
		// Find the longes duration of among the indicators
		for (Indicator indicator : this.characteristics) {
			// Compare times
			if (indicator.getDuration() > this.minimumEventDuration) {
				this.minimumEventDuration = indicator.getDuration();
			}
		}
	}

	@Override
	public TimeInterval evaluate(Row patientDataRow) {
		// Store the result of all evaluation
		boolean allIndicatorsTrue = true;
		// Evaluate each predicate
		for (Indicator indicator : this.characteristics) {
			// Perform the test, and it with allIndicatorsTrue
			try {
				allIndicatorsTrue = allIndicatorsTrue & indicator.getPredicate().test(patientDataRow);	
			} catch (NullPointerException e) {
				return null;
			}
		}
		// All tests passed
		if (allIndicatorsTrue) {
			// Get the current timestamp
			LocalDateTime currentRowTimestamp = LocalDateTime.parse(patientDataRow.getString(0));
			// If the startPointer is null set it, else set the end pointer
			if (this.startPointer == null) {
				this.startPointer = currentRowTimestamp;
			} else {
				this.endPointer = currentRowTimestamp;
			}
			// Wait for the next row to evaluate if a Event was found
			return null;
		} else {
			/**
			 * If the difference between the start and end pointer is longer than the minimum duration,
			 *  a Event has been detected, so create a time interval and return it 
			 */
			if (this.startPointer != null && this.endPointer != null && this.startPointer.until(this.endPointer, ChronoUnit.MINUTES) > this.minimumEventDuration) {
				// A Event was found! Create the time interval
				
				return new TimeInterval(this.startPointer, this.endPointer);


			} else {
				// Some test failed, reset pointers and return null
				clear();
				return null;
			}
		}

		// //Evaluate() får passed et Row. 
		// //Det Row bliver så testet for hver indicator. Hvis Predicate.test() opfyldes, returnerer den TRUE
		// this.characteristics.forEach((indicator)  -> {
		// 	if(indicator.getPredicate().test(patientDataRow)){
			
		// 	//Henter værdien for TimeStamp kolonnen, laver den til simpledateformat, og lægger den i en Date
		// 	try {
		// 		Date date1 = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS").parse(patientDataRow.<String>getAs("TimeStamp"));
		// 	} catch (Exception e) {
		// 		System.out.println("Date formatting failed");
		// 		e.printStackTrace();
		// 	}
			


		// 	//Return TimeInterval
		// 	}
		// });
			
		// return null;
	}

	@Override
	public void clear() {
		this.startPointer = null;
		this.endPointer = null;	
	}

	@Override
	public Integer getMinDuration() {
		return this.minimumEventDuration;
	}
}
