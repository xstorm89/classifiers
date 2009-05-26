package org.pr.clustering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.pr.clustering.hierarchical.Cluster;
import org.pr.clustering.hierarchical.Hierarchical;
import org.pr.clustering.hierarchical.LinkageCriterion;
import org.pr.clustering.soft.FuzzyCMeans;
import org.pr.clustering.soft.SoftKMeans;
import org.pr.clustering.ui.ChartWindow;
import org.pr.clustering.ui.DendroWindow;
import org.swtchart.Chart;
import org.swtchart.IAxisSet;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries;
import org.swtchart.ISeriesSet;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class ControllerImpl implements ControllerIF {

	int numberOfRuns = 20;
	
	@Override
	public List<ClusteringAlgorithmMultiRunningResult> doKMeansBenchmark
			(List<ClusteringAlgorithm> algorithms,
			int k,  
			String dataFilename,
			String delimeter,
			boolean lastColumnIsLable,
			int numRuns) {
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		
		List<ClusteringAlgorithmMultiRunningResult> results = new ArrayList<ClusteringAlgorithmMultiRunningResult>();
		
		for (int i = 0; i < algorithms.size(); i++) {
			List<Run> runs = new ArrayList<Run>();
			for (int j = 0; j < numRuns; j++) {
				AbstractPartitioningAlgorithm algorithm = AbstractClusteringAlgorithm.Factory.createHardPartitioningAlgorithm(algorithms.get(i), k, patterns);
				algorithm.partition();
				runs.add(new Run(j, algorithm.getObjectiveFunction(), algorithm.getClusteringResult()));
			}
			results.add(new ClusteringAlgorithmMultiRunningResult(algorithms.get(i), runs));
		}
		
		displayKMeansBenchmarkResults(results, algorithms);
		
		return results;
	}
	
	@Override
	public List<ClusteringAlgorithmMultiRunningResult> runHierarchicalAlgorithm
		(LinkageCriterion linkageCriterion, 
		String dataFilename,
		String delimeter, 
		boolean lastColumnIsLable) {
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		Hierarchical hierachical = AbstractClusteringAlgorithm.Factory.createHierachhicalAlgorithm(linkageCriterion, patterns);
		hierachical.partition();
		Cluster rootCluster = hierachical.getRootCluster();
		DendroWindow dendroWindow = new DendroWindow(rootCluster);
		dendroWindow.run();
		
		return null;
	}
	
	@Override
	public String runFuzzyKMeansAlgorithm
		(int k, double m, 
		String dataFilename, String delimeter, boolean lastColumnIsLable) {

		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		
		FuzzyCMeans fuzzyKMeans = new FuzzyCMeans(patterns, k, m);
		fuzzyKMeans.partition();
		return fuzzyKMeans.printResults();
	}

	@Override
	public String runSoftKMeansAlgorithm
		(int k, double m, double alpha, String dataFilename,
		String delimeter, boolean lastColumnIsLable) {
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		
		SoftKMeans softKMeans = new SoftKMeans(patterns, k, m, alpha);
		softKMeans.partition();
		return softKMeans.printResults();
	}
	
	private void displayKMeansBenchmarkResults(List<ClusteringAlgorithmMultiRunningResult> results, List<ClusteringAlgorithm> algorithms) {
		ChartWindow resultWindow = new ChartWindow();
		resultWindow.createMainWindow();
		resultWindow.open();
		
		this.numberOfRuns = results.get(0).getRuns().size();
		
		double[] means = new double[algorithms.size()];
		for (int i = 0; i < numberOfRuns; i++) {
			for (int j = 0; j < means.length; j++) {
				means[j] += results.get(j).getRuns().get(i).getY();
			}
		}
		
		for (int j = 0; j < means.length; j++) {
			means[j] /= numberOfRuns;
		}
		
		double[] vars = new double[algorithms.size()];
		
		double kmeansVar=0,DHFVar=0,DHBVar=0,ABFVar=0,AFBVar=0;
		for (int i = 0; i < numberOfRuns; i++) {
			for (int j = 0; j < means.length; j++) {
				vars[j] += Math.abs(results.get(j).getRuns().get(i).getY() - means[j]);
			}
		}
		
		kmeansVar /= (numberOfRuns-1);
		DHFVar /= (numberOfRuns-1);
		DHBVar /= (numberOfRuns-1);
		ABFVar /= (numberOfRuns-1);
		AFBVar /= (numberOfRuns-1);
		
		Chart chart = resultWindow.getChart();
		ISeriesSet seriesSet = chart.getSeriesSet();
		ISeries[] ser = seriesSet.getSeries();
		for (int i = 0; i < ser.length; i++) {
			seriesSet.deleteSeries(ser[i].getId());
		}
		
		int algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.KMeans);
		if (algorithmIndex > -1) {
			ILineSeries kMeansSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "KMeans (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			kMeansSeries.setYSeries(results.get(algorithmIndex).getObjectiveFunctionSeries());
			kMeansSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 0, 0)));
			kMeansSeries.setLineWidth(10);
			kMeansSeries.setSymbolType(PlotSymbolType.CIRCLE);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.DHF);
		if (algorithmIndex > -1) {
			ILineSeries DHFSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "DHF (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			DHFSeries.setYSeries(results.get(algorithmIndex).getObjectiveFunctionSeries());
			DHFSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 0, 255)));
			DHFSeries.setLineWidth(8);
			DHFSeries.setSymbolType(PlotSymbolType.PLUS);
		}

		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.DHB);
		if (algorithmIndex > -1) {
			ILineSeries DHBSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "DHB (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			DHBSeries.setYSeries(results.get(algorithmIndex).getObjectiveFunctionSeries());
			DHBSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 128, 0)));
			DHBSeries.setLineWidth(6);
			DHBSeries.setSymbolType(PlotSymbolType.CROSS);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.AFB);
		if (algorithmIndex > -1) {
			ILineSeries AFBSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "AFB (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			AFBSeries.setYSeries(results.get(algorithmIndex).getObjectiveFunctionSeries());
			AFBSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 128, 0)));
			AFBSeries.setLineWidth(4);
			AFBSeries.setSymbolType(PlotSymbolType.TRIANGLE);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.ABF);
		if (algorithmIndex > -1) {
			ILineSeries ABFSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "ABF (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			ABFSeries.setYSeries(results.get(algorithmIndex).getObjectiveFunctionSeries());
			ABFSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 0, 0)));
			ABFSeries.setLineWidth(2);
			ABFSeries.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
		}

		IAxisSet axisSet = chart.getAxisSet();
		axisSet.adjustRange();
		chart.redraw();
	}

	private String format(double number){
		String string = Double.toString(number);
		try {
			string= string.substring(0, string.indexOf('.')+5);
		} catch (StringIndexOutOfBoundsException e) {
		}
		return string;
	}
 
}
