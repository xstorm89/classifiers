package org.pr.clustering.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.pr.clustering.Vector;
import org.swtchart.Chart;
import org.swtchart.IAxisSet;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries;
import org.swtchart.ISeriesSet;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class MultiClusterDisplayer implements Closeable {

	int numberOfRuns = 20;
	
	ChartWindow resultWindow;
	
	List<List<Vector>> patternsList;
	
	private static List<RGB> COLORS = new ArrayList<RGB>();
	
	static {
		COLORS.add(new RGB(255, 0, 0));
		COLORS.add(new RGB(0, 255, 0));
		COLORS.add(new RGB(0, 0, 255));
		COLORS.add(new RGB(128, 128, 0));
		COLORS.add(new RGB(0, 128, 128));
		COLORS.add(new RGB(128, 0, 128));
	}
	
	Random rand = new Random(Calendar.getInstance().getTimeInMillis());
	
	public MultiClusterDisplayer(List<List<Vector>> patternsList) {
		if (patternsList.get(0) == null || patternsList.get(0).size() == 0)
			throw new IllegalArgumentException("patterns should contain at least one element");
		
		if (patternsList.get(0).get(0).getDimensionCount() < 2)
			throw new IllegalArgumentException("patterns should at least have 2 dimensions!");
		
		if (patternsList.get(0).get(0).getDimensionCount() > 2)
			System.out.println("patterns has more than 2 dimensions. only 2 dimensions will be displayed, and the others will be ignored");
		
		this.patternsList = patternsList;
	}
	
	public void displayClusters() {
		resultWindow = new ChartWindow();
		resultWindow.createMainWindow();
		resultWindow.setChartTitle("Clustering Results");
		resultWindow.setXAxixTitle("X");
		resultWindow.setYAxixTitle("Y");
		resultWindow.open();
		Chart chart = resultWindow.getChart();
		ISeriesSet seriesSet = chart.getSeriesSet();
		ISeries[] ser = seriesSet.getSeries();
		
		for (int i = 0; i < ser.length; i++) {
			seriesSet.deleteSeries(ser[i].getId());
		}

		for (int i = 0; i < patternsList.size(); i++) {
			List<Vector> patterns = patternsList.get(i);
			double[] xSeries = new double[patterns.size()];
			double[] ySeries = new double[patterns.size()];
			for (int j = 0; j < patterns.size(); j++) {
				xSeries[j] = patterns.get(j).getDimension(0);
				ySeries[j] = patterns.get(j).getDimension(1);
			}
			
			ILineSeries ABFSeries = (ILineSeries) seriesSet
				.createSeries(SeriesType.LINE, "Cluster " + i);
			ABFSeries.setXSeries(xSeries);
			ABFSeries.setYSeries(ySeries);
			ABFSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 255, 255)));
			ABFSeries.setLineWidth(0);
			ABFSeries.setSymbolType(PlotSymbolType.CIRCLE);
			RGB color;
			if (i < COLORS.size())
				color = COLORS.get(i);
			else
				color = new RGB(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			ABFSeries.setSymbolColor(new Color(Display.getDefault(), color));
		}
		
		IAxisSet axisSet = chart.getAxisSet();
		axisSet.adjustRange();
		chart.redraw();
	}
	
	@Override
	public void close() {
		if (resultWindow != null)
			resultWindow.close();
	}
 
}
