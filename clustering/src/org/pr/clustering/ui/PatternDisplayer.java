package org.pr.clustering.ui;

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

public class PatternDisplayer implements Closeable {

	int numberOfRuns = 20;
	
	private Vector[] patterns;
	
	ChartWindow resultWindow;
	
	public PatternDisplayer(Vector[] patterns) {
		if (patterns == null || patterns.length == 0)
			throw new IllegalArgumentException("patterns should contain at least one element");
		
		if (patterns[0].getDimensionCount() < 2)
			throw new IllegalArgumentException("patterns should at least have 2 dimensions!");
		
		if (patterns[0].getDimensionCount() > 2)
			System.out.println("patterns has more than 2 dimensions. only 2 dimensions will be displayed, and the others will be ignored");
		
		this.patterns = patterns;
	}
	
	public void showPatterns() {
		resultWindow = new ChartWindow();
		resultWindow.createMainWindow();
		resultWindow.setChartTitle("Visualizing Patterns");
		resultWindow.setXAxixTitle("X");
		resultWindow.setYAxixTitle("Y");
		resultWindow.open();
		Chart chart = resultWindow.getChart();
		ISeriesSet seriesSet = chart.getSeriesSet();
		ISeries[] ser = seriesSet.getSeries();
		
		for (int i = 0; i < ser.length; i++) {
			seriesSet.deleteSeries(ser[i].getId());
		}

		if (patterns[0].getDimensionCount() == 2) { // we can draw it in 2D
			double[] xSeries = new double[patterns.length];
			double[] ySeries = new double[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				xSeries[i] = patterns[i].getDimension(0);
				ySeries[i] = patterns[i].getDimension(1);
			}
			
			ILineSeries ABFSeries= (ILineSeries) seriesSet.createSeries
			(SeriesType.LINE, "pattern");
			ABFSeries.setXSeries(xSeries);
			ABFSeries.setYSeries(ySeries);
			ABFSeries.setLineColor(new Color(Display.getDefault(), new RGB(255, 255, 255)));
			ABFSeries.setLineWidth(0);
			ABFSeries.setSymbolType(PlotSymbolType.CIRCLE);
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
