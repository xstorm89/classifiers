package org.pr.clustering.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MainWindow extends Composite {

	private Combo combo = null;
	private Label label = null;
	private Combo combo1 = null;
	private Label klabel = null;
	private Label label1 = null;
	private Label label2 = null;
	private Text text = null;
	private Text text1 = null;
	
	private Button button = null;
	private Text text2 = null;
	private Label label3 = null;
    
	private Shell shell = null;
	private Button goButton = null;
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
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog fd 
					= new FileDialog(shell, SWT.OPEN);
			    fd.setText("Open");
			    fd.setFilterPath("C:/");
			    String[] filterExt = {"*.txt","*.doc", ".rtf", "*.*"};
			    fd.setFilterExtensions(filterExt);
				text2.setText(fd.open());
			}
			
		});
		createCombo();
		setSize(new Point(810, 648));
		setLayout(null);
		label = new Label(this, SWT.NONE);
		label.setBounds(new Rectangle(8, 53, 67, 18));
		label.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label.setText("Algorithm");
		createCombo1();
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
		text = new Text(this, SWT.BORDER);
		text.setLocation(new Point(85, 126));
		text.setSize(new Point(62, 21));
		text1 = new Text(this, SWT.BORDER);
		text1.setLocation(new Point(85, 163));
		text1.setSize(new Point(62, 21));
		text2 = new Text(this, SWT.BORDER);
		text2.setLocation(new Point(7, 231));
		text2.setSize(new Point(249, 21));
		label3 = new Label(this, SWT.NONE);
		label3.setBounds(new Rectangle(8, 202, 85, 19));
		label3.setFont(new Font(Display.getDefault(), "Calibri", 12, SWT.NORMAL));
		label3.setText("Dataset File:");
		goButton = new Button(this, SWT.NONE);
		goButton.setBounds(new Rectangle(163, 268, 93, 29));
		goButton.setFont(new Font(Display.getDefault(), "Calibri", 14, SWT.NORMAL));
		goButton.setText("Go");
	}

	/**
	 * This method initializes combo	
	 *
	 */
	private void createCombo() {
		combo = new Combo(this, SWT.READ_ONLY);
		combo.setText("this");
		combo.setBounds(new Rectangle(85, 52, 171, 25));
		combo.setItems("KMeans,DHF,FHB,AFB,ABF,Hirarchical,    Single,    Complete,    UPGMA,    WGMA".split(","));
	}

	/**
	 * This method initializes combo1	
	 *
	 */
	private void createCombo1() {
		combo1 = new Combo(this, SWT.NONE);
		combo1.setBounds(new Rectangle(85, 89, 54, 20));
		combo1.setItems("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20".split(","));
	}

}  //  @jve:decl-index=0:visual-constraint="11,14"
