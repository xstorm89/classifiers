package org.pr.clustering;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.pr.clustering.util.DoubleUtils;

public abstract class IterativeMinimumSquareError extends KMeans {

	public IterativeMinimumSquareError(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}
	
	public IterativeMinimumSquareError(int k, Vector[] patterns) {
		super(k, patterns);
	}
	
	@Override
	public List<Integer> partition() {
		List<Vector> Z = new ArrayList<Vector>();
		for (int i = 0; i < k; i++) {
			int index = ( patterns.length * (i) ) / k;
			Z.add(patterns[index]);
		}
		
		double newGlobalObjFun = getMeanSquareError(Z);
		double oldGlobalObjFun;
		int numberOfStableIterations = 0;
		for (int m = 0; ; m++) {
			oldGlobalObjFun = newGlobalObjFun;
			
			// for each pattern, find its cluster
			// then calc the gain of moving it out of that cluster
			// and the cost of moving it to any of the other clusters
			for (int l = 0; l < patterns.length; l++) {
				int i = mm.getClusterForPattern(l);
				int[] iPatterns = mm.getPatternsForCluster(i);
				if (iPatterns.length > 1) {
					double gain = (iPatterns.length / (iPatterns.length - 1))
						* Vector.euclideanDistance(patterns[l], Z.get(i));
					
					double minCost = Double.MAX_VALUE;
					int clusterToMoveTo = -1;
					// search for a cluster to move this pattern to
					// to decrease the objective function
					for (int j = 0; j < k; j++) {
						if (j != i) {// exclude ith cluster that we are trying to take a pattern from
							int[] otherClusterPatterns = mm.getPatternsForCluster(j);
							double cost = (((double)otherClusterPatterns.length) / (double)(otherClusterPatterns.length + 1))
								* Vector.euclideanDistance(patterns[l], Z.get(j));
							if (cost < gain) { // there's an improvement, but is this is the best improvement
								if (cost < minCost) { // better than all before
									minCost = cost;
									clusterToMoveTo = j;
								}
								if (shouldStop(m)) {
									break;
								}
							}
						}
					}
					if (clusterToMoveTo > -1) { // move pattern i to cluster clusterToMoveTo 
						mm.movePattern(l, clusterToMoveTo);
						updateZ(Z, i, clusterToMoveTo);
					}
				}
			}
			
			newGlobalObjFun = getMeanSquareError(Z);
			// if the objective function is the same
			// advance stability count
			if (DoubleUtils.equal(newGlobalObjFun, oldGlobalObjFun)) {
				numberOfStableIterations++;
				if (numberOfStableIterations == patterns.length) { // we're done
					break;
				}
			} else
				numberOfStableIterations = 0; // reset, stability has been broken
		}
		
		cluserCenters = Z;
		
		return mm.getClusters();	
	}
	
	protected abstract boolean shouldStop(int m);

	public static void main(String[] args) { 
//		Vector v1 = new Vector(0, 0);
//		Vector v2 = new Vector(1.5, 1.5);
//		Vector v3 = new Vector(1, 1);
//		Vector v4 = new Vector(5, 5);
//		Vector v5 = new Vector(5.5, 5.5);
//		Vector v6 = new Vector(6, 6);
//		Vector v7 = new Vector(100, 100);
//		Vector v8 = new Vector(105, 105);
//		Vector v9 = new Vector(102, 102);
		
		IterativeMinimumSquareError DHF = new DHFirst(2, 2, "C:/Gaussian.in", "\t");
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> DHFClusters = DHF.partition();
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("DHF--------------------");
		System.out.println(DHF.printResults());
		System.out.println("--------------------");
		System.out.println("ExecutionTime = " + (endTime - startTime) / 1000 + " sec");
	}
	
}
