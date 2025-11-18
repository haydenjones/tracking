package ca.jhayden.tracking.swing.simple;

import java.util.Optional;

import javax.swing.JFrame;

import org.jfree.chart.JFreeChart;

import ca.jhayden.tracking.boundary.ReportInfo;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.swing.SimpleHub;

public class SimpleTrackingJFrame extends JFrame implements SimpleHub {
	private static final long serialVersionUID = -6727999856815813793L;

	private final SimpleUiSetup setup;

	public SimpleTrackingJFrame(SimpleUiSetup setup) {
		super();
		this.setup = setup;
		SimpleMainJPanel main = new SimpleMainJPanel(this, setup);
		add(main);
	}

	@Override
	public void submit(Track track) {
		try {
			setup.ai().record(track);
			System.out.println("Recorded!");
		}
		catch (TrackException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Track> getLatest(String code) {
		return setup.ai().getLatest(code);
	}

	@Override
	public JFreeChart makeChart(ReportInfo report) {
		return setup.ai().makeReport(report);
	}
}
