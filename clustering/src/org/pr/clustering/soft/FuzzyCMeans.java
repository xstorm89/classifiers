package org.pr.clustering.soft;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.pr.clustering.AbstractClusteringAlgorithm;
import org.pr.clustering.Vector;

/**
 * @author Ahmad
 */
public class FuzzyCMeans {

	Vector[] patterns;
	int k;
	double m;
	
	MembershipMatrix mm;
	
	public FuzzyCMeans(Vector[] patterns, int k, double m) {
		this.patterns = patterns;
		this.k = k;
		this.m = m;
	}

	public void partition() {
		// 1. choose an arbitrary membership matrix
		mm = new MembershipMatrix(patterns.length, k);
		
		// 2. calculate cluster centers
		List<Vector> newZ = calculateZ();
		
		List<Vector> oldZ;
		
		double exponent = 2.0 / (m - 1); 
		for (int m = 0; ; m++) {
			oldZ = newZ;
			
			// 3. update membership matrix
			for (int i = 0; i < patterns.length; i++) { // loop over patterns
				for (int j = 0; j < k; j++) { // loop over clusters
					double denominator = 0;
					double xi_zj_distance = Vector.euclideanDistance(patterns[i], newZ.get(j));
					for (int r = 0; r < k; r++) {
						double xi_zr_distance = Vector.euclideanDistance(patterns[i], newZ.get(r));
						double ratio = Math.pow(xi_zj_distance / xi_zr_distance, exponent);
						denominator += ratio;
					}
					
					mm.matrix[i][j] = 1.0 / denominator; 
				}
			}
			
			newZ = calculateZ();
			
			if (newZ.equals(oldZ))
				break;
		}
		
		System.out.println(newZ);
	}
	
	/**
	 * calculates cluster centers with current clustering configurations.
	 */
	protected List<Vector> calculateZ() {
		List<Vector> C = new ArrayList<Vector>();
		for (int j = 0; j < k; j++) {
			double[] values = new double[this.patterns[0].getDimensionCount()];
			// we need to iteration to get each dimension
			for (int dimIndex = 0; dimIndex < values.length; dimIndex++) {
				double nominator = 0;
				for (int i = 0; i < patterns.length; i++) {
					nominator += Math.pow(mm.matrix[i][j], m) * patterns[i].getDimension(dimIndex); 
				}
				
				double denominator = 0;
				for (int i = 0; i < patterns.length; i++) {
					denominator += Math.pow(mm.matrix[i][j], m);
				}
				
				values[dimIndex] = nominator / denominator;
			}
			C.add(new Vector(values));
		}
		
		return C;
	}
	
	public String printResults() {
		StringBuilder sb = new StringBuilder("");
		sb.append("pattern \t\t cluster memberships" + "\n");
		
		DecimalFormat format = new DecimalFormat("#.###");
		
		for (int i = 0; i < patterns.length; i++) { // loop over patterns
			sb.append("\n"); // + patterns[i] + ": ");
			for (int j = 0; j < k; j++) { // loop over clusters
				sb.append(format.format(mm.matrix[i][j]) + "\t");
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(AbstractClusteringAlgorithm.loadPatterns(2, "C:/Gaussian.in", "\t"), 2, 2);
		fuzzyCMeans.partition();
		System.out.println(fuzzyCMeans.printResults());
	}
	
}
