package classifiers;

import java.util.Vector;

import classifiers.util.Point;

public class KMeansClassifier {
	/**
	 * Used to hold points to be classified
	 */
	private Vector<Point> points;
	/**
	 * used to hold number of clusters
	 */
	private int k;
	
	/**
	 * this is used to hold the k cluster means
	 */
	Vector<Point> means = new Vector<Point>();
	
	/**
	 * this is used to hold the members of the clusters in each step
	 */
	Vector<Vector<Point>> cluster_members = new Vector<Vector<Point>>();
	
	
	public Vector<Point> getPoints() {
		return points;
	}

	public void setPoints(Vector<Point> points) {
		this.points = points;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
	
	public KMeansClassifier (Vector<Point> pts) {
		this.points = pts;
	}
	
	public void classify() {
		// TODO: i will choose random k initial cluster centers.
		for(int i = 0; i < this.k; i ++) {
			int index = (int) Math.random() * this.points.size();
			this.means.add(this.points.get(index));
		}
		
		//TODO: then distribute the rest of points on them using the MECD classifier for all
		// the points.
		for(int i = 0; i < this.points.size(); i ++) {
			
		}
		
		//TODO: then i will recalculate the new mean based on the points in the cluster.
		
		//TODO: repeat until reach a threshold.
	}
}
