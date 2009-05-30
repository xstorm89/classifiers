package org.pr.clustering.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Alaa
 */
public class ClusteringResultWindow implements Closeable {

	private Shell resultWindow = null;  //  @jve:decl-index=0:visual-constraint="-30,-16"
	private String printedClusteringResult;
	private String title;
	
	public ClusteringResultWindow(String title, String printedClusteringResult) {
		this.printedClusteringResult = printedClusteringResult;
		this.title = title;
		createMainWindow();
	}
	
	/**
	 * This method initializes mainWindow
	 */
	public void createMainWindow() {
		resultWindow = new Shell();
		resultWindow.setImage(new Image(Display.getCurrent(),"resources/MathWorldIcon.gif"));
		resultWindow.setText(title);
		resultWindow.setMaximized(false);
		resultWindow.setSize(new Point(394, 671));
		resultWindow.setLayout(new FillLayout());
		
		FormData formData2 = new FormData();
		formData2.bottom = new FormAttachment(100,-5);
		formData2.left = new FormAttachment(30,50);
		formData2.right = new FormAttachment(70,-50);
		FormData formData1 = new FormData();
		formData1.bottom = new FormAttachment(100,-5);
		formData1.right = new FormAttachment(30,-50);
		formData1.left = new FormAttachment(0,50);
		Text clusteringResultTextArea = new Text(resultWindow, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		clusteringResultTextArea.setText(printedClusteringResult);
	}

	public void open() {
		resultWindow.open();
	}
	
	@Override
	public void close() {
		if (resultWindow != null && resultWindow.isVisible())
			resultWindow.close();
	}

}  //  @jve:decl-index=0:visual-constraint="153,17"
