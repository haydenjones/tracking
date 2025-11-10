package ca.jhayden.tracking.swing.simple.reports;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.jhayden.tracking.swing.SimpleHub;

public class ReportsJPanel extends JPanel {
	private static final long serialVersionUID = 2334782439516846195L;

	private final SimpleHub hub;
	private final JLabel label = new JLabel("Reports");
	private final JComboBox cbReports = null;

	public ReportsJPanel(SimpleHub hub) {
		super();
		this.hub = hub;
		this.add(label);
	}
}
