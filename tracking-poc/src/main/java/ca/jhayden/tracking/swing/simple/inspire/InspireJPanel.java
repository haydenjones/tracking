package ca.jhayden.tracking.swing.simple.inspire;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InspireJPanel extends JPanel {
	private static final long serialVersionUID = -6911407476671689823L;

	private final JLabel label = new JLabel("You have been inspired!");

	public InspireJPanel() {
		super();
		this.add(label);
	}
}
