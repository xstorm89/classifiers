package org.pr.clustering.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.pr.clustering.hierarchical.LinkageCriterion;

public class MainWindow extends Composite {

	private Combo kCombo = null;
	private Label klabel = null;
	private Text inputFileText = null;
	private Button lastColumnIsLabelCheckBox = null;
	private Button tabRadioButton = null;
	private Label label4 = null;
	private Button spaceRadioButton = null;
	
	private Shell shell = null;
	private int style;
	
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
	private Group hierarchicalGroup = null;
	private Button singleLinkRadioButton = null;
	private Button completeLinkRadioButton = null;
	private Button upgmaRadioButton = null;
	private Button upgmcRadioButton = null;
	private Button wpgmaRadioButton = null;
	private Button wpgmcRadioButton = null;
	private Button wardRadioButton = null;
	private Button hierarchicalGoButton = null;
	private Button commaRadioButton = null;
	private Group fuzzyGroup = null;
	private Button fuzzyRadioButton = null;
	private Button softRadioButton = null;
	private Label alphaLabel = null;
	private Label label1 = null;
	private Text mText = null;
	private Text alphaText = null;
	private Label label2 = null;
	private Text fuzzyKText = null;
	private Button fuzzyGoButton = null;
	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initialize();
		shell = this.getShell();
		this.style = style;
		this.getShell().setImage(new Image(Display.getCurrent(),"resources/MathWorldIcon.gif"));
	}

	private void initialize() {
		this.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		setSize(new Point(810, 648));
		setLayout(null);
		createHardPartitioningGroup();
		createDataSetGroup();
		createHierarchicalGroup();
		createFuzzyGroup();
	}

	/**
	 * This method initializes hardPartitioningGroup	
	 *
	 */
	private void createHardPartitioningGroup() {
		hardPartitioningGroup = new Group(this, SWT.NONE);
		hardPartitioningGroup.setLayout(null);
		hardPartitioningGroup.setText("Partitioning Algorithms");
		hardPartitioningGroup.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		hardPartitioningGroup.setBounds(new Rectangle(9, 125, 383, 174));
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
		aFBCheckBox.setBounds(new Rectangle(9, 89, 110, 16));
		aFBCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		aFBCheckBox.setText("AFB Algorithm");
		aFBCheckBox.setSelection(false);
		aBFCheckBox = new Button(hardPartitioningGroup, SWT.CHECK);
		aBFCheckBox.setBounds(new Rectangle(9, 110, 110, 16));
		aBFCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		aBFCheckBox.setText("ABF Algorithm");
		aBFCheckBox.setSelection(false);
		
		klabel = new Label(hardPartitioningGroup, SWT.LEFT);
		klabel.setBounds(new Rectangle(9, 141, 17, 20));
		klabel.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		klabel.setText("K:");
		
		kCombo = new Combo(hardPartitioningGroup, SWT.NONE);
		kCombo.setBounds(new Rectangle(31, 140, 48, 21));
		kCombo.setItems("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20".split(","));
		
		numOfRunsText = new Text(hardPartitioningGroup, SWT.BORDER);
		numOfRunsText.setBounds(new Rectangle(186, 140, 62, 19));
		label5 = new Label(hardPartitioningGroup, SWT.NONE);
		label5.setBounds(new Rectangle(91, 141, 97, 19));
		label5.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		label5.setText("Number of Runs:");
		partitioningGoButton = new Button(hardPartitioningGroup, SWT.NONE);
		partitioningGoButton.setBounds(new Rectangle(301, 138, 77, 23));
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
		dataSetGroup.setBounds(new Rectangle(7, 6, 384, 111));
		
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
			    fd.setFilterPath("F:/Masters/Pattern Recognition/Datasets");
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
		tabRadioButton.setBounds(new Rectangle(92, 55, 43, 21));
		tabRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		tabRadioButton.setText("TAB");
		
		spaceRadioButton = new Button(dataSetGroup, SWT.RADIO);
		spaceRadioButton.setBounds(new Rectangle(141, 55, 56, 19));
		spaceRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		spaceRadioButton.setText("SPACE");
		commaRadioButton = new Button(dataSetGroup, SWT.RADIO);
		commaRadioButton.setBounds(new Rectangle(200, 55, 67, 18));
		commaRadioButton.setText("COMMA");
		commaRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		
	}

	/**
	 * This method initializes hierarchicalGroup	
	 *
	 */
	private void createHierarchicalGroup() {
		hierarchicalGroup = new Group(this, SWT.NONE);
		hierarchicalGroup.setLayout(null);
		hierarchicalGroup.setText("Hierarchical Algorithms");
		hierarchicalGroup.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		hierarchicalGroup.setBounds(new Rectangle(404, 125, 381, 175));
		singleLinkRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		singleLinkRadioButton.setBounds(new Rectangle(9, 24, 138, 16));
		singleLinkRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		singleLinkRadioButton.setSelection(true);
		singleLinkRadioButton.setText("Single Link");
		completeLinkRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		completeLinkRadioButton.setBounds(new Rectangle(9, 45, 138, 16));
		completeLinkRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		completeLinkRadioButton.setText("Complete Link");
		upgmaRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		upgmaRadioButton.setBounds(new Rectangle(9, 66, 138, 16));
		upgmaRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		upgmaRadioButton.setText("UPGMA");
		upgmcRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		upgmcRadioButton.setBounds(new Rectangle(9, 87, 138, 16));
		upgmcRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		upgmcRadioButton.setText("UPGMC");
		wpgmaRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wpgmaRadioButton.setBounds(new Rectangle(9, 108, 138, 16));
		wpgmaRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		wpgmaRadioButton.setText("WPGMA");
		wpgmcRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wpgmcRadioButton.setBounds(new Rectangle(9, 129, 138, 16));
		wpgmcRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		wpgmcRadioButton.setText("WPGMC");
		wardRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wardRadioButton.setBounds(new Rectangle(9, 150, 138, 16));
		wardRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		wardRadioButton.setText("Ward");
		hierarchicalGoButton = new Button(hierarchicalGroup, SWT.NONE);
		hierarchicalGoButton.setText("Go >>");
		hierarchicalGoButton.setLocation(new Point(302, 137));
		hierarchicalGoButton.setSize(new Point(77, 23));
		hierarchicalGoButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		hierarchicalGoButton.addListener(SWT.MouseUp, new Listener(){
			@Override
			public void handleEvent(Event event) {
				LinkageCriterion linkageCriterion = null;  
				if (singleLinkRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.SINGLE;
				if (completeLinkRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.COMPLETE;
				if (upgmaRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.UPGMA;
				if (wpgmaRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.WPGMA;
				if (upgmcRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.UPGMC;
				if (wpgmcRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.WPGMC;
				if (wardRadioButton.getSelection())
					linkageCriterion = LinkageCriterion.Ward;
				String fileName = inputFileText.getText();
				String delimeter = tabRadioButton.getSelection()
					? "\t"
					: spaceRadioButton.getSelection()
						? " "
						: ",";
				boolean lastColumnIsLable = lastColumnIsLabelCheckBox.getSelection();
				controller.runHierarchicalAlgorithm(linkageCriterion, fileName, delimeter, lastColumnIsLable);
			}});
	}

	/**
	 * This method initializes fuzzyGroup	
	 *
	 */
	private void createFuzzyGroup() {
		fuzzyGroup = new Group(this, SWT.NONE);
		fuzzyGroup.setLayout(null);
		fuzzyGroup.setText("Fuzzy and Soft Algorithms");
		fuzzyGroup.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		fuzzyGroup.setBounds(new Rectangle(8, 311, 388, 147));
		fuzzyRadioButton = new Button(fuzzyGroup, SWT.RADIO);
		fuzzyRadioButton.setBounds(new Rectangle(18, 30, 97, 18));
		fuzzyRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		fuzzyRadioButton.setSelection(true);
		fuzzyRadioButton.setText("Fuzzy K-Means");
		fuzzyRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				widgetSelected(arg0);
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				alphaLabel.setVisible(false);
				alphaText.setVisible(false);
			}
		});
		softRadioButton = new Button(fuzzyGroup, SWT.RADIO);
		softRadioButton.setBounds(new Rectangle(126, 28, 100, 21));
		softRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		softRadioButton.setText("Soft K-Means");
		softRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				widgetSelected(arg0);
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				alphaLabel.setVisible(true);
				alphaText.setVisible(true);
			}
		});
		alphaLabel = new Label(fuzzyGroup, SWT.NONE);
		alphaLabel.setBounds(new Rectangle(5, 117, 43, 18));
		alphaLabel.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		alphaLabel.setVisible(false);
		alphaLabel.setText("Alpha:");
		label1 = new Label(fuzzyGroup, SWT.NONE);
		label1.setBounds(new Rectangle(30, 90, 18, 20));
		label1.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		label1.setText("m:");
		mText = new Text(fuzzyGroup, SWT.BORDER);
		mText.setBounds(new Rectangle(56, 87, 70, 21));
		alphaText = new Text(fuzzyGroup, SWT.BORDER);
		alphaText.setBounds(new Rectangle(55, 116, 71, 21));
		alphaText.setVisible(false);
		label2 = new Label(fuzzyGroup, SWT.NONE);
		label2.setBounds(new Rectangle(34, 62, 14, 19));
		label2.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		label2.setText("K:");
		fuzzyKText = new Text(fuzzyGroup, SWT.BORDER);
		fuzzyKText.setBounds(new Rectangle(56, 59, 70, 21));
		fuzzyGoButton = new Button(fuzzyGroup, SWT.NONE);
		fuzzyGoButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		fuzzyGoButton.setLocation(new Point(303, 114));
		fuzzyGoButton.setSize(new Point(77, 23));
		fuzzyGoButton.setText("Go >>");
		fuzzyGoButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int k = fuzzyKText.getText() != null && ! fuzzyKText.getText().equals("")
					? Integer.valueOf(fuzzyKText.getText())
					: 0;
					
				double m = mText.getText() != null && ! mText.getText().equals("")
					? Double.valueOf(mText.getText())
					: 0;
					
				double alpha = alphaText.getText() != null && ! alphaText.getText().equals("")
					? Double.valueOf(alphaText.getText())
					: 0;
				
				String fileName = inputFileText.getText();
				String delimeter = tabRadioButton.getSelection()
					? "\t"
					: " ";
				boolean lastColumnIsLable = lastColumnIsLabelCheckBox.getSelection();
				
				if (fuzzyRadioButton.getSelection()) {
					String result = controller.runFuzzyKMeansAlgorithm(k, m, fileName, delimeter, lastColumnIsLable);
					new ClusteringResultWindow("Fuzzy K-Means Clustering", result).open();
				} else {
					String result = controller.runSoftKMeansAlgorithm(k, m, alpha, fileName, delimeter, lastColumnIsLable);
					new ClusteringResultWindow("Soft K-Means Clustering", result).open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				widgetSelected(arg0);
			}});
	}

}  //  @jve:decl-index=0:visual-constraint="-5,-1"
