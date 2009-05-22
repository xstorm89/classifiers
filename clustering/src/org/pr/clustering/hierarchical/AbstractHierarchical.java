package org.pr.clustering.hierarchical;

import java.util.ArrayList;
import java.util.List;

import org.pr.clustering.Vector;
import org.pr.clustering.hierarchical.SparseMatrix.DistanceInfo;

/**
 * @author Ahmad Faheem & Amr Ghoneim 
 */
public class AbstractHierarchical {

	Vector[] patterns;
	LinkageCriterion linkageCriterion;
	
	List<Cluster> clusters;
	
	AbstractHierarchical(Vector[] patterns, LinkageCriterion linkageCriterion) {
		this.patterns = patterns;
		this.linkageCriterion = linkageCriterion;
	}
	
	public List<Integer> partition() {
		// create a cluster for each pattern.
		clusters = new ArrayList<Cluster>();
		for (int i = 0; i < patterns.length; i++) {
			clusters.add(new Cluster("C" + i, i));
		}

		SparseMatrix sm = new SparseMatrix(patterns, linkageCriterion);
		
		int iterationCount = 0;
		for (DistanceInfo distanceInfo = sm.getClosestPair(); 
			distanceInfo != null; 
			distanceInfo = sm.getClosestPair()) {
			
			// we need to merge clusters distanceInfo.row and distanceInfo.column
			// into one cluster
			// this happens in two steps:
			//  1. merging the distances
			//  2. merging the clusters themselves that are stored in clusters list
			sm.merge(distanceInfo.row, distanceInfo.column);
			
			Cluster newCluster = new Cluster(clusters.get(distanceInfo.row), clusters.get(distanceInfo.column));
			clusters.remove(distanceInfo.column);
			clusters.remove(distanceInfo.row);
			clusters.add(distanceInfo.row, newCluster);
			
			iterationCount++;
		}
		
		System.out.println(clusters.get(0));
		
		return null;
	}
	
	public static void main(String[] args) {
		Vector v0 = new Vector(0);
		Vector v1 = new Vector(1);
		Vector v2 = new Vector(3);
		Vector v3 = new Vector(5);
		Vector v4 = new Vector(7);
		Vector v5 = new Vector(10);

		List<Vector> patternList = new ArrayList<Vector>();
		patternList.add(v0);
		patternList.add(v1);
		patternList.add(v2);
		patternList.add(v3);
		patternList.add(v4);
		patternList.add(v5);
		
		AbstractHierarchical hierarchical = new AbstractHierarchical(patternList.toArray(new Vector[patternList.size()]), LinkageCriterion.WPGMC);
		hierarchical.partition();
		
	}

	
}
