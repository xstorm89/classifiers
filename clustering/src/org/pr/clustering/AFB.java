package org.pr.clustering;

/**
 * First on odd & Best on even
 * 
 * @author Ahmad
 */
public class AFB extends IterativeMinimumSquareError {

	public AFB(int k, int dims, String filename, String delimiter) {
		super(k, dims, filename, delimiter);
	}

	@Override
	protected boolean shouldStop(int m) {
		if (m % 2 == 1) // odd
			return true;
		return false;
	}

}
