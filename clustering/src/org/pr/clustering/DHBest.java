package org.pr.clustering;

public class DHBest extends IterativeMinimumSquareError {

	public DHBest(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}

	@Override
	protected boolean shouldStop(int m) {
		return false;
	}

}
