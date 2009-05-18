package org.pr.clustering;

/**
 * Best on odd & First on even
 * 
 * @author Ahmad
 */
public class ABF extends IterativeMinimumSquareError {

	public ABF(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}

	@Override
	protected boolean shouldStop(int m) {
		if (m % 2 == 1) // odd
			return false;
		return true;
	}

}
