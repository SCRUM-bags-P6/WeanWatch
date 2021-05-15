package WeanWatch.model;

import java.io.Serializable;

import org.apache.spark.sql.Row;

public interface DetectionAlgorithm extends Serializable {

	public TimeInterval evaluate(Row patientDataRow);

	public void clear();

	public Integer getMinDuration();
	
}
