package org.pr.clustering;

public class AFB extends IterativeMinimumSquareError {

	public AFB(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}

	@Override
	protected boolean shouldStop(int m) {
		if (m % 2 == 1) // odd
			return false;
		return true;
	}

}
