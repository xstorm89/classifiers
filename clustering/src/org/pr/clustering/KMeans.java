package org.pr.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
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
	
	List<Vector> cluserCenters;
	
	public KMeans(int k, Vector... patterns) {
		this.k = k;
		this.patterns = patterns;
		mm = new MembershipMatrix(patterns.length, k);
	}
	
	public KMeans(int k, int dims, String filename, String delimiter) {
		this.k = k;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			List<Vector> patternList = new ArrayList<Vector>();
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				String[] strValues = line.split(delimiter);
				double[] values = new double[dims];
				for (int i = 0; i < dims && i < strValues.length; i++) {
					values[i] = Double.valueOf(strValues[i]);
				}
				patternList.add(new Vector(values));
			}
			patterns = new Vector[patternList.size()];
			for (int i = 0; i < patternList.size(); i++) {
				patterns[i] = patternList.get(i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		cluserCenters = newZ;
		
		return mm.getClusters();	
	}
	
	/**
	 * calculates cluster centers
	 * @return
	 */
	protected List<Vector> calculateZ() {
		List<Vector> z = new ArrayList<Vector>();
		for (int j = 0; j < k; j++) {
			int[] patternIndexes = mm.getPatternsForCluster(j);
			z.add(Vector.calculateCenter(getPatternsWithIndexes(patternIndexes)));
		}
		
		return z;
	}
	
	protected void updateZ(List<Vector> Z, int... changedClusters) {
		for (int i = 0; i < changedClusters.length; i++) {
			Z.remove(changedClusters[i]);
			Z.add(i, calculateClusterCenter(i));
		}
	}
	
	
	protected Vector calculateClusterCenter(int clusterIndex) {
		int[] patternIndexes = mm.getPatternsForCluster(clusterIndex);
		return Vector.calculateCenter(getPatternsWithIndexes(patternIndexes));
	}
	
	private Vector[] getPatternsWithIndexes(int[] indexes) {
		Vector[] patterns = new Vector[indexes.length];
		for (int i = 0; i < patterns.length; i++) {
			patterns[i] = this.patterns[indexes[i]];
		}
		
		return patterns;
	}
	
	public double getMeanSquareError(List<Vector> Z) {
		double globalError = 0;
		List<Integer> clusters = mm.getClusters();
		for (int i = 0; i < k; i++) {
			double clusterError = getClusterMeanSquareError(clusters.get(i), Z);
			globalError += clusterError;
		}
		
		return globalError;
	}
	
	public double getClusterMeanSquareError(int clusterIndex, List<Vector> Z) {
		int[] clusterPatterns = mm.getPatternsForCluster(clusterIndex);
		
		// foreach pattern in the cluster, calculate its distance
		// from the cluster center
		double clusterError = 0;
		Vector clusterCenter = Z.get(clusterIndex);
		for (int j = 0; j < clusterPatterns.length; j++) {
			clusterError += Vector.euclideanDistance(patterns[clusterPatterns[j]], clusterCenter);
		}
		
		return clusterError;
	}
	
	public String printResults() {
		StringBuilder sb = new StringBuilder("");
		sb.append("pattern \t\t cluster" + "\n");
		
		List<Integer> clusters = mm.getClusters();
		for (int i = 0; i < patterns.length; i++) {
			sb.append(patterns[i] + "\t" + (clusters.get(i) + 1) + "\n");
		}
		
		return sb.toString();
	}
	
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
//		
//		KMeans kmeans = new KMeans(3, v1, v5, v4, v7, v9, v8, v2, v3, v6);
		
		KMeans kmeans = new KMeans(2, 2, "C:/Gaussian.in", "\t");
		List<Integer> clusters = kmeans.partition();
		
		IterativeMinimumSquareError DHF = new IterativeMinimumSquareError(2, 2, "C:/Gaussian.in", "\t");
		List<Integer> DHFClusters = DHF.partition();
		
		System.out.println(kmeans.printResults());
	}
	
}
