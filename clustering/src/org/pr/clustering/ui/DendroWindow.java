/**
 * 
 */
package org.pr.clustering.ui;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JDesktopPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import sun.awt.image.codec.JPEGImageEncoderImpl;

import com.sun.image.codec.jpeg.ImageFormatException;

import data.Matrix;
import ddraw.Dendrogram;
import ddraw.Jif;

/**
 * @author Alaa
 *
 */
public class DendroWindow {

	private Shell mainWindow = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Composite chartComposite = null;
	private int n,m;
	private Matrix x;  //  @jve:decl-index=0:
	private Button saveImageButton = null;
	private int method=0; //1 min, 2 max, 3 average, 4 mean
	private Frame locationFrame;
	private JDesktopPane desktop;
	public DendroWindow(){
		createMainWindow();
	}
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
		locationFrame = SWT_AWT.new_Frame(chartComposite);
		desktop = new JDesktopPane();
		locationFrame.add(desktop);
	
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
		chartComposite = new Composite(mainWindow, SWT.EMBEDDED);
		chartComposite.setLayout(new GridLayout());
		chartComposite.setLayoutData(formData);

		createChart();
	}



	/**
	 * This method initializes mainWindow
	 */
	private void createMainWindow() {
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
		mainWindow = new Shell();
		mainWindow.setImage(new Image(Display.getCurrent(),"ClusterMaster.ico"));
		mainWindow.setText("Hierarchical Clustering");
		mainWindow.setMaximized(true);
		createChartComposite();
		mainWindow.setSize(new Point(515, 429));
		mainWindow.setLayout(new FormLayout());
		saveImageButton = new Button(mainWindow, SWT.NONE);
		saveImageButton.setText("Save Image");
		saveImageButton.setEnabled(false);
		saveImageButton.setLayoutData(formData3);
		saveImageButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FileDialog dlg = new FileDialog(mainWindow,SWT.SAVE);
						dlg.setFilterNames(new String[] {"JPEG (*.jpg)"});
						dlg.setFilterExtensions(new String[] {"*.jpg"});
						String filename = dlg.open();
						if(filename != null) {
							save(((Jif)desktop.getSelectedFrame()).getDendro(),filename);
							
						}
					}
				});
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setM(int m) {
		this.m = m;
	}


	public void setX(Matrix x) {
		this.x = x;
	}

	public void enableSave() {
		saveImageButton.setEnabled(true);
	}
	public void runOptions(int method){
		//set parameters here
		this.method = method;
	}

	public void run() {
		//run
		HierarchicalClustering algo=null;
		String title = "Untitled";
		switch(method) {
		case 1:
			algo = new SingleLink(n,m,x);
			title = "Single Link";
			break;
		case 2:
			algo = new CompleteLink(n,m,x);
			title = "Complete Link";
			break;
		case 3:
			algo = new UPGMA(n,m,x);
			title = "UPGMA";
			break;
		case 4:
			algo = new UPGMC(n,m,x);
			title = "UPGMC";
			break;
		case 5:
			algo = new WPGMA(n,m,x);
			title = "WPGMA";
			break;
		case 6:
			algo = new WPGMC(n,m,x);
			title = "WPGMC";
			break;
		case 7:
			algo = new Ward(n,m,x);
			title = "Ward";
			break;
		}
//		locationFrame.removeAll();
		Jif jif = new Jif();
		algo.setDendro(jif.getDendro());
		algo.loop();
		jif.setup(new File(title));
		desktop.add(jif);
		try {
			jif.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		locationFrame.repaint();

	}
	
	public void save(Dendrogram d,String filename){
				try {
					FileOutputStream out = new FileOutputStream(new File(filename));

					//codage de l'image-tampon au format JPEG dans le fichier sélectionné
					JPEGImageEncoderImpl jpegenc = new JPEGImageEncoderImpl(out);
					jpegenc.encode(imageBuilder(d));
					out.close();
				} catch (ImageFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}//fin save()
	public BufferedImage imageBuilder(Dendrogram d){
		BufferedImage bi;

		//création de l' image-tampon au dimension du dendrogramme
		bi=new BufferedImage((int)d.getPreferredSize().getWidth(),(int)d.getPreferredSize().getHeight(),BufferedImage.TYPE_INT_RGB);

		//création du graphique de l' image-tampon
		Graphics2D g = bi.createGraphics();

		//dessin du dendrogramme dans ce graphique
		d.paintComponent(g);
		return bi;
	}
	public void open() {
		// TODO Auto-generated method stub
		mainWindow.open();
	}
	public JDesktopPane getDesktop() {
		// TODO Auto-generated method stub
		return desktop;
	}

//	private String format(double number){
//		String string = Double.toString(number);
//		try {
//			string= string.substring(0, string.indexOf('.')+5);
//		} catch (StringIndexOutOfBoundsException e) {
//		}
//		return string;
//	}

}
