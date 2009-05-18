package org.pr.clustering;

public class DHFirst extends IterativeMinimumSquareError {

	public DHFirst(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}

	@Override
	protected boolean shouldStop(int m) {
		return true;
	}

}
