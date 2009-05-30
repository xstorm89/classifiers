package org.pr.clustering.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;

/**
 * @author Alaa
 */
public class ChartWindow implements Closeable {

	private Shell resultWindow = null;  //  @jve:decl-index=0:visual-constraint="56,71"
	private Chart chart = null;
	private Composite chartComposite = null;

	private Button saveImageButton = null;
	/**
	 * This method initializes canvas	
	 *
	 */
	private void createChart() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		chart = new Chart(chartComposite, SWT.NONE);
		chart.setLayoutData(gridData);
		chart.getTitle().setText("Hard Partitioning Algorithms");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Runs");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Objective Function");
	}

	public void setWindowTitle(String title) {
		resultWindow.setText(title);
	}
	
	public void setChartTitle(String title) {
		chart.getTitle().setText(title);
	}
	
	public void setXAxixTitle(String title) {
		chart.getAxisSet().getXAxis(0).getTitle().setText(title);
	}
	
	public void setYAxixTitle(String title) {
		chart.getAxisSet().getYAxis(0).getTitle().setText(title);
	}
	
	/**
	 * This method initializes chartComposite	
	 *
	 */
	private void createChartComposite() {
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(100,-30);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment();
		formData.left = new FormAttachment();
		chartComposite = new Composite(resultWindow, SWT.NONE);
		chartComposite.setLayout(new GridLayout());
		chartComposite.setLayoutData(formData);
		createChart();
	}

	/**
	 * This method initializes mainWindow
	 */
	public void createMainWindow() {
		FormData formData3 = new FormData();
		formData3.bottom = new FormAttachment(100,-5);
		formData3.right = new FormAttachment(100,-50);
		formData3.left = new FormAttachment(70,50);
		FormData formData2 = new FormData();
		formData2.bottom = new FormAttachment(100,-5);
		formData2.left = new FormAttachment(30,50);
		formData2.right = new FormAttachment(70,-50);
		FormData formData1 = new FormData();
		formData1.bottom = new FormAttachment(100,-5);
		formData1.right = new FormAttachment(30,-50);
		formData1.left = new FormAttachment(0,50);
		resultWindow = new Shell();
		resultWindow.setImage(new Image(Display.getCurrent(),"resources/MathWorldIcon.gif"));
		resultWindow.setText("Hard Partitioning Algorithms");
		resultWindow.setMaximized(true);
		createChartComposite();
		resultWindow.setSize(new Point(515, 429));
		resultWindow.setLayout(new FormLayout());
		saveImageButton = new Button(resultWindow, SWT.NONE);
		saveImageButton.setText("Save Image");
		saveImageButton.setLayoutData(formData3);
		saveImageButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FileDialog dlg = new FileDialog(resultWindow,SWT.SAVE);
						dlg.setFilterNames(new String[] {"Windows Bitmap with compression (*.bmp)","Graphics Interchange Format (*.gif)","JPEG (*.jpg)","PNG (*.png)"});
						dlg.setFilterExtensions(new String[] {"*.bmp","*.gif","*.jpg","*.png"});
						String filename = dlg.open();
						if(filename != null) {
							int format=0;
							if(filename.endsWith(".bmp"))
								format = SWT.IMAGE_BMP_RLE;
							else if(filename.endsWith("*.gif"))
								format = SWT.IMAGE_GIF;
							else if(filename.endsWith("*.jpg"))
								format = SWT.IMAGE_JPEG;
							else if(filename.endsWith("*.png"))
								format = SWT.IMAGE_PNG;
							
							
							Image image = new Image(Display.getCurrent(),chart.getBounds());
							GC gc = new GC(image);
							chart.print(gc);
							ImageLoader ldr = new ImageLoader();
							ldr.data = new ImageData[1];
							ldr.data[0] = image.getImageData();
							ldr.save(filename, format);
						}
						resultWindow.layout();
						resultWindow.redraw();
						resultWindow.notifyListeners(SWT.Paint, new Event());
						resultWindow.update();
					}
				});
	}

	public void enableSave() {
		saveImageButton.setEnabled(true);
	}

	public void open() {
		resultWindow.open();
		
	}

	public Chart getChart() {
		// TODO Auto-generated method stub
		return chart;
	}
	
	@Override
	public void close() {
		if (resultWindow != null && resultWindow.isVisible())
			resultWindow.close();
	}

}
