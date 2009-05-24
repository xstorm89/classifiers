package org.pr.clustering;

import java.util.List;

public interface ControllerIF {

	public List<ClusterRunResult> doKMeansBenchmark
		(int k,  
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable,
		int numRuns,
		ClusteringAlgorithm... algorithms);
	
}
