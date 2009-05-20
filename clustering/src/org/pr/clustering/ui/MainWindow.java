package org.pr.clustering.ui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;

public class MainWindow {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Combo algorithmClassCombo = null;
	private Label label = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Shell");
		createAlgorithmClassCombo();
		sShell.setSize(new Point(759, 640));
		sShell.setLayout(null);
		label = new Label(sShell, SWT.NONE);
		label.setBounds(new Rectangle(8, 30, 84, 18));
		label.setText("Algorithm Class");
	}

	/**
	 * This method initializes algorithmClassCombo	
	 *
	 */
	private void createAlgorithmClassCombo() {
		algorithmClassCombo = new Combo(sShell, SWT.NONE);
		algorithmClassCombo.setBounds(new Rectangle(100, 29, 141, 21));
	}

}
