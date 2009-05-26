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
import org.pr.clustering.hierarchical.Cluster;

import sun.awt.image.codec.JPEGImageEncoderImpl;

import com.sun.image.codec.jpeg.ImageFormatException;

import ddraw.Dendrogram;
import ddraw.Jif;
import ddraw.Node;

/**
 * @author Alaa
 *
 */
public class DendroWindow {

	private Shell mainWindow = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Composite chartComposite = null;
	private Button saveImageButton = null;
	private Frame locationFrame;
	private JDesktopPane desktop;
	
	private Cluster rootCluster;
	
	Shell shell = null;
	
	public DendroWindow(Cluster rootCluster) {
		this.rootCluster = rootCluster;
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
		mainWindow.setImage(new Image(Display.getCurrent(),"resources/MathWorldIcon.gif"));
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

	public void enableSave() {
		saveImageButton.setEnabled(true);
	}
	
	public JDesktopPane getDesktop() {
		// TODO Auto-generated method stub
		return desktop;
	}
	
	public void open() {
		// TODO Auto-generated method stub
		mainWindow.open();
	}
	
	public void run() {
		mainWindow.open();
		JDesktopPane desktop = this.getDesktop();
		Jif jif = new Jif();
		fillDendro(jif.getDendro(), rootCluster);
		jif.setSize(desktop.getSize());
		jif.setup(new File("C:/dendroFile.jif"));
		desktop.add(jif);
		try {
			jif.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		desktop.repaint();
		this.enableSave();
		// locationFrame.repaint();
	}
	
	private void fillDendro(Dendrogram dendro, Cluster root) {
		if(root.left == null) { // root.right would also equal null
			dendro.getHash().put(root.name, new Node(root.name));
			return;
		}
		
		fillDendro(dendro, root.left);
		fillDendro(dendro, root.right);
		
		Node newNode = new Node
			((Node) dendro.getHash().get(root.left.name),
			(Node) dendro.getHash().get(root.right.name),
			root.left.name,
			root.distanceBetweenLeftAndRightClusters);

		dendro.getHash().put(root.name, newNode);
		dendro.setLastNode(root.name);
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

}
