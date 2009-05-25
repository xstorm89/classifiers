package org.pr.clustering.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pr.clustering.ClusteringAlgorithm;
import org.pr.clustering.ControllerIF;
import org.pr.clustering.ControllerImpl;

public class MainWindow extends Composite {

	private Combo kCombo = null;
	private Label klabel = null;
	private Text inputFileText = null;
	private Button lastColumnIsLabelCheckBox = null;
	private Button tabRadioButton = null;
	private Label label4 = null;
	private Button spaceRadioButton = null;
	
	private Shell shell = null;
	
	ControllerIF controller = new ControllerImpl();
	private Group hardPartitioningGroup = null;  //  @jve:decl-index=0:visual-constraint="14,661"
	private Button kMeansCheckBox = null;
	private Button dHBCheckBox = null;
	private Button dHFCheckBox = null;
	private Button aFBCheckBox = null;
	private Button aBFCheckBox = null;
	private Text numOfRunsText = null;
	private Label label5 = null;
	private Button partitioningGoButton = null;
	private Group dataSetGroup = null;
	private Button BrowseButton = null;
	
	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initialize();
		shell = this.getShell();
		this.getShell().setImage(new Image(Display.getCurrent(),"clustering.jpg"));
	}

	private void initialize() {
		this.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		setSize(new Point(810, 648));
		setLayout(null);
		createHardPartitioningGroup();
		createDataSetGroup();
	}

	/**
	 * This method initializes hardPartitioningGroup	
	 *
	 */
	private void createHardPartitioningGroup() {
		hardPartitioningGroup = new Group(this, SWT.NONE);
		hardPartitioningGroup.setLayout(null);
		hardPartitioningGroup.setText("Clustering Algorithms");
		hardPartitioningGroup.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		hardPartitioningGroup.setBounds(new Rectangle(9, 125, 258, 146));
		kMeansCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		kMeansCheckBox.setBounds(new Rectangle(8, 26, 121, 16));
		kMeansCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		kMeansCheckBox.setText("KMeans Algorithm");
		kMeansCheckBox.setVisible(true);
		kMeansCheckBox.setSelection(false);
		dHFCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		dHFCheckBox.setBounds(new Rectangle(8, 68, 102, 16));
		dHFCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		dHFCheckBox.setText("DHF Algorithm");
		dHFCheckBox.setSelection(false);
		dHBCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		dHBCheckBox.setBounds(new Rectangle(8, 47, 98, 16));
		dHBCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		dHBCheckBox.setText("DHB Algorithm");
		dHBCheckBox.setSelection(false);
		aFBCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		aFBCheckBox.setBounds(new Rectangle(134, 26, 110, 16));
		aFBCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		aFBCheckBox.setText("AFB Algorithm");
		aFBCheckBox.setSelection(false);
		aBFCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		aBFCheckBox.setBounds(new Rectangle(134, 47, 110, 16));
		aBFCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		aBFCheckBox.setText("ABF Algorithm");
		aBFCheckBox.setSelection(false);
		
		klabel = new Label(hardPartitioningGroup, SWT.LEFT);
		klabel.setBounds(new Rectangle(8, 92, 17, 20));
		klabel.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		klabel.setText("K:");
		
		kCombo = new Combo(hardPartitioningGroup, SWT.NONE);
		kCombo.setBounds(new Rectangle(30, 91, 48, 21));
		kCombo.setItems("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20".split(","));
		
		numOfRunsText = new Text(hardPartitioningGroup, SWT.BORDER);
		numOfRunsText.setBounds(new Rectangle(185, 91, 62, 19));
		label5 = new Label(hardPartitioningGroup, SWT.NONE);
		label5.setBounds(new Rectangle(90, 92, 97, 19));
		label5.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		label5.setText("Number of Runs:");
		partitioningGoButton = new Button(hardPartitioningGroup, SWT.NONE);
		partitioningGoButton.setBounds(new Rectangle(91, 117, 77, 23));
		partitioningGoButton.setText("Go >>");
		partitioningGoButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		partitioningGoButton.addListener(SWT.MouseUp, new Listener(){
			@Override
			public void handleEvent(Event event) {
				List<ClusteringAlgorithm> algorithms = new ArrayList<ClusteringAlgorithm>();
				if (kMeansCheckBox.getSelection())
					algorithms.add(ClusteringAlgorithm.KMeans);
				if (dHFCheckBox.getSelection())
					algorithms.add(ClusteringAlgorithm.DHF);
				if (dHBCheckBox.getSelection())
					algorithms.add(ClusteringAlgorithm.DHB);
				if (aFBCheckBox.getSelection())
					algorithms.add(ClusteringAlgorithm.AFB);
				if (aBFCheckBox.getSelection())
					algorithms.add(ClusteringAlgorithm.ABF);
				int k = kCombo.getText() != null && ! kCombo.getText().equals("")
					? Integer.valueOf(kCombo.getText())
					: 0; 
				String fileName = inputFileText.getText();
				String delimeter = tabRadioButton.getSelection()
					? "\t"
					: " ";
				boolean lastColumnIsLable = lastColumnIsLabelCheckBox.getSelection();
				int numberOfRuns = numOfRunsText.getText() != null && ! numOfRunsText.getText().equals("")
					? Integer.valueOf(numOfRunsText.getText())
					: 5; 
				controller.doKMeansBenchmark(algorithms, k, fileName, delimeter, lastColumnIsLable, numberOfRuns);
			}});
	}

	/**
	 * This method initializes dataSetGroup	
	 *
	 */
	private void createDataSetGroup() {
		dataSetGroup = new Group(this, SWT.NONE);
		dataSetGroup.setLayout(null);
		dataSetGroup.setText("Dataset");
		dataSetGroup.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		dataSetGroup.setBounds(new Rectangle(7, 6, 377, 111));
		
		inputFileText = new Text(dataSetGroup, SWT.BORDER);
		inputFileText.setBounds(new Rectangle(14, 24, 239, 21));
		
		BrowseButton = new Button(dataSetGroup, SWT.NONE);
		BrowseButton.setBounds(new Rectangle(258, 23, 116, 24));
		BrowseButton.setText("Browse");
		BrowseButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		BrowseButton.addMouseListener(new MouseListener(){
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog fd 
					= new FileDialog(shell, SWT.OPEN);
			    fd.setText("Open");
			    fd.setFilterPath("C:/");
			    String[] filterExt = {"*.in","*.txt","*.*"};
			    fd.setFilterExtensions(filterExt);
				inputFileText.setText(fd.open());
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}
			
		});

		label4 = new Label(dataSetGroup, SWT.NONE);
		label4.setBounds(new Rectangle(15, 55, 75, 20));
		label4.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label4.setForeground(new Color(Display.getCurrent(), 0, 0, 160));
		label4.setText("Separator:");
		
		lastColumnIsLabelCheckBox = new Button(dataSetGroup, SWT.CHECK);
		lastColumnIsLabelCheckBox.setBounds(new Rectangle(18, 82, 210, 24));
		lastColumnIsLabelCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		lastColumnIsLabelCheckBox.setText("Last column is a label");
		
		tabRadioButton = new Button(dataSetGroup, SWT.RADIO);
		tabRadioButton.setBounds(new Rectangle(92, 55, 54, 21));
		tabRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		tabRadioButton.setText("TAB");
		
		spaceRadioButton = new Button(dataSetGroup, SWT.RADIO);
		spaceRadioButton.setBounds(new Rectangle(152, 57, 56, 19));
		spaceRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		spaceRadioButton.setText("SPACE");
		
	}

}  //  @jve:decl-index=0:visual-constraint="-5,-1"
