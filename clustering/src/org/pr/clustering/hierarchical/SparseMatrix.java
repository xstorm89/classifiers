package org.pr.clustering.hierarchical;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.pr.clustering.Vector;

/**
 * @author Ahmad
 */
public class SparseMatrix {

	int n;
	
	double[][] rows;
	
	PriorityQueue<DistanceInfo> sortedDistances;
	
	public SparseMatrix(int n) {
		this.n = n;
		rows = new double[n][];
		for (int i = 0; i < n; i++) {
			rows[i] = new double[n];
		}
	}
	
	public SparseMatrix(Vector[] patterns) {
		this.n = patterns.length;
		rows = new double[n][];
		for (int i = 0; i < n; i++) {
			rows[i] = new double[n];
		}
		
		sortedDistances = new PriorityQueue<DistanceInfo>();
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < row; col++) {
				double distance = Vector.euclideanDistance(patterns[row], patterns[col]); 
				rows[row][col] = distance;
				sortedDistances.add(new DistanceInfo(distance, row, col));
			}
		}
	}
	
	public DistanceInfo getClosestPair() {
		return sortedDistances.peek();
	}
	
	public DistanceInfo removeClosestPair() {
		return sortedDistances.remove();
	}
	
	public class DistanceInfo implements Comparable<DistanceInfo> {
		double distance;
		int row; 
		int column;
		
		DistanceInfo (double distance, int row, int column) {
			this.distance = distance;
			this.row = row;
			this.column = column;
		}

		@Override
		public int compareTo(DistanceInfo o) {
			return Double.compare(distance, o.distance);
		}
		
		@Override
		public String toString() {
			return "<" + row + "," + column + "> = " + distance;
		}
		
	}
	
	public static void main(String[] args) {
		Vector v0 = new Vector(0, 0);
		Vector v1 = new Vector(1, 1);
		Vector v2 = new Vector(5, 5);
		Vector v3 = new Vector(7, 7);
		Vector v4 = new Vector(20, 20);
		Vector v5 = new Vector(30, 30);

		List<Vector> patternList = new ArrayList<Vector>();
		patternList.add(v0);
		patternList.add(v1);
		patternList.add(v2);
		patternList.add(v3);
		patternList.add(v4);
		patternList.add(v5);
		
		SparseMatrix sm = new SparseMatrix(patternList.toArray(new Vector[patternList.size()]));
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
		System.out.println(sm.removeClosestPair());
	}

	/**
	 * put all distances in a heap
	 * 
	 * loop until matrixLastIndex = 0;
	 * 
	 * use the heap to find the closest two clusters, i and j, where i < j
	 * create a new cluster to represent i and j
	 * for each cluster c in 0 -> matrixLastIndex, where c != i and c != j
	 * 		distance-of-i-c = 
	 * 		
	 * remove jth row
	 * 
	 */
}
