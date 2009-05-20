package org.pr.clustering;

public enum ClusteringAlgorithm {

	KMeans("KMeans"), 
	DHF("DHF"), 
	DHB("DHB"), 
	AFB("AFB"),
	ABF("ABF"), 
	HEIRACHICAL_MAX("HEIRACHICAL_Max");
	
	String name;
	
	ClusteringAlgorithm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
