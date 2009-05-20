package org.pr.clustering;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements ControllerIF {

	@Override
	public List<ClusterRunResult> doKMeansBenchmark
			(int k,  
			int dims,
			String dataFilename,
			String delimeter,
			int numRuns,
			ClusteringAlgorithm... algorithms) {
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dims, dataFilename, delimeter);
		
		List<ClusterRunResult> results = new ArrayList<ClusterRunResult>();
		
		for (int i = 0; i < algorithms.length; i++) {
			List<Run> runs = new ArrayList<Run>();
			for (int j = 0; j < numRuns; j++) {
				AbstractClusteringAlgorithm algorithm = AbstractClusteringAlgorithm.Factory.create(algorithms[i], k, patterns);
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
			(2, 2, "C:/Gaussian.in", "\t", 2, 
			 ClusteringAlgorithm.KMeans, ClusteringAlgorithm.ABF, ClusteringAlgorithm.AFB);
		System.out.println("");
	}
 
}
