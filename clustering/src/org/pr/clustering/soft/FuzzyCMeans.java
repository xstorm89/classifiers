package org.pr.clustering.soft;

import java.util.ArrayList;
import java.util.List;

import org.pr.clustering.Vector;

/**
 * @author Ahmad
 */
public class FuzzyCMeans {

	Vector[] patterns;
	int c;
	double m;
	
	MembershipMatrix mm;
	
	public FuzzyCMeans(Vector[] patterns, int c, double m) {
		this.patterns = patterns;
		this.c = c;
		this.m = m;
		mm = new MembershipMatrix(patterns.length, c);
	}

	public void partition() {
		List<Vector> newC = calculateC();
		
		System.out.println(newC);
	}
	
	/**
	 * calculates cluster centers
	 * @return
	 */
	protected List<Vector> calculateC() {
		List<Vector> C = new ArrayList<Vector>();
		for (int j = 0; j < c; j++) {
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
	
	public static void main(String[] args) {
		Vector v0 = new Vector(0, 0);
		Vector v1 = new Vector(1, 1);
		Vector v2 = new Vector(3, 3);
		Vector v3 = new Vector(5, 5);
		Vector v4 = new Vector(7, 7);
		Vector v5 = new Vector(10, 10);

		List<Vector> patternList = new ArrayList<Vector>();
		patternList.add(v0);
		patternList.add(v1);
		patternList.add(v2);
		patternList.add(v3);
		patternList.add(v4);
		patternList.add(v5);
		
		FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(patternList.toArray(new Vector[patternList.size()]), 3, 2);
		fuzzyCMeans.partition();
	}
	
}
