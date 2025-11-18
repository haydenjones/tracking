package ca.jhayden.tracking.swing.simple.reports;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import ca.jhayden.tracking.swing.SimpleHub;

public class ReportsJPanel extends JPanel {
	public static ReportsJPanel getInstance(SimpleHub hub) {
		JFreeChart chart = hub.makeChart(null);
		return new ReportsJPanel(hub, chart);
	}

	private static final long serialVersionUID = 2334782439516846195L;

	private final SimpleHub hub;
	private final JFreeChart chart;

	private ReportsJPanel(SimpleHub hub, JFreeChart chart) {
		super();
		this.hub = hub;
		this.chart = chart;
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 400));
		this.add(chartPanel);
	}
}
