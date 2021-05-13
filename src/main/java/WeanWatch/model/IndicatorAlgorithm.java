package WeanWatch.model;

import java.util.Date;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;



public class IndicatorAlgorithm implements DetectionAlgorithm{
	private Date startPointer;
	private Date endPointer;
	private Integer minimumCaseDuration;
	private Indicator[] characteristics;

	@Override
	public void DetectionAlgorithm(Integer minDuration) {
		// TODO Auto-generated method stub	
	}

	@Override
	public TimeInterval evaluate(Row patientDataRow) {
		// TODO Evaluer, så den returner et TimeInterval hvis Predicate testen er true, og Null vis prediate testen er false
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

	protected Indicator Indicator(Integer duration, Indicator[] characteristics){
		
		//TODO returner en indicator
		return null;
	}


	
	
}
