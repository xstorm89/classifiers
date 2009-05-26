package org.pr.clustering;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.pr.clustering.hierarchical.LinkageCriterion;

public interface ControllerIF {

	public List<ClusteringAlgorithmMultiRunningResult> doKMeansBenchmark
		(List<ClusteringAlgorithm> algorithms,
		int k,  
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable,
		int numRuns);
	
	public List<ClusteringAlgorithmMultiRunningResult> runHierarchicalAlgorithm
		(LinkageCriterion linkageCriterion, 
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable);
	
	public String runFuzzyKMeansAlgorithm
		(int k,
		double m,
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable);
	
	public String runSoftKMeansAlgorithm
		(int k,
		double m,
		double alpha,
		String dataFilename,
		String delimeter,
		boolean lastColumnIsLable);
		
	
}
