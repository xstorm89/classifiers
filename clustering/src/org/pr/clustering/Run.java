package org.pr.clustering;

public class Run {

	/**
	 * the run index
	 */
	double runIndex;
	
	/**
	 * obj function at the xth run.
	 */
	double objFunction;
	
	PatternMembership[] clusteringResult;

	public Run(double runIndex, double objFunction, PatternMembership[] clusteringResult) {
		this.clusteringResult = clusteringResult;
		this.runIndex = runIndex;
		this.objFunction = objFunction;
	}

	public double getX() {
		return runIndex;
	}

	public double getY() {
		return objFunction;
	}

	public PatternMembership[] getClusteringResult() {
		return clusteringResult;
	}
}
