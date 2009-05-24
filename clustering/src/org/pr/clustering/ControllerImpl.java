package org.pr.clustering;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements ControllerIF {

	@Override
	public List<ClusterRunResult> doKMeansBenchmark
			(int k,  
			String dataFilename,
			String delimeter,
			boolean lastColumnIsLable,
			int numRuns,
			ClusteringAlgorithm... algorithms) {
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		
		List<ClusterRunResult> results = new ArrayList<ClusterRunResult>();
		
		for (int i = 0; i < algorithms.length; i++) {
			List<Run> runs = new ArrayList<Run>();
			for (int j = 0; j < numRuns; j++) {
				AbstractPartitioningAlgorithm algorithm = (AbstractPartitioningAlgorithm) AbstractClusteringAlgorithm.Factory.create(algorithms[i], k, -1, -1, patterns);
				algorithm.partition();
				runs.add(new Run(j, algorithm.getMeanSquareError(), algorithm.getClusteringResult()));
			}
			results.add(new ClusterRunResult(algorithms[i], runs));
		}
		
		return results;
	}
	
	public static void main(String[] args) {
		ControllerIF controller = new ControllerImpl();
		List<ClusterRunResult> results = controller.doKMeansBenchmark
			(2, "C:/Gaussian.in", "\t", true, 2, 
			 ClusteringAlgorithm.KMeans, ClusteringAlgorithm.ABF, ClusteringAlgorithm.AFB);
		System.out.println("");
	}
 
}
