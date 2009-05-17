package org.pr.clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmad
 *
 */
public class KMeans {

	int k;
	Vector[] patterns;
	MembershipMatrix mm;
	
	public KMeans(int k, Vector... patterns) {
		this.k = k;
		this.patterns = patterns;
		mm = new MembershipMatrix(patterns.length, k);
	}
	
	/**
	 * returns an array of integers, the ith elements represents the cluster 
	 * index of the ith pattern
	 */
	public List<Integer> partition() {
		List<Vector> newZ = new ArrayList<Vector>();
		for (int i = 0; i < k; i++) {
			newZ.add(patterns[i]);
		}
		
		List<Vector> oldZ;
		
//		List<Integer> oldClusterConfiguration = null;
//		List<Integer> newClusterConfiguration = mm.getClusters();
		for (int m = 0; ; m++) {
			oldZ = newZ;
			
			// for each pattern, find the cluster center that is closest
			// to that pattern, and then put that pattern into that cluster
			int[] clusterIndexes = new int[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				int nearestCluster = patterns[i].getNearestPointIndex(newZ);
				clusterIndexes[i] = nearestCluster;
			}
			mm.moveAllPatterns(clusterIndexes);
			
			newZ = calculateZ();
			
			if (newZ.equals(oldZ))
				break;
		}
		
		
		return mm.getClusters();	
	}
	
	/**
	 * calculates cluster centers
	 * @return
	 */
	private List<Vector> calculateZ() {
		List<Vector> z = new ArrayList<Vector>();
		for (int j = 0; j < k; j++) {
			int[] patternIndexes = mm.getPatterns(j);
			z.add(Vector.calculateCenter(getPatternsWithIndexes(patternIndexes)));
		}
		
		return z;
	}
	
	private Vector[] getPatternsWithIndexes(int[] indexes) {
		Vector[] patterns = new Vector[indexes.length];
		for (int i = 0; i < patterns.length; i++) {
			patterns[i] = this.patterns[indexes[i]];
		}
		
		return patterns;
	}
	
	public static void main(String[] args) { 
		Vector v1 = new Vector(0, 0);
		Vector v4 = new Vector(5, 5);
		Vector v8 = new Vector(105, 105);		
		Vector v5 = new Vector(5.5, 5.5);
		Vector v6 = new Vector(6, 6);
		Vector v7 = new Vector(100, 100);
		Vector v3 = new Vector(1, 1);
		Vector v9 = new Vector(102, 102);
		Vector v2 = new Vector(1.5, 1.5);
		
		KMeans kmeans = new KMeans(3, v1, v5, v4, v7, v9, v8, v2, v3, v6);
		// Vector[] z = kmeans.calculateZ();
		List<Integer> clusters = kmeans.partition();
		
		System.out.println("");
	}
}
