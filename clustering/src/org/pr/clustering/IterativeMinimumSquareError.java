package org.pr.clustering;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.pr.clustering.hierarchical.LinkageCriterion;
import org.pr.clustering.hierarchical.SparseMatrix;
import org.pr.clustering.hierarchical.SparseMatrix.DistanceInfo;
import org.pr.clustering.util.DoubleUtils;

public abstract class IterativeMinimumSquareError extends AbstractPartitioningAlgorithm {
	
	public static int MAX_ITERATIONS = 5000;
	
	public IterativeMinimumSquareError(int k, Vector[] patterns, ClusteringAlgorithm type) {
		super(k, patterns, type);
	}
	
	@Override
	public List<Integer> partition() {
		// List<Vector> Z = getRandomCenters();
		List<Vector> Z = calculateZ();
		
		
//		// we need to randomize this step
//		List<Vector> Z = new ArrayList<Vector>();
//		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
//		List<Integer> usedPatterns = new ArrayList<Integer>();
//		for (int i = 0; i < k; i++) {
//			Integer pattern;
//			do {
//				pattern = rand.nextInt(patterns.length); 
//			} while (usedPatterns.contains(pattern));
//			
//			usedPatterns.add(pattern);
//			Z.add(patterns[pattern]);
//		}
		
		
		double newGlobalObjFun = getObjectiveFunction(Z);
		double oldGlobalObjFun;
		int numberOfStableIterations = 0;
		for (int m = 0; m < MAX_ITERATIONS; m++) {
		// for (int m = 0; ; m++) {
			oldGlobalObjFun = newGlobalObjFun;
			
			// for each pattern, find its cluster
			// then calc the gain of moving it out of that cluster
			// and the cost of moving it to any of the other clusters
			for (int l = 0; l < patterns.length; l++) {
				int originalCluster = mm.getClusterForPattern(l);
				// int[] iPatterns = mm.getPatternsForCluster(originalCluster);
				if (clusterSizes[originalCluster] > 1) {
					double gain = (((double)clusterSizes[originalCluster]) / (double)(clusterSizes[originalCluster] - 1))
						* Vector.euclideanDistance(patterns[l], Z.get(originalCluster));
					
					double minCost = Double.MAX_VALUE;
					int clusterToMoveTo = -1;
					// search for a cluster to move this pattern to
					// to decrease the objective function
					for (int j = 0; j < k; j++) {
						if (j != originalCluster) {// exclude ith cluster that we are trying to take a pattern from
							// int[] otherClusterPatterns = mm.getPatternsForCluster(j);
							double cost = (((double)clusterSizes[j]) / (double)(clusterSizes[j] + 1))
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
						
						// update cluster sizes
						clusterSizes[originalCluster]--;
						clusterSizes[clusterToMoveTo]++;
						
						newGlobalObjFun += minCost - gain;
						
						// we need to update the centers with min effort
						{
							Vector originalCenter = Z.get(originalCluster);
							double[] values = new double[originalCenter.getDimensionCount()];
							for (int i = 0; i < originalCenter.getDimensionCount(); i++) {
								values[i] 
								    = (originalCenter.values[i] * (double)clusterSizes[originalCluster] - patterns[l].values[i]) 
									    / (double)(clusterSizes[originalCluster] - 1); 
							}
							
							Vector newOriginalCenter = new Vector(values);
							Z.remove(originalCluster);
							Z.add(originalCluster, newOriginalCenter);
						}
						
						{
							Vector movedToCenter = Z.get(clusterToMoveTo);
							double[] values = new double[movedToCenter.getDimensionCount()];
							for (int i = 0; i < movedToCenter.getDimensionCount(); i++) {
								values[i] 
								    = (movedToCenter.values[i] * (double)clusterSizes[clusterToMoveTo] + patterns[l].values[i]) 
									    / (double)(clusterSizes[clusterToMoveTo] + 1); 
							}
							
							Vector newMovedToCenter = new Vector(values);
							Z.remove(clusterToMoveTo);
							Z.add(clusterToMoveTo, newMovedToCenter);
						}
						 
						// updateZ(Z, originalCluster, clusterToMoveTo);
					}
				}
			}
			
			// newGlobalObjFun = getObjectiveFunction(Z);
			
			// if the objective function is the same
			// advance stability count
			if (DoubleUtils.equal(newGlobalObjFun, oldGlobalObjFun)) {
				numberOfStableIterations++;
				// if (numberOfStableIterations == patterns.length) { // we're done
				if (numberOfStableIterations == 1) { // we're done
					break;
				}
			} else
				numberOfStableIterations = 0; // reset, stability has been broken
		}
		
		cluserCenters = Z;
		
		return mm.getClusters();	
	}
	
	private List<Vector> getRandomCenters() {
		List<Integer> indexes = new ArrayList<Integer>();
		List<Vector> centers = new ArrayList<Vector>();
		SparseMatrix sm = new SparseMatrix(patterns, LinkageCriterion.SINGLE);
		
		// reversing the distaceList to make the top elements is the farthest two patterns
		DistanceInfo[] decreasingArray = new DistanceInfo[sm.distancesQueue.size()];
		for (int i = decreasingArray.length - 1; i >= 0; i--) {
			decreasingArray[i] = sm.distancesQueue.remove();
		}
		
		int index = 0;
		do {
			DistanceInfo distanceInfo = decreasingArray[index++];
			if (! indexes.contains(distanceInfo.row) && ! indexes.contains(distanceInfo.column)) {
				indexes.add(distanceInfo.row);
				if (indexes.size() < k)
					indexes.add(distanceInfo.column);
			}
				
		} while (indexes.size() < k);
		
		for (Integer i : indexes) {
			Vector center = (Vector) patterns[i].clone(); 
			centers.add(center);
			System.out.println("cluster center: " + center);
		}
		
		return centers;
	}
	
	/**
	 * Returns true when the implementing class wants to execute the core DHF, 
	 * and false if the implementing class wants to execute the core DHF.
	 */
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
		
		AbstractClusteringAlgorithm DHF 
			= AbstractClusteringAlgorithm.Factory.create
				(ClusteringAlgorithm.DHF, 2, 2, -1, "C:/Gaussian.in", "\t", true);
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Integer> DHFClusters = DHF.partition();
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("DHF--------------------");
		System.out.println(DHF.printResults());
		System.out.println("--------------------");
		System.out.println("ExecutionTime = " + (endTime - startTime) / 1000 + " sec");
	}
	
}
