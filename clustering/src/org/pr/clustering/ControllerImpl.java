package org.pr.clustering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.pr.clustering.ui.ChartWindow;
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
		
//		ClusteringAlgorithm[] algorithms = new ClusteringAlgorithm[] { 
//				ClusteringAlgorithm.KMeans, 
//				ClusteringAlgorithm.DHF, 
//				ClusteringAlgorithm.DHB,
//				ClusteringAlgorithm.AFB,
//				ClusteringAlgorithm.ABF
//			}; 
		
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
		
		run(results, algorithms);
		
		return results;
	}
	
	public void run(List<ClusteringAlgorithmMultiRunningResult> results, List<ClusteringAlgorithm> algorithms) {
		ChartWindow resultWindow = new ChartWindow();
		resultWindow.createMainWindow();
		resultWindow.open();
		
		this.numberOfRuns = results.get(0).getRuns().size();
		
		double[] means = new double[algorithms.size()];
		
//		double kmeansMean = 0, DHFMean=0, DHBMean=0, ABFMean=0, AFBMean=0;
		for (int i = 0; i < numberOfRuns; i++) {
//			kmeansMean += results.get(0).getPointLists().get(i).getY();
//			DHFMean += results.get(1).getPointLists().get(i).getY();
//			DHBMean += results.get(2).getPointLists().get(i).getY();
//			AFBMean += results.get(3).getPointLists().get(i).getY();
//			ABFMean += results.get(4).getPointLists().get(i).getY();
			
			for (int j = 0; j < means.length; j++) {
				means[j] += results.get(j).getRuns().get(i).getY();
			}
		}
		
		for (int j = 0; j < means.length; j++) {
			means[j] /= numberOfRuns;
		}
		
//		kmeansMean /= numberOfRuns;
//		DHFMean /= numberOfRuns;
//		DHBMean /= numberOfRuns;
//		ABFMean /= numberOfRuns;
//		AFBMean /= numberOfRuns;
		
		double[] vars = new double[algorithms.size()];
		
		double kmeansVar=0,DHFVar=0,DHBVar=0,ABFVar=0,AFBVar=0;
		for (int i = 0; i < numberOfRuns; i++) {
			for (int j = 0; j < means.length; j++) {
				vars[j] += Math.abs(results.get(j).getRuns().get(i).getY() - means[j]);
			}
			
//			kmeansVar += Math.abs(results.get(0).getPointLists().get(i).getY() - kmeansMean);
//			DHBVar += Math.abs(results.get(1).getPointLists().get(i).getY() - DHBMean);
//			DHFVar += Math.abs(results.get(2).getPointLists().get(i).getY() - DHFMean);
//			ABFVar += Math.abs(results.get(3).getPointLists().get(i).getY() - ABFMean);
//			AFBVar += Math.abs(results.get(4).getPointLists().get(i).getY() - AFBMean);
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
		
//		for (int j = 0; j < algorithms.size(); j++) {
//			ILineSeries series = (ILineSeries) seriesSet.createSeries(SeriesType.LINE, algorithms.get(j).getName() + " (μ=" + format(means[j]) + ", σ=" + format(vars[j]) + ")");
//			series.setYSeries(results.get(0).getObjectiveFunctionSeries());
//			series.setLineColor(new Color(Display.getDefault(), getAlgorithmColor(algorithms.get(j))));
//			series.setLineWidth(1 + (6 - (int) (((double) j) * 1.5)));
//			series.setSymbolType(PlotSymbolType.CIRCLE);
//		}
		
		int algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.KMeans);
		if (algorithmIndex > -1) {
			ILineSeries kMeansSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "KMeans (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			kMeansSeries.setYSeries(results.get(0).getObjectiveFunctionSeries());
			kMeansSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 0, 0)));
			kMeansSeries.setLineWidth(10);
			kMeansSeries.setSymbolType(PlotSymbolType.CIRCLE);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.DHF);
		if (algorithmIndex > -1) {
			ILineSeries DHFSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "DHF (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			DHFSeries.setYSeries(results.get(1).getObjectiveFunctionSeries());
			DHFSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 0, 255)));
			DHFSeries.setLineWidth(8);
			DHFSeries.setSymbolType(PlotSymbolType.PLUS);
		}

		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.DHB);
		if (algorithmIndex > -1) {
			ILineSeries DHBSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "DHB (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			DHBSeries.setYSeries(results.get(2).getObjectiveFunctionSeries());
			DHBSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 128, 0)));
			DHBSeries.setLineWidth(6);
			DHBSeries.setSymbolType(PlotSymbolType.CROSS);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.AFB);
		if (algorithmIndex > -1) {
			ILineSeries AFBSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "AFB (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			AFBSeries.setYSeries(results.get(3).getObjectiveFunctionSeries());
			AFBSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 128, 0)));
			AFBSeries.setLineWidth(4);
			AFBSeries.setSymbolType(PlotSymbolType.TRIANGLE);
		}
		
		algorithmIndex = algorithms.indexOf(ClusteringAlgorithm.ABF);
		if (algorithmIndex > -1) {
			ILineSeries ABFSeries= (ILineSeries) seriesSet.createSeries
				(SeriesType.LINE, "ABF (μ=" + format(means[algorithmIndex]) + ", σ=" + format(vars[algorithmIndex]) + ")");
			ABFSeries.setYSeries(results.get(4).getObjectiveFunctionSeries());
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
	
	private RGB getAlgorithmColor(ClusteringAlgorithm clusteringAlgorithm) {
		if (clusteringAlgorithm.equals(ClusteringAlgorithm.KMeans))
			return new RGB(255, 0, 0);
		else if (clusteringAlgorithm.equals(ClusteringAlgorithm.DHB))
			return new RGB(0, 0, 255);
		else if (clusteringAlgorithm.equals(ClusteringAlgorithm.DHF))
			return new RGB(0, 128, 0);
		else if (clusteringAlgorithm.equals(ClusteringAlgorithm.ABF))
			return new RGB(255, 128, 0);
		else if (clusteringAlgorithm.equals(ClusteringAlgorithm.AFB))
			return new RGB(0, 0, 0);
		else
			return new RGB(0, 0, 0);
	}
 
}
