package org.pr.clustering;

import java.util.List;

public interface ControllerIF {

	public List<ClusteringAlgorithmMultiRunningResult> doKMeansBenchmark
		(List<ClusteringAlgorithm> algorithms,
		int k,  
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable,
		int numRuns);
	
}
