package org.pr.clustering.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pr.clustering.AbstractClusteringAlgorithm;
import org.pr.clustering.ClusteringAlgorithm;
import org.pr.clustering.ControllerIF;
import org.pr.clustering.ControllerImpl;

public class MainWindow extends Composite {

	private Combo algorithmCombo = null;
	private Label label = null;
	private Combo kCombo = null;
	private Label klabel = null;
	private Label label1 = null;
	private Label label2 = null;
	private Text mText = null;
	private Text alphaText = null;
	
	private Button button = null;
	private Text inputFileText = null;
	private Label label3 = null;
    
	private Shell shell = null;
	private Button goButton = null;
	
	ControllerIF controller = new ControllerImpl();
	private Button lastColumnIsLabelCheckBox = null;
	private Button tabRadioButton = null;
	private Label label4 = null;
	private Button spaceRadioButton = null;
	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initialize();
		shell = this.getShell();
	}

	private void initialize() {
		button = new Button(this, SWT.NONE);
		button.setBounds(new Rectangle(162, 201, 94, 25));
		button.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		button.setText("Browse");
		button.addMouseListener(new MouseListener(){
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog fd 
					= new FileDialog(shell, SWT.OPEN);
			    fd.setText("Open");
			    fd.setFilterPath("C:/");
			    String[] filterExt = {"*.txt","*.in","*.*"};
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
		createAlgorithmCombo();
		this.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		setSize(new Point(810, 648));
		setLayout(null);
		label = new Label(this, SWT.NONE);
		label.setBounds(new Rectangle(8, 53, 67, 18));
		label.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label.setText("Algorithm");
		createKCombo();
		klabel = new Label(this, SWT.RIGHT);
		klabel.setBounds(new Rectangle(8, 92, 67, 15));
		klabel.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		klabel.setText("K");
		label1 = new Label(this, SWT.RIGHT);
		label1.setBounds(new Rectangle(8, 165, 67, 28));
		label1.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label1.setText("Alpha");
		label2 = new Label(this, SWT.RIGHT);
		label2.setBounds(new Rectangle(8, 128, 67, 16));
		label2.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label2.setText("m");
		mText = new Text(this, SWT.BORDER);
		mText.setLocation(new Point(85, 126));
		mText.setSize(new Point(62, 21));
		alphaText = new Text(this, SWT.BORDER);
		alphaText.setLocation(new Point(85, 163));
		alphaText.setSize(new Point(62, 21));
		inputFileText = new Text(this, SWT.BORDER);
		inputFileText.setLocation(new Point(7, 231));
		inputFileText.setSize(new Point(249, 21));
		label3 = new Label(this, SWT.NONE);
		label3.setBounds(new Rectangle(8, 202, 85, 19));
		label3.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label3.setText("Dataset File:");
		goButton = new Button(this, SWT.NONE);
		goButton.setBounds(new Rectangle(160, 333, 93, 29));
		goButton.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		goButton.setText("Go >>");
		lastColumnIsLabelCheckBox = new Button(this, SWT.CHECK);
		lastColumnIsLabelCheckBox.setBounds(new Rectangle(10, 293, 184, 22));
		lastColumnIsLabelCheckBox.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		lastColumnIsLabelCheckBox.setText("Last column is a label");
		tabRadioButton = new Button(this, SWT.RADIO);
		tabRadioButton.setBounds(new Rectangle(90, 262, 48, 19));
		tabRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		tabRadioButton.setText("TAB");
		label4 = new Label(this, SWT.NONE);
		label4.setBounds(new Rectangle(9, 262, 77, 19));
		label4.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label4.setForeground(new Color(Display.getCurrent(), 0, 0, 160));
		label4.setText("Separator:");
		spaceRadioButton = new Button(this, SWT.RADIO);
		spaceRadioButton.setBounds(new Rectangle(145, 262, 52, 19));
		spaceRadioButton.setFont(new Font(Display.getDefault(), "Calibri", 10, SWT.NORMAL));
		spaceRadioButton.setText("SPACE");
		goButton.addListener(SWT.MouseUp, new Listener(){
			@Override
			public void handleEvent(Event event) {
				String algorithm = algorithmCombo.getItem(algorithmCombo.getSelectionIndex()).trim();
				int k =  kCombo.getText() != null && ! kCombo.getText().equals("")
					? Integer.valueOf(kCombo.getText())
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
				AbstractClusteringAlgorithm clusteringAlgorithm 
					= AbstractClusteringAlgorithm.Factory.create
						(ClusteringAlgorithm.createAlgorithmByName(algorithm), k, m, alpha, fileName, delimeter, lastColumnIsLable);
				clusteringAlgorithm.partition();
			}
			
		});
	}

	/**
	 * This method initializes algorithmCombo	
	 *
	 */
	private void createAlgorithmCombo() {
		algorithmCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		algorithmCombo.setText("this");
		algorithmCombo.setItems
			(("K-means,"
			 + "DHF,DHB,AFB,ABF,"
			 + "Hierarchical,    SINGLE,    COMPLETE,    UPGMA,    WPGMA,    UPGMC,    WPGMC,    Ward,"
			 + "Fuzzy K-means,Soft K-means")
			 	.split(","));
		algorithmCombo.setBounds(new Rectangle(85, 53, 169, 21));
	}

	/**
	 * This method initializes kCombo	
	 *
	 */
	private void createKCombo() {
		kCombo = new Combo(this, SWT.NONE);
		kCombo.setBounds(new Rectangle(85, 89, 54, 20));
		kCombo.setItems("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20".split(","));
	}

}  //  @jve:decl-index=0:visual-constraint="-6,0"
