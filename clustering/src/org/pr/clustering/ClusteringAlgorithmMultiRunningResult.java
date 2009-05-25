package org.pr.clustering;

import java.util.List;

public class ClusteringAlgorithmMultiRunningResult {

	private ClusteringAlgorithm algorithm;  
	private List<Run> runs;
	
	public ClusteringAlgorithmMultiRunningResult
		(ClusteringAlgorithm algorithm, List<Run> pointLists) {
		
		this.algorithm = algorithm;
		this.runs = pointLists;
	}

	public ClusteringAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(ClusteringAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public List<Run> getRuns() {
		return runs;
	}

	public void setPointLists(List<Run> pointLists) {
		this.runs = pointLists;
	}
	
	public double[] getObjectiveFunctionSeries() {
		double[] objectiveFunctionSeries = new double[runs.size()];
		for (int i = 0; i < runs.size(); i++) {
			objectiveFunctionSeries[i] = runs.get(i).getY();
		}
		return objectiveFunctionSeries;
	}
	
}
 