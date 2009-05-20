package org.pr.clustering;

import java.util.List;

public interface ControllerIF {

	public List<ClusterRunResult> doKMeansBenchmark
		(int k,  
		int dims,
		String dataFilename,
		String delimeter,
		int numRuns,
		ClusteringAlgorithm... algorithms);
	
}
