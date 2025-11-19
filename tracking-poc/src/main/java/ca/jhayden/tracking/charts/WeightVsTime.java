package ca.jhayden.tracking.charts;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import ca.jhayden.tracking.entity.Track;

public class WeightVsTime {
	private final List<Track> sorted = new ArrayList<>();

	int computeMax(double start, int increment, double minDelta) {
		int base = (int) start / increment;
		base = base * increment;
		while (base < start + minDelta) {
			base += increment;
		}
		return base;
	}

	double computeMin(double start, int increment, double minDelta) {
		int base = (int) start / increment;
		base = base * increment;
		while (base > start - minDelta) {
			base -= increment;
		}
		return base;
	}

	Range computeRange(MinMax minMax) {
		System.out.println(minMax);
		Range r = new Range(computeMin(minMax.min(), 5, 1f), computeMax(minMax.max(), 5, 1f));
		System.out.println(r);
		return r;
	}

	public WeightVsTime(List<Track> history) {
		super();
		sorted.addAll(history);
		sorted.sort(Track.SortBy.WHEN_ASC);
	}

	public JFreeChart perform() {
		// Prepared the Data
		final DefaultCategoryDataset data = new DefaultCategoryDataset();
		MinMax minMax = null;
		for (Track track : sorted) {
			if (track.getTrackingTypeCode().equals("WEIGHT_LBS")) {
				minMax = (minMax == null) ? new MinMax(track.getValue1(), track.getValue1())
						: minMax.with(track.getValue1());
				data.addValue(track.getValue1(), "", new DateLabel(track.getWhen().toLocalDate()));
			}
		}

		final Range r = computeRange(minMax);

		String title = "Weight Over Time";
		String categoryAxisLabel = "Date";
		String valueAxisLabel = "Weight";

		final JFreeChart chart = ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, data);
		CategoryPlot plot = chart.getCategoryPlot();
		ValueAxis va = plot.getRangeAxis();
		va.setRange(r);
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90); // Rotates labels 90 degrees upwards (vertical)
		return chart;
	}
}
