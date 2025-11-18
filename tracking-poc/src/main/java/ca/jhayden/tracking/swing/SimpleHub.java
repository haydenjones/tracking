package ca.jhayden.tracking.swing;

import java.util.Optional;

import org.jfree.chart.JFreeChart;

import ca.jhayden.tracking.boundary.ReportInfo;
import ca.jhayden.tracking.entity.Track;

public interface SimpleHub {
	public abstract void submit(Track track);

	public abstract Optional<Track> getLatest(String code);

	public abstract JFreeChart makeChart(ReportInfo report);
}
