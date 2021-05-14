package WeanWatch.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;



public class IndicatorAlgorithm implements DetectionAlgorithm {
	private Date startPointer;
	private Date endPointer;
	private Integer minimumCaseDuration;
	private ArrayList<Indicator> characteristics = new ArrayList<Indicator>();

	@Override
	public void DetectionAlgorithm(Integer minDuration) {
		// TODO Auto-generated method stub	
	}

	@Override
	public TimeInterval evaluate(Row patientDataRow) {
		//Evaluate() får passed et Row. 
		//Det Row bliver så testet for hver indicator. Hvis Predicate.test() opfyldes, returnerer den TRUE
		this.characteristics.forEach((indicator)  -> {
			if(indicator.getPredicate().test(patientDataRow)){
			
			//Henter værdien for TimeStamp kolonnen, laver den til simpledateformat, og lægger den i en Date
			try {
				Date date1 = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS").parse(patientDataRow.<String>getAs("TimeStamp"));
			} catch (Exception e) {
				System.out.println("Date formatting failed");
				e.printStackTrace();
			}
			


			//Return TimeInterval
			}
		});
			
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub		
	}

	@Override
	public Integer getMinDuration() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Indicator Indicator(Integer duration, ArrayList<Indicator> characteristics){
		
		//TODO returner en indicator
		return null;
	}


	
	
}
