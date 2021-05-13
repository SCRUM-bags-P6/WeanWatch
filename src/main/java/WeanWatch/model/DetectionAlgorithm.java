package WeanWatch.model;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DetectionAlgorithm {
	public void DetectionAlgorithm(Integer minDuration);

	public TimeInterval evaluate(Row patientDataRow);

	public void clear();

	public Integer getMinDuration();
	
}
