/**
 * 
 */
package org.pr.clustering.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Alaa
 *
 */
public class RunDialog extends Composite {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="213,39"
	private Group group = null;  //  @jve:decl-index=0:visual-constraint="668,288"
	private Button kMeansCheckBox = null;
	private Button dHBCheckBox = null;
	private Button dHFCheckBox = null;
	private Button aFBCheckBox = null;
	private Button aBFCheckBox = null;
	private Label label = null;
	private Text runsText = null;
	private Button goButton = null;
	private Button cancelButton = null;
	private MainWindow owner;
	private Group clusteringGroup = null;
	private Button classicClustering = null;
	private Button softClustering = null;
	private Button hierarchicalClustering = null;
	private Group hierarchicalGroup;
	private Button singleLinkRadioButton;
	private Button completeLinkRadioButton;
	private Button upgmcRadioButton;
	private Button upgmaRadioButton;
	private Button wpgmaRadioButton;
	private Button wpgmcRadioButton;
	private Button wardRadioButton;
	private Group softGroup;
	private Label epsilonLabel;
	private Text epsilonText;
	private Label alphaLabel;
	private Text alphaText;
	private Label fuzzifierLabel;
	private Text fuzzifierText;
	
	public RunDialog(Composite parent, int style) {
		super(parent, style);
		createSShell(getVisible(), getEnabled(), getDragDetect(), isDisposed(), false, style, style, style, style, style, style);
	}
	
//	public RunDialog(MainWindow owner, boolean runKMeans, boolean runDHB, boolean runDHF, boolean runAFB, boolean runABF, int numberOfRuns, double epsilon, double m, double alpha, int method, int runType) {
//		this.owner = owner;
//		createSShell( runKMeans, runDHB, runDHF, runAFB, runABF, numberOfRuns, epsilon, m, alpha, method, runType);
//	}

	/**
	 * This method initializes sShell
	 * @param runType 
	 * @param method 
	 * @param alpha 
	 * @param m 
	 * @param epsilon 
	 * @param numberOfRuns 
	 * @param runABF 
	 * @param runAFB 
	 * @param runDHF 
	 * @param runDHB 
	 * @param runKMeans 
	 */
	private void createSShell(boolean runKMeans, boolean runDHB, boolean runDHF, boolean runAFB, boolean runABF, int numberOfRuns, double epsilon, double m, double alpha, int method, int runType) {
		sShell = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		sShell.setImage(new Image(Display.getCurrent(),"ClusterMaster.ico"));
		sShell.setText("Run");
		sShell.setSize(new Point(446, 304));
		sShell.setLayout(null);
		createGroup(runKMeans, runDHB, runDHF, runAFB, runABF);
		createHGroup(method);
		createComposite(epsilon, m, alpha);
		createClusteringGroup(runType);
		label = new Label(group, SWT.NONE);
		label.setText("Number of runs:");
		label.setBounds(new Rectangle(110, 66, 78, 13));
		runsText = new Text(group, SWT.BORDER);
		runsText.setBounds(new Rectangle(190, 63, 57, 19));
		runsText.setText(Integer.toString(numberOfRuns));
		goButton = new Button(sShell, SWT.NONE);
		goButton.setBounds(new Rectangle(194, 230, 108, 23));
		goButton.setText("Go");
		goButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//				owner.runOptions(kMeansCheckBox.getSelection(), dHBCheckBox.getSelection(), dHFCheckBox.getSelection(), aFBCheckBox.getSelection(), aBFCheckBox.getSelection(), Integer.parseInt(runsText.getText()));
//				owner.softOptions(Double.parseDouble(epsilonText.getText())
//						,Double.parseDouble(alphaText.getText())
//						,Double.parseDouble(fuzzifierText.getText()));
				int method;
				if(singleLinkRadioButton.getSelection())
					method = 1;
				else if(completeLinkRadioButton.getSelection())
					method = 2;
				else if(upgmaRadioButton.getSelection())
					method = 3;
				else if(upgmcRadioButton.getSelection())
					method = 4;
				else if(wpgmaRadioButton.getSelection())
					method = 5;
				else if(wpgmcRadioButton.getSelection())
					method = 6;
				else
					method = 7;
				int type = 0;
				if(classicClustering.getSelection())
					type=(1);
				else if(softClustering.getSelection())
					type=(2);
				else if(hierarchicalClustering.getSelection())
					type=(3);
//				owner.hierarchicalOptions(method);
				sShell.setVisible(false);
				sShell.close();
				sShell.dispose();
//				owner.run(type);
//				owner.enableSave();
			}
		});
		cancelButton = new Button(sShell, SWT.NONE);
		cancelButton.setBounds(new Rectangle(306, 230, 108, 23));
		cancelButton.setText("Cancel");
		cancelButton
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShell.setVisible(false);
						sShell.close();
						sShell.dispose();
					}
				});
	}

	/**
	 * This method initializes composite	
	 * @param alpha 
	 * @param m 
	 * @param epsilon 
	 *
	 */
	private void createComposite(double epsilon, double m, double alpha) {
		softGroup = new Group(sShell, SWT.NONE);
		softGroup.setText("Soft Clustering Parameters");
		softGroup.setLayout(null);
		softGroup.setBounds(new Rectangle(164, 93, 258, 89));
		softGroup.setVisible(false);
		epsilonLabel = new Label(softGroup, SWT.NONE);
		epsilonLabel.setText("Epsilon:");
		epsilonLabel.setBounds(new Rectangle(15, 18, 37, 18));
		epsilonText = new Text(softGroup, SWT.BORDER);
		epsilonText.setText(Double.toString(epsilon));
		epsilonText.setBounds(new Rectangle(57, 18, 193, 18));
		alphaLabel = new Label(softGroup, SWT.NONE);
		alphaLabel.setText("Alpha:");
		alphaLabel.setBounds(new Rectangle(21, 41, 31, 18));
		alphaText = new Text(softGroup, SWT.BORDER);
		alphaText.setText(Double.toString(alpha));
		alphaText.setBounds(new Rectangle(57, 41, 193, 18));
		fuzzifierLabel = new Label(softGroup, SWT.NONE);
		fuzzifierLabel.setText("Fuzzifier:");
		fuzzifierLabel.setBounds(new Rectangle(8, 64, 44, 17));
		fuzzifierText = new Text(softGroup, SWT.BORDER);
		fuzzifierText.setText(Double.toString(m));
		fuzzifierText.setBounds(new Rectangle(57, 64, 193, 17));
	}
	/**
	 * This method initializes group	
	 * @param runABF 
	 * @param runAFB 
	 * @param runDHF 
	 * @param runDHB 
	 * @param runKMeans 
	 * @param runType 
	 *
	 */
	private void createGroup(boolean runKMeans, boolean runDHB, boolean runDHF, boolean runAFB, boolean runABF) {
		group = new Group(sShell, SWT.NONE);
		group.setText("Clustering Algorithms");
		group.setBounds(new Rectangle(163, 2, 257, 86));
		group.setLayout(null);
		kMeansCheckBox = new Button(group, SWT.CHECK);
		kMeansCheckBox.setText("KMeans Algorithm");
		kMeansCheckBox.setBounds(new Rectangle(8, 20, 110, 16));
		kMeansCheckBox.setSelection(runKMeans);
		dHBCheckBox = new Button(group, SWT.CHECK);
		dHBCheckBox.setText("DHB Algorithm");
		dHBCheckBox.setBounds(new Rectangle(8, 41, 98, 16));
		dHBCheckBox.setSelection(runDHB);
		dHFCheckBox = new Button(group, SWT.CHECK);
		dHFCheckBox.setText("DHF Algorithm");
		dHFCheckBox.setBounds(new Rectangle(8, 62, 92, 16));
		dHFCheckBox.setSelection(runDHF);
		aFBCheckBox = new Button(group, SWT.CHECK);
		aFBCheckBox.setText("AFB Algorithm");
		aFBCheckBox.setBounds(new Rectangle(134, 20, 110, 16));
		aFBCheckBox.setSelection(runAFB);
		aBFCheckBox = new Button(group, SWT.CHECK);
		aBFCheckBox.setText("ABF Algorithm");
		aBFCheckBox.setBounds(new Rectangle(134, 41, 110, 16));
		aBFCheckBox.setSelection(runABF);
	}
	/**
	 * This method initializes group	
	 * @param method 
	 *
	 */
	private void createHGroup(int method) {
		hierarchicalGroup = new Group(sShell, SWT.NONE);
		hierarchicalGroup.setText("Choose algorithms to run");
		hierarchicalGroup.setBounds(new Rectangle(3, 92, 154, 169));
		hierarchicalGroup.setLayout(null);
		singleLinkRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		singleLinkRadioButton.setText("Single Link");
		singleLinkRadioButton.setBounds(new Rectangle(8, 18, 138, 16));
		completeLinkRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		completeLinkRadioButton.setText("Complete Link");
		completeLinkRadioButton.setBounds(new Rectangle(8, 39, 138, 16));
		upgmaRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		upgmaRadioButton.setText("UPGMA");
		upgmaRadioButton.setBounds(new Rectangle(8, 60, 138, 16));
		upgmcRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		
		upgmcRadioButton.setText("UPGMC");
		upgmcRadioButton.setBounds(new Rectangle(8, 81, 138, 16));
		wpgmaRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wpgmaRadioButton.setText("WPGMA");
		wpgmaRadioButton.setBounds(new Rectangle(8, 102, 138, 16));
		wpgmcRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wpgmcRadioButton.setText("WPGMC");
		wpgmcRadioButton.setBounds(new Rectangle(8, 123, 138, 16));
		wardRadioButton = new Button(hierarchicalGroup, SWT.RADIO);
		wardRadioButton.setText("Ward");
		wardRadioButton.setBounds(new Rectangle(8, 144, 138, 16));
		switch(method) {
		case 1:
			singleLinkRadioButton.setSelection(true);
			break;
		case 2:
			completeLinkRadioButton.setSelection(true);
			break;
		case 3:
			upgmaRadioButton.setSelection(true);
			break;
		case 4:
			upgmcRadioButton.setSelection(true);
			break;
		case 5:
			wpgmaRadioButton.setSelection(true);
			break;
		case 6:
			wpgmcRadioButton.setSelection(true);
			break;
		case 7:
			wardRadioButton.setSelection(true);
			break;
		}
	}

	public void show() {
		sShell.setVisible(true);
		sShell.layout();
	}

	/**
	 * This method initializes clusteringGroup	
	 * @param runType 
	 *
	 */
	private void createClusteringGroup(int runType) {
		clusteringGroup = new Group(sShell, SWT.NONE);
		clusteringGroup.setLayout(null);
		clusteringGroup.setText("Clustering Type");
		clusteringGroup.setBounds(new Rectangle(2, 2, 155, 85));
		classicClustering = new Button(clusteringGroup, SWT.RADIO);
		classicClustering.setText("Hard Partitioning");
		classicClustering.setBounds(new Rectangle(8, 18, 122, 16));
		classicClustering
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(classicClustering.getSelection()) {
							group.setVisible(true);
							hierarchicalGroup.setVisible(false);
							softGroup.setVisible(false);
						}
					}
				});
		softClustering = new Button(clusteringGroup, SWT.RADIO);
		softClustering.setText("Soft/Fuzzy Clustering");
		softClustering.setBounds(new Rectangle(8, 39, 124, 16));
		softClustering
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(softClustering.getSelection()) {
							group.setVisible(false);
							hierarchicalGroup.setVisible(false);
							softGroup.setVisible(true);
						}
					}
				});
		hierarchicalClustering = new Button(clusteringGroup, SWT.RADIO);
		hierarchicalClustering.setText("Hierarchical Clustering");
		hierarchicalClustering.setBounds(new Rectangle(8, 60, 127, 16));
		hierarchicalClustering
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(hierarchicalClustering.getSelection()) {
							group.setVisible(false);
							hierarchicalGroup.setVisible(true);
							softGroup.setVisible(false);
						}
					}
				});
		switch(runType) {
		case 1:
			classicClustering.setSelection(true);
			group.setVisible(true);
			hierarchicalGroup.setVisible(false);
			softGroup.setVisible(false);
			break;
		case 2:
			softClustering.setSelection(true);
			group.setVisible(false);
			hierarchicalGroup.setVisible(false);
			softGroup.setVisible(true);
			break;
		case 3:
			hierarchicalClustering.setSelection(true);
			group.setVisible(false);
			hierarchicalGroup.setVisible(true);
			softGroup.setVisible(false);
			break;

		}
		
	}
	

}
