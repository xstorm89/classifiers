package org.pr.clustering.hierarchical;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmad
 */
public class Cluster {

	Cluster left = null;
	Cluster right = null;
	List<Integer> patternIndexes;
	
	String name;
	
	public Cluster(String name, int patternIndex) {
		patternIndexes = new ArrayList<Integer>();
		patternIndexes.add(patternIndex);
	}
	
	public Cluster(Cluster left, Cluster right) {
		this.left = left;
		this.right = right;
		this.name = left.name + "," + right.name;
		
		patternIndexes = new ArrayList<Integer>();
		for (Integer index : left.getPatternIndexes()) {
			patternIndexes.add(index);
		}
		for (Integer index : right.getPatternIndexes()) {
			patternIndexes.add(index);
		}
	}
	
	List<Integer> getPatternIndexes() {
		return patternIndexes;
	}
}
