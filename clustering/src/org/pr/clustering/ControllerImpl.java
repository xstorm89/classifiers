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
	public List<ClusterRunResult> doKMeansBenchmark
			(int k,  
			String dataFilename,
			String delimeter,
			boolean lastColumnIsLable,
			int numRuns) {
		
		ClusteringAlgorithm[] algorithms = new ClusteringAlgorithm[] { 
				ClusteringAlgorithm.KMeans, 
				ClusteringAlgorithm.DHF, 
				ClusteringAlgorithm.DHB,
				ClusteringAlgorithm.AFB,
				ClusteringAlgorithm.ABF
			}; 
		
		Vector[] patterns = AbstractClusteringAlgorithm.loadPatterns(dataFilename, delimeter, lastColumnIsLable);
		
		List<ClusterRunResult> results = new ArrayList<ClusterRunResult>();
		
		for (int i = 0; i < algorithms.length; i++) {
			List<Run> runs = new ArrayList<Run>();
			for (int j = 0; j < numRuns; j++) {
				AbstractPartitioningAlgorithm algorithm = AbstractClusteringAlgorithm.Factory.createHardPartitioningAlgorithm(algorithms[i], k, patterns);
				algorithm.partition();
				runs.add(new Run(j, algorithm.getObjectiveFunction(), algorithm.getClusteringResult()));
			}
			results.add(new ClusterRunResult(algorithms[i], runs));
		}
		
		run(results);
		
		return results;
	}
	
	public static void main(String[] args) {
		ControllerIF controller = new ControllerImpl();
		List<ClusterRunResult> results = controller.doKMeansBenchmark
			(2, "C:/Gaussian.in", "\t", true, 2);
		System.out.println("");
	}
	
	public void run(List<ClusterRunResult> results) {
		ChartWindow resultWindow = new ChartWindow();
		resultWindow.createMainWindow();
		resultWindow.open();
		
		this.numberOfRuns = results.get(0).getPointLists().size();
		
		double kmeansMean = 0, DHFMean=0, DHBMean=0, ABFMean=0, AFBMean=0;
		for (int i = 0; i < numberOfRuns; i++) {
			kmeansMean += results.get(0).getPointLists().get(i).getY();
			DHFMean += results.get(1).getPointLists().get(i).getY();
			DHBMean += results.get(2).getPointLists().get(i).getY();
			AFBMean += results.get(3).getPointLists().get(i).getY();
			ABFMean += results.get(4).getPointLists().get(i).getY();
		}
		
		kmeansMean /= numberOfRuns;
		DHFMean /= numberOfRuns;
		DHBMean /= numberOfRuns;
		ABFMean /= numberOfRuns;
		AFBMean /= numberOfRuns;
		
		double kmeansVar=0,DHFVar=0,DHBVar=0,ABFVar=0,AFBVar=0;
		for (int i = 0; i < numberOfRuns; i++) {
			kmeansVar += Math.abs(results.get(0).getPointLists().get(i).getY() - kmeansMean);
			DHBVar += Math.abs(results.get(1).getPointLists().get(i).getY() - DHBMean);
			DHFVar += Math.abs(results.get(2).getPointLists().get(i).getY() - DHFMean);
			ABFVar += Math.abs(results.get(3).getPointLists().get(i).getY() - ABFMean);
			AFBVar += Math.abs(results.get(4).getPointLists().get(i).getY() - AFBMean);
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
		
		ILineSeries kMeansSeries= (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "KMeans (μ="+format(kmeansMean)+", σ="+format(kmeansVar)+")");
		kMeansSeries.setYSeries(results.get(0).getObjectiveFunctionSeries());
		kMeansSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 0, 0)));
		kMeansSeries.setLineWidth(10);
		kMeansSeries.setSymbolType(PlotSymbolType.CIRCLE);

		ILineSeries DHFSeries= (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "DHF (μ="+format(DHFMean)+", σ="+format(DHFVar)+")");
		DHFSeries.setYSeries(results.get(1).getObjectiveFunctionSeries());
		DHFSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 0, 255)));
		DHFSeries.setLineWidth(8);
		DHFSeries.setSymbolType(PlotSymbolType.PLUS);
		
		ILineSeries DHBSeries= (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "DHB (μ="+format(DHBMean)+", σ="+format(DHBVar)+")");
		DHBSeries.setYSeries(results.get(2).getObjectiveFunctionSeries());
		DHBSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 128, 0)));
		DHBSeries.setLineWidth(6);
		DHBSeries.setSymbolType(PlotSymbolType.CROSS);

		ILineSeries AFBSeries= (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "AFB (μ="+format(AFBMean)+", σ="+format(AFBVar)+")");
		AFBSeries.setYSeries(results.get(3).getObjectiveFunctionSeries());
		AFBSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 128, 0)));
		AFBSeries.setLineWidth(4);
		AFBSeries.setSymbolType(PlotSymbolType.TRIANGLE);

		ILineSeries ABFSeries= (ILineSeries) seriesSet.createSeries(SeriesType.LINE, "ABF (μ="+format(ABFMean)+", σ="+format(ABFVar)+")");
		ABFSeries.setYSeries(results.get(4).getObjectiveFunctionSeries());
		ABFSeries.setLineColor(new Color(Display.getDefault(), new RGB(0, 0, 0)));
		ABFSeries.setLineWidth(2);
		ABFSeries.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);

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
